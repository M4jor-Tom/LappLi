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
}
