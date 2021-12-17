package com.muller.lappli.service.impl;

import com.muller.lappli.domain.BangleSupply;
import com.muller.lappli.repository.BangleSupplyRepository;
import com.muller.lappli.service.BangleSupplyService;
import com.muller.lappli.service.LifterService;
import com.muller.lappli.service.abstracts.AbstractLiftedSupplyServiceImpl;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BangleSupply}.
 */
@Service
@Transactional
public class BangleSupplyServiceImpl extends AbstractLiftedSupplyServiceImpl<BangleSupply> implements BangleSupplyService {

    private final Logger log = LoggerFactory.getLogger(BangleSupplyServiceImpl.class);

    private final BangleSupplyRepository bangleSupplyRepository;

    public BangleSupplyServiceImpl(LifterService lifterService, BangleSupplyRepository bangleSupplyRepository) {
        super(lifterService);
        this.bangleSupplyRepository = bangleSupplyRepository;
    }

    @Override
    public BangleSupply save(BangleSupply bangleSupply) {
        log.debug("Request to save BangleSupply : {}", bangleSupply);
        return onRead(bangleSupplyRepository.save(bangleSupply));
    }

    @Override
    public Optional<BangleSupply> partialUpdate(BangleSupply bangleSupply) {
        log.debug("Request to partially update BangleSupply : {}", bangleSupply);

        return onOptionalRead(
            bangleSupplyRepository
                .findById(bangleSupply.getId())
                .map(existingBangleSupply -> {
                    if (bangleSupply.getApparitions() != null) {
                        existingBangleSupply.setApparitions(bangleSupply.getApparitions());
                    }
                    if (bangleSupply.getDescription() != null) {
                        existingBangleSupply.setDescription(bangleSupply.getDescription());
                    }

                    return existingBangleSupply;
                })
                .map(bangleSupplyRepository::save)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<BangleSupply> findAll() {
        log.debug("Request to get all BangleSupplies");
        return onListRead(bangleSupplyRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BangleSupply> findOne(Long id) {
        log.debug("Request to get BangleSupply : {}", id);
        return onOptionalRead(bangleSupplyRepository.findById(id));
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BangleSupply : {}", id);
        bangleSupplyRepository.deleteById(id);
    }
}
