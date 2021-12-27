package com.muller.lappli.service.impl;

import com.muller.lappli.domain.OneStudySupply;
import com.muller.lappli.repository.OneStudySupplyRepository;
import com.muller.lappli.service.LifterService;
import com.muller.lappli.service.OneStudySupplyService;
import com.muller.lappli.service.abstracts.AbstractLiftedSupplyServiceImpl;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OneStudySupply}.
 */
@Service
@Transactional
public class OneStudySupplyServiceImpl extends AbstractLiftedSupplyServiceImpl<OneStudySupply> implements OneStudySupplyService {

    private final Logger log = LoggerFactory.getLogger(OneStudySupplyServiceImpl.class);

    private final OneStudySupplyRepository oneStudySupplyRepository;

    public OneStudySupplyServiceImpl(LifterService lifterService, OneStudySupplyRepository oneStudySupplyRepository) {
        super(lifterService);
        this.oneStudySupplyRepository = oneStudySupplyRepository;
    }

    @Override
    public OneStudySupply save(OneStudySupply oneStudySupply) {
        log.debug("Request to save OneStudySupply : {}", oneStudySupply);
        return onRead(oneStudySupplyRepository.save(oneStudySupply));
    }

    @Override
    public Optional<OneStudySupply> partialUpdate(OneStudySupply oneStudySupply) {
        log.debug("Request to partially update OneStudySupply : {}", oneStudySupply);

        return onOptionalRead(
            oneStudySupplyRepository
                .findById(oneStudySupply.getId())
                .map(existingOneStudySupply -> {
                    if (oneStudySupply.getApparitions() != null) {
                        existingOneStudySupply.setApparitions(oneStudySupply.getApparitions());
                    }
                    if (oneStudySupply.getNumber() != null) {
                        existingOneStudySupply.setNumber(oneStudySupply.getNumber());
                    }
                    if (oneStudySupply.getDesignation() != null) {
                        existingOneStudySupply.setDesignation(oneStudySupply.getDesignation());
                    }
                    if (oneStudySupply.getDescription() != null) {
                        existingOneStudySupply.setDescription(oneStudySupply.getDescription());
                    }
                    if (oneStudySupply.getMarkingType() != null) {
                        existingOneStudySupply.setMarkingType(oneStudySupply.getMarkingType());
                    }
                    if (oneStudySupply.getGramPerMeterLinearMass() != null) {
                        existingOneStudySupply.setGramPerMeterLinearMass(oneStudySupply.getGramPerMeterLinearMass());
                    }
                    if (oneStudySupply.getMilimeterDiameter() != null) {
                        existingOneStudySupply.setMilimeterDiameter(oneStudySupply.getMilimeterDiameter());
                    }
                    if (oneStudySupply.getSurfaceColor() != null) {
                        existingOneStudySupply.setSurfaceColor(oneStudySupply.getSurfaceColor());
                    }

                    return existingOneStudySupply;
                })
                .map(oneStudySupplyRepository::save)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<OneStudySupply> findAll() {
        log.debug("Request to get all OneStudySupplies");
        return onListRead(oneStudySupplyRepository.findAll());
    }

    /**
     *  Get all the oneStudySupplies where Position is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<OneStudySupply> findAllWherePositionIsNull() {
        log.debug("Request to get all oneStudySupplies where Position is null");
        return StreamSupport
            .stream(oneStudySupplyRepository.findAll().spliterator(), false)
            .filter(oneStudySupply -> oneStudySupply.getPosition() == null)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OneStudySupply> findOne(Long id) {
        log.debug("Request to get OneStudySupply : {}", id);
        return onOptionalRead(oneStudySupplyRepository.findById(id));
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OneStudySupply : {}", id);
        oneStudySupplyRepository.deleteById(id);
    }
}
