package com.muller.lappli.domain;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DomainManager {

    public static final DecimalFormat JACQUES_DOUBLE_DECIMAL_FORMAT = new DecimalFormat("#.##");

    /**
     * When displaying hours,
     *
     * this will be the post-comma count of decimals
     */
    public static final Long HOUR_DECIMAL_COUNT = Long.valueOf(2);

    /**
     * When returning Long and supposedly positive values,
     *
     * this returned will signal that an error has occured
     */
    public static final Long ERROR_LONG_POSITIVE_VALUE = null;

    /**
     * A String to be used for prompting to help noticing
     */
    public static final String noticeBar = "\n\n=============\n\n";

    /**
     * @param toNotice the string to be returned surrounded
     * by {@link DomainManager#noticeBar}
     * @return the surrounded String
     */
    public static String noticeString(String toNotice) {
        return noticeBar + toNotice + noticeBar;
    }

    /**
     * Like println(), but prints with
     *
     * {@link #noticeString}
     *
     * @param toNotice The String which will be prompted in a noticeable way
     */
    public static void noticeInPrompt(String toNotice) {
        System.out.println(noticeString(toNotice));
    }

    /**
     * @param booleans the booleans to count true iterations
     * @return the count of true iterations
     */
    public static final Long trueCount(Boolean... booleans) {
        Long count = Long.valueOf(0);

        for (boolean bool : booleans) {
            if (bool) {
                count++;
            }
        }

        return count;
    }

    /**
     * @param mustMatch the iterations count to match
     * @param booleans the booleans to count true iterations
     * @return true if iteration count matches mustMatch
     */
    public static final Boolean trueCountIs(Long mustMatch, Boolean... booleans) {
        return trueCount(booleans).equals(Long.valueOf(mustMatch));
    }

    /**
     * Like {@link DomainManager#trueCountIs(Long, Boolean...)} with several match values possible
     *
     * @param mustMatchOne the true booleans count which makes the method return true;
     * @param booleans the booleans to get counted if true
     * @return true if the booleans count is one of the possibilities listed in mustMatchOne
     */
    public static final Boolean trueCountIsIn(Iterable<Long> mustMatchOne, Boolean... booleans) {
        for (Long mustMatch : mustMatchOne) {
            if (trueCountIs(mustMatch, booleans)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Like {@link DomainManager#trueCountIs(Long, Boolean...)} with Long.valueOf(1) as mustMatch
     *
     * @param booleans the booleans to count true iterations
     * @return true if iteration count matches 1
     */
    public static final Boolean trueCountIs0Or1(Boolean... booleans) {
        ArrayList<Long> zeroOrOneList = new ArrayList<Long>();
        zeroOrOneList.add(Long.valueOf(0));
        zeroOrOneList.add(Long.valueOf(1));
        return trueCountIsIn(zeroOrOneList, booleans);
    }

    /**
     * Prints a value as a {@link String}, as Muller
     * standardizes it
     *
     * @param value the value to be formatted
     * @return the String formated value
     */
    public static final String mullerStandardizedFormat(Double value) {
        return JACQUES_DOUBLE_DECIMAL_FORMAT.format(value);
    }
}
