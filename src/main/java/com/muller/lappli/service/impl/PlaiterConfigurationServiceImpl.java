package com.muller.lappli.service.impl;

import com.muller.lappli.domain.PlaiterConfiguration;
import com.muller.lappli.repository.PlaiterConfigurationRepository;
import com.muller.lappli.service.PlaiterConfigurationService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PlaiterConfiguration}.
 */
@Service
@Transactional
public class PlaiterConfigurationServiceImpl implements PlaiterConfigurationService {

    private final Logger log = LoggerFactory.getLogger(PlaiterConfigurationServiceImpl.class);

    private final PlaiterConfigurationRepository plaiterConfigurationRepository;

    public PlaiterConfigurationServiceImpl(PlaiterConfigurationRepository plaiterConfigurationRepository) {
        this.plaiterConfigurationRepository = plaiterConfigurationRepository;
    }

    @Override
    public PlaiterConfiguration save(PlaiterConfiguration plaiterConfiguration) {
        log.debug("Request to save PlaiterConfiguration : {}", plaiterConfiguration);
        return plaiterConfigurationRepository.save(plaiterConfiguration);
    }

    @Override
    public Optional<PlaiterConfiguration> partialUpdate(PlaiterConfiguration plaiterConfiguration) {
        log.debug("Request to partially update PlaiterConfiguration : {}", plaiterConfiguration);

        return plaiterConfigurationRepository
            .findById(plaiterConfiguration.getId())
            .map(existingPlaiterConfiguration -> {
                if (plaiterConfiguration.getUsedBobinsCount() != null) {
                    existingPlaiterConfiguration.setUsedBobinsCount(plaiterConfiguration.getUsedBobinsCount());
                }

                return existingPlaiterConfiguration;
            })
            .map(plaiterConfigurationRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlaiterConfiguration> findAll() {
        log.debug("Request to get all PlaiterConfigurations");
        return plaiterConfigurationRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PlaiterConfiguration> findOne(Long id) {
        log.debug("Request to get PlaiterConfiguration : {}", id);
        return plaiterConfigurationRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PlaiterConfiguration : {}", id);
        plaiterConfigurationRepository.deleteById(id);
    }
}
