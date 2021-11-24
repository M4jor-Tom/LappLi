package com.muller.lappli.domain;

import javax.validation.constraints.NotNull;

public class Dimension {

    private double value;
    private String unityText;

    public Dimension(double value, String unityText) {
        setValue(value);
        setUnityText(unityText);
    }

    public void setValue(@NotNull double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setUnityText(@NotNull String unityText) {
        this.unityText = unityText;
    }

    public String getUnityText() {
        return unityText;
    }
}
