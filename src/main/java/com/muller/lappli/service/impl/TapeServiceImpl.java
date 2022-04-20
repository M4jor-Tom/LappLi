package com.muller.lappli.service.impl;

import com.muller.lappli.domain.Tape;
import com.muller.lappli.repository.TapeRepository;
import com.muller.lappli.service.TapeService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Tape}.
 */
@Service
@Transactional
public class TapeServiceImpl implements TapeService {

    private final Logger log = LoggerFactory.getLogger(TapeServiceImpl.class);

    private final TapeRepository tapeRepository;

    public TapeServiceImpl(TapeRepository tapeRepository) {
        this.tapeRepository = tapeRepository;
    }

    @Override
    public Tape save(Tape tape) {
        log.debug("Request to save Tape : {}", tape);
        return tapeRepository.save(tape);
    }

    @Override
    public Optional<Tape> partialUpdate(Tape tape) {
        log.debug("Request to partially update Tape : {}", tape);

        return tapeRepository
            .findById(tape.getId())
            .map(existingTape -> {
                if (tape.getNumber() != null) {
                    existingTape.setNumber(tape.getNumber());
                }
                if (tape.getDesignation() != null) {
                    existingTape.setDesignation(tape.getDesignation());
                }
                if (tape.getMilimeterWidth() != null) {
                    existingTape.setMilimeterWidth(tape.getMilimeterWidth());
                }
                if (tape.getMilimeterDiameterIncidency() != null) {
                    existingTape.setMilimeterDiameterIncidency(tape.getMilimeterDiameterIncidency());
                }

                return existingTape;
            })
            .map(tapeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tape> findAll() {
        log.debug("Request to get all Tapes");
        return tapeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Tape> findOne(Long id) {
        log.debug("Request to get Tape : {}", id);
        return tapeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Tape : {}", id);
        tapeRepository.deleteById(id);
    }
}
