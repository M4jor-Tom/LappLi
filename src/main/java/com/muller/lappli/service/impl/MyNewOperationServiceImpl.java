package com.muller.lappli.service.impl;

import com.muller.lappli.domain.MyNewOperation;
import com.muller.lappli.repository.MyNewOperationRepository;
import com.muller.lappli.service.MyNewOperationService;
import com.muller.lappli.service.StrandSupplyService;
import com.muller.lappli.service.abstracts.AbstractNonCentralOperationServiceImpl;
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
public class MyNewOperationServiceImpl extends AbstractNonCentralOperationServiceImpl<MyNewOperation> implements MyNewOperationService {

    private final Logger log = LoggerFactory.getLogger(MyNewOperationServiceImpl.class);

    public MyNewOperationServiceImpl(MyNewOperationRepository myNewOperationRepository, StrandSupplyService strandSupplyService) {
        super(strandSupplyService, myNewOperationRepository);
    }

    @Override
    public Optional<MyNewOperation> partialUpdate(MyNewOperation myNewOperation) {
        log.debug("Request to partially update MyNewOperation : {}", myNewOperation);

        return getJpaRepository()
            .findById(myNewOperation.getId())
            .map(existingMyNewOperation -> {
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
            .map(getJpaRepository()::save);
    }

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    protected String getDomainClassName() {
        return "MyNewOperation";
    }
}
