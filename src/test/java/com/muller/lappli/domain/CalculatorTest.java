package com.muller.lappli.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.muller.lappli.domain.interfaces.ICalculator;
import com.muller.lappli.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class CalculatorTest {

    @Test
    void verifyCalculatorInstance() throws Exception {
        TestUtil.equalsVerifier(ICalculator.getInstance().getClass());

        assertThat(ICalculator.getInstance().isTargetCalculatorInstance()).isTrue();
    }
}
