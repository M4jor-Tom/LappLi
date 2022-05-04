package com.muller.lappli.service.impl;

import com.muller.lappli.domain.CarrierPlaitFiber;
import com.muller.lappli.repository.CarrierPlaitFiberRepository;
import com.muller.lappli.service.CarrierPlaitFiberService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CarrierPlaitFiber}.
 */
@Service
@Transactional
public class CarrierPlaitFiberServiceImpl implements CarrierPlaitFiberService {

    private final Logger log = LoggerFactory.getLogger(CarrierPlaitFiberServiceImpl.class);

    private final CarrierPlaitFiberRepository carrierPlaitFiberRepository;

    public CarrierPlaitFiberServiceImpl(CarrierPlaitFiberRepository carrierPlaitFiberRepository) {
        this.carrierPlaitFiberRepository = carrierPlaitFiberRepository;
    }

    @Override
    public CarrierPlaitFiber save(CarrierPlaitFiber carrierPlaitFiber) {
        log.debug("Request to save CarrierPlaitFiber : {}", carrierPlaitFiber);
        return carrierPlaitFiberRepository.save(carrierPlaitFiber);
    }

    @Override
    public Optional<CarrierPlaitFiber> partialUpdate(CarrierPlaitFiber carrierPlaitFiber) {
        log.debug("Request to partially update CarrierPlaitFiber : {}", carrierPlaitFiber);

        return carrierPlaitFiberRepository
            .findById(carrierPlaitFiber.getId())
            .map(existingCarrierPlaitFiber -> {
                if (carrierPlaitFiber.getNumber() != null) {
                    existingCarrierPlaitFiber.setNumber(carrierPlaitFiber.getNumber());
                }
                if (carrierPlaitFiber.getDesignation() != null) {
                    existingCarrierPlaitFiber.setDesignation(carrierPlaitFiber.getDesignation());
                }
                if (carrierPlaitFiber.getGramPerMeterLinearMass() != null) {
                    existingCarrierPlaitFiber.setGramPerMeterLinearMass(carrierPlaitFiber.getGramPerMeterLinearMass());
                }
                if (carrierPlaitFiber.getSquareMilimeterSection() != null) {
                    existingCarrierPlaitFiber.setSquareMilimeterSection(carrierPlaitFiber.getSquareMilimeterSection());
                }
                if (carrierPlaitFiber.getDecaNewtonLoad() != null) {
                    existingCarrierPlaitFiber.setDecaNewtonLoad(carrierPlaitFiber.getDecaNewtonLoad());
                }

                return existingCarrierPlaitFiber;
            })
            .map(carrierPlaitFiberRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CarrierPlaitFiber> findAll() {
        log.debug("Request to get all CarrierPlaitFibers");
        return carrierPlaitFiberRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CarrierPlaitFiber> findOne(Long id) {
        log.debug("Request to get CarrierPlaitFiber : {}", id);
        return carrierPlaitFiberRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CarrierPlaitFiber : {}", id);
        carrierPlaitFiberRepository.deleteById(id);
    }
}
