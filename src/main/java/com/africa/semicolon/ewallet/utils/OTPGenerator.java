package com.africa.semicolon.ewallet.utils;

import java.util.Random;

public class OTPGenerator {
    public static StringBuilder generateOTP(){
        String num = "0123456789";
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 4; i++) {
            stringBuilder.append(num.charAt(random.nextInt(10)));
        }
        return stringBuilder;
    }
}
