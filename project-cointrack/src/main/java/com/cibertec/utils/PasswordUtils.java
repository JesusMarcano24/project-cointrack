package com.cibertec.utils;

import org.apache.commons.text.RandomStringGenerator;

public class PasswordUtils {

    public static String generarPasswordAleatoria(int longitud) {
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange('0', 'z')
                .filteredBy(Character::isLetterOrDigit)
                .build();

        return generator.generate(longitud);
    }
}
