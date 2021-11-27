package com.muller.lappli.service;

import com.muller.lappli.domain.Lifter;
import com.muller.lappli.repository.LifterRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Lifter}.
 */
@Service
@Transactional
public class LifterService {

    private final Logger log = LoggerFactory.getLogger(LifterService.class);

    private final LifterRepository lifterRepository;

    public LifterService(LifterRepository lifterRepository) {
        this.lifterRepository = lifterRepository;
    }

    /**
     * Save a lifter.
     *
     * @param lifter the entity to save.
     * @return the persisted entity.
     */
    public Lifter save(Lifter lifter) {
        log.debug("Request to save Lifter : {}", lifter);
        return lifterRepository.save(lifter);
    }

    /**
     * Partially update a lifter.
     *
     * @param lifter the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Lifter> partialUpdate(Lifter lifter) {
        log.debug("Request to partially update Lifter : {}", lifter);

        return lifterRepository
            .findById(lifter.getId())
            .map(existingLifter -> {
                if (lifter.getIndex() != null) {
                    existingLifter.setIndex(lifter.getIndex());
                }
                if (lifter.getMinimumMilimeterDiameter() != null) {
                    existingLifter.setMinimumMilimeterDiameter(lifter.getMinimumMilimeterDiameter());
                }
                if (lifter.getMaximumMilimeterDiameter() != null) {
                    existingLifter.setMaximumMilimeterDiameter(lifter.getMaximumMilimeterDiameter());
                }
                if (lifter.getSupportsSpirallyColoredMarkingType() != null) {
                    existingLifter.setSupportsSpirallyColoredMarkingType(lifter.getSupportsSpirallyColoredMarkingType());
                }
                if (lifter.getSupportsLongitudinallyColoredMarkingType() != null) {
                    existingLifter.setSupportsLongitudinallyColoredMarkingType(lifter.getSupportsLongitudinallyColoredMarkingType());
                }
                if (lifter.getSupportsNumberedMarkingType() != null) {
                    existingLifter.setSupportsNumberedMarkingType(lifter.getSupportsNumberedMarkingType());
                }

                return existingLifter;
            })
            .map(lifterRepository::save);
    }

    /**
     * Get all the lifters.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Lifter> findAll() {
        log.debug("Request to get all Lifters");
        return lifterRepository.findAll();
    }

    /**
     * Get one lifter by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Lifter> findOne(Long id) {
        log.debug("Request to get Lifter : {}", id);
        return lifterRepository.findById(id);
    }

    /**
     * Delete the lifter by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Lifter : {}", id);
        lifterRepository.deleteById(id);
    }
}
