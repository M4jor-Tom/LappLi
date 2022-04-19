package com.muller.lappli.service.impl;

import com.muller.lappli.domain.SteelFiber;
import com.muller.lappli.repository.SteelFiberRepository;
import com.muller.lappli.service.SteelFiberService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SteelFiber}.
 */
@Service
@Transactional
public class SteelFiberServiceImpl implements SteelFiberService {

    private final Logger log = LoggerFactory.getLogger(SteelFiberServiceImpl.class);

    private final SteelFiberRepository steelFiberRepository;

    public SteelFiberServiceImpl(SteelFiberRepository steelFiberRepository) {
        this.steelFiberRepository = steelFiberRepository;
    }

    @Override
    public SteelFiber save(SteelFiber steelFiber) {
        log.debug("Request to save SteelFiber : {}", steelFiber);
        return steelFiberRepository.save(steelFiber);
    }

    @Override
    public Optional<SteelFiber> partialUpdate(SteelFiber steelFiber) {
        log.debug("Request to partially update SteelFiber : {}", steelFiber);

        return steelFiberRepository
            .findById(steelFiber.getId())
            .map(existingSteelFiber -> {
                if (steelFiber.getNumber() != null) {
                    existingSteelFiber.setNumber(steelFiber.getNumber());
                }
                if (steelFiber.getDesignation() != null) {
                    existingSteelFiber.setDesignation(steelFiber.getDesignation());
                }
                if (steelFiber.getMetalFiberKind() != null) {
                    existingSteelFiber.setMetalFiberKind(steelFiber.getMetalFiberKind());
                }
                if (steelFiber.getMilimeterDiameter() != null) {
                    existingSteelFiber.setMilimeterDiameter(steelFiber.getMilimeterDiameter());
                }

                return existingSteelFiber;
            })
            .map(steelFiberRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SteelFiber> findAll() {
        log.debug("Request to get all SteelFibers");
        return steelFiberRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SteelFiber> findOne(Long id) {
        log.debug("Request to get SteelFiber : {}", id);
        return steelFiberRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SteelFiber : {}", id);
        steelFiberRepository.deleteById(id);
    }
}
