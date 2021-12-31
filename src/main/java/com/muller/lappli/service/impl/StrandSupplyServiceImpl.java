package com.muller.lappli.service.impl;

import com.muller.lappli.domain.StrandSupply;
import com.muller.lappli.repository.StrandSupplyRepository;
import com.muller.lappli.service.StrandSupplyService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link StrandSupply}.
 */
@Service
@Transactional
public class StrandSupplyServiceImpl implements StrandSupplyService {

    private final Logger log = LoggerFactory.getLogger(StrandSupplyServiceImpl.class);

    private final StrandSupplyRepository strandSupplyRepository;

    public StrandSupplyServiceImpl(StrandSupplyRepository strandSupplyRepository) {
        this.strandSupplyRepository = strandSupplyRepository;
    }

    @Override
    public StrandSupply save(StrandSupply strandSupply) {
        log.debug("Request to save StrandSupply : {}", strandSupply);
        return strandSupplyRepository.save(strandSupply);
    }

    @Override
    public Optional<StrandSupply> partialUpdate(StrandSupply strandSupply) {
        log.debug("Request to partially update StrandSupply : {}", strandSupply);

        return strandSupplyRepository
            .findById(strandSupply.getId())
            .map(existingStrandSupply -> {
                if (strandSupply.getSupplyState() != null) {
                    existingStrandSupply.setSupplyState(strandSupply.getSupplyState());
                }
                if (strandSupply.getApparitions() != null) {
                    existingStrandSupply.setApparitions(strandSupply.getApparitions());
                }
                if (strandSupply.getMarkingType() != null) {
                    existingStrandSupply.setMarkingType(strandSupply.getMarkingType());
                }
                if (strandSupply.getDescription() != null) {
                    existingStrandSupply.setDescription(strandSupply.getDescription());
                }

                return existingStrandSupply;
            })
            .map(strandSupplyRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StrandSupply> findAll() {
        log.debug("Request to get all StrandSupplies");
        return strandSupplyRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StrandSupply> findOne(Long id) {
        log.debug("Request to get StrandSupply : {}", id);
        return strandSupplyRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete StrandSupply : {}", id);
        strandSupplyRepository.deleteById(id);
    }
}
