package com.muller.lappli.service.impl;

import com.muller.lappli.domain.StrandSupply;
import com.muller.lappli.domain.abstracts.AbstractSupply;
import com.muller.lappli.repository.StrandSupplyRepository;
import com.muller.lappli.service.StrandSupplyService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link StrandSupply}.
 */
@Service
@Transactional
public class StrandSupplyServiceImpl implements StrandSupplyService {

    private final Logger log = LoggerFactory.getLogger(StrandSupplyServiceImpl.class);

    private final StrandSupplyRepository strandSupplyRepository;

    public StrandSupplyServiceImpl(StrandSupplyRepository strandSupplyRepository) {
        this.strandSupplyRepository = strandSupplyRepository;
    }

    public StrandSupply onRead(StrandSupply domainObject) {
        if (domainObject != null && domainObject.getStrand() != null) {
            for (AbstractSupply<?> supply : domainObject.getStrand().getSupplies()) {
                supply.setObserverStrandSupply(domainObject);
            }
        }

        return domainObject;
    }

    @Override
    public StrandSupply save(StrandSupply strandSupply) {
        log.debug("Request to save StrandSupply : {}", strandSupply);
        return onRead(strandSupplyRepository.save(strandSupply));
    }

    @Override
    public Optional<StrandSupply> partialUpdate(StrandSupply strandSupply) {
        log.debug("Request to partially update StrandSupply : {}", strandSupply);

        return onOptionalRead(
            strandSupplyRepository
                .findById(strandSupply.getId())
                .map(existingStrandSupply -> {
                    if (strandSupply.getApparitions() != null) {
                        existingStrandSupply.setApparitions(strandSupply.getApparitions());
                    }
                    if (strandSupply.getMarkingType() != null) {
                        existingStrandSupply.setMarkingType(strandSupply.getMarkingType());
                    }
                    if (strandSupply.getDescription() != null) {
                        existingStrandSupply.setDescription(strandSupply.getDescription());
                    }
                    if (strandSupply.getDiameterAssemblyStep() != null) {
                        existingStrandSupply.setDiameterAssemblyStep(strandSupply.getDiameterAssemblyStep());
                    }
                    if (strandSupply.getAssemblyMean() != null) {
                        existingStrandSupply.setAssemblyMean(strandSupply.getAssemblyMean());
                    }
                    if (strandSupply.getForceCentralUtilityComponent() != null) {
                        existingStrandSupply.setForceCentralUtilityComponent(strandSupply.getForceCentralUtilityComponent());
                    }

                    return existingStrandSupply;
                })
                .map(strandSupplyRepository::save)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<StrandSupply> findAll() {
        log.debug("Request to get all StrandSupplies");
        return onListRead(strandSupplyRepository.findAll());
    }

    /**
     *  Get all the strandSupplies where CentralAssembly is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<StrandSupply> findAllWhereCentralAssemblyIsNull() {
        log.debug("Request to get all strandSupplies where CentralAssembly is null");
        return StreamSupport
            .stream(strandSupplyRepository.findAll().spliterator(), false)
            .filter(strandSupply -> strandSupply.getCentralAssembly() == null)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StrandSupply> findOne(Long id, Boolean autoGenerateAssemblies) {
        log.debug("Request to get StrandSupply : {}", id);
        return onOptionalRead(strandSupplyRepository.findById(id));
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete StrandSupply : {}", id);
        strandSupplyRepository.deleteById(id);
    }
}
