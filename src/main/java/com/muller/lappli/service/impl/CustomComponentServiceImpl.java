package com.muller.lappli.service.impl;

import com.muller.lappli.domain.CustomComponent;
import com.muller.lappli.repository.CustomComponentRepository;
import com.muller.lappli.service.CustomComponentService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CustomComponent}.
 */
@Service
@Transactional
public class CustomComponentServiceImpl implements CustomComponentService {

    private final Logger log = LoggerFactory.getLogger(CustomComponentServiceImpl.class);

    private final CustomComponentRepository customComponentRepository;

    public CustomComponentServiceImpl(CustomComponentRepository customComponentRepository) {
        this.customComponentRepository = customComponentRepository;
    }

    @Override
    public CustomComponent save(CustomComponent customComponent) {
        log.debug("Request to save CustomComponent : {}", customComponent);
        return customComponentRepository.save(customComponent);
    }

    @Override
    public Optional<CustomComponent> partialUpdate(CustomComponent customComponent) {
        log.debug("Request to partially update CustomComponent : {}", customComponent);

        return customComponentRepository
            .findById(customComponent.getId())
            .map(existingCustomComponent -> {
                if (customComponent.getNumber() != null) {
                    existingCustomComponent.setNumber(customComponent.getNumber());
                }
                if (customComponent.getDesignation() != null) {
                    existingCustomComponent.setDesignation(customComponent.getDesignation());
                }
                if (customComponent.getGramPerMeterLinearMass() != null) {
                    existingCustomComponent.setGramPerMeterLinearMass(customComponent.getGramPerMeterLinearMass());
                }
                if (customComponent.getMilimeterDiameter() != null) {
                    existingCustomComponent.setMilimeterDiameter(customComponent.getMilimeterDiameter());
                }
                if (customComponent.getSurfaceColor() != null) {
                    existingCustomComponent.setSurfaceColor(customComponent.getSurfaceColor());
                }

                return existingCustomComponent;
            })
            .map(customComponentRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomComponent> findAll() {
        log.debug("Request to get all CustomComponents");
        return customComponentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustomComponent> findOne(Long id) {
        log.debug("Request to get CustomComponent : {}", id);
        return customComponentRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustomComponent : {}", id);
        customComponentRepository.deleteById(id);
    }
}
