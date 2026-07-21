package com.ravindra.commercex.payment.mapper;

import com.ravindra.commercex.payment.dto.response.PaymentResponse;
import com.ravindra.commercex.payment.dto.response.PaymentTransactionResponse;
import com.ravindra.commercex.payment.entity.Payment;
import com.ravindra.commercex.payment.entity.PaymentTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(target = "orderId", source = "order.id")
    PaymentResponse toResponse(Payment payment);

    PaymentTransactionResponse toResponse(
        PaymentTransaction transaction
    );

}
