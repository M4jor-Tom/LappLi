package com.muller.lappli.service.impl;

import com.muller.lappli.domain.CarrierPlait;
import com.muller.lappli.domain.PlaiterConfiguration;
import com.muller.lappli.repository.CarrierPlaitRepository;
import com.muller.lappli.service.CarrierPlaitService;
import com.muller.lappli.service.PlaiterConfigurationService;
import com.muller.lappli.service.StrandSupplyService;
import com.muller.lappli.service.abstracts.AbstractNonCentralOperationServiceImpl;
import java.util.List;
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

    private final PlaiterConfigurationService plaiterConfigurationService;

    public CarrierPlaitServiceImpl(
        CarrierPlaitRepository carrierPlaitRepository,
        StrandSupplyService strandSupplyService,
        PlaiterConfigurationService plaiterConfigurationService
    ) {
        super(strandSupplyService, carrierPlaitRepository);
        this.plaiterConfigurationService = plaiterConfigurationService;
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

        return onOptionalRead(
            getJpaRepository()
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
                        existingCarrierPlait.setAnonymousCarrierPlaitFiberDesignation(
                            carrierPlait.getAnonymousCarrierPlaitFiberDesignation()
                        );
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
                .map(getJpaRepository()::save)
        );
    }

    @Override
    public CarrierPlait onRead(CarrierPlait domainObject) {
        //  Fetch all PlaiterConfigurations
        List<PlaiterConfiguration> allPlaiterConfigurations = plaiterConfigurationService.findAll();

        //Finding min value of CarrierPlaitFibersPerBobins for the domainObject CarrierPlait...
        Long minimumCarrierPlaitFibersPerBobin = Long.MAX_VALUE;
        for (PlaiterConfiguration plaiterConfiguration : allPlaiterConfigurations) {
            //...and for this PlaiterConfiguration
            if (domainObject.getCarrierPlaitFibersPerBobinsMinimumCount(plaiterConfiguration) == null) {
                return domainObject;
            }
            minimumCarrierPlaitFibersPerBobin =
                Math.min(minimumCarrierPlaitFibersPerBobin, domainObject.getCarrierPlaitFibersPerBobinsMinimumCount(plaiterConfiguration));
        }

        //Adding PlaiterConfigurations which have the minimum possible value of CarrierPlaitFibersPerBobin
        for (PlaiterConfiguration plaiterConfiguration : allPlaiterConfigurations) {
            if (domainObject.getCarrierPlaitFibersPerBobinsMinimumCount(plaiterConfiguration) == minimumCarrierPlaitFibersPerBobin) {
                domainObject.getPlaiterConfigurationsWithMinimumCarrierPlaitFibersPerBobin().add(plaiterConfiguration);
            }
        }

        return domainObject;
    }
}
