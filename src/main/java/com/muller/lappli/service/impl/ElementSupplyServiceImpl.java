package com.muller.lappli.service.impl;

import com.muller.lappli.domain.ElementSupply;
import com.muller.lappli.repository.ElementSupplyRepository;
import com.muller.lappli.service.ElementService;
import com.muller.lappli.service.ElementSupplyService;
import com.muller.lappli.service.LifterService;
import com.muller.lappli.service.abstracts.AbstractLiftedSupplyServiceImpl;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ElementSupply}.
 */
@Service
@Transactional
public class ElementSupplyServiceImpl extends AbstractLiftedSupplyServiceImpl<ElementSupply> implements ElementSupplyService {

    private final Logger log = LoggerFactory.getLogger(ElementSupplyServiceImpl.class);

    private final ElementSupplyRepository elementSupplyRepository;

    private final ElementService elementService;

    public ElementSupplyServiceImpl(
        LifterService lifterService,
        ElementSupplyRepository elementSupplyRepository,
        ElementService elementService
    ) {
        super(lifterService);
        this.elementSupplyRepository = elementSupplyRepository;
        this.elementService = elementService;
    }

    @Override
    public ElementSupply save(ElementSupply elementSupply) {
        log.debug("Request to save ElementSupply : {}", elementSupply);
        return onRead(elementSupplyRepository.save(elementSupply));
    }

    @Override
    public Optional<ElementSupply> partialUpdate(ElementSupply elementSupply) {
        log.debug("Request to partially update ElementSupply : {}", elementSupply);

        return onOptionalRead(
            elementSupplyRepository
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
                .map(elementSupplyRepository::save)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<ElementSupply> findAll() {
        log.debug("Request to get all ElementSupplies");
        return onListRead(elementSupplyRepository.findAll());
    }

    /**
     *  Get all the elementSupplies where Position is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ElementSupply> findAllWherePositionIsNull() {
        log.debug("Request to get all elementSupplies where Position is null");
        return StreamSupport
            .stream(elementSupplyRepository.findAll().spliterator(), false)
            .filter(elementSupply -> elementSupply.getPosition() == null)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ElementSupply> findOne(Long id) {
        log.debug("Request to get ElementSupply : {}", id);
        return onOptionalRead(elementSupplyRepository.findById(id));
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ElementSupply : {}", id);
        elementSupplyRepository.deleteById(id);
    }

    @Override
    public ElementSupply onRead(ElementSupply domainObject) {
        return super.onRead(domainObject.element(elementService.onRead(domainObject.getElement())));
    }
}
