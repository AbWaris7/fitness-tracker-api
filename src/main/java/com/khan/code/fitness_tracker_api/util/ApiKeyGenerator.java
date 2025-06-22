package com.khan.code.fitness_tracker_api.util;

import java.math.BigInteger;
import java.security.SecureRandom;

public class ApiKeyGenerator {
    public static String generateApiKey() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        return new BigInteger(1, bytes).toString(16);
    }
}
