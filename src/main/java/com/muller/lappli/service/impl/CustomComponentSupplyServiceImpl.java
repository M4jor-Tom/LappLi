package com.muller.lappli.service.impl;

import com.muller.lappli.domain.CustomComponentSupply;
import com.muller.lappli.repository.CustomComponentSupplyRepository;
import com.muller.lappli.service.CustomComponentSupplyService;
import com.muller.lappli.service.LifterService;
import com.muller.lappli.service.abstracts.AbstractLiftedSupplyServiceImpl;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CustomComponentSupply}.
 */
@Service
@Transactional
public class CustomComponentSupplyServiceImpl
    extends AbstractLiftedSupplyServiceImpl<CustomComponentSupply>
    implements CustomComponentSupplyService {

    private final Logger log = LoggerFactory.getLogger(CustomComponentSupplyServiceImpl.class);

    private final CustomComponentSupplyRepository customComponentSupplyRepository;

    public CustomComponentSupplyServiceImpl(LifterService lifterService, CustomComponentSupplyRepository customComponentSupplyRepository) {
        super(lifterService);
        this.customComponentSupplyRepository = customComponentSupplyRepository;
    }

    @Override
    public CustomComponentSupply save(CustomComponentSupply customComponentSupply) {
        log.debug("Request to save CustomComponentSupply : {}", customComponentSupply);
        return onRead(customComponentSupplyRepository.save(customComponentSupply));
    }

    @Override
    public Optional<CustomComponentSupply> partialUpdate(CustomComponentSupply customComponentSupply) {
        log.debug("Request to partially update CustomComponentSupply : {}", customComponentSupply);

        return onOptionalRead(
            customComponentSupplyRepository
                .findById(customComponentSupply.getId())
                .map(existingCustomComponentSupply -> {
                    if (customComponentSupply.getApparitions() != null) {
                        existingCustomComponentSupply.setApparitions(customComponentSupply.getApparitions());
                    }
                    if (customComponentSupply.getDescription() != null) {
                        existingCustomComponentSupply.setDescription(customComponentSupply.getDescription());
                    }
                    if (customComponentSupply.getMarkingType() != null) {
                        existingCustomComponentSupply.setMarkingType(customComponentSupply.getMarkingType());
                    }

                    return existingCustomComponentSupply;
                })
                .map(customComponentSupplyRepository::save)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomComponentSupply> findAll() {
        log.debug("Request to get all CustomComponentSupplies");
        return onListRead(customComponentSupplyRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustomComponentSupply> findOne(Long id) {
        log.debug("Request to get CustomComponentSupply : {}", id);
        return onOptionalRead(customComponentSupplyRepository.findById(id));
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustomComponentSupply : {}", id);
        customComponentSupplyRepository.deleteById(id);
    }
}
