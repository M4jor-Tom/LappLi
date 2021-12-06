package com.muller.lappli.service;

import com.muller.lappli.domain.ElementKind;
import com.muller.lappli.domain.ElementKindEdition;
import com.muller.lappli.repository.ElementKindEditionRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ElementKindEdition}.
 */
@Service
@Transactional
public class ElementKindEditionService {

    private final Logger log = LoggerFactory.getLogger(ElementKindEditionService.class);

    private final ElementKindEditionRepository elementKindEditionRepository;

    public ElementKindEditionService(ElementKindEditionRepository elementKindEditionRepository) {
        this.elementKindEditionRepository = elementKindEditionRepository;
    }

    /**
     * @return a Comparator anonym class used to sort in findAllSorted()
     */
    private Comparator<ElementKindEdition> getComparator() {
        return new Comparator<ElementKindEdition>() {
            @Override
            public int compare(ElementKindEdition o1, ElementKindEdition o2) {
                if (o1.getEditionDateTime().isBefore(o2.getEditionDateTime())) {
                    return 1;
                } else if (o1.getEditionDateTime().equals(o2.getEditionDateTime())) {
                    return 0;
                }

                return -1;
            }
        };
    }

    /**
     * updates elementKindList's members if possible
     *
     * @param elementKindList to be fetched for members to edit
     * @return updated elementKindList
     */
    public List<ElementKind> update(List<ElementKind> elementKindList) {
        for (ElementKind elementKind : elementKindList) {
            elementKind = update(elementKind);
        }

        return elementKindList;
    }

    /**
     * Gets an updated version of elementKind
     *
     * @param elementKind the elementKind to edit
     * @return elementKind once edited
     */
    public ElementKind update(ElementKind elementKind) {
        for (ElementKindEdition elementKindEdition : findAllSortedFor(elementKind)) {
            elementKind = elementKindEdition.update(elementKind);
        }

        return elementKind;
    }

    /**
     * Like findAllSorted() but only ElementKindEditions corresponding to elementKind
     *
     * @param elementKind the one to give editions to
     * @return
     */
    public List<ElementKindEdition> findAllSortedFor(ElementKind elementKind) {
        ArrayList<ElementKindEdition> elementKindEditionArrayList = new ArrayList<ElementKindEdition>();

        for (ElementKindEdition elementKindEdition : findAllSorted()) {
            if (elementKindEdition.getEditedElementKind().getId().equals(elementKind.getId())) {
                elementKindEditionArrayList.add(elementKindEdition);
            }
        }

        return elementKindEditionArrayList;
    }

    /**
     * Like findAll() but sorted with getComparator()
     *
     * @return sorted elementKindEditionList
     */
    private List<ElementKindEdition> findAllSorted() {
        List<ElementKindEdition> elementKindEditionList = findAll();
        elementKindEditionList.sort(getComparator());
        return elementKindEditionList;
    }

    /**
     * Save a elementKindEdition.
     *
     * @param elementKindEdition the entity to save.
     * @return the persisted entity.
     */
    public ElementKindEdition save(ElementKindEdition elementKindEdition) {
        log.debug("Request to save ElementKindEdition : {}", elementKindEdition);
        return elementKindEditionRepository.save(elementKindEdition);
    }

    /**
     * Partially update a elementKindEdition.
     *
     * @param elementKindEdition the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ElementKindEdition> partialUpdate(ElementKindEdition elementKindEdition) {
        log.debug("Request to partially update ElementKindEdition : {}", elementKindEdition);

        return elementKindEditionRepository
            .findById(elementKindEdition.getId())
            .map(existingElementKindEdition -> {
                if (elementKindEdition.getNewGramPerMeterLinearMass() != null) {
                    existingElementKindEdition.setNewGramPerMeterLinearMass(elementKindEdition.getNewGramPerMeterLinearMass());
                }
                if (elementKindEdition.getNewMilimeterDiameter() != null) {
                    existingElementKindEdition.setNewMilimeterDiameter(elementKindEdition.getNewMilimeterDiameter());
                }
                if (elementKindEdition.getNewInsulationThickness() != null) {
                    existingElementKindEdition.setNewInsulationThickness(elementKindEdition.getNewInsulationThickness());
                }

                return existingElementKindEdition;
            })
            .map(elementKindEditionRepository::save);
    }

    /**
     * Get all the elementKindEditions.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ElementKindEdition> findAll() {
        log.debug("Request to get all ElementKindEditions");
        List<ElementKindEdition> elementKindEditionList = elementKindEditionRepository.findAll();

        for (ElementKindEdition elementKindEdition : elementKindEditionList) {
            elementKindEdition.forceNotNull();
        }

        return elementKindEditionList;
    }

    /**
     * Get one elementKindEdition by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ElementKindEdition> findOne(Long id) {
        log.debug("Request to get ElementKindEdition : {}", id);
        return elementKindEditionRepository.findById(id);
    }

    /**
     * Delete the elementKindEdition by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ElementKindEdition : {}", id);
        elementKindEditionRepository.deleteById(id);
    }
}
