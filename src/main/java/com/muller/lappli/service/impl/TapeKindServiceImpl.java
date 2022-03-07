package com.muller.lappli.service.impl;

import com.muller.lappli.domain.TapeKind;
import com.muller.lappli.repository.TapeKindRepository;
import com.muller.lappli.service.TapeKindService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TapeKind}.
 */
@Service
@Transactional
public class TapeKindServiceImpl implements TapeKindService {

    private final Logger log = LoggerFactory.getLogger(TapeKindServiceImpl.class);

    private final TapeKindRepository tapeKindRepository;

    public TapeKindServiceImpl(TapeKindRepository tapeKindRepository) {
        this.tapeKindRepository = tapeKindRepository;
    }

    @Override
    public TapeKind save(TapeKind tapeKind) {
        log.debug("Request to save TapeKind : {}", tapeKind);
        return tapeKindRepository.save(tapeKind);
    }

    @Override
    public Optional<TapeKind> partialUpdate(TapeKind tapeKind) {
        log.debug("Request to partially update TapeKind : {}", tapeKind);

        return tapeKindRepository
            .findById(tapeKind.getId())
            .map(existingTapeKind -> {
                if (tapeKind.getTargetCoveringRate() != null) {
                    existingTapeKind.setTargetCoveringRate(tapeKind.getTargetCoveringRate());
                }
                if (tapeKind.getDesignation() != null) {
                    existingTapeKind.setDesignation(tapeKind.getDesignation());
                }

                return existingTapeKind;
            })
            .map(tapeKindRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TapeKind> findAll() {
        log.debug("Request to get all TapeKinds");
        return tapeKindRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TapeKind> findOne(Long id) {
        log.debug("Request to get TapeKind : {}", id);
        return tapeKindRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TapeKind : {}", id);
        tapeKindRepository.deleteById(id);
    }
}
