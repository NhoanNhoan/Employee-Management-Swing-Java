/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handler;

import java.util.Random;

/**
 *
 * @author LENOVO IDEAPAD
 */
public class TableHandlerUtility {
    private TableHandlerUtility() {}
    
    public static String generateRandomId(String prefix, int length) {
        final String alpha = "abcdefghijklmnopqrstuvwxyz"; // a-z
        final String alphaUpperCase = alpha.toUpperCase(); // A-Z
        final String digits = "0123456789"; // 0-9
        final String specials = "~=+%^*/()[]{}/!@#$?|";
        final String ALPHA_NUMERIC = alpha + alphaUpperCase + digits;
        final String ALL = alpha + alphaUpperCase + digits;
        Random random = new Random();
        StringBuilder id = new StringBuilder();

        id.append(prefix);
        for (int i = 0; i < length - prefix.length(); i++) {
            char randomLetter = ALL.charAt(random.nextInt(ALL.length()));
            id.append(randomLetter);
        }

        return id.toString();
    }
}
