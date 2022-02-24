package com.muller.lappli.service.impl;

import com.muller.lappli.domain.CoreAssembly;
import com.muller.lappli.repository.CoreAssemblyRepository;
import com.muller.lappli.service.CoreAssemblyService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CoreAssembly}.
 */
@Service
@Transactional
public class CoreAssemblyServiceImpl implements CoreAssemblyService {

    private final Logger log = LoggerFactory.getLogger(CoreAssemblyServiceImpl.class);

    private final CoreAssemblyRepository coreAssemblyRepository;

    public CoreAssemblyServiceImpl(CoreAssemblyRepository coreAssemblyRepository) {
        this.coreAssemblyRepository = coreAssemblyRepository;
    }

    @Override
    public CoreAssembly save(CoreAssembly coreAssembly) {
        log.debug("Request to save CoreAssembly : {}", coreAssembly);
        return coreAssemblyRepository.save(coreAssembly);
    }

    @Override
    public Optional<CoreAssembly> partialUpdate(CoreAssembly coreAssembly) {
        log.debug("Request to partially update CoreAssembly : {}", coreAssembly);

        return coreAssemblyRepository
            .findById(coreAssembly.getId())
            .map(existingCoreAssembly -> {
                if (coreAssembly.getAssemblyLayer() != null) {
                    existingCoreAssembly.setAssemblyLayer(coreAssembly.getAssemblyLayer());
                }
                if (coreAssembly.getForcedMeanMilimeterComponentDiameter() != null) {
                    existingCoreAssembly.setForcedMeanMilimeterComponentDiameter(coreAssembly.getForcedMeanMilimeterComponentDiameter());
                }

                return existingCoreAssembly;
            })
            .map(coreAssemblyRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CoreAssembly> findAll() {
        log.debug("Request to get all CoreAssemblies");
        return coreAssemblyRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CoreAssembly> findOne(Long id) {
        log.debug("Request to get CoreAssembly : {}", id);
        return coreAssemblyRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CoreAssembly : {}", id);
        coreAssemblyRepository.deleteById(id);
    }
}
