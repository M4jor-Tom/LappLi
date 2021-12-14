package com.muller.lappli.service.impl;

import com.muller.lappli.domain.ElementSupply;
import com.muller.lappli.repository.ElementSupplyRepository;
import com.muller.lappli.service.ElementSupplyService;
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
public class ElementSupplyServiceImpl implements ElementSupplyService {

    private final Logger log = LoggerFactory.getLogger(ElementSupplyServiceImpl.class);

    private final ElementSupplyRepository elementSupplyRepository;

    public ElementSupplyServiceImpl(ElementSupplyRepository elementSupplyRepository) {
        this.elementSupplyRepository = elementSupplyRepository;
    }

    @Override
    public ElementSupply save(ElementSupply elementSupply) {
        log.debug("Request to save ElementSupply : {}", elementSupply);
        return elementSupplyRepository.save(elementSupply);
    }

    @Override
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

    @Override
    @Transactional(readOnly = true)
    public List<ElementSupply> findAll() {
        log.debug("Request to get all ElementSupplies");
        return elementSupplyRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ElementSupply> findOne(Long id) {
        log.debug("Request to get ElementSupply : {}", id);
        return elementSupplyRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ElementSupply : {}", id);
        elementSupplyRepository.deleteById(id);
    }
}
