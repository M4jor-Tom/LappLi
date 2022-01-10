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
public abstract class AbstractLiftedSupply<T> extends AbstractSupply<T> {

    @Transient
    private List<Lifter> bestLifterList;

    /**
     * Default Constructor for AbstractLiftedSupply
     *
     * Sets bestLifterList as an empty ArrayList
     */
    public AbstractLiftedSupply() {
        this(new ArrayList<>());
    }

    /**
     * Full Field Constructor for AbstractLiftedSupply
     *
     * @param bestLifterList the bestLifterList to set
     */
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

    /**
     * Chained setter of AbstractLiftedSupply.bestLifterList
     *
     * @param bestLifterList to set
     * @return this
     */
    public T bestLifterList(List<Lifter> bestLifterList) {
        setBestLifterList(bestLifterList);

        return getThis();
    }
}
