package com.muller.lappli.service.impl;

import com.muller.lappli.domain.Strand;
import com.muller.lappli.domain.SupplyPosition;
import com.muller.lappli.repository.StrandRepository;
import com.muller.lappli.service.StrandService;
import com.muller.lappli.service.SupplyPositionService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
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
        saveSupplyPositions(strand.getSupplyPositions());
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
                    if (strand.getDiameterAssemblyStep() != null) {
                        existingStrand.setDiameterAssemblyStep(strand.getDiameterAssemblyStep());
                    }
                    if (strand.getAssemblyMean() != null) {
                        existingStrand.setAssemblyMean(strand.getAssemblyMean());
                    }
                    if (strand.getForcesCentralUtilityComponent() != null) {
                        existingStrand.setForcesCentralUtilityComponent(strand.getForcesCentralUtilityComponent());
                    }

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

    /**
     *  Get all the strands where CentralAssembly is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Strand> findAllWhereCentralAssemblyIsNull() {
        log.debug("Request to get all strands where CentralAssembly is null");
        return onListRead(
            StreamSupport
                .stream(strandRepository.findAll().spliterator(), false)
                .filter(strand -> strand.getCentralAssembly() == null)
                .collect(Collectors.toList())
        );
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
        return domainObject;
    }
}
