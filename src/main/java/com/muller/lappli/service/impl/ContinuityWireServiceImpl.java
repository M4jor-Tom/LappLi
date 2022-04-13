package com.muller.lappli.service.impl;

import com.muller.lappli.domain.ContinuityWire;
import com.muller.lappli.repository.ContinuityWireRepository;
import com.muller.lappli.service.ContinuityWireService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ContinuityWire}.
 */
@Service
@Transactional
public class ContinuityWireServiceImpl implements ContinuityWireService {

    private final Logger log = LoggerFactory.getLogger(ContinuityWireServiceImpl.class);

    private final ContinuityWireRepository continuityWireRepository;

    public ContinuityWireServiceImpl(ContinuityWireRepository continuityWireRepository) {
        this.continuityWireRepository = continuityWireRepository;
    }

    @Override
    public ContinuityWire save(ContinuityWire continuityWire) {
        log.debug("Request to save ContinuityWire : {}", continuityWire);
        return continuityWireRepository.save(continuityWire);
    }

    @Override
    public Optional<ContinuityWire> partialUpdate(ContinuityWire continuityWire) {
        log.debug("Request to partially update ContinuityWire : {}", continuityWire);

        return continuityWireRepository
            .findById(continuityWire.getId())
            .map(existingContinuityWire -> {
                if (continuityWire.getMetalFiberKind() != null) {
                    existingContinuityWire.setMetalFiberKind(continuityWire.getMetalFiberKind());
                }
                if (continuityWire.getMilimeterDiameter() != null) {
                    existingContinuityWire.setMilimeterDiameter(continuityWire.getMilimeterDiameter());
                }
                if (continuityWire.getFlexibility() != null) {
                    existingContinuityWire.setFlexibility(continuityWire.getFlexibility());
                }

                return existingContinuityWire;
            })
            .map(continuityWireRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContinuityWire> findAll() {
        log.debug("Request to get all ContinuityWires");
        return continuityWireRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ContinuityWire> findOne(Long id) {
        log.debug("Request to get ContinuityWire : {}", id);
        return continuityWireRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ContinuityWire : {}", id);
        continuityWireRepository.deleteById(id);
    }
}
