package com.muller.lappli.service.impl;

import com.muller.lappli.domain.FlatSheathing;
import com.muller.lappli.repository.FlatSheathingRepository;
import com.muller.lappli.service.FlatSheathingService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FlatSheathing}.
 */
@Service
@Transactional
public class FlatSheathingServiceImpl implements FlatSheathingService {

    private final Logger log = LoggerFactory.getLogger(FlatSheathingServiceImpl.class);

    private final FlatSheathingRepository flatSheathingRepository;

    public FlatSheathingServiceImpl(FlatSheathingRepository flatSheathingRepository) {
        this.flatSheathingRepository = flatSheathingRepository;
    }

    @Override
    public FlatSheathing save(FlatSheathing flatSheathing) {
        log.debug("Request to save FlatSheathing : {}", flatSheathing);
        return flatSheathingRepository.save(flatSheathing);
    }

    @Override
    public Optional<FlatSheathing> partialUpdate(FlatSheathing flatSheathing) {
        log.debug("Request to partially update FlatSheathing : {}", flatSheathing);

        return flatSheathingRepository
            .findById(flatSheathing.getId())
            .map(existingFlatSheathing -> {
                if (flatSheathing.getOperationLayer() != null) {
                    existingFlatSheathing.setOperationLayer(flatSheathing.getOperationLayer());
                }
                if (flatSheathing.getMilimeterWidth() != null) {
                    existingFlatSheathing.setMilimeterWidth(flatSheathing.getMilimeterWidth());
                }
                if (flatSheathing.getMilimeterHeight() != null) {
                    existingFlatSheathing.setMilimeterHeight(flatSheathing.getMilimeterHeight());
                }

                return existingFlatSheathing;
            })
            .map(flatSheathingRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FlatSheathing> findAll() {
        log.debug("Request to get all FlatSheathings");
        return flatSheathingRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FlatSheathing> findOne(Long id) {
        log.debug("Request to get FlatSheathing : {}", id);
        return flatSheathingRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FlatSheathing : {}", id);
        flatSheathingRepository.deleteById(id);
    }
}
