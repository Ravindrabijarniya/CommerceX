package com.ravindra.commercex.admin.service;


import com.ravindra.commercex.order.dto.response.OrderResponse;
import com.ravindra.commercex.order.dto.response.OrderSummaryResponse;
import com.ravindra.commercex.order.entity.Order;
import com.ravindra.commercex.order.mapper.OrderMapper;
import com.ravindra.commercex.order.repository.OrderRepository;
import com.ravindra.commercex.order.validator.OrderBusinessValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@RequiredArgsConstructor
@Transactional
public class AdminOrderServiceImpl
    implements AdminOrderService {


    private final OrderRepository orderRepository;

    private final OrderBusinessValidator validator;

    private final OrderMapper orderMapper;



    @Override
    @Transactional(readOnly = true)
    public Page<OrderSummaryResponse> getOrders(
        Pageable pageable
    ){

        return orderRepository
            .findAll(pageable)
            .map(orderMapper::toSummaryResponse);

    }



    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrder(
        Long id
    ){

        Order order =
            validator.getOrder(id);


        return orderMapper.toResponse(order);

    }




    @Override
    public void markProcessing(
        Long id
    ){

        Order order =
            validator.getOrder(id);


        order.startProcessing();

    }



    @Override
    public void markShipped(
        Long id
    ){

        Order order =
            validator.getOrder(id);


        order.markAsShipped();

    }




    @Override
    public void markDelivered(
        Long id
    ){

        Order order =
            validator.getOrder(id);


        order.markAsDelivered();

    }




    @Override
    public void cancelOrder(
        Long id
    ){

        Order order =
            validator.getOrder(id);


        order.cancel();

    }

}
