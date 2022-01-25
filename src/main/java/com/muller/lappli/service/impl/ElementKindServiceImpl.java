package com.muller.lappli.service.impl;

import com.muller.lappli.domain.EditionListManager;
import com.muller.lappli.domain.ElementKind;
import com.muller.lappli.domain.ElementKindEdition;
import com.muller.lappli.repository.ElementKindRepository;
import com.muller.lappli.service.ElementKindEditionService;
import com.muller.lappli.service.ElementKindService;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ElementKind}.
 */
@Service
@Transactional
public class ElementKindServiceImpl implements ElementKindService {

    private final Logger log = LoggerFactory.getLogger(ElementKindServiceImpl.class);

    private final ElementKindRepository elementKindRepository;

    private final ElementKindEditionService elementKindEditionService;

    private Instant instant;

    public ElementKindServiceImpl(ElementKindRepository elementKindRepository, ElementKindEditionService elementKindEditionService) {
        this.elementKindRepository = elementKindRepository;
        this.elementKindEditionService = elementKindEditionService;
        this.instant = Instant.now();
    }

    @Override
    public ElementKind save(ElementKind elementKind) {
        log.debug("Request to save ElementKind : {}", elementKind);
        return elementKindRepository.save(elementKind);
    }

    @Override
    public Optional<ElementKind> partialUpdate(ElementKind elementKind) {
        log.debug("Request to partially update ElementKind : {}", elementKind);

        return elementKindRepository
            .findById(elementKind.getId())
            .map(existingElementKind -> {
                if (elementKind.getDesignation() != null) {
                    existingElementKind.setDesignation(elementKind.getDesignation());
                }
                if (elementKind.getGramPerMeterLinearMass() != null) {
                    existingElementKind.setGramPerMeterLinearMass(elementKind.getGramPerMeterLinearMass());
                }
                if (elementKind.getMilimeterDiameter() != null) {
                    existingElementKind.setMilimeterDiameter(elementKind.getMilimeterDiameter());
                }
                if (elementKind.getInsulationThickness() != null) {
                    existingElementKind.setInsulationThickness(elementKind.getInsulationThickness());
                }

                return existingElementKind;
            })
            .map(elementKindRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ElementKind> findAll() {
        log.debug("Request to get all ElementKinds");
        return onListRead(elementKindRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ElementKind> findOne(Long id) {
        log.debug("Request to get ElementKind : {}", id);
        return onOptionalRead(elementKindRepository.findById(id));
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ElementKind : {}", id);
        elementKindRepository.deleteById(id);
    }

    public ElementKindService instant(Instant instant) {
        this.instant = instant;
        return this;
    }

    @Override
    public ElementKind onRead(ElementKind domainObject) {
        EditionListManager<ElementKind> elm = new EditionListManager<ElementKind>(new ArrayList<>());

        for (ElementKindEdition elementKindEdition : elementKindEditionService.findByEditedElementKindId(domainObject.getId())) {
            elm.getEditionList().add(elementKindEdition);
        }

        domainObject.setEditionListManager(elm);

        return domainObject.copyAtInstant(this.instant);
    }
}
