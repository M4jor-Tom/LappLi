package com.muller.lappli.service.impl;

import com.muller.lappli.domain.Position;
import com.muller.lappli.repository.PositionRepository;
import com.muller.lappli.service.PositionService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Position}.
 */
@Service
@Transactional
public class PositionServiceImpl implements PositionService {

    private final Logger log = LoggerFactory.getLogger(PositionServiceImpl.class);

    private final PositionRepository positionRepository;

    public PositionServiceImpl(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    @Override
    public Position save(Position position) {
        log.debug("Request to save Position : {}", position);
        return positionRepository.save(position);
    }

    @Override
    public Optional<Position> partialUpdate(Position position) {
        log.debug("Request to partially update Position : {}", position);

        return positionRepository
            .findById(position.getId())
            .map(existingPosition -> {
                if (position.getValue() != null) {
                    existingPosition.setValue(position.getValue());
                }

                return existingPosition;
            })
            .map(positionRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Position> findAll() {
        log.debug("Request to get all Positions");
        return positionRepository.findAll();
    }

    /**
     *  Get all the positions where OwnerCentralAssembly is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Position> findAllWhereOwnerCentralAssemblyIsNull() {
        log.debug("Request to get all positions where OwnerCentralAssembly is null");
        return StreamSupport
            .stream(positionRepository.findAll().spliterator(), false)
            .filter(position -> position.getOwnerCentralAssembly() == null)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Position> findOne(Long id) {
        log.debug("Request to get Position : {}", id);
        return positionRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Position : {}", id);
        positionRepository.deleteById(id);
    }
}
