package com.muller.lappli.service.impl;

import com.muller.lappli.domain.BangleSupply;
import com.muller.lappli.domain.CustomComponentSupply;
import com.muller.lappli.domain.ElementSupply;
import com.muller.lappli.domain.OneStudySupply;
import com.muller.lappli.domain.SupplyPosition;
import com.muller.lappli.domain.abstracts.AbstractSupply;
import com.muller.lappli.repository.SupplyPositionRepository;
import com.muller.lappli.service.BangleSupplyService;
import com.muller.lappli.service.CustomComponentSupplyService;
import com.muller.lappli.service.ElementSupplyService;
import com.muller.lappli.service.OneStudySupplyService;
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
 * Service Implementation for managing {@link SupplyPosition}.
 */
@Service
@Transactional
public class SupplyPositionServiceImpl implements SupplyPositionService {

    private final Logger log = LoggerFactory.getLogger(SupplyPositionServiceImpl.class);

    private final SupplyPositionRepository supplyPositionRepository;

    private final BangleSupplyService bangleSupplyService;

    private final CustomComponentSupplyService customComponentSupplyService;

    private final ElementSupplyService elementSupplyService;

    private final OneStudySupplyService oneStudySupplyService;

    public SupplyPositionServiceImpl(
        SupplyPositionRepository supplyPositionRepository,
        BangleSupplyService bangleSupplyService,
        CustomComponentSupplyService customComponentSupplyService,
        ElementSupplyService elementSupplyService,
        OneStudySupplyService oneStudySupplyService
    ) {
        this.supplyPositionRepository = supplyPositionRepository;
        this.bangleSupplyService = bangleSupplyService;
        this.customComponentSupplyService = customComponentSupplyService;
        this.elementSupplyService = elementSupplyService;
        this.oneStudySupplyService = oneStudySupplyService;
    }

    private void saveSupply(SupplyPosition supplyPosition) {
        AbstractSupply<?> supply = supplyPosition.getSupply();
        if (supply != null) {
            switch (supply.getSupplyKind()) {
                case BANGLE:
                    bangleSupplyService.save((BangleSupply) supply);
                    break;
                case CUSTOM_COMPONENT:
                    customComponentSupplyService.save((CustomComponentSupply) supply);
                    break;
                case ELEMENT:
                    elementSupplyService.save((ElementSupply) supply);
                    break;
                case ONE_STUDY:
                    oneStudySupplyService.save((OneStudySupply) supply);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public SupplyPosition save(SupplyPosition supplyPosition) {
        log.debug("Request to save SupplyPosition : {}", supplyPosition);
        saveSupply(supplyPosition);
        return supplyPositionRepository.save(supplyPosition);
    }

    @Override
    public Optional<SupplyPosition> partialUpdate(SupplyPosition supplyPosition) {
        log.debug("Request to partially update SupplyPosition : {}", supplyPosition);

        return supplyPositionRepository
            .findById(supplyPosition.getId())
            .map(existingSupplyPosition -> {
                if (supplyPosition.getSupplyApparitionsUsage() != null) {
                    existingSupplyPosition.setSupplyApparitionsUsage(supplyPosition.getSupplyApparitionsUsage());
                }

                return existingSupplyPosition;
            })
            .map(supplyPositionRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SupplyPosition> findAll() {
        log.debug("Request to get all SupplyPositions");
        return supplyPositionRepository.findAll();
    }

    /**
     *  Get all the supplyPositions where OwnerCentralAssembly is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SupplyPosition> findAllWhereOwnerCentralAssemblyIsNull() {
        log.debug("Request to get all supplyPositions where OwnerCentralAssembly is null");
        return StreamSupport
            .stream(supplyPositionRepository.findAll().spliterator(), false)
            .filter(supplyPosition -> supplyPosition.getOwnerCentralAssembly() == null)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SupplyPosition> findOne(Long id) {
        log.debug("Request to get SupplyPosition : {}", id);
        return supplyPositionRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SupplyPosition : {}", id);
        supplyPositionRepository.deleteById(id);
    }
}
