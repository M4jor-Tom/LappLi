package com.muller.lappli.domain;

import com.muller.lappli.domain.interfaces.Commitable;
import com.muller.lappli.domain.interfaces.IEdition;
import java.util.List;

/**
 * This class wraps a List of IEdition instances
 */
public class EditionListManager<C extends Commitable<C>> {

    private List<IEdition<C>> editionList;

    public EditionListManager(List<IEdition<C>> editionList) {
        setEditionList(editionList);
    }

    public List<IEdition<C>> getEditionList() {
        return editionList;
    }

    public void setEditionList(List<IEdition<C>> editionList) {
        this.editionList = editionList;
    }
}
