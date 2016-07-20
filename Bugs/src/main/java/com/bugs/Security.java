//package com.bugs;

/**
 * Created by Piyush on 7/20/16.
 */

import java.io.IOException;
import java.util.Random;

/**
 * Rule: squid:S2245
 *  When software generates predictable values in a context requiring unpredictability, it may be possible
 *  for attacker to derive the values. For example java.util.Random is not random, and follows a pattern
 *  which should not be used for generating unpredictable values. Instead use SecureRandom which is in java.security
 */

class GenerateBug {
    private int password;

    int generateFourDigitPassowrd()
    {
        Random random = new Random(); // Random shouldn't be used for generating password. Instead use secureRandom
        this.password = 1000 + (int)(Math.random() * 9999);
        return this.password;
    }
}

public class Security
{
    public static void  main(String args[])throws IOException
    {
        GenerateBug obj = new GenerateBug();
        int password = obj.generateFourDigitPassowrd();
        System.out.println(password);
    }
}
