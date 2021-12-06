package com.muller.lappli.domain.interfaces;

import com.muller.lappli.domain.EditionListManager;
import com.muller.lappli.domain.abstracts.AbstractEdition;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * This interface uses default implementation depending of an EditionListManager
 *
 * It makes a domain class able to rewind its modifications through an historic
 *
 * The type parameter C MUST be the the implementation class
 *
 */
public interface Commitable<C extends Commitable<C>> {
    public EditionListManager<C> getEditionListManager();

    /**
     * Gets each edition which is dated before a given instant
     *
     * @param instant the instant before which modification shall be
     * @return a List of AbstractEdition instances for a given instant
     */
    public default List<AbstractEdition<C>> getEditionListTill(Instant instant) {
        ArrayList<AbstractEdition<C>> editionArrayList = new ArrayList<AbstractEdition<C>>();

        for (AbstractEdition<C> edition : getEditionListManager().getEditionList()) {
            if (edition.getEditionInstant().isBefore(instant)) {
                editionArrayList.add(edition);
            }
        }

        return editionArrayList;
    }

    /**
     * Gets an altered version of a Commitable object at a given instant
     *
     * @param commitable the receiver of editions, surely this or this.clone()
     * @param instant the instant before which modifications shall be applied
     * @return an edited version of commitable parameter
     */
    public default C getAtInstant(C commitable, Instant instant) {
        for (AbstractEdition<C> edition : getEditionListTill(instant)) {
            edition.update(commitable);
        }

        return commitable;
    }
}
