package com.muller.lappli.service.impl;

import com.muller.lappli.domain.Plait;
import com.muller.lappli.repository.PlaitRepository;
import com.muller.lappli.service.PlaitService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Plait}.
 */
@Service
@Transactional
public class PlaitServiceImpl implements PlaitService {

    private final Logger log = LoggerFactory.getLogger(PlaitServiceImpl.class);

    private final PlaitRepository plaitRepository;

    public PlaitServiceImpl(PlaitRepository plaitRepository) {
        this.plaitRepository = plaitRepository;
    }

    @Override
    public Plait save(Plait plait) {
        log.debug("Request to save Plait : {}", plait);
        return plaitRepository.save(plait);
    }

    @Override
    public Optional<Plait> partialUpdate(Plait plait) {
        log.debug("Request to partially update Plait : {}", plait);

        return plaitRepository
            .findById(plait.getId())
            .map(existingPlait -> {
                if (plait.getOperationLayer() != null) {
                    existingPlait.setOperationLayer(plait.getOperationLayer());
                }
                if (plait.getTargetCoveringRate() != null) {
                    existingPlait.setTargetCoveringRate(plait.getTargetCoveringRate());
                }
                if (plait.getTargetDegreeAngle() != null) {
                    existingPlait.setTargetDegreeAngle(plait.getTargetDegreeAngle());
                }
                if (plait.getTargetingCoveringRateNotAngle() != null) {
                    existingPlait.setTargetingCoveringRateNotAngle(plait.getTargetingCoveringRateNotAngle());
                }
                if (plait.getAnonymousMetalFiberNumber() != null) {
                    existingPlait.setAnonymousMetalFiberNumber(plait.getAnonymousMetalFiberNumber());
                }
                if (plait.getAnonymousMetalFiberDesignation() != null) {
                    existingPlait.setAnonymousMetalFiberDesignation(plait.getAnonymousMetalFiberDesignation());
                }
                if (plait.getAnonymousMetalFiberMetalFiberKind() != null) {
                    existingPlait.setAnonymousMetalFiberMetalFiberKind(plait.getAnonymousMetalFiberMetalFiberKind());
                }
                if (plait.getAnonymousMetalFiberMilimeterDiameter() != null) {
                    existingPlait.setAnonymousMetalFiberMilimeterDiameter(plait.getAnonymousMetalFiberMilimeterDiameter());
                }

                return existingPlait;
            })
            .map(plaitRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Plait> findAll() {
        log.debug("Request to get all Plaits");
        return plaitRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Plait> findOne(Long id) {
        log.debug("Request to get Plait : {}", id);
        return plaitRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Plait : {}", id);
        plaitRepository.deleteById(id);
    }
}
