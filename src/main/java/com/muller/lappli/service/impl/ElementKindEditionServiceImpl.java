package com.muller.lappli.service.impl;

import com.muller.lappli.domain.ElementKindEdition;
import com.muller.lappli.repository.ElementKindEditionRepository;
import com.muller.lappli.service.ElementKindEditionService;
import java.time.Instant;
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
public class ElementKindEditionServiceImpl implements ElementKindEditionService {

    private final Logger log = LoggerFactory.getLogger(ElementKindEditionServiceImpl.class);

    private final ElementKindEditionRepository elementKindEditionRepository;

    public ElementKindEditionServiceImpl(ElementKindEditionRepository elementKindEditionRepository) {
        this.elementKindEditionRepository = elementKindEditionRepository;
    }

    @Override
    public List<ElementKindEdition> findByEditedElementKindIdAndEditionDateTimeBefore(Long editedElementKindId, Instant editionDateTime) {
        return elementKindEditionRepository.findByEditedElementKindIdAndEditionDateTimeBefore(editedElementKindId, editionDateTime);
    }

    @Override
    public List<ElementKindEdition> findByEditedElementKindId(Long editedElementKindId) {
        return elementKindEditionRepository.findByEditedElementKindId(editedElementKindId);
    }

    @Override
    public ElementKindEdition save(ElementKindEdition elementKindEdition) {
        log.debug("Request to save ElementKindEdition : {}", elementKindEdition);
        return elementKindEditionRepository.save(elementKindEdition);
    }

    @Override
    public Optional<ElementKindEdition> partialUpdate(ElementKindEdition elementKindEdition) {
        log.debug("Request to partially update ElementKindEdition : {}", elementKindEdition);

        return elementKindEditionRepository
            .findById(elementKindEdition.getId())
            .map(existingElementKindEdition -> {
                //[JDL DON'T OVERWRITE]
                /*if (elementKindEdition.getEditionDateTime() != null) {
                    existingElementKindEdition.setEditionDateTime(elementKindEdition.getEditionDateTime());
                }*/
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

    @Override
    @Transactional(readOnly = true)
    public List<ElementKindEdition> findAll() {
        log.debug("Request to get all ElementKindEditions");
        return elementKindEditionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ElementKindEdition> findOne(Long id) {
        log.debug("Request to get ElementKindEdition : {}", id);
        return elementKindEditionRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ElementKindEdition : {}", id);
        elementKindEditionRepository.deleteById(id);
    }
}
