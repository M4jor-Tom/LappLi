package com.muller.lappli.service.impl;

import com.muller.lappli.domain.CarrierPlait;
import com.muller.lappli.domain.ContinuityWireLongitLaying;
import com.muller.lappli.domain.CoreAssembly;
import com.muller.lappli.domain.FlatSheathing;
import com.muller.lappli.domain.IntersticeAssembly;
import com.muller.lappli.domain.Plait;
import com.muller.lappli.domain.Screen;
import com.muller.lappli.domain.Sheathing;
import com.muller.lappli.domain.StrandSupply;
import com.muller.lappli.domain.StripLaying;
import com.muller.lappli.domain.TapeLaying;
import com.muller.lappli.domain.abstracts.AbstractSupply;
import com.muller.lappli.domain.interfaces.INonCentralOperation;
import com.muller.lappli.repository.StrandSupplyRepository;
import com.muller.lappli.service.CarrierPlaitService;
import com.muller.lappli.service.ContinuityWireLongitLayingService;
import com.muller.lappli.service.CoreAssemblyService;
import com.muller.lappli.service.FlatSheathingService;
import com.muller.lappli.service.IntersticeAssemblyService;
import com.muller.lappli.service.PlaitService;
import com.muller.lappli.service.ScreenService;
import com.muller.lappli.service.SheathingService;
import com.muller.lappli.service.StrandSupplyService;
import com.muller.lappli.service.StripLayingService;
import com.muller.lappli.service.TapeLayingService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link StrandSupply}.
 */
@Service
@Transactional
public class StrandSupplyServiceImpl implements StrandSupplyService {

    private final Logger log = LoggerFactory.getLogger(StrandSupplyServiceImpl.class);

    private final StrandSupplyRepository strandSupplyRepository;

    private final CoreAssemblyService coreAssemblyService;

    private final IntersticeAssemblyService intersticeAssemblyService;

    private final TapeLayingService tapeLayingService;

    private final ScreenService screenService;

    private final StripLayingService stripLayingService;

    private final PlaitService plaitService;

    private final CarrierPlaitService carrierPlaitService;

    private final SheathingService sheathingService;

    private final FlatSheathingService flatSheathingService;

    private final ContinuityWireLongitLayingService continuityWireLongitLayingService;

    public StrandSupplyServiceImpl(
        StrandSupplyRepository strandSupplyRepository,
        @Lazy CoreAssemblyService coreAssemblyService,
        @Lazy IntersticeAssemblyService intersticeAssemblyService,
        @Lazy TapeLayingService tapeLayingService,
        @Lazy ScreenService screenService,
        @Lazy StripLayingService stripLayingService,
        @Lazy PlaitService plaitService,
        @Lazy CarrierPlaitService carrierPlaitService,
        @Lazy SheathingService sheathingService,
        @Lazy FlatSheathingService flatSheathingService,
        @Lazy ContinuityWireLongitLayingService continuityWireLongitLayingService
    ) {
        this.strandSupplyRepository = strandSupplyRepository;
        this.coreAssemblyService = coreAssemblyService;
        this.intersticeAssemblyService = intersticeAssemblyService;
        this.tapeLayingService = tapeLayingService;
        this.screenService = screenService;
        this.stripLayingService = stripLayingService;
        this.plaitService = plaitService;
        this.carrierPlaitService = carrierPlaitService;
        this.sheathingService = sheathingService;
        this.flatSheathingService = flatSheathingService;
        this.continuityWireLongitLayingService = continuityWireLongitLayingService;
    }

    public StrandSupply onRead(StrandSupply domainObject) {
        if (domainObject != null) {
            if (domainObject.getStrand() != null) {
                for (AbstractSupply<?> supply : domainObject.getStrand().getSupplies()) {
                    supply.setObserverStrandSupply(domainObject);
                }
            }
            if (domainObject.getCarrierPlaits() != null) {
                for (CarrierPlait carrierPlait : domainObject.getCarrierPlaits()) {
                    domainObject.getCarrierPlaits().remove(carrierPlait);
                    domainObject.addCarrierPlaits(carrierPlaitService.onRead(carrierPlait));
                }
            }
        }

        return domainObject;
    }

    @Override
    public void actualizeNonCentralOperationsFor(StrandSupply toActualize, INonCentralOperation<?> toInsert) {
        save(toActualize.prepareInsertNonCentralOperation(toInsert));

        //Update non central operations
        //[NON_CENTRAL_OPERATION]
        for (CoreAssembly coreAssembly : toActualize.getCoreAssemblies()) {
            coreAssemblyService.save(coreAssembly);
        }

        for (IntersticeAssembly intersticeAssembly : toActualize.getIntersticeAssemblies()) {
            intersticeAssemblyService.save(intersticeAssembly);
        }

        for (TapeLaying tapeLaying : toActualize.getTapeLayings()) {
            Boolean actualizeOwnerStrandSupply = false;
            tapeLayingService.save(tapeLaying, actualizeOwnerStrandSupply, false);
        }

        for (Screen screen : toActualize.getScreens()) {
            Boolean actualizeOwnerStrandSupply = false;
            screenService.save(screen, actualizeOwnerStrandSupply, false);
        }

        for (StripLaying stripLaying : toActualize.getStripLayings()) {
            Boolean actualizeOwnerStrandSupply = false;
            stripLayingService.save(stripLaying, actualizeOwnerStrandSupply, false);
        }

        for (Plait plait : toActualize.getPlaits()) {
            Boolean actualizeOwnerStrandSupply = false;
            plaitService.save(plait, actualizeOwnerStrandSupply, false);
        }

        for (CarrierPlait carrierPlait : toActualize.getCarrierPlaits()) {
            Boolean actualizeOwnerStrandSupply = false;
            carrierPlaitService.save(carrierPlait, actualizeOwnerStrandSupply, false);
        }

        for (Sheathing sheathing : toActualize.getSheathings()) {
            Boolean actualizeOwnerStrandSupply = false;
            sheathingService.save(sheathing, actualizeOwnerStrandSupply, false);
        }

        for (FlatSheathing flatSheathing : toActualize.getFlatSheathings()) {
            Boolean actualizeOwnerStrandSupply = false;
            flatSheathingService.save(flatSheathing, actualizeOwnerStrandSupply, false);
        }

        for (ContinuityWireLongitLaying continuityWireLongitLaying : toActualize.getContinuityWireLongitLayings()) {
            Boolean actualizeOwnerStrandSupply = false;
            continuityWireLongitLayingService.save(continuityWireLongitLaying, actualizeOwnerStrandSupply, false);
        }
    }

    @Override
    public StrandSupply save(StrandSupply strandSupply) {
        log.debug("Request to save StrandSupply : {}", strandSupply);
        return onRead(strandSupplyRepository.save(strandSupply));
    }

    @Override
    public Optional<StrandSupply> partialUpdate(StrandSupply strandSupply) {
        log.debug("Request to partially update StrandSupply : {}", strandSupply);

        return onOptionalRead(
            strandSupplyRepository
                .findById(strandSupply.getId())
                .map(existingStrandSupply -> {
                    if (strandSupply.getApparitions() != null) {
                        existingStrandSupply.setApparitions(strandSupply.getApparitions());
                    }
                    if (strandSupply.getMarkingType() != null) {
                        existingStrandSupply.setMarkingType(strandSupply.getMarkingType());
                    }
                    if (strandSupply.getDescription() != null) {
                        existingStrandSupply.setDescription(strandSupply.getDescription());
                    }
                    if (strandSupply.getDiameterAssemblyStep() != null) {
                        existingStrandSupply.setDiameterAssemblyStep(strandSupply.getDiameterAssemblyStep());
                    }
                    if (strandSupply.getAssemblyMean() != null) {
                        existingStrandSupply.setAssemblyMean(strandSupply.getAssemblyMean());
                    }
                    if (strandSupply.getForceCentralUtilityComponent() != null) {
                        existingStrandSupply.setForceCentralUtilityComponent(strandSupply.getForceCentralUtilityComponent());
                    }

                    return existingStrandSupply;
                })
                .map(strandSupplyRepository::save)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<StrandSupply> findAll() {
        log.debug("Request to get all StrandSupplies");
        return onListRead(strandSupplyRepository.findAll());
    }

    /**
     *  Get all the strandSupplies where CentralAssembly is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<StrandSupply> findAllWhereCentralAssemblyIsNull() {
        log.debug("Request to get all strandSupplies where CentralAssembly is null");
        return StreamSupport
            .stream(strandSupplyRepository.findAll().spliterator(), false)
            .filter(strandSupply -> strandSupply.getCentralAssembly() == null)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StrandSupply> findOne(Long id, Boolean autoGenerateAssemblies) {
        log.debug("Request to get StrandSupply : {}", id);
        Optional<StrandSupply> strandSupplyOptional = strandSupplyRepository.findById(id);

        if (autoGenerateAssemblies && strandSupplyOptional.isPresent()) {
            strandSupplyOptional.get().autoGenerateAssemblies();
        }

        return onOptionalRead(strandSupplyOptional);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete StrandSupply : {}", id);
        strandSupplyRepository.deleteById(id);
    }
}
