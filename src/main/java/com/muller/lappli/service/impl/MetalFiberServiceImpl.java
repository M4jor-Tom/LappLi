package com.muller.lappli.service.impl;

import com.muller.lappli.domain.MetalFiber;
import com.muller.lappli.repository.MetalFiberRepository;
import com.muller.lappli.service.MetalFiberService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MetalFiber}.
 */
@Service
@Transactional
public class MetalFiberServiceImpl implements MetalFiberService {

    private final Logger log = LoggerFactory.getLogger(MetalFiberServiceImpl.class);

    private final MetalFiberRepository metalFiberRepository;

    public MetalFiberServiceImpl(MetalFiberRepository metalFiberRepository) {
        this.metalFiberRepository = metalFiberRepository;
    }

    @Override
    public MetalFiber save(MetalFiber metalFiber) {
        log.debug("Request to save MetalFiber : {}", metalFiber);
        return metalFiberRepository.save(metalFiber);
    }

    @Override
    public Optional<MetalFiber> partialUpdate(MetalFiber metalFiber) {
        log.debug("Request to partially update MetalFiber : {}", metalFiber);

        return metalFiberRepository
            .findById(metalFiber.getId())
            .map(existingMetalFiber -> {
                if (metalFiber.getNumber() != null) {
                    existingMetalFiber.setNumber(metalFiber.getNumber());
                }
                if (metalFiber.getDesignation() != null) {
                    existingMetalFiber.setDesignation(metalFiber.getDesignation());
                }
                if (metalFiber.getMetalFiberKind() != null) {
                    existingMetalFiber.setMetalFiberKind(metalFiber.getMetalFiberKind());
                }
                if (metalFiber.getMilimeterDiameter() != null) {
                    existingMetalFiber.setMilimeterDiameter(metalFiber.getMilimeterDiameter());
                }

                return existingMetalFiber;
            })
            .map(metalFiberRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MetalFiber> findAll() {
        log.debug("Request to get all MetalFibers");
        return metalFiberRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MetalFiber> findOne(Long id) {
        log.debug("Request to get MetalFiber : {}", id);
        return metalFiberRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MetalFiber : {}", id);
        metalFiberRepository.deleteById(id);
    }
}
