package com.muller.lappli.service.impl;

import com.muller.lappli.domain.Strand;
import com.muller.lappli.domain.SupplyPosition;
import com.muller.lappli.repository.StrandRepository;
import com.muller.lappli.service.StrandService;
import com.muller.lappli.service.SupplyPositionService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Strand}.
 */
@Service
@Transactional
public class StrandServiceImpl implements StrandService {

    private final Logger log = LoggerFactory.getLogger(StrandServiceImpl.class);

    private final StrandRepository strandRepository;

    private final SupplyPositionService supplyPositionService;

    public StrandServiceImpl(StrandRepository strandRepository, SupplyPositionService supplyPositionService) {
        this.strandRepository = strandRepository;
        this.supplyPositionService = supplyPositionService;
    }

    @Override
    public Strand save(Strand strand) {
        log.debug("Request to save Strand : {}", strand);
        return onRead(strandRepository.save(strand));
    }

    private void saveSupplyPositions(Iterable<SupplyPosition> supplyPositions) {
        for (SupplyPosition supplyPosition : supplyPositions) {
            supplyPositionService.save(supplyPosition);
        }
    }

    @Override
    public Optional<Strand> partialUpdate(Strand strand) {
        log.debug("Request to partially update Strand : {}", strand);

        return onOptionalRead(
            strandRepository
                .findById(strand.getId())
                .map(existingStrand -> {
                    return existingStrand;
                })
                .map(strandRepository::save)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<Strand> findAll() {
        log.debug("Request to get all Strands");
        return onListRead(strandRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Strand> findOne(Long id) {
        log.debug("Request to get Strand : {}", id);
        return onOptionalRead(strandRepository.findById(id));
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Strand : {}", id);
        strandRepository.deleteById(id);
    }

    @Override
    public Strand onRead(Strand domainObject) {
        saveSupplyPositions(domainObject.getSupplyPositions());
        return domainObject;
    }
}
