package com.muller.lappli.service.impl;

import com.muller.lappli.domain.CarrierPlait;
import com.muller.lappli.repository.CarrierPlaitRepository;
import com.muller.lappli.service.CarrierPlaitService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CarrierPlait}.
 */
@Service
@Transactional
public class CarrierPlaitServiceImpl implements CarrierPlaitService {

    private final Logger log = LoggerFactory.getLogger(CarrierPlaitServiceImpl.class);

    private final CarrierPlaitRepository carrierPlaitRepository;

    public CarrierPlaitServiceImpl(CarrierPlaitRepository carrierPlaitRepository) {
        this.carrierPlaitRepository = carrierPlaitRepository;
    }

    @Override
    public CarrierPlait save(CarrierPlait carrierPlait) {
        log.debug("Request to save CarrierPlait : {}", carrierPlait);
        return carrierPlaitRepository.save(carrierPlait);
    }

    @Override
    public Optional<CarrierPlait> partialUpdate(CarrierPlait carrierPlait) {
        log.debug("Request to partially update CarrierPlait : {}", carrierPlait);

        return carrierPlaitRepository
            .findById(carrierPlait.getId())
            .map(existingCarrierPlait -> {
                if (carrierPlait.getOperationLayer() != null) {
                    existingCarrierPlait.setOperationLayer(carrierPlait.getOperationLayer());
                }
                if (carrierPlait.getMinimumDecaNewtonLoad() != null) {
                    existingCarrierPlait.setMinimumDecaNewtonLoad(carrierPlait.getMinimumDecaNewtonLoad());
                }
                if (carrierPlait.getDegreeAssemblyAngle() != null) {
                    existingCarrierPlait.setDegreeAssemblyAngle(carrierPlait.getDegreeAssemblyAngle());
                }
                if (carrierPlait.getForcedEndPerBobinsCount() != null) {
                    existingCarrierPlait.setForcedEndPerBobinsCount(carrierPlait.getForcedEndPerBobinsCount());
                }

                return existingCarrierPlait;
            })
            .map(carrierPlaitRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CarrierPlait> findAll() {
        log.debug("Request to get all CarrierPlaits");
        return carrierPlaitRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CarrierPlait> findOne(Long id) {
        log.debug("Request to get CarrierPlait : {}", id);
        return carrierPlaitRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CarrierPlait : {}", id);
        carrierPlaitRepository.deleteById(id);
    }
}
