package com.ravindra.commercex.order.service;

import com.ravindra.commercex.address.entity.Address;
import com.ravindra.commercex.address.validator.AddressBusinessValidator;
import com.ravindra.commercex.auth.entity.User;
import com.ravindra.commercex.cart.entity.Cart;
import com.ravindra.commercex.cart.entity.CartItem;
import com.ravindra.commercex.cart.exception.EmptyCartException;
import com.ravindra.commercex.cart.validation.CartBusinessValidator;
import com.ravindra.commercex.inventory.validation.InventoryBusinessValidator;
import com.ravindra.commercex.order.dto.request.CreateOrderRequest;
import com.ravindra.commercex.order.dto.response.OrderResponse;
import com.ravindra.commercex.order.dto.response.OrderSummaryResponse;
import com.ravindra.commercex.order.entity.Order;
import com.ravindra.commercex.order.entity.OrderItem;
import com.ravindra.commercex.order.entity.OrderPricing;
import com.ravindra.commercex.order.entity.ShippingAddress;
import com.ravindra.commercex.order.mapper.OrderMapper;
import com.ravindra.commercex.order.repository.OrderRepository;
import com.ravindra.commercex.order.validator.OrderBusinessValidator;
import com.ravindra.commercex.security.service.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderBusinessValidator orderBusinessValidator;
    private final OrderMapper orderMapper;

    private final CurrentUserService currentUserService;

    private final CartBusinessValidator cartBusinessValidator;
    private final AddressBusinessValidator addressBusinessValidator;
    private final InventoryBusinessValidator inventoryBusinessValidator;

    private final OrderNumberGenerator orderNumberGenerator;

    @Override
    public OrderResponse createOrder(CreateOrderRequest request) {

        User user = currentUserService.getCurrentUser();

        Cart cart = cartBusinessValidator.getCart(user);

        validateCart(cart);

        Address address = addressBusinessValidator.getAddress(
            request.shippingAddressId(),
            user
        );

        inventoryBusinessValidator.validateCartInventory(cart);

        Order order = buildOrder(user, address, cart, cart.getTotalItems());

        orderRepository.save(order);

        clearCart(cart);

        return orderMapper.toResponse(order);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderSummaryResponse> getOrders() {

        User user = currentUserService.getCurrentUser();

        return orderBusinessValidator.getOrders(user)
            .stream()
            .map(orderMapper::toSummaryResponse)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrder(Long id) {

        User user = currentUserService.getCurrentUser();

        Order order = orderBusinessValidator.getOrder(id, user);

        return orderMapper.toResponse(order);
    }

    @Override
    public void cancelOrder(Long id) {

        User user = currentUserService.getCurrentUser();

        Order order = orderBusinessValidator.getOrder(id, user);

        order.cancel();
    }



    private void validateCart(Cart cart) {

        if (cart.isEmpty()) {
            throw new EmptyCartException();
        }
    }

    private Order buildOrder(
        User user,
        Address address,
        Cart cart,
        Integer totalItems
    ) {

        ShippingAddress shippingAddress = createShippingSnapshot(address);

        OrderPricing pricing = createPricingSnapshot(cart);

        Order order = Order.create(
            orderNumberGenerator.generate(),
            user,
            shippingAddress,
            pricing,
            cart.getTotalItems()
        );

        order.addItems(createOrderItems(cart));

        return order;
    }

    private ShippingAddress createShippingSnapshot(Address address) {

        return ShippingAddress.of(
            address.getFullName(),
            address.getPhoneNumber(),
            address.getAddressLine1(),
            address.getAddressLine2(),
            address.getCity(),
            address.getState(),
            address.getPostalCode(),
            address.getCountry()
        );
    }

    private OrderPricing createPricingSnapshot(Cart cart) {

        BigDecimal subtotal = cart.calculateSubtotal();

        BigDecimal shippingCharge = BigDecimal.ZERO;
        BigDecimal discount = BigDecimal.ZERO;
        BigDecimal tax = BigDecimal.ZERO;

        BigDecimal grandTotal = subtotal
            .add(shippingCharge)
            .add(tax)
            .subtract(discount);

        return OrderPricing.of(
            subtotal,
            shippingCharge,
            discount,
            tax,
            grandTotal
        );
    }

    private List<OrderItem> createOrderItems(Cart cart) {

        return cart.getItems()
            .stream()
            .map(this::createOrderItem)
            .toList();
    }

    private OrderItem createOrderItem(CartItem cartItem) {

        return OrderItem.create(
            cartItem.getProduct().getId(),
            cartItem.getProduct().getName(),
            cartItem.getProduct().getSlug(),
            cartItem.getUnitPrice(),
            cartItem.getQuantity()
        );
    }

    private void clearCart(Cart cart) {

        cart.clear();
    }

}
