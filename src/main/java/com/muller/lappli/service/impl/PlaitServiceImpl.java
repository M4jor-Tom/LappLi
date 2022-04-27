package com.muller.lappli.service.impl;

import com.muller.lappli.domain.Plait;
import com.muller.lappli.domain.exception.RatioBoundExceedingException;
import com.muller.lappli.repository.PlaitRepository;
import com.muller.lappli.service.PlaitService;
import com.muller.lappli.service.StrandSupplyService;
import com.muller.lappli.service.abstracts.AbstractNonCentralOperationServiceImpl;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Plait}.
 */
@Service
@Transactional
public class PlaitServiceImpl extends AbstractNonCentralOperationServiceImpl<Plait> implements PlaitService {

    private final Logger log = LoggerFactory.getLogger(PlaitServiceImpl.class);

    public PlaitServiceImpl(PlaitRepository plaitRepository, StrandSupplyService strandSupplyService) {
        super(strandSupplyService, plaitRepository);
    }

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    protected String getDomainClassName() {
        return "Plait";
    }

    @Override
    public Optional<Plait> partialUpdate(Plait plait) throws RatioBoundExceedingException {
        log.debug("Request to partially update Plait : {}", plait);

        try {
            return getJpaRepository()
                .findById(plait.getId())
                .map(existingPlait -> {
                    if (plait.getOperationLayer() != null) {
                        existingPlait.setOperationLayer(plait.getOperationLayer());
                    }
                    if (plait.getTargetCoveringRate() != null) {
                        try {
                            existingPlait.setTargetCoveringRate(plait.getTargetCoveringRate());
                        } catch (RatioBoundExceedingException e) {
                            throw new RatioBoundExceedingTemporaryRuntimeException(e);
                        }
                    }
                    if (plait.getTargetDegreeAngle() != null) {
                        existingPlait.setTargetDegreeAngle(plait.getTargetDegreeAngle());
                    }
                    if (plait.getTargetingCoveringRateNotAngle() != null) {
                        existingPlait.setTargetingCoveringRateNotAngle(plait.getTargetingCoveringRateNotAngle());
                    }
                    if (plait.getAnonymousMetalFiberNumber() != null) {
                        existingPlait.setAnonymousMetalFiberNumber(plait.getAnonymousMetalFiberNumber());
                    }
                    if (plait.getAnonymousMetalFiberDesignation() != null) {
                        existingPlait.setAnonymousMetalFiberDesignation(plait.getAnonymousMetalFiberDesignation());
                    }
                    if (plait.getAnonymousMetalFiberMetalFiberKind() != null) {
                        existingPlait.setAnonymousMetalFiberMetalFiberKind(plait.getAnonymousMetalFiberMetalFiberKind());
                    }
                    if (plait.getAnonymousMetalFiberMilimeterDiameter() != null) {
                        existingPlait.setAnonymousMetalFiberMilimeterDiameter(plait.getAnonymousMetalFiberMilimeterDiameter());
                    }

                    rollbackOperationLayerIfUpdate(existingPlait);

                    return existingPlait;
                })
                .map(getJpaRepository()::save);
        } catch (RatioBoundExceedingTemporaryRuntimeException e) {
            throw e.getRatioBoundExceedingException();
        }
    }

    //Robert C. Martin, go fuck youself
    private class RatioBoundExceedingTemporaryRuntimeException extends RuntimeException {

        private RatioBoundExceedingException ratioBoundExceedingException;

        public RatioBoundExceedingTemporaryRuntimeException(RatioBoundExceedingException e) {
            setRatioBoundExceedingException(ratioBoundExceedingException);
        }

        public RatioBoundExceedingException getRatioBoundExceedingException() {
            return ratioBoundExceedingException;
        }

        private void setRatioBoundExceedingException(RatioBoundExceedingException ratioBoundExceedingException) {
            this.ratioBoundExceedingException = ratioBoundExceedingException;
        }
    }
}
