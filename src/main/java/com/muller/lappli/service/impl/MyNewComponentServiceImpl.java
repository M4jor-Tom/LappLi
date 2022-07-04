package com.muller.lappli.service.impl;

import com.muller.lappli.domain.MyNewComponent;
import com.muller.lappli.repository.MyNewComponentRepository;
import com.muller.lappli.service.MyNewComponentService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MyNewComponent}.
 */
@Service
@Transactional
public class MyNewComponentServiceImpl implements MyNewComponentService {

    private final Logger log = LoggerFactory.getLogger(MyNewComponentServiceImpl.class);

    private final MyNewComponentRepository myNewComponentRepository;

    public MyNewComponentServiceImpl(MyNewComponentRepository myNewComponentRepository) {
        this.myNewComponentRepository = myNewComponentRepository;
    }

    @Override
    public MyNewComponent save(MyNewComponent myNewComponent) {
        log.debug("Request to save MyNewComponent : {}", myNewComponent);
        return myNewComponentRepository.save(myNewComponent);
    }

    @Override
    public Optional<MyNewComponent> partialUpdate(MyNewComponent myNewComponent) {
        log.debug("Request to partially update MyNewComponent : {}", myNewComponent);

        return myNewComponentRepository
            .findById(myNewComponent.getId())
            .map(existingMyNewComponent -> {
                if (myNewComponent.getNumber() != null) {
                    existingMyNewComponent.setNumber(myNewComponent.getNumber());
                }
                if (myNewComponent.getDesignation() != null) {
                    existingMyNewComponent.setDesignation(myNewComponent.getDesignation());
                }
                if (myNewComponent.getData() != null) {
                    existingMyNewComponent.setData(myNewComponent.getData());
                }

                return existingMyNewComponent;
            })
            .map(myNewComponentRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MyNewComponent> findAll() {
        log.debug("Request to get all MyNewComponents");
        return myNewComponentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MyNewComponent> findOne(Long id) {
        log.debug("Request to get MyNewComponent : {}", id);
        return myNewComponentRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MyNewComponent : {}", id);
        myNewComponentRepository.deleteById(id);
    }
}
