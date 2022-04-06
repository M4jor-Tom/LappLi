package com.muller.lappli.service.impl;

import com.muller.lappli.domain.Strip;
import com.muller.lappli.repository.StripRepository;
import com.muller.lappli.service.StripService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Strip}.
 */
@Service
@Transactional
public class StripServiceImpl implements StripService {

    private final Logger log = LoggerFactory.getLogger(StripServiceImpl.class);

    private final StripRepository stripRepository;

    public StripServiceImpl(StripRepository stripRepository) {
        this.stripRepository = stripRepository;
    }

    @Override
    public Strip save(Strip strip) {
        log.debug("Request to save Strip : {}", strip);
        return stripRepository.save(strip);
    }

    @Override
    public Optional<Strip> partialUpdate(Strip strip) {
        log.debug("Request to partially update Strip : {}", strip);

        return stripRepository
            .findById(strip.getId())
            .map(existingStrip -> {
                if (strip.getNumber() != null) {
                    existingStrip.setNumber(strip.getNumber());
                }
                if (strip.getDesignation() != null) {
                    existingStrip.setDesignation(strip.getDesignation());
                }
                if (strip.getMilimeterThickness() != null) {
                    existingStrip.setMilimeterThickness(strip.getMilimeterThickness());
                }

                return existingStrip;
            })
            .map(stripRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Strip> findAll() {
        log.debug("Request to get all Strips");
        return stripRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Strip> findOne(Long id) {
        log.debug("Request to get Strip : {}", id);
        return stripRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Strip : {}", id);
        stripRepository.deleteById(id);
    }
}
