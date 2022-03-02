package com.muller.lappli.domain;

import com.muller.lappli.web.rest.TestUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CalculatorTest {

    private final Logger logger = LoggerFactory.getLogger(CalculatorTest.class);

    @Test
    void verifyCalculator() throws Exception {
        TestUtil.equalsVerifier(CalculatorEmptyImpl.class);

        if (CalculatorManager.getCalculatorInstance().isTargetCalculatorInstance()) {
            TestUtil.equalsVerifier(CalculatorManager.getCalculatorInstance().getClass());
        } else {
            logger.warn("Target class not found, using empty implementation for tests instead");
        }
    }
}
