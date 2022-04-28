package com.muller.lappli.service.impl;

import com.muller.lappli.domain.TapeLaying;
import com.muller.lappli.repository.TapeLayingRepository;
import com.muller.lappli.service.StrandSupplyService;
import com.muller.lappli.service.TapeLayingService;
import com.muller.lappli.service.abstracts.AbstractNonCentralOperationServiceImpl;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TapeLaying}.
 */
@Service
@Transactional
public class TapeLayingServiceImpl extends AbstractNonCentralOperationServiceImpl<TapeLaying> implements TapeLayingService {

    private final Logger log = LoggerFactory.getLogger(TapeLayingServiceImpl.class);

    public TapeLayingServiceImpl(TapeLayingRepository tapeLayingRepository, StrandSupplyService strandSupplyService) {
        super(strandSupplyService, tapeLayingRepository);
    }

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    protected String getDomainClassName() {
        return "TapeLaying";
    }

    @Override
    public Optional<TapeLaying> partialUpdate(TapeLaying tapeLaying) {
        log.debug("Request to partially update TapeLaying : {}", tapeLaying);

        return getJpaRepository()
            .findById(tapeLaying.getId())
            .map(existingTapeLaying -> {
                if (tapeLaying.getAssemblyMean() != null) {
                    existingTapeLaying.setAssemblyMean(tapeLaying.getAssemblyMean());
                }

                return existingTapeLaying;
            })
            .map(getJpaRepository()::save);
    }
}
