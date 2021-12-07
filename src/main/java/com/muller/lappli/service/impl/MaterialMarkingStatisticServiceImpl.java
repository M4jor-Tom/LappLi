package com.muller.lappli.service.impl;

import com.muller.lappli.domain.MaterialMarkingStatistic;
import com.muller.lappli.repository.MaterialMarkingStatisticRepository;
import com.muller.lappli.service.MaterialMarkingStatisticService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MaterialMarkingStatistic}.
 */
@Service
@Transactional
public class MaterialMarkingStatisticServiceImpl implements MaterialMarkingStatisticService {

    private final Logger log = LoggerFactory.getLogger(MaterialMarkingStatisticServiceImpl.class);

    private final MaterialMarkingStatisticRepository materialMarkingStatisticRepository;

    public MaterialMarkingStatisticServiceImpl(MaterialMarkingStatisticRepository materialMarkingStatisticRepository) {
        this.materialMarkingStatisticRepository = materialMarkingStatisticRepository;
    }

    @Override
    public MaterialMarkingStatistic save(MaterialMarkingStatistic materialMarkingStatistic) {
        log.debug("Request to save MaterialMarkingStatistic : {}", materialMarkingStatistic);
        return materialMarkingStatisticRepository.save(materialMarkingStatistic);
    }

    @Override
    public Optional<MaterialMarkingStatistic> partialUpdate(MaterialMarkingStatistic materialMarkingStatistic) {
        log.debug("Request to partially update MaterialMarkingStatistic : {}", materialMarkingStatistic);

        return materialMarkingStatisticRepository
            .findById(materialMarkingStatistic.getId())
            .map(existingMaterialMarkingStatistic -> {
                if (materialMarkingStatistic.getMarkingType() != null) {
                    existingMaterialMarkingStatistic.setMarkingType(materialMarkingStatistic.getMarkingType());
                }
                if (materialMarkingStatistic.getMarkingTechnique() != null) {
                    existingMaterialMarkingStatistic.setMarkingTechnique(materialMarkingStatistic.getMarkingTechnique());
                }
                if (materialMarkingStatistic.getMeterPerSecondSpeed() != null) {
                    existingMaterialMarkingStatistic.setMeterPerSecondSpeed(materialMarkingStatistic.getMeterPerSecondSpeed());
                }

                return existingMaterialMarkingStatistic;
            })
            .map(materialMarkingStatisticRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaterialMarkingStatistic> findAll() {
        log.debug("Request to get all MaterialMarkingStatistics");
        return materialMarkingStatisticRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MaterialMarkingStatistic> findOne(Long id) {
        log.debug("Request to get MaterialMarkingStatistic : {}", id);
        return materialMarkingStatisticRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MaterialMarkingStatistic : {}", id);
        materialMarkingStatisticRepository.deleteById(id);
    }
}
