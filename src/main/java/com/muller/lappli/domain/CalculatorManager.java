package com.muller.lappli.domain;

import com.muller.lappli.domain.interfaces.ICalculator;

/**
 * A maker for {@link com.muller.lappli.domain.interfaces.ICalculator}
 */
public class CalculatorManager {

    /**
     * The name of the calculator class which is secret and
     * not pushed to GitHub to make specific calculations
     * about Cables
     */
    private static final String TARGET_CALCULATOR_INSTANCE_CLASS_NAME = "com.muller.lappli.domain.CalculatorMullerSecretImpl";

    private static ICalculator calculatorInstance = null;

    /**
     * @return an instance implementing ICalculator
     */
    public static ICalculator getCalculatorInstance() {
        if (calculatorInstance == null) {
            calculatorInstance = ICalculator.getNewInstance(TARGET_CALCULATOR_INSTANCE_CLASS_NAME, new CalculatorEmptyImpl());
        }

        return calculatorInstance;
    }
}
