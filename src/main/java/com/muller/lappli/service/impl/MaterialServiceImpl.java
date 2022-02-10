package com.muller.lappli.service.impl;

import com.muller.lappli.domain.Material;
import com.muller.lappli.repository.MaterialRepository;
import com.muller.lappli.service.MaterialService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Material}.
 */
@Service
@Transactional
public class MaterialServiceImpl implements MaterialService {

    private final Logger log = LoggerFactory.getLogger(MaterialServiceImpl.class);

    private final MaterialRepository materialRepository;

    public MaterialServiceImpl(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    @Override
    public Material save(Material material) {
        log.debug("Request to save Material : {}", material);
        return materialRepository.save(material);
    }

    @Override
    public Optional<Material> partialUpdate(Material material) {
        log.debug("Request to partially update Material : {}", material);

        return materialRepository
            .findById(material.getId())
            .map(existingMaterial -> {
                if (material.getNumber() != null) {
                    existingMaterial.setNumber(material.getNumber());
                }
                if (material.getDesignation() != null) {
                    existingMaterial.setDesignation(material.getDesignation());
                }
                if (material.getKilogramPerCubeMeterVolumicDensity() != null) {
                    existingMaterial.setKilogramPerCubeMeterVolumicDensity(material.getKilogramPerCubeMeterVolumicDensity());
                }

                return existingMaterial;
            })
            .map(materialRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Material> findAll() {
        log.debug("Request to get all Materials");
        return materialRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Material> findOne(Long id) {
        log.debug("Request to get Material : {}", id);
        return materialRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Material : {}", id);
        materialRepository.deleteById(id);
    }
}
