package com.muller.lappli.service.impl;

import com.muller.lappli.domain.Bangle;
import com.muller.lappli.repository.BangleRepository;
import com.muller.lappli.service.BangleService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Bangle}.
 */
@Service
@Transactional
public class BangleServiceImpl implements BangleService {

    private final Logger log = LoggerFactory.getLogger(BangleServiceImpl.class);

    private final BangleRepository bangleRepository;

    public BangleServiceImpl(BangleRepository bangleRepository) {
        this.bangleRepository = bangleRepository;
    }

    @Override
    public Bangle save(Bangle bangle) {
        log.debug("Request to save Bangle : {}", bangle);
        return bangleRepository.save(bangle);
    }

    @Override
    public Optional<Bangle> partialUpdate(Bangle bangle) {
        log.debug("Request to partially update Bangle : {}", bangle);

        return bangleRepository
            .findById(bangle.getId())
            .map(existingBangle -> {
                if (bangle.getNumber() != null) {
                    existingBangle.setNumber(bangle.getNumber());
                }
                if (bangle.getDesignation() != null) {
                    existingBangle.setDesignation(bangle.getDesignation());
                }
                if (bangle.getGramPerMeterLinearMass() != null) {
                    existingBangle.setGramPerMeterLinearMass(bangle.getGramPerMeterLinearMass());
                }
                if (bangle.getMilimeterDiameter() != null) {
                    existingBangle.setMilimeterDiameter(bangle.getMilimeterDiameter());
                }

                return existingBangle;
            })
            .map(bangleRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Bangle> findAll() {
        log.debug("Request to get all Bangles");
        return bangleRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Bangle> findOne(Long id) {
        log.debug("Request to get Bangle : {}", id);
        return bangleRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Bangle : {}", id);
        bangleRepository.deleteById(id);
    }
}
