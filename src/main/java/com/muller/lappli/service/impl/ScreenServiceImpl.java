package com.muller.lappli.service.impl;

import com.muller.lappli.domain.Screen;
import com.muller.lappli.repository.ScreenRepository;
import com.muller.lappli.service.ScreenService;
import com.muller.lappli.service.StrandSupplyService;
import com.muller.lappli.service.abstracts.AbstractNonCentralOperationServiceImpl;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Screen}.
 */
@Service
@Transactional
public class ScreenServiceImpl extends AbstractNonCentralOperationServiceImpl<Screen> implements ScreenService {

    private final Logger log = LoggerFactory.getLogger(ScreenServiceImpl.class);

    public ScreenServiceImpl(ScreenRepository screenRepository, StrandSupplyService strandSupplyService) {
        super(strandSupplyService, screenRepository);
    }

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    protected String getDomainClassName() {
        return "Screen";
    }

    @Override
    public Optional<Screen> partialUpdate(Screen screen) {
        log.debug("Request to partially update Screen : {}", screen);

        return getJpaRepository()
            .findById(screen.getId())
            .map(existingScreen -> {
                if (screen.getOperationLayer() != null) {
                    existingScreen.setOperationLayer(screen.getOperationLayer());
                }
                if (screen.getAssemblyMeanIsSameThanAssemblys() != null) {
                    existingScreen.setAssemblyMeanIsSameThanAssemblys(screen.getAssemblyMeanIsSameThanAssemblys());
                }
                if (screen.getForcedDiameterAssemblyStep() != null) {
                    existingScreen.setForcedDiameterAssemblyStep(screen.getForcedDiameterAssemblyStep());
                }
                if (screen.getAnonymousCopperFiberNumber() != null) {
                    existingScreen.setAnonymousCopperFiberNumber(screen.getAnonymousCopperFiberNumber());
                }
                if (screen.getAnonymousCopperFiberDesignation() != null) {
                    existingScreen.setAnonymousCopperFiberDesignation(screen.getAnonymousCopperFiberDesignation());
                }
                if (screen.getAnonymousCopperFiberKind() != null) {
                    existingScreen.setAnonymousCopperFiberKind(screen.getAnonymousCopperFiberKind());
                }
                if (screen.getAnonymousCopperFiberMilimeterDiameter() != null) {
                    existingScreen.setAnonymousCopperFiberMilimeterDiameter(screen.getAnonymousCopperFiberMilimeterDiameter());
                }

                rollbackOperationLayerIfUpdate(existingScreen);

                return existingScreen;
            })
            .map(getJpaRepository()::save);
    }
}
