package edu.vsp.util;

import java.util.List;
import java.util.regex.Pattern;

public class ValidatorUtil {

    public static boolean matches(String value, String regex) {
        return Pattern
                .compile(regex)
                .matcher(value)
                .matches();
    }

    /**
     * Checks whether the elements in the list matches the specified matcher's pattern
     *
     * @param list  list of String
     * @param regex matcher's pattern
     * @return true if each string in list matches passed matcher's pattern
     */
    public static boolean listMatches(List<String> list, String regex) {
        return list.stream()
                .allMatch(line -> matches(line, regex));
    }

    public static <T> boolean isListSizeValid(List<T> list, int min, int max) {
        return list.size() >= min && list.size() <= max;
    }
}
