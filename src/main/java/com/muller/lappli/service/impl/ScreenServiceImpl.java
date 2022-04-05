package com.muller.lappli.service.impl;

import com.muller.lappli.domain.Screen;
import com.muller.lappli.repository.ScreenRepository;
import com.muller.lappli.service.ScreenService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Screen}.
 */
@Service
@Transactional
public class ScreenServiceImpl implements ScreenService {

    private final Logger log = LoggerFactory.getLogger(ScreenServiceImpl.class);

    private final ScreenRepository screenRepository;

    public ScreenServiceImpl(ScreenRepository screenRepository) {
        this.screenRepository = screenRepository;
    }

    @Override
    public Screen save(Screen screen) {
        log.debug("Request to save Screen : {}", screen);
        return screenRepository.save(screen);
    }

    @Override
    public Optional<Screen> partialUpdate(Screen screen) {
        log.debug("Request to partially update Screen : {}", screen);

        return screenRepository
            .findById(screen.getId())
            .map(existingScreen -> {
                if (screen.getOperationLayer() != null) {
                    existingScreen.setOperationLayer(screen.getOperationLayer());
                }
                if (screen.getAssemblyMeanIsSameThanAssemblys() != null) {
                    existingScreen.setAssemblyMeanIsSameThanAssemblys(screen.getAssemblyMeanIsSameThanAssemblys());
                }
                if (screen.getForcedDiameterAssemblyStep() != null) {
                    existingScreen.setForcedDiameterAssemblyStep(screen.getForcedDiameterAssemblyStep());
                }

                return existingScreen;
            })
            .map(screenRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Screen> findAll() {
        log.debug("Request to get all Screens");
        return screenRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Screen> findOne(Long id) {
        log.debug("Request to get Screen : {}", id);
        return screenRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Screen : {}", id);
        screenRepository.deleteById(id);
    }
}
