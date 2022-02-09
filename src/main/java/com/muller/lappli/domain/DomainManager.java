package com.muller.lappli.domain;

import java.util.ArrayList;

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
     * @param mustMatchOne
     * @param booleans
     * @return
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
}
