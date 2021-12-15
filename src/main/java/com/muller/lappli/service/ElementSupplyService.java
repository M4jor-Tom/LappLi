package com.muller.lappli.service;

import com.muller.lappli.domain.ElementSupply;
import com.muller.lappli.repository.ElementSupplyRepository;
import com.muller.lappli.service.abstracts.AbstractLiftedSupplyService;
import com.muller.lappli.service.interfaces.ISupplyService;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ElementSupply}.
 */
@Service
@Transactional
public class ElementSupplyService extends AbstractLiftedSupplyService<ElementSupply> implements ISupplyService<ElementSupply> {

    private final Logger log = LoggerFactory.getLogger(ElementSupplyService.class);

    private final ElementSupplyRepository elementSupplyRepository;

    public ElementSupplyService(ElementSupplyRepository elementSupplyRepository, LifterService lifterService) {
        super(elementSupplyRepository, lifterService);
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
     * Delete the elementSupply by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ElementSupply : {}", id);
        elementSupplyRepository.deleteById(id);
    }

    @Override
    public Set<ElementSupply> findByStrandId(Long id) {
        Set<ElementSupply> elementSupplyList = new HashSet<>();
        for (ElementSupply elementSupply : findAll()) {
            if (elementSupply.getStrand().getId().equals(id)) {
                elementSupplyList.add(elementSupply);
            }
        }

        return elementSupplyList;
    }
}
