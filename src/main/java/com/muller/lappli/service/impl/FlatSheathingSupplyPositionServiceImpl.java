package com.muller.lappli.service.impl;

import com.muller.lappli.domain.FlatSheathingSupplyPosition;
import com.muller.lappli.repository.FlatSheathingSupplyPositionRepository;
import com.muller.lappli.repository.SupplyPositionRepository;
import com.muller.lappli.service.FlatSheathingSupplyPositionService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FlatSheathingSupplyPosition}.
 */
@Service
@Transactional
public class FlatSheathingSupplyPositionServiceImpl implements FlatSheathingSupplyPositionService {

    private final Logger log = LoggerFactory.getLogger(FlatSheathingSupplyPositionServiceImpl.class);

    private final FlatSheathingSupplyPositionRepository flatSheathingSupplyPositionRepository;

    private final SupplyPositionRepository supplyPositionRepository;

    public FlatSheathingSupplyPositionServiceImpl(
        FlatSheathingSupplyPositionRepository flatSheathingSupplyPositionRepository,
        SupplyPositionRepository supplyPositionRepository
    ) {
        this.flatSheathingSupplyPositionRepository = flatSheathingSupplyPositionRepository;
        this.supplyPositionRepository = supplyPositionRepository;
    }

    @Override
    public FlatSheathingSupplyPosition save(FlatSheathingSupplyPosition flatSheathingSupplyPosition) {
        log.debug("Request to save FlatSheathingSupplyPosition : {}", flatSheathingSupplyPosition);
        Long supplyPositionId = flatSheathingSupplyPosition.getSupplyPosition().getId();
        supplyPositionRepository.findById(supplyPositionId).ifPresent(flatSheathingSupplyPosition::supplyPosition);
        return flatSheathingSupplyPositionRepository.save(flatSheathingSupplyPosition);
    }

    @Override
    public Optional<FlatSheathingSupplyPosition> partialUpdate(FlatSheathingSupplyPosition flatSheathingSupplyPosition) {
        log.debug("Request to partially update FlatSheathingSupplyPosition : {}", flatSheathingSupplyPosition);

        return flatSheathingSupplyPositionRepository
            .findById(flatSheathingSupplyPosition.getId())
            .map(existingFlatSheathingSupplyPosition -> {
                if (flatSheathingSupplyPosition.getLocationInOwnerFlatSheathing() != null) {
                    existingFlatSheathingSupplyPosition.setLocationInOwnerFlatSheathing(
                        flatSheathingSupplyPosition.getLocationInOwnerFlatSheathing()
                    );
                }

                return existingFlatSheathingSupplyPosition;
            })
            .map(flatSheathingSupplyPositionRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FlatSheathingSupplyPosition> findAll() {
        log.debug("Request to get all FlatSheathingSupplyPositions");
        return flatSheathingSupplyPositionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FlatSheathingSupplyPosition> findOne(Long id) {
        log.debug("Request to get FlatSheathingSupplyPosition : {}", id);
        return flatSheathingSupplyPositionRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FlatSheathingSupplyPosition : {}", id);
        flatSheathingSupplyPositionRepository.deleteById(id);
    }
}
