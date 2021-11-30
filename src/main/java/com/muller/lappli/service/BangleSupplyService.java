package com.muller.lappli.service;

import com.muller.lappli.domain.BangleSupply;
import com.muller.lappli.repository.BangleSupplyRepository;
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
public class BangleSupplyService {

    private final Logger log = LoggerFactory.getLogger(BangleSupplyService.class);

    private final BangleSupplyRepository bangleSupplyRepository;

    public BangleSupplyService(BangleSupplyRepository bangleSupplyRepository) {
        this.bangleSupplyRepository = bangleSupplyRepository;
    }

    /**
     * Save a bangleSupply.
     *
     * @param bangleSupply the entity to save.
     * @return the persisted entity.
     */
    public BangleSupply save(BangleSupply bangleSupply) {
        log.debug("Request to save BangleSupply : {}", bangleSupply);
        return bangleSupplyRepository.save(bangleSupply);
    }

    /**
     * Partially update a bangleSupply.
     *
     * @param bangleSupply the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BangleSupply> partialUpdate(BangleSupply bangleSupply) {
        log.debug("Request to partially update BangleSupply : {}", bangleSupply);

        return bangleSupplyRepository
            .findById(bangleSupply.getId())
            .map(existingBangleSupply -> {
                if (bangleSupply.getApparitions() != null) {
                    existingBangleSupply.setApparitions(bangleSupply.getApparitions());
                }

                return existingBangleSupply;
            })
            .map(bangleSupplyRepository::save);
    }

    /**
     * Get all the bangleSupplies.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<BangleSupply> findAll() {
        log.debug("Request to get all BangleSupplies");
        return bangleSupplyRepository.findAll();
    }

    /**
     * Get one bangleSupply by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BangleSupply> findOne(Long id) {
        log.debug("Request to get BangleSupply : {}", id);
        return bangleSupplyRepository.findById(id);
    }

    /**
     * Delete the bangleSupply by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BangleSupply : {}", id);
        bangleSupplyRepository.deleteById(id);
    }
}