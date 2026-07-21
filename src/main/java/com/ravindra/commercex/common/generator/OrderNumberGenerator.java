//package com.ravindra.commercex.common.generator;
//
//import org.springframework.stereotype.Component;
//
//import java.util.UUID;
//
//@Component
//public class OrderNumberGenerator implements NumberGenerator {
//
//    private static final String PREFIX = "ORD-";
//
//
//    @Override
//    public String generate() {
//
//        return prefix()
//            + UUID.randomUUID()
//            .toString()
//            .replace("-", "")
//            .substring(0, 12)
//            .toUpperCase();
//    }
//}
