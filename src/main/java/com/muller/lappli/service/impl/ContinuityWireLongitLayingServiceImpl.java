package com.muller.lappli.service.impl;

import com.muller.lappli.domain.ContinuityWireLongitLaying;
import com.muller.lappli.repository.ContinuityWireLongitLayingRepository;
import com.muller.lappli.service.ContinuityWireLongitLayingService;
import com.muller.lappli.service.StrandSupplyService;
import com.muller.lappli.service.abstracts.AbstractNonCentralOperationServiceImpl;
import java.util.List;
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

    private final ContinuityWireLongitLayingRepository continuityWireLongitLayingRepository;

    public ContinuityWireLongitLayingServiceImpl(
        ContinuityWireLongitLayingRepository continuityWireLongitLayingRepository,
        StrandSupplyService strandSupplyService
    ) {
        super(strandSupplyService);
        this.continuityWireLongitLayingRepository = continuityWireLongitLayingRepository;
    }

    @Override
    public ContinuityWireLongitLaying save(ContinuityWireLongitLaying continuityWireLongitLaying, Boolean actualizeOwnerStrandSupply) {
        log.debug("Request to save ContinuityWireLongitLaying : {}", continuityWireLongitLaying);

        if (actualizeOwnerStrandSupply) {
            actualizeOwnerStrandSupply(continuityWireLongitLaying);
        }

        return continuityWireLongitLayingRepository.save(continuityWireLongitLaying);
    }

    @Override
    public Optional<ContinuityWireLongitLaying> partialUpdate(ContinuityWireLongitLaying continuityWireLongitLaying) {
        log.debug("Request to partially update ContinuityWireLongitLaying : {}", continuityWireLongitLaying);

        return continuityWireLongitLayingRepository
            .findById(continuityWireLongitLaying.getId())
            .map(existingContinuityWireLongitLaying -> {
                if (continuityWireLongitLaying.getOperationLayer() != null) {
                    existingContinuityWireLongitLaying.setOperationLayer(continuityWireLongitLaying.getOperationLayer());
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
            .map(continuityWireLongitLayingRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContinuityWireLongitLaying> findAll() {
        log.debug("Request to get all ContinuityWireLongitLayings");
        return continuityWireLongitLayingRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ContinuityWireLongitLaying> findOne(Long id) {
        log.debug("Request to get ContinuityWireLongitLaying : {}", id);
        return continuityWireLongitLayingRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ContinuityWireLongitLaying : {}", id);
        continuityWireLongitLayingRepository.deleteById(id);
    }
}
