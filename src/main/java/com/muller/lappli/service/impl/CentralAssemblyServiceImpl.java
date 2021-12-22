package com.muller.lappli.service.impl;

import com.muller.lappli.domain.CentralAssembly;
import com.muller.lappli.repository.CentralAssemblyRepository;
import com.muller.lappli.service.CentralAssemblyService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
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

    public CentralAssemblyServiceImpl(CentralAssemblyRepository centralAssemblyRepository) {
        this.centralAssemblyRepository = centralAssemblyRepository;
    }

    @Override
    public CentralAssembly save(CentralAssembly centralAssembly) {
        log.debug("Request to save CentralAssembly : {}", centralAssembly);
        return centralAssemblyRepository.save(centralAssembly);
    }

    @Override
    public Optional<CentralAssembly> partialUpdate(CentralAssembly centralAssembly) {
        log.debug("Request to partially update CentralAssembly : {}", centralAssembly);

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

    /**
     *  Get all the centralAssemblies where Strand is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CentralAssembly> findAllWhereStrandIsNull() {
        log.debug("Request to get all centralAssemblies where Strand is null");
        return StreamSupport
            .stream(centralAssemblyRepository.findAll().spliterator(), false)
            .filter(centralAssembly -> centralAssembly.getStrand() == null)
            .collect(Collectors.toList());
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
