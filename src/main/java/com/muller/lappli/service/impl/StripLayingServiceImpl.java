package com.muller.lappli.service.impl;

import com.muller.lappli.domain.StripLaying;
import com.muller.lappli.repository.StripLayingRepository;
import com.muller.lappli.service.StrandSupplyService;
import com.muller.lappli.service.StripLayingService;
import com.muller.lappli.service.abstracts.AbstractNonCentralOperationServiceImpl;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link StripLaying}.
 */
@Service
@Transactional
public class StripLayingServiceImpl extends AbstractNonCentralOperationServiceImpl<StripLaying> implements StripLayingService {

    private final Logger log = LoggerFactory.getLogger(StripLayingServiceImpl.class);

    private final StripLayingRepository stripLayingRepository;

    public StripLayingServiceImpl(StripLayingRepository stripLayingRepository, StrandSupplyService strandSupplyService) {
        super(strandSupplyService);
        this.stripLayingRepository = stripLayingRepository;
    }

    @Override
    public StripLaying save(StripLaying stripLaying) {
        log.debug("Request to save StripLaying : {}", stripLaying);
        actualizeOwnerStrandSupply(stripLaying);
        return stripLayingRepository.save(stripLaying);
    }

    @Override
    public Optional<StripLaying> partialUpdate(StripLaying stripLaying, Boolean actualizeOwnerStrandSupply) {
        log.debug("Request to partially update StripLaying : {}", stripLaying);

        return stripLayingRepository
            .findById(stripLaying.getId())
            .map(existingStripLaying -> {
                if (stripLaying.getOperationLayer() != null) {
                    existingStripLaying.setOperationLayer(stripLaying.getOperationLayer());
                }

                if (actualizeOwnerStrandSupply) {
                    actualizeOwnerStrandSupply(existingStripLaying);
                }

                return existingStripLaying;
            })
            .map(stripLayingRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StripLaying> findAll() {
        log.debug("Request to get all StripLayings");
        return stripLayingRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StripLaying> findOne(Long id) {
        log.debug("Request to get StripLaying : {}", id);
        return stripLayingRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete StripLaying : {}", id);
        stripLayingRepository.deleteById(id);
    }
}
