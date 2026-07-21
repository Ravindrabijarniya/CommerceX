package com.ravindra.commercex.payment.service;


import com.ravindra.commercex.auth.entity.User;
import com.ravindra.commercex.common.generator.PaymentNumberGenerator;
import com.ravindra.commercex.order.entity.Order;
import com.ravindra.commercex.payment.dto.request.CreatePaymentRequest;
import com.ravindra.commercex.payment.dto.response.PaymentResponse;
import com.ravindra.commercex.payment.entity.Payment;
import com.ravindra.commercex.payment.entity.PaymentEvent;
import com.ravindra.commercex.payment.entity.PaymentTransaction;
import com.ravindra.commercex.payment.enums.PaymentEventType;
import com.ravindra.commercex.payment.mapper.PaymentMapper;
import com.ravindra.commercex.payment.provider.PaymentProvider;
import com.ravindra.commercex.payment.provider.PaymentProviderFactory;
import com.ravindra.commercex.payment.provider.dto.PaymentInitiationResponse;
import com.ravindra.commercex.payment.repository.PaymentRepository;
import com.ravindra.commercex.payment.validator.PaymentBusinessValidator;
import com.ravindra.commercex.security.service.CurrentUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {


    private final PaymentRepository paymentRepository;


    private final PaymentBusinessValidator validator;


    private final PaymentProviderFactory providerFactory;


    private final PaymentMapper paymentMapper;

    private final PaymentNumberGenerator paymentNumberGenerator;

    private final CurrentUserService currentUserService;



    public PaymentResponse createPayment(
        CreatePaymentRequest request,
        String idempotencyKey
    ){

        User user = currentUserService.getCurrentUser();

        Payment existingPayment =
            paymentRepository
                .findByIdempotencyKey(idempotencyKey)
                .orElse(null);

        if (existingPayment != null) {
            return paymentMapper.toResponse(existingPayment);
        }


        Order order =
            validator.validatePaymentCreation(
                request.orderId(),
                user
            );



        Payment payment =
            Payment.create(

                paymentNumberGenerator.generate(),

                order,

                order.getPricing()
                    .getGrandTotal(),

                "INR",

                request.provider(),

                request.paymentMethod(),

                idempotencyKey
            );



        PaymentProvider provider =
            providerFactory.getProvider(
                request.provider()
            );



        payment.markInitiated();



        PaymentInitiationResponse response =
            provider.initiate(payment);



        PaymentTransaction transaction =
            PaymentTransaction.create(1);



        transaction.markSuccess(
            response.providerReference()
        );



        payment.addTransaction(transaction);



        payment.addEvent(

            PaymentEvent.create(

                PaymentEventType.PAYMENT_INITIATED,

                "Payment initiated",

                null
            )
        );



        paymentRepository.save(payment);



        return paymentMapper.toResponse(payment);
    }


}
