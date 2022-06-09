package com.muller.lappli.service.impl;

import com.muller.lappli.domain.FlatSheathingSupplyPosition;
import com.muller.lappli.repository.FlatSheathingSupplyPositionRepository;
import com.muller.lappli.service.FlatSheathingSupplyPositionService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
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

    public FlatSheathingSupplyPositionServiceImpl(FlatSheathingSupplyPositionRepository flatSheathingSupplyPositionRepository) {
        this.flatSheathingSupplyPositionRepository = flatSheathingSupplyPositionRepository;
    }

    @Override
    public FlatSheathingSupplyPosition save(FlatSheathingSupplyPosition flatSheathingSupplyPosition) {
        log.debug("Request to save FlatSheathingSupplyPosition : {}", flatSheathingSupplyPosition);
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

    /**
     *  Get all the flatSheathingSupplyPositions where SupplyPosition is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<FlatSheathingSupplyPosition> findAllWhereSupplyPositionIsNull() {
        log.debug("Request to get all flatSheathingSupplyPositions where SupplyPosition is null");
        return StreamSupport
            .stream(flatSheathingSupplyPositionRepository.findAll().spliterator(), false)
            .filter(flatSheathingSupplyPosition -> flatSheathingSupplyPosition.getSupplyPosition() == null)
            .collect(Collectors.toList());
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
