package com.muller.lappli.domain;

public class DomainManager {

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
    public static final Long ERROR_LONG_POSITIVE_VALUE = Long.valueOf(-1);

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
     * Like {@link System.out.println()}, but prints with
     *
     * {@link DomainManager#noticeString()}
     */
    public static void noticeInPrompt(String toNotice) {
        System.out.println(noticeString(toNotice));
    }
}
