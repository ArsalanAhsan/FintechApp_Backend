package com.iconsult.utility;

import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.Random;

@Component
public class Util {

    public static String generateOtp() {
        return new DecimalFormat("000000")
                .format(new Random().nextInt(999999));
    }

}
