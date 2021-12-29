package com.muller.lappli.service.impl;

import com.muller.lappli.domain.CentralAssembly;
import com.muller.lappli.domain.exception.PositionHasSeveralSupplyException;
import com.muller.lappli.domain.exception.PositionInSeveralAssemblyException;
import com.muller.lappli.repository.CentralAssemblyRepository;
import com.muller.lappli.repository.StrandRepository;
import com.muller.lappli.service.CentralAssemblyService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CentralAssembly}.
 */
@Service
@Transactional
public class CentralAssemblyServiceImpl implements CentralAssemblyService {

    private final Logger log = LoggerFactory.getLogger(CentralAssemblyServiceImpl.class);

    private final CentralAssemblyRepository centralAssemblyRepository;

    private final StrandRepository strandRepository;

    public CentralAssemblyServiceImpl(CentralAssemblyRepository centralAssemblyRepository, StrandRepository strandRepository) {
        this.centralAssemblyRepository = centralAssemblyRepository;
        this.strandRepository = strandRepository;
    }

    @Override
    public CentralAssembly save(CentralAssembly centralAssembly)
        throws PositionInSeveralAssemblyException, PositionHasSeveralSupplyException {
        log.debug("Request to save CentralAssembly : {}", centralAssembly);
        Long strandId = centralAssembly.getStrand().getId();
        strandRepository.findById(strandId).ifPresent(centralAssembly::strand);

        centralAssembly.checkPositions();

        return centralAssemblyRepository.save(centralAssembly);
    }

    @Override
    public Optional<CentralAssembly> partialUpdate(CentralAssembly centralAssembly)
        throws PositionInSeveralAssemblyException, PositionHasSeveralSupplyException {
        log.debug("Request to partially update CentralAssembly : {}", centralAssembly);

        centralAssembly.checkPositions();

        return centralAssemblyRepository
            .findById(centralAssembly.getId())
            .map(existingCentralAssembly -> {
                if (centralAssembly.getProductionStep() != null) {
                    existingCentralAssembly.setProductionStep(centralAssembly.getProductionStep());
                }

                return existingCentralAssembly;
            })
            .map(centralAssemblyRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CentralAssembly> findAll() {
        log.debug("Request to get all CentralAssemblies");
        return centralAssemblyRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CentralAssembly> findOne(Long id) {
        log.debug("Request to get CentralAssembly : {}", id);
        return centralAssemblyRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CentralAssembly : {}", id);
        centralAssemblyRepository.deleteById(id);
    }
}
