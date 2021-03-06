package com.muller.lappli.service.impl;

import com.muller.lappli.domain.Study;
import com.muller.lappli.domain.UserData;
import com.muller.lappli.repository.StudyRepository;
import com.muller.lappli.security.SecurityUtils;
import com.muller.lappli.service.SaveException;
import com.muller.lappli.service.StudyService;
import com.muller.lappli.service.UserDataService;
import java.util.Comparator;
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

    private final UserDataService userDataService;

    public StudyServiceImpl(StudyRepository studyRepository, UserDataService userDataService) {
        this.studyRepository = studyRepository;
        this.userDataService = userDataService;
    }

    @Override
    public Study save(Study study, Boolean shouldBeAuthored) throws SaveException {
        log.debug("Request to save Study : {}", study);

        //If study creation is legal
        if (study.isAuthored() == shouldBeAuthored && SecurityUtils.getCurrentUserLogin().isPresent()) {
            Optional<UserData> userDataOptional = userDataService.findOrCreateUserDataByLogin(SecurityUtils.getCurrentUserLogin().get());

            if (userDataOptional.isPresent()) {
                //If its UserData author is found

                //Precising study's author in case it's null
                study.setAuthor(userDataOptional.get());

                //Double check UserData exists in database
                Boolean userDataExistsInDatabase = userDataOptional.get().getId() != null;
                if (userDataOptional.get().getId() != null) {
                    userDataExistsInDatabase = userDataService.findOne(userDataOptional.get().getId()).isPresent();
                }

                if (!userDataExistsInDatabase) {
                    //If UserData does not exists in database

                    //Save it
                    userDataService.save(userDataOptional.get());
                }
                return studyRepository.save(study);
            }
        }

        throw new SaveException();
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
        List<Study> studyList = studyRepository.findAll();

        studyList.sort(
            new Comparator<Study>() {
                @Override
                public int compare(Study o1, Study o2) {
                    if (o1.getNumber().equals(o2.getNumber())) {
                        //return o1.getIndex() < o2.getIndex();
                        return 0;
                    }

                    return o1.getNumber() < o2.getNumber() ? 1 : -1;
                }
            }
        );

        return studyList;
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
