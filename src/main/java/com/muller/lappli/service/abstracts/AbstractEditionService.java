package com.muller.lappli.service.abstracts;

import com.muller.lappli.domain.EditionListManager;
import com.muller.lappli.domain.abstracts.AbstractEdition;
import com.muller.lappli.domain.interfaces.Commitable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class AbstractEditionService<C extends Commitable<C>> {

    /**
     * Shall return findAll() in subclasses
     *
     * For that, at implementation, loop across editions and add them
     * to a new list one by one
     */
    public abstract List<AbstractEdition<C>> tooAbstractFindAll();

    /**
     * Finds AbstractEditions corresponding to Commitable object,
     * then sets its EditionListManager with them
     *
     * @param commitable the commitable to set its EditionListManager
     */
    public void setEditionListManagerTo(C commitable) {
        EditionListManager<C> editionListManager = new EditionListManager<C>(new ArrayList<>());

        for (AbstractEdition<C> abstractEdition : findAllSortedFor(commitable)) {
            editionListManager.getEditionList().add(abstractEdition);
        }

        commitable.setEditionListManager(editionListManager);
    }

    /**
     * Like findAllSorted() but only AbstractEditions corresponding to commitable
     *
     * @param commitable the one to give editions to
     * @return
     */
    public List<AbstractEdition<C>> findAllSortedFor(C commitable) {
        ArrayList<AbstractEdition<C>> abstractEditionArrayList = new ArrayList<AbstractEdition<C>>();

        for (AbstractEdition<C> abstractEdition : findAllSorted()) {
            if (abstractEdition.getEditedCommitable().getId().equals(commitable.getId())) {
                abstractEditionArrayList.add(abstractEdition);
            }
        }

        return abstractEditionArrayList;
    }

    /**
     * Like findAll() but sorted with getComparator()
     *
     * @return sorted abstractEditionList
     */
    private List<AbstractEdition<C>> findAllSorted() {
        List<AbstractEdition<C>> abstractEditionList = tooAbstractFindAll();
        abstractEditionList.sort(getComparator());
        return abstractEditionList;
    }

    /**
     * @return a Comparator anonym class used to sort in findAllSorted()
     */
    private Comparator<AbstractEdition<C>> getComparator() {
        return new Comparator<AbstractEdition<C>>() {
            @Override
            public int compare(AbstractEdition<C> o1, AbstractEdition<C> o2) {
                if (o1.getEditionInstant().isBefore(o2.getEditionInstant())) {
                    return 1;
                } else if (o1.getEditionInstant().equals(o2.getEditionInstant())) {
                    return 0;
                }

                return -1;
            }
        };
    }

    /**
     * Gets an updated version of commitable instance
     *
     * @param commitable the commitable to edit
     * @return commitable once edited
     */
    public C update(C commitable) {
        for (AbstractEdition<C> abstractEdition : findAllSortedFor(commitable)) {
            commitable = abstractEdition.update(commitable);
        }

        return commitable;
    }

    /**
     * updates commitableList's members if possible
     *
     * @param commitableList to be fetched for members to edit
     * @return updated commitableList
     */
    public List<C> update(List<C> commitableList) {
        for (C commitable : commitableList) {
            commitable = update(commitable);
        }

        return commitableList;
    }
}
