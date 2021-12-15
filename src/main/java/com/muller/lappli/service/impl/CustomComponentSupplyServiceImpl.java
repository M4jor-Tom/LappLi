package com.muller.lappli.service.impl;

import com.muller.lappli.domain.CustomComponentSupply;
import com.muller.lappli.repository.CustomComponentSupplyRepository;
import com.muller.lappli.repository.LifterRepository;
import com.muller.lappli.service.CustomComponentSupplyService;
import com.muller.lappli.service.LifterService;
import com.muller.lappli.service.abstracts.AbstractLiftedSupplyService;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
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
    extends AbstractLiftedSupplyService<CustomComponentSupply>
    implements CustomComponentSupplyService {

    private final Logger log = LoggerFactory.getLogger(CustomComponentSupplyServiceImpl.class);

    private final CustomComponentSupplyRepository customComponentSupplyRepository;

    public CustomComponentSupplyServiceImpl(
        LifterRepository lifterRepository,
        LifterService lifterService,
        CustomComponentSupplyRepository customComponentSupplyRepository
    ) {
        super(customComponentSupplyRepository, lifterService);
        this.customComponentSupplyRepository = customComponentSupplyRepository;
    }

    @Override
    public CustomComponentSupply save(CustomComponentSupply customComponentSupply) {
        log.debug("Request to save CustomComponentSupply : {}", customComponentSupply);
        return customComponentSupplyRepository.save(customComponentSupply);
    }

    @Override
    public Optional<CustomComponentSupply> partialUpdate(CustomComponentSupply customComponentSupply) {
        log.debug("Request to partially update CustomComponentSupply : {}", customComponentSupply);

        return customComponentSupplyRepository
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
            .map(customComponentSupplyRepository::save);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustomComponentSupply : {}", id);
        customComponentSupplyRepository.deleteById(id);
    }

    @Override
    public Set<CustomComponentSupply> findByStrandId(Long id) {
        Set<CustomComponentSupply> customComponentSupplyList = new HashSet<>();
        for (CustomComponentSupply customComponentSupply : customComponentSupplyRepository.findAll()) {
            if (customComponentSupply.getStrand().getId().equals(id)) {
                customComponentSupplyList.add(customComponentSupply);
            }
        }

        return customComponentSupplyList;
    }
}
