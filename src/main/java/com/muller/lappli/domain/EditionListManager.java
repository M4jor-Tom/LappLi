package com.muller.lappli.domain;

import com.muller.lappli.domain.abstracts.AbstractEdition;
import com.muller.lappli.domain.interfaces.Commitable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class wraps a List of AbstractEdition instances
 */
public class EditionListManager<C extends Commitable<C>> {

    private List<AbstractEdition<C>> editionList;

    public EditionListManager() {
        this(new ArrayList<>());
    }

    public EditionListManager(List<AbstractEdition<C>> editionList) {
        super();
        setEditionList(editionList);
    }

    public List<AbstractEdition<C>> getEditionList() {
        return editionList;
    }

    public void setEditionList(List<AbstractEdition<C>> editionList) {
        this.editionList = editionList;
    }
}
