package com.ravindra.commercex.order.dto.response;

import java.math.BigDecimal;

public record OrderPricingResponse(

    BigDecimal subtotal,

    BigDecimal shippingCharge,

    BigDecimal discount,

    BigDecimal tax,

    BigDecimal grandTotal

) {
}
