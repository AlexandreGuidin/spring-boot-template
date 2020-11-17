package org.template.util;

import java.text.Normalizer;

import static java.util.Optional.ofNullable;

public class StringUtils extends org.apache.commons.lang3.StringUtils {

    private static String onlyAlphaNumeric(String input) {
        if (input == null) return null;
        return input.replaceAll("[^a-zA-Z0-9]+", "");
    }

    public static String normalize(String nome) {
        return ofNullable(nome)
                .map(s -> Normalizer.normalize(s, Normalizer.Form.NFD))
                .map(s -> s.replaceAll("[^\\p{ASCII}]", ""))
                .map(s -> s.replace(" ", ""))
                .map(StringUtils::lowerCase)
                .map(StringUtils::onlyAlphaNumeric)
                .orElse("");
    }

    public static String removeNullCharacters(String content) {
        return content.replace("\u0000", "");
    }
}
