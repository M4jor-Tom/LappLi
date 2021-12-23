package com.muller.lappli.service.impl;

import com.muller.lappli.domain.IntersticeAssembly;
import com.muller.lappli.repository.IntersticeAssemblyRepository;
import com.muller.lappli.service.IntersticeAssemblyService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link IntersticeAssembly}.
 */
@Service
@Transactional
public class IntersticeAssemblyServiceImpl implements IntersticeAssemblyService {

    private final Logger log = LoggerFactory.getLogger(IntersticeAssemblyServiceImpl.class);

    private final IntersticeAssemblyRepository intersticeAssemblyRepository;

    public IntersticeAssemblyServiceImpl(IntersticeAssemblyRepository intersticeAssemblyRepository) {
        this.intersticeAssemblyRepository = intersticeAssemblyRepository;
    }

    @Override
    public IntersticeAssembly save(IntersticeAssembly intersticeAssembly) {
        log.debug("Request to save IntersticeAssembly : {}", intersticeAssembly);
        return intersticeAssemblyRepository.save(intersticeAssembly);
    }

    @Override
    public Optional<IntersticeAssembly> partialUpdate(IntersticeAssembly intersticeAssembly) {
        log.debug("Request to partially update IntersticeAssembly : {}", intersticeAssembly);

        return intersticeAssemblyRepository
            .findById(intersticeAssembly.getId())
            .map(existingIntersticeAssembly -> {
                if (intersticeAssembly.getProductionStep() != null) {
                    existingIntersticeAssembly.setProductionStep(intersticeAssembly.getProductionStep());
                }

                return existingIntersticeAssembly;
            })
            .map(intersticeAssemblyRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IntersticeAssembly> findAll() {
        log.debug("Request to get all IntersticeAssemblies");
        return intersticeAssemblyRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<IntersticeAssembly> findOne(Long id) {
        log.debug("Request to get IntersticeAssembly : {}", id);
        return intersticeAssemblyRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete IntersticeAssembly : {}", id);
        intersticeAssemblyRepository.deleteById(id);
    }
}
