package com.muller.lappli.service.impl;

import com.muller.lappli.domain.LifterRunMeasure;
import com.muller.lappli.repository.LifterRunMeasureRepository;
import com.muller.lappli.service.LifterRunMeasureService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LifterRunMeasure}.
 */
@Service
@Transactional
public class LifterRunMeasureServiceImpl implements LifterRunMeasureService {

    private final Logger log = LoggerFactory.getLogger(LifterRunMeasureServiceImpl.class);

    private final LifterRunMeasureRepository lifterRunMeasureRepository;

    public LifterRunMeasureServiceImpl(LifterRunMeasureRepository lifterRunMeasureRepository) {
        this.lifterRunMeasureRepository = lifterRunMeasureRepository;
    }

    @Override
    public LifterRunMeasure save(LifterRunMeasure lifterRunMeasure) {
        log.debug("Request to save LifterRunMeasure : {}", lifterRunMeasure);
        return lifterRunMeasureRepository.save(lifterRunMeasure);
    }

    @Override
    public Optional<LifterRunMeasure> partialUpdate(LifterRunMeasure lifterRunMeasure) {
        log.debug("Request to partially update LifterRunMeasure : {}", lifterRunMeasure);

        return lifterRunMeasureRepository
            .findById(lifterRunMeasure.getId())
            .map(existingLifterRunMeasure -> {
                if (lifterRunMeasure.getMilimeterDiameter() != null) {
                    existingLifterRunMeasure.setMilimeterDiameter(lifterRunMeasure.getMilimeterDiameter());
                }
                if (lifterRunMeasure.getMeterPerSecondSpeed() != null) {
                    existingLifterRunMeasure.setMeterPerSecondSpeed(lifterRunMeasure.getMeterPerSecondSpeed());
                }
                if (lifterRunMeasure.getMarkingType() != null) {
                    existingLifterRunMeasure.setMarkingType(lifterRunMeasure.getMarkingType());
                }
                if (lifterRunMeasure.getMarkingTechnique() != null) {
                    existingLifterRunMeasure.setMarkingTechnique(lifterRunMeasure.getMarkingTechnique());
                }
                if (lifterRunMeasure.getHourPreparationTime() != null) {
                    existingLifterRunMeasure.setHourPreparationTime(lifterRunMeasure.getHourPreparationTime());
                }

                return existingLifterRunMeasure;
            })
            .map(lifterRunMeasureRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LifterRunMeasure> findAll() {
        log.debug("Request to get all LifterRunMeasures");
        return lifterRunMeasureRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LifterRunMeasure> findOne(Long id) {
        log.debug("Request to get LifterRunMeasure : {}", id);
        return lifterRunMeasureRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LifterRunMeasure : {}", id);
        lifterRunMeasureRepository.deleteById(id);
    }
}
