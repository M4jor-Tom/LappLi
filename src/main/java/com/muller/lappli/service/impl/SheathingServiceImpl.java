package com.muller.lappli.service.impl;

import com.muller.lappli.domain.Sheathing;
import com.muller.lappli.repository.SheathingRepository;
import com.muller.lappli.service.SheathingService;
import com.muller.lappli.service.StrandSupplyService;
import com.muller.lappli.service.abstracts.AbstractNonCentralOperationServiceImpl;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Sheathing}.
 */
@Service
@Transactional
public class SheathingServiceImpl extends AbstractNonCentralOperationServiceImpl<Sheathing> implements SheathingService {

    private final Logger log = LoggerFactory.getLogger(SheathingServiceImpl.class);

    private final SheathingRepository sheathingRepository;

    public SheathingServiceImpl(SheathingRepository sheathingRepository, StrandSupplyService strandSupplyService) {
        super(strandSupplyService);
        this.sheathingRepository = sheathingRepository;
    }

    @Override
    public Sheathing save(Sheathing sheathing, Boolean actualizeOwnerStrandSupply) {
        log.debug("Request to save Sheathing : {}", sheathing);

        if (actualizeOwnerStrandSupply) {
            actualizeOwnerStrandSupply(sheathing);
        }
        return sheathingRepository.save(sheathing);
    }

    @Override
    public Optional<Sheathing> partialUpdate(Sheathing sheathing) {
        log.debug("Request to partially update Sheathing : {}", sheathing);

        return sheathingRepository
            .findById(sheathing.getId())
            .map(existingSheathing -> {
                if (sheathing.getOperationLayer() != null) {
                    existingSheathing.setOperationLayer(sheathing.getOperationLayer());
                }
                if (sheathing.getMilimeterThickness() != null) {
                    existingSheathing.setMilimeterThickness(sheathing.getMilimeterThickness());
                }
                if (sheathing.getSheathingKind() != null) {
                    existingSheathing.setSheathingKind(sheathing.getSheathingKind());
                }
                return existingSheathing;
            })
            .map(sheathingRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Sheathing> findAll() {
        log.debug("Request to get all Sheathings");
        return sheathingRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Sheathing> findOne(Long id) {
        log.debug("Request to get Sheathing : {}", id);
        return sheathingRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Sheathing : {}", id);
        sheathingRepository.deleteById(id);
    }
}
