package com.muller.lappli.service;

import com.muller.lappli.domain.ElementSupply;
import com.muller.lappli.repository.ElementSupplyRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ElementSupply}.
 */
@Service
@Transactional
public class ElementSupplyService {

    private final Logger log = LoggerFactory.getLogger(ElementSupplyService.class);

    private final ElementSupplyRepository elementSupplyRepository;

    public ElementSupplyService(ElementSupplyRepository elementSupplyRepository) {
        this.elementSupplyRepository = elementSupplyRepository;
    }

    /**
     * Save a elementSupply.
     *
     * @param elementSupply the entity to save.
     * @return the persisted entity.
     */
    public ElementSupply save(ElementSupply elementSupply) {
        log.debug("Request to save ElementSupply : {}", elementSupply);
        return elementSupplyRepository.save(elementSupply);
    }

    /**
     * Partially update a elementSupply.
     *
     * @param elementSupply the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ElementSupply> partialUpdate(ElementSupply elementSupply) {
        log.debug("Request to partially update ElementSupply : {}", elementSupply);

        return elementSupplyRepository
            .findById(elementSupply.getId())
            .map(existingElementSupply -> {
                if (elementSupply.getApparitions() != null) {
                    existingElementSupply.setApparitions(elementSupply.getApparitions());
                }
                if (elementSupply.getMarkingType() != null) {
                    existingElementSupply.setMarkingType(elementSupply.getMarkingType());
                }
                if (elementSupply.getDescription() != null) {
                    existingElementSupply.setDescription(elementSupply.getDescription());
                }

                return existingElementSupply;
            })
            .map(elementSupplyRepository::save);
    }

    /**
     * Get all the elementSupplies.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ElementSupply> findAll() {
        log.debug("Request to get all ElementSupplies");
        return elementSupplyRepository.findAll();
    }

    /**
     * Get one elementSupply by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ElementSupply> findOne(Long id) {
        log.debug("Request to get ElementSupply : {}", id);
        return elementSupplyRepository.findById(id);
    }

    /**
     * Delete the elementSupply by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ElementSupply : {}", id);
        elementSupplyRepository.deleteById(id);
    }
}
