package com.muller.lappli.service.impl;

import com.muller.lappli.domain.ContinuityWireLongitLaying;
import com.muller.lappli.repository.ContinuityWireLongitLayingRepository;
import com.muller.lappli.service.ContinuityWireLongitLayingService;
import com.muller.lappli.service.StrandSupplyService;
import com.muller.lappli.service.abstracts.AbstractNonCentralOperationServiceImpl;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ContinuityWireLongitLaying}.
 */
@Service
@Transactional
public class ContinuityWireLongitLayingServiceImpl
    extends AbstractNonCentralOperationServiceImpl<ContinuityWireLongitLaying>
    implements ContinuityWireLongitLayingService {

    private final Logger log = LoggerFactory.getLogger(ContinuityWireLongitLayingServiceImpl.class);

    public ContinuityWireLongitLayingServiceImpl(
        ContinuityWireLongitLayingRepository continuityWireLongitLayingRepository,
        StrandSupplyService strandSupplyService
    ) {
        super(strandSupplyService, continuityWireLongitLayingRepository);
    }

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    protected String getDomainClassName() {
        return "ContinuityWireLongitLaying";
    }

    @Override
    public Optional<ContinuityWireLongitLaying> partialUpdate(ContinuityWireLongitLaying continuityWireLongitLaying) {
        log.debug("Request to partially update ContinuityWireLongitLaying : {}", continuityWireLongitLaying);

        return getJpaRepository()
            .findById(continuityWireLongitLaying.getId())
            .map(existingContinuityWireLongitLaying -> {
                if (continuityWireLongitLaying.getAnonymousContinuityWireDesignation() != null) {
                    existingContinuityWireLongitLaying.setAnonymousContinuityWireDesignation(
                        continuityWireLongitLaying.getAnonymousContinuityWireDesignation()
                    );
                }
                if (continuityWireLongitLaying.getAnonymousContinuityWireGramPerMeterLinearMass() != null) {
                    existingContinuityWireLongitLaying.setAnonymousContinuityWireGramPerMeterLinearMass(
                        continuityWireLongitLaying.getAnonymousContinuityWireGramPerMeterLinearMass()
                    );
                }
                if (continuityWireLongitLaying.getAnonymousContinuityWireMetalFiberKind() != null) {
                    existingContinuityWireLongitLaying.setAnonymousContinuityWireMetalFiberKind(
                        continuityWireLongitLaying.getAnonymousContinuityWireMetalFiberKind()
                    );
                }
                if (continuityWireLongitLaying.getAnonymousContinuityWireMilimeterDiameter() != null) {
                    existingContinuityWireLongitLaying.setAnonymousContinuityWireMilimeterDiameter(
                        continuityWireLongitLaying.getAnonymousContinuityWireMilimeterDiameter()
                    );
                }
                if (continuityWireLongitLaying.getAnonymousContinuityWireFlexibility() != null) {
                    existingContinuityWireLongitLaying.setAnonymousContinuityWireFlexibility(
                        continuityWireLongitLaying.getAnonymousContinuityWireFlexibility()
                    );
                }

                return existingContinuityWireLongitLaying;
            })
            .map(getJpaRepository()::save);
    }
}
