package com.muller.lappli.service.impl;

import com.muller.lappli.domain.TapeLaying;
import com.muller.lappli.repository.TapeLayingRepository;
import com.muller.lappli.service.TapeLayingService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TapeLaying}.
 */
@Service
@Transactional
public class TapeLayingServiceImpl implements TapeLayingService {

    private final Logger log = LoggerFactory.getLogger(TapeLayingServiceImpl.class);

    private final TapeLayingRepository tapeLayingRepository;

    public TapeLayingServiceImpl(TapeLayingRepository tapeLayingRepository) {
        this.tapeLayingRepository = tapeLayingRepository;
    }

    @Override
    public TapeLaying save(TapeLaying tapeLaying) {
        log.debug("Request to save TapeLaying : {}", tapeLaying);
        return tapeLayingRepository.save(tapeLaying);
    }

    @Override
    public Optional<TapeLaying> partialUpdate(TapeLaying tapeLaying) {
        log.debug("Request to partially update TapeLaying : {}", tapeLaying);

        return tapeLayingRepository
            .findById(tapeLaying.getId())
            .map(existingTapeLaying -> {
                if (tapeLaying.getOperationLayer() != null) {
                    existingTapeLaying.setOperationLayer(tapeLaying.getOperationLayer());
                }
                if (tapeLaying.getAssemblyMean() != null) {
                    existingTapeLaying.setAssemblyMean(tapeLaying.getAssemblyMean());
                }

                return existingTapeLaying;
            })
            .map(tapeLayingRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TapeLaying> findAll() {
        log.debug("Request to get all TapeLayings");
        return tapeLayingRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TapeLaying> findOne(Long id) {
        log.debug("Request to get TapeLaying : {}", id);
        return tapeLayingRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TapeLaying : {}", id);
        tapeLayingRepository.deleteById(id);
    }
}
