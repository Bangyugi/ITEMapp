package com.bangvan.EMwebapp.util;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class OTPutil {
    public String generateOTP() {
        Random rand = new Random();
        int number = rand.nextInt(999999) ;
        String output = Integer.toString(number);
        while (output.length() < 6) {
            output = "0" + output;
        }
        return output;
    }
}
