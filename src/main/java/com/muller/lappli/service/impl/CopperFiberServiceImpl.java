package com.muller.lappli.service.impl;

import com.muller.lappli.domain.CopperFiber;
import com.muller.lappli.repository.CopperFiberRepository;
import com.muller.lappli.service.CopperFiberService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CopperFiber}.
 */
@Service
@Transactional
public class CopperFiberServiceImpl implements CopperFiberService {

    private final Logger log = LoggerFactory.getLogger(CopperFiberServiceImpl.class);

    private final CopperFiberRepository copperFiberRepository;

    public CopperFiberServiceImpl(CopperFiberRepository copperFiberRepository) {
        this.copperFiberRepository = copperFiberRepository;
    }

    @Override
    public CopperFiber save(CopperFiber copperFiber) {
        log.debug("Request to save CopperFiber : {}", copperFiber);
        return copperFiberRepository.save(copperFiber);
    }

    @Override
    public Optional<CopperFiber> partialUpdate(CopperFiber copperFiber) {
        log.debug("Request to partially update CopperFiber : {}", copperFiber);

        return copperFiberRepository
            .findById(copperFiber.getId())
            .map(existingCopperFiber -> {
                if (copperFiber.getNumber() != null) {
                    existingCopperFiber.setNumber(copperFiber.getNumber());
                }
                if (copperFiber.getDesignation() != null) {
                    existingCopperFiber.setDesignation(copperFiber.getDesignation());
                }
                if (copperFiber.getMetalFiberKind() != null) {
                    existingCopperFiber.setMetalFiberKind(copperFiber.getMetalFiberKind());
                }
                if (copperFiber.getMilimeterDiameter() != null) {
                    existingCopperFiber.setMilimeterDiameter(copperFiber.getMilimeterDiameter());
                }

                return existingCopperFiber;
            })
            .map(copperFiberRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CopperFiber> findAll() {
        log.debug("Request to get all CopperFibers");
        return copperFiberRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CopperFiber> findOne(Long id) {
        log.debug("Request to get CopperFiber : {}", id);
        return copperFiberRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CopperFiber : {}", id);
        copperFiberRepository.deleteById(id);
    }
}
