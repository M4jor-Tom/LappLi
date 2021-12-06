package com.muller.lappli.service.impl;

import com.muller.lappli.domain.ElementKind;
import com.muller.lappli.repository.ElementKindRepository;
import com.muller.lappli.service.ElementKindService;
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

    public ElementKindServiceImpl(ElementKindRepository elementKindRepository) {
        this.elementKindRepository = elementKindRepository;
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
        return elementKindRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ElementKind> findOne(Long id) {
        log.debug("Request to get ElementKind : {}", id);
        return elementKindRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ElementKind : {}", id);
        elementKindRepository.deleteById(id);
    }
}
