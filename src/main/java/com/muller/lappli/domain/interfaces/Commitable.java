package com.muller.lappli.domain.interfaces;

import com.muller.lappli.domain.EditionListManager;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public interface Commitable<C extends Commitable<C>> {
    public EditionListManager<C> getEditionListManager();

    public default List<IEdition<C>> getEditionListTill(Instant instant) {
        ArrayList<IEdition<C>> editionArrayList = new ArrayList<IEdition<C>>();

        for (IEdition<C> edition : getEditionListManager().getEditionList()) {
            if (edition.getEditionInstant().isBefore(instant)) {
                editionArrayList.add(edition);
            }
        }

        return editionArrayList;
    }

    public default C getAtInstant(C commitable, Instant instant) {
        for (IEdition<C> edition : getEditionListTill(instant)) {
            edition.update(commitable);
        }

        return commitable;
    }
}
