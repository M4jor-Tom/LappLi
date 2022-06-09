package com.muller.lappli.service.impl;

import com.muller.lappli.domain.FlatSheathing;
import com.muller.lappli.repository.FlatSheathingRepository;
import com.muller.lappli.service.FlatSheathingService;
import com.muller.lappli.service.StrandSupplyService;
import com.muller.lappli.service.abstracts.AbstractNonCentralOperationServiceImpl;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FlatSheathing}.
 */
@Service
@Transactional
public class FlatSheathingServiceImpl extends AbstractNonCentralOperationServiceImpl<FlatSheathing> implements FlatSheathingService {

    private final Logger log = LoggerFactory.getLogger(FlatSheathingServiceImpl.class);

    public FlatSheathingServiceImpl(FlatSheathingRepository flatSheathingRepository, StrandSupplyService strandSupplyService) {
        super(strandSupplyService, flatSheathingRepository);
    }

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    protected String getDomainClassName() {
        return "FlatSheathing";
    }

    @Override
    public Optional<FlatSheathing> partialUpdate(FlatSheathing flatSheathing) {
        log.debug("Request to partially update FlatSheathing : {}", flatSheathing);

        return getJpaRepository()
            .findById(flatSheathing.getId())
            .map(existingFlatSheathing -> {
                if (flatSheathing.getSheathingKind() != null) {
                    existingFlatSheathing.setSheathingKind(flatSheathing.getSheathingKind());
                }
                if (flatSheathing.getMilimeterWidth() != null) {
                    existingFlatSheathing.setMilimeterWidth(flatSheathing.getMilimeterWidth());
                }
                if (flatSheathing.getMilimeterHeight() != null) {
                    existingFlatSheathing.setMilimeterHeight(flatSheathing.getMilimeterHeight());
                }

                return existingFlatSheathing;
            })
            .map(getJpaRepository()::save);
    }
}
