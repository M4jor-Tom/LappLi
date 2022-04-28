package com.muller.lappli.service.impl;

import com.muller.lappli.domain.StripLaying;
import com.muller.lappli.repository.StripLayingRepository;
import com.muller.lappli.service.StrandSupplyService;
import com.muller.lappli.service.StripLayingService;
import com.muller.lappli.service.abstracts.AbstractNonCentralOperationServiceImpl;
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

    public StripLayingServiceImpl(StripLayingRepository stripLayingRepository, StrandSupplyService strandSupplyService) {
        super(strandSupplyService, stripLayingRepository);
    }

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    protected String getDomainClassName() {
        return "StripLaying";
    }

    @Override
    public Optional<StripLaying> partialUpdate(StripLaying stripLaying) {
        log.debug("Request to partially update StripLaying : {}", stripLaying);

        return getJpaRepository()
            .findById(stripLaying.getId())
            .map(existingStripLaying -> {
                return existingStripLaying;
            })
            .map(getJpaRepository()::save);
    }
}
