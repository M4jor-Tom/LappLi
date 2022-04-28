package com.muller.lappli.service.impl;

import com.muller.lappli.domain.Sheathing;
import com.muller.lappli.repository.SheathingRepository;
import com.muller.lappli.service.SheathingService;
import com.muller.lappli.service.StrandSupplyService;
import com.muller.lappli.service.abstracts.AbstractNonCentralOperationServiceImpl;
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

    public SheathingServiceImpl(SheathingRepository sheathingRepository, StrandSupplyService strandSupplyService) {
        super(strandSupplyService, sheathingRepository);
    }

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    protected String getDomainClassName() {
        return "Sheathing";
    }

    @Override
    public Optional<Sheathing> partialUpdate(Sheathing sheathing) {
        log.debug("Request to partially update Sheathing : {}", sheathing);

        return getJpaRepository()
            .findById(sheathing.getId())
            .map(existingSheathing -> {
                if (sheathing.getMilimeterThickness() != null) {
                    existingSheathing.setMilimeterThickness(sheathing.getMilimeterThickness());
                }
                if (sheathing.getSheathingKind() != null) {
                    existingSheathing.setSheathingKind(sheathing.getSheathingKind());
                }

                return existingSheathing;
            })
            .map(getJpaRepository()::save);
    }
}
