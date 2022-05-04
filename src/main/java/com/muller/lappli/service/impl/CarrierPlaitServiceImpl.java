package com.muller.lappli.service.impl;

import com.muller.lappli.domain.CarrierPlait;
import com.muller.lappli.repository.CarrierPlaitRepository;
import com.muller.lappli.service.CarrierPlaitService;
import com.muller.lappli.service.StrandSupplyService;
import com.muller.lappli.service.abstracts.AbstractNonCentralOperationServiceImpl;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CarrierPlait}.
 */
@Service
@Transactional
public class CarrierPlaitServiceImpl extends AbstractNonCentralOperationServiceImpl<CarrierPlait> implements CarrierPlaitService {

    private final Logger log = LoggerFactory.getLogger(CarrierPlaitServiceImpl.class);

    public CarrierPlaitServiceImpl(CarrierPlaitRepository carrierPlaitRepository, StrandSupplyService strandSupplyService) {
        super(strandSupplyService, carrierPlaitRepository);
    }

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    protected String getDomainClassName() {
        return "CarrierPlait";
    }

    @Override
    public Optional<CarrierPlait> partialUpdate(CarrierPlait carrierPlait) {
        log.debug("Request to partially update CarrierPlait : {}", carrierPlait);

        return getJpaRepository()
            .findById(carrierPlait.getId())
            .map(existingCarrierPlait -> {
                if (carrierPlait.getMinimumDecaNewtonLoad() != null) {
                    existingCarrierPlait.setMinimumDecaNewtonLoad(carrierPlait.getMinimumDecaNewtonLoad());
                }
                if (carrierPlait.getDegreeAssemblyAngle() != null) {
                    existingCarrierPlait.setDegreeAssemblyAngle(carrierPlait.getDegreeAssemblyAngle());
                }
                if (carrierPlait.getForcedEndPerBobinsCount() != null) {
                    existingCarrierPlait.setForcedEndPerBobinsCount(carrierPlait.getForcedEndPerBobinsCount());
                }
                if (carrierPlait.getAnonymousCarrierPlaitFiberNumber() != null) {
                    existingCarrierPlait.setAnonymousCarrierPlaitFiberNumber(carrierPlait.getAnonymousCarrierPlaitFiberNumber());
                }
                if (carrierPlait.getAnonymousCarrierPlaitFiberDesignation() != null) {
                    existingCarrierPlait.setAnonymousCarrierPlaitFiberDesignation(carrierPlait.getAnonymousCarrierPlaitFiberDesignation());
                }
                if (carrierPlait.getAnonymousCarrierPlaitFiberDecitexTitration() != null) {
                    existingCarrierPlait.setAnonymousCarrierPlaitFiberDecitexTitration(
                        carrierPlait.getAnonymousCarrierPlaitFiberDecitexTitration()
                    );
                }
                if (carrierPlait.getAnonymousCarrierPlaitFiberGramPerSquareMilimeterPerMeterDensity() != null) {
                    existingCarrierPlait.setAnonymousCarrierPlaitFiberGramPerSquareMilimeterPerMeterDensity(
                        carrierPlait.getAnonymousCarrierPlaitFiberGramPerSquareMilimeterPerMeterDensity()
                    );
                }
                if (carrierPlait.getAnonymousCarrierPlaitFiberDecaNewtonLoad() != null) {
                    existingCarrierPlait.setAnonymousCarrierPlaitFiberDecaNewtonLoad(
                        carrierPlait.getAnonymousCarrierPlaitFiberDecaNewtonLoad()
                    );
                }

                return existingCarrierPlait;
            })
            .map(getJpaRepository()::save);
    }
}
