package com.muller.lappli.service.impl;

import com.muller.lappli.domain.MyNewComponentSupply;
import com.muller.lappli.repository.MyNewComponentSupplyRepository;
import com.muller.lappli.service.LifterService;
import com.muller.lappli.service.MyNewComponentSupplyService;
import com.muller.lappli.service.abstracts.AbstractLiftedSupplyServiceImpl;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MyNewComponentSupply}.
 */
@Service
@Transactional
public class MyNewComponentSupplyServiceImpl
    extends AbstractLiftedSupplyServiceImpl<MyNewComponentSupply>
    implements MyNewComponentSupplyService {

    private final Logger log = LoggerFactory.getLogger(MyNewComponentSupplyServiceImpl.class);

    private final MyNewComponentSupplyRepository myNewComponentSupplyRepository;

    public MyNewComponentSupplyServiceImpl(LifterService lifterService, MyNewComponentSupplyRepository myNewComponentSupplyRepository) {
        super(lifterService);
        this.myNewComponentSupplyRepository = myNewComponentSupplyRepository;
    }

    @Override
    public MyNewComponentSupply save(MyNewComponentSupply myNewComponentSupply) {
        log.debug("Request to save MyNewComponentSupply : {}", myNewComponentSupply);
        return onRead(myNewComponentSupplyRepository.save(myNewComponentSupply));
    }

    @Override
    public Optional<MyNewComponentSupply> partialUpdate(MyNewComponentSupply myNewComponentSupply) {
        log.debug("Request to partially update MyNewComponentSupply : {}", myNewComponentSupply);

        return onOptionalRead(
            myNewComponentSupplyRepository
                .findById(myNewComponentSupply.getId())
                .map(existingMyNewComponentSupply -> {
                    if (myNewComponentSupply.getApparitions() != null) {
                        existingMyNewComponentSupply.setApparitions(myNewComponentSupply.getApparitions());
                    }
                    if (myNewComponentSupply.getDescription() != null) {
                        existingMyNewComponentSupply.setDescription(myNewComponentSupply.getDescription());
                    }
                    if (myNewComponentSupply.getMarkingType() != null) {
                        existingMyNewComponentSupply.setMarkingType(myNewComponentSupply.getMarkingType());
                    }

                    return existingMyNewComponentSupply;
                })
                .map(myNewComponentSupplyRepository::save)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<MyNewComponentSupply> findAll() {
        log.debug("Request to get all MyNewComponentSupplies");
        return onListRead(myNewComponentSupplyRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MyNewComponentSupply> findOne(Long id) {
        log.debug("Request to get MyNewComponentSupply : {}", id);
        return onOptionalRead(myNewComponentSupplyRepository.findById(id));
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MyNewComponentSupply : {}", id);
        myNewComponentSupplyRepository.deleteById(id);
    }
}
