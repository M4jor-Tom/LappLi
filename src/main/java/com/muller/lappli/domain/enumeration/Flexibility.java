package com.muller.lappli.domain.enumeration;

/**
 * The Flexibility enumeration.
 */
public enum Flexibility {
    S("S", true),
    ES("ES", true),
    SES("SES", true),
    MB("MB", true),
    UES("UES", true),
    USES("USES", false),
    DS("DS", true),
    R("", true),
    SS("SS", false),
    L5("L5", false),
    L6("L6", false);

    private String designation;

    private Boolean proposed;

    private Flexibility(String designation, Boolean proposed) {
        setDesignation(designation);
        setProposed(proposed);
    }

    public String getDesignation() {
        return designation;
    }

    private void setDesignation(String designation) {
        this.designation = designation;
    }

    public Boolean isProposed() {
        return proposed;
    }

    private void setProposed(Boolean proposed) {
        this.proposed = proposed;
    }
}
