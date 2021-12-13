package com.muller.lappli.service.impl;

import com.muller.lappli.domain.ISupply;
import com.muller.lappli.repository.ISupplyRepository;
import com.muller.lappli.service.ISupplyService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ISupply}.
 */
@Service
@Transactional
public class ISupplyServiceImpl implements ISupplyService {

    private final Logger log = LoggerFactory.getLogger(ISupplyServiceImpl.class);

    private final ISupplyRepository iSupplyRepository;

    public ISupplyServiceImpl(ISupplyRepository iSupplyRepository) {
        this.iSupplyRepository = iSupplyRepository;
    }

    @Override
    public ISupply save(ISupply iSupply) {
        log.debug("Request to save ISupply : {}", iSupply);
        return iSupplyRepository.save(iSupply);
    }

    @Override
    public Optional<ISupply> partialUpdate(ISupply iSupply) {
        log.debug("Request to partially update ISupply : {}", iSupply);

        return iSupplyRepository
            .findById(iSupply.getId())
            .map(existingISupply -> {
                if (iSupply.getApparitions() != null) {
                    existingISupply.setApparitions(iSupply.getApparitions());
                }
                if (iSupply.getMilimeterDiameter() != null) {
                    existingISupply.setMilimeterDiameter(iSupply.getMilimeterDiameter());
                }
                if (iSupply.getGramPerMeterLinearMass() != null) {
                    existingISupply.setGramPerMeterLinearMass(iSupply.getGramPerMeterLinearMass());
                }

                return existingISupply;
            })
            .map(iSupplyRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ISupply> findAll() {
        log.debug("Request to get all ISupplies");
        return iSupplyRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ISupply> findOne(Long id) {
        log.debug("Request to get ISupply : {}", id);
        return iSupplyRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ISupply : {}", id);
        iSupplyRepository.deleteById(id);
    }
}
