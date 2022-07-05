package com.muller.lappli.service.impl;

import com.muller.lappli.domain.MyNewOperation;
import com.muller.lappli.repository.MyNewOperationRepository;
import com.muller.lappli.service.MyNewOperationService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MyNewOperation}.
 */
@Service
@Transactional
public class MyNewOperationServiceImpl implements MyNewOperationService {

    private final Logger log = LoggerFactory.getLogger(MyNewOperationServiceImpl.class);

    private final MyNewOperationRepository myNewOperationRepository;

    public MyNewOperationServiceImpl(MyNewOperationRepository myNewOperationRepository) {
        this.myNewOperationRepository = myNewOperationRepository;
    }

    @Override
    public MyNewOperation save(MyNewOperation myNewOperation) {
        log.debug("Request to save MyNewOperation : {}", myNewOperation);
        return myNewOperationRepository.save(myNewOperation);
    }

    @Override
    public Optional<MyNewOperation> partialUpdate(MyNewOperation myNewOperation) {
        log.debug("Request to partially update MyNewOperation : {}", myNewOperation);

        return myNewOperationRepository
            .findById(myNewOperation.getId())
            .map(existingMyNewOperation -> {
                if (myNewOperation.getOperationLayer() != null) {
                    existingMyNewOperation.setOperationLayer(myNewOperation.getOperationLayer());
                }
                if (myNewOperation.getOperationData() != null) {
                    existingMyNewOperation.setOperationData(myNewOperation.getOperationData());
                }
                if (myNewOperation.getAnonymousMyNewComponentNumber() != null) {
                    existingMyNewOperation.setAnonymousMyNewComponentNumber(myNewOperation.getAnonymousMyNewComponentNumber());
                }
                if (myNewOperation.getAnonymousMyNewComponentDesignation() != null) {
                    existingMyNewOperation.setAnonymousMyNewComponentDesignation(myNewOperation.getAnonymousMyNewComponentDesignation());
                }
                if (myNewOperation.getAnonymousMyNewComponentData() != null) {
                    existingMyNewOperation.setAnonymousMyNewComponentData(myNewOperation.getAnonymousMyNewComponentData());
                }

                return existingMyNewOperation;
            })
            .map(myNewOperationRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MyNewOperation> findAll() {
        log.debug("Request to get all MyNewOperations");
        return myNewOperationRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MyNewOperation> findOne(Long id) {
        log.debug("Request to get MyNewOperation : {}", id);
        return myNewOperationRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MyNewOperation : {}", id);
        myNewOperationRepository.deleteById(id);
    }
}
