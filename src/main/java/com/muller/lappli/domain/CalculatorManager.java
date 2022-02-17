package com.muller.lappli.domain;

import com.muller.lappli.domain.interfaces.ICalculator;

public class CalculatorManager {

    private static ICalculator calculatorInstance = null;

    public static ICalculator getCalculatorInstance() {
        if (calculatorInstance == null) {
            calculatorInstance = ICalculator.getNewInstance();
        }

        return calculatorInstance;
    }
}
