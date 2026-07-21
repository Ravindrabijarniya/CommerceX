package com.ravindra.commercex.common.generator;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentNumberGenerator
    implements NumberGenerator {

    private static final String PREFIX = "PAY-";

    @Override
    public String generate() {

        return PREFIX +
            UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 12)
                .toUpperCase();
    }


}
