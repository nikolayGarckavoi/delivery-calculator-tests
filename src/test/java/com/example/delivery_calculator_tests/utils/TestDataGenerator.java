package com.example.delivery_calculator_tests.utils;

public class TestDataGenerator {
    public static double randomWeight() {
        return Math.round((0.1 + Math.random() * 999.9) * 10.0) / 10.0;
    }
}