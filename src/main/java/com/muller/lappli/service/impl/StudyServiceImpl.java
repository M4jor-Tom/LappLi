package com.muller.lappli.service.impl;

import com.muller.lappli.domain.Study;
import com.muller.lappli.repository.StudyRepository;
import com.muller.lappli.service.StudyService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Study}.
 */
@Service
@Transactional
public class StudyServiceImpl implements StudyService {

    private final Logger log = LoggerFactory.getLogger(StudyServiceImpl.class);

    private final StudyRepository studyRepository;

    public StudyServiceImpl(StudyRepository studyRepository) {
        this.studyRepository = studyRepository;
    }

    @Override
    public Study save(Study study) {
        log.debug("Request to save Study : {}", study);
        return studyRepository.save(study);
    }

    @Override
    public Optional<Study> partialUpdate(Study study) {
        log.debug("Request to partially update Study : {}", study);

        return studyRepository
            .findById(study.getId())
            .map(existingStudy -> {
                if (study.getNumber() != null) {
                    existingStudy.setNumber(study.getNumber());
                }

                return existingStudy;
            })
            .map(studyRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Study> findAll() {
        log.debug("Request to get all Studies");
        return studyRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Study> findOne(Long id) {
        log.debug("Request to get Study : {}", id);
        return studyRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Study : {}", id);
        studyRepository.deleteById(id);
    }
}
