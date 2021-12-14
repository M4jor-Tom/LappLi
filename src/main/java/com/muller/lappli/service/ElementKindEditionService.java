package com.muller.lappli.service;

import com.muller.lappli.domain.ElementKindEdition;
import com.muller.lappli.repository.ElementKindEditionRepository;
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
                if (elementKindEdition.getEditionDateTime() != null) {
                    existingElementKindEdition.setEditionDateTime(elementKindEdition.getEditionDateTime());
                }
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
        return elementKindEditionRepository.findAll();
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
