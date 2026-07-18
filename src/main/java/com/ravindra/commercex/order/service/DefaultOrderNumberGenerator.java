package com.ravindra.commercex.order.service;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
public class DefaultOrderNumberGenerator implements OrderNumberGenerator {

    private static final DateTimeFormatter FORMATTER =
        DateTimeFormatter.BASIC_ISO_DATE;

    @Override
    public String generate() {

        String date = LocalDate.now().format(FORMATTER);

        String suffix = UUID.randomUUID()
            .toString()
            .replace("-", "")
            .substring(0, 8)
            .toUpperCase();

        return "ORD-" + date + "-" + suffix;
    }

}
