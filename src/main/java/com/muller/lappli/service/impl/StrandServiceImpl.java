package com.muller.lappli.service.impl;

import com.muller.lappli.domain.Strand;
import com.muller.lappli.repository.StrandRepository;
import com.muller.lappli.service.StrandService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Strand}.
 */
@Service
@Transactional
public class StrandServiceImpl implements StrandService {

    private final Logger log = LoggerFactory.getLogger(StrandServiceImpl.class);

    private final StrandRepository strandRepository;

    public StrandServiceImpl(StrandRepository strandRepository) {
        this.strandRepository = strandRepository;
    }

    @Override
    public Strand save(Strand strand) {
        log.debug("Request to save Strand : {}", strand);
        return strandRepository.save(strand);
    }

    @Override
    public Optional<Strand> partialUpdate(Strand strand) {
        log.debug("Request to partially update Strand : {}", strand);

        return strandRepository
            .findById(strand.getId())
            .map(existingStrand -> {
                if (strand.getDesignation() != null) {
                    existingStrand.setDesignation(strand.getDesignation());
                }
                if (strand.getHousingOperationType() != null) {
                    existingStrand.setHousingOperationType(strand.getHousingOperationType());
                }

                return existingStrand;
            })
            .map(strandRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Strand> findAll() {
        log.debug("Request to get all Strands");
        return strandRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Strand> findOne(Long id) {
        log.debug("Request to get Strand : {}", id);
        return strandRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Strand : {}", id);
        strandRepository.deleteById(id);
    }
}
