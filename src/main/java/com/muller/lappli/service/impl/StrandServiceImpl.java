package com.muller.lappli.service.impl;

import com.muller.lappli.domain.Strand;
import com.muller.lappli.domain.StrandSupply;
import com.muller.lappli.domain.abstracts.AbstractSupply;
import com.muller.lappli.repository.StrandRepository;
import com.muller.lappli.service.BangleSupplyService;
import com.muller.lappli.service.CustomComponentSupplyService;
import com.muller.lappli.service.ElementSupplyService;
import com.muller.lappli.service.OneStudySupplyService;
import com.muller.lappli.service.StrandService;
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
 * Service Implementation for managing {@link Strand}.
 */
@Service
@Transactional
public class StrandServiceImpl implements StrandService {

    private final Logger log = LoggerFactory.getLogger(StrandServiceImpl.class);

    private final StrandRepository strandRepository;

    private final BangleSupplyService bangleSupplyService;

    private final CustomComponentSupplyService customComponentSupplyService;

    private final ElementSupplyService elementSupplyService;

    private final OneStudySupplyService oneStudySupplyService;

    private final StrandSupplyService strandSupplyService;

    public StrandServiceImpl(
        StrandRepository strandRepository,
        BangleSupplyService bangleSupplyService,
        CustomComponentSupplyService customComponentSupplyService,
        ElementSupplyService elementSupplyService,
        OneStudySupplyService oneStudySupplyService,
        StrandSupplyService strandSupplyService
    ) {
        this.strandRepository = strandRepository;
        this.bangleSupplyService = bangleSupplyService;
        this.customComponentSupplyService = customComponentSupplyService;
        this.elementSupplyService = elementSupplyService;
        this.oneStudySupplyService = oneStudySupplyService;
        this.strandSupplyService = strandSupplyService;
    }

    @Override
    public Strand save(Strand strand) {
        log.debug("Request to save Strand : {}", strand);
        return onRead(strandRepository.save(strand));
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
    public Optional<Strand> findOneByStrandSupplyId(Long strandSupplyId) {
        Optional<StrandSupply> strandSupplyOptional = strandSupplyService.findOne(strandSupplyId);

        Optional<Strand> strandOptional = Optional.empty();

        if (strandSupplyOptional.isPresent()) {
            strandOptional = findOne(strandSupplyOptional.get().getStrand().getId());

            if (strandOptional.isPresent()) {
                for (AbstractSupply<?> supply : strandOptional.get().getSupplies()) {
                    supply.setObserverStrandSupply(strandSupplyOptional.get());
                }
            }
        }

        return strandOptional;
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
        return domainObject
            .bangleSupplies(bangleSupplyService.onSetRead(domainObject.getBangleSupplies()))
            .customComponentSupplies(customComponentSupplyService.onSetRead(domainObject.getCustomComponentSupplies()))
            .elementSupplies(elementSupplyService.onSetRead(domainObject.getElementSupplies()))
            .oneStudySupplies(oneStudySupplyService.onSetRead(domainObject.getOneStudySupplies()));
    }
}
