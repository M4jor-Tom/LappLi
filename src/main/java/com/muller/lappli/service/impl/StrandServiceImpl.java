package com.muller.lappli.service.impl;

import com.muller.lappli.domain.Strand;
import com.muller.lappli.repository.StrandRepository;
import com.muller.lappli.service.BangleSupplyService;
import com.muller.lappli.service.CustomComponentSupplyService;
import com.muller.lappli.service.ElementSupplyService;
import com.muller.lappli.service.StrandService;
import com.muller.lappli.service.abstracts.AbstractSpecificationExecutorService;
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
public class StrandServiceImpl extends AbstractSpecificationExecutorService<Strand> implements StrandService {

    private final Logger log = LoggerFactory.getLogger(StrandServiceImpl.class);

    private final StrandRepository strandRepository;

    private final BangleSupplyService bangleSupplyService;

    private final CustomComponentSupplyService customComponentSupplyService;

    private final ElementSupplyService elementSupplyService;

    public StrandServiceImpl(
        StrandRepository strandRepository,
        BangleSupplyService bangleSupplyService,
        CustomComponentSupplyService customComponentSupplyService,
        ElementSupplyService elementSupplyService
    ) {
        super(strandRepository);
        this.strandRepository = strandRepository;
        this.bangleSupplyService = bangleSupplyService;
        this.customComponentSupplyService = customComponentSupplyService;
        this.elementSupplyService = elementSupplyService;
    }

    @Override
    public Strand save(Strand strand) {
        log.debug("Request to save Strand : {}", strand);
        return strandRepository.save(strand);
    }

    @Override
    public Optional<Strand> partialUpdate(Strand strand) {
        log.debug("Request to partially update Strand : {}", strand);

        return strandRepository
            .findById(strand.getId())
            .map(existingStrand -> {
                if (strand.getDesignation() != null) {
                    existingStrand.setDesignation(strand.getDesignation());
                }
                if (strand.getHousingOperationType() != null) {
                    existingStrand.setHousingOperationType(strand.getHousingOperationType());
                }

                return existingStrand;
            })
            .map(strandRepository::save);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Strand : {}", id);
        strandRepository.deleteById(id);
    }

    @Override
    protected Strand onDomainObjectGetting(Strand domainObject) {
        Long strandId = domainObject.getId();

        return domainObject
            .bangleSupplies(bangleSupplyService.findByStrandId(strandId))
            .customComponentSupplies(customComponentSupplyService.findByStrandId(strandId))
            .elementSupplies(elementSupplyService.findByStrandId(strandId));
    }
}
