package com.muller.lappli.service.impl;

import com.muller.lappli.domain.CentralAssembly;
import com.muller.lappli.repository.CentralAssemblyRepository;
import com.muller.lappli.repository.StrandSupplyRepository;
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

    private final StrandSupplyRepository strandSupplyRepository;

    public CentralAssemblyServiceImpl(CentralAssemblyRepository centralAssemblyRepository, StrandSupplyRepository strandSupplyRepository) {
        this.centralAssemblyRepository = centralAssemblyRepository;
        this.strandSupplyRepository = strandSupplyRepository;
    }

    @Override
    public CentralAssembly save(CentralAssembly centralAssembly) {
        log.debug("Request to save CentralAssembly : {}", centralAssembly);
        Long strandSupplyId = centralAssembly.getOwnerStrandSupply().getId();
        strandSupplyRepository.findById(strandSupplyId).ifPresent(centralAssembly::ownerStrandSupply);
        return centralAssemblyRepository.save(centralAssembly);
    }

    @Override
    public Optional<CentralAssembly> partialUpdate(CentralAssembly centralAssembly) {
        log.debug("Request to partially update CentralAssembly : {}", centralAssembly);

        return centralAssemblyRepository
            .findById(centralAssembly.getId())
            .map(existingCentralAssembly -> {
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
