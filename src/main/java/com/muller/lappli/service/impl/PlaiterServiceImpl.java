package com.muller.lappli.service.impl;

import com.muller.lappli.domain.Plaiter;
import com.muller.lappli.repository.PlaiterRepository;
import com.muller.lappli.service.PlaiterService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Plaiter}.
 */
@Service
@Transactional
public class PlaiterServiceImpl implements PlaiterService {

    private final Logger log = LoggerFactory.getLogger(PlaiterServiceImpl.class);

    private final PlaiterRepository plaiterRepository;

    public PlaiterServiceImpl(PlaiterRepository plaiterRepository) {
        this.plaiterRepository = plaiterRepository;
    }

    @Override
    public Plaiter save(Plaiter plaiter) {
        log.debug("Request to save Plaiter : {}", plaiter);
        return plaiterRepository.save(plaiter);
    }

    @Override
    public Optional<Plaiter> partialUpdate(Plaiter plaiter) {
        log.debug("Request to partially update Plaiter : {}", plaiter);

        return plaiterRepository
            .findById(plaiter.getId())
            .map(existingPlaiter -> {
                if (plaiter.getIndex() != null) {
                    existingPlaiter.setIndex(plaiter.getIndex());
                }
                if (plaiter.getTotalBobinsCount() != null) {
                    existingPlaiter.setTotalBobinsCount(plaiter.getTotalBobinsCount());
                }

                return existingPlaiter;
            })
            .map(plaiterRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Plaiter> findAll() {
        log.debug("Request to get all Plaiters");
        return plaiterRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Plaiter> findOne(Long id) {
        log.debug("Request to get Plaiter : {}", id);
        return plaiterRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Plaiter : {}", id);
        plaiterRepository.deleteById(id);
    }
}
