package com.muller.lappli.service.impl;

import com.muller.lappli.domain.Copper;
import com.muller.lappli.repository.CopperRepository;
import com.muller.lappli.service.CopperService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Copper}.
 */
@Service
@Transactional
public class CopperServiceImpl implements CopperService {

    private final Logger log = LoggerFactory.getLogger(CopperServiceImpl.class);

    private final CopperRepository copperRepository;

    public CopperServiceImpl(CopperRepository copperRepository) {
        this.copperRepository = copperRepository;
    }

    @Override
    public Copper save(Copper copper) {
        log.debug("Request to save Copper : {}", copper);
        return copperRepository.save(copper);
    }

    @Override
    public Optional<Copper> partialUpdate(Copper copper) {
        log.debug("Request to partially update Copper : {}", copper);

        return copperRepository
            .findById(copper.getId())
            .map(existingCopper -> {
                if (copper.getNumber() != null) {
                    existingCopper.setNumber(copper.getNumber());
                }
                if (copper.getDesignation() != null) {
                    existingCopper.setDesignation(copper.getDesignation());
                }

                return existingCopper;
            })
            .map(copperRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Copper> findAll() {
        log.debug("Request to get all Coppers");
        return copperRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Copper> findOne(Long id) {
        log.debug("Request to get Copper : {}", id);
        return copperRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Copper : {}", id);
        copperRepository.deleteById(id);
    }
}
