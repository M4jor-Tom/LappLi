package com.muller.lappli.domain.abstracts;

import com.muller.lappli.domain.Lifter;
import java.util.List;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public abstract class AbstractLiftedSupply extends AbstractSupply {

    @Transient
    private List<Lifter> bestLifterList;

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

    public List<Lifter> getBestLifterList() {
        return bestLifterList;
    }

    public void setBestLifterList(List<Lifter> bestLifterList) {
        this.bestLifterList = bestLifterList;
    }
}
