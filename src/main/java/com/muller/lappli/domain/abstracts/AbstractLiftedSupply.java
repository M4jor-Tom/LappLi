package com.muller.lappli.domain.abstracts;

import com.muller.lappli.domain.Lifter;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * An abstract mother class for each Supply class that has
 * a Lifting operation as a supply operation
 */
@MappedSuperclass
public abstract class AbstractLiftedSupply extends AbstractSupply {

    @Transient
    private List<Lifter> bestLifterList;

    public AbstractLiftedSupply() {
        this(new ArrayList<>());
    }

    public AbstractLiftedSupply(List<Lifter> bestLifterList) {
        setBestLifterList(bestLifterList);
    }

    /**
     * Gets a list of each Lifter names for getBestLifterList()
     *
     * @return a List of Lifter names
     */
    public String getBestLiftersNames() {
        if (getBestLifterList() == null) {
            return "";
        }

        String names = "";

        for (Lifter lifter : getBestLifterList()) {
            names = names + lifter.getName() + " ";
        }

        return names;
    }

    /**
     * Gets a list of each Lifter that have the same, best
     * set of statistics for the current supply
     *
     * @return a List of Lifter
     */
    public List<Lifter> getBestLifterList() {
        return bestLifterList;
    }

    /**
     * This method shall only be called for setting bestLifterList by
     * a service that is capable of determining it
     *
     * @param bestLifterList
     */
    public void setBestLifterList(List<Lifter> bestLifterList) {
        this.bestLifterList = bestLifterList;
    }
}
