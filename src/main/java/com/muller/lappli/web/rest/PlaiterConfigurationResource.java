package com.muller.lappli.web.rest;

import com.muller.lappli.domain.PlaiterConfiguration;
import com.muller.lappli.repository.PlaiterConfigurationRepository;
import com.muller.lappli.service.PlaiterConfigurationService;
import com.muller.lappli.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.muller.lappli.domain.PlaiterConfiguration}.
 */
@RestController
@RequestMapping("/api")
public class PlaiterConfigurationResource {

    private final Logger log = LoggerFactory.getLogger(PlaiterConfigurationResource.class);

    private static final String ENTITY_NAME = "plaiterConfiguration";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlaiterConfigurationService plaiterConfigurationService;

    private final PlaiterConfigurationRepository plaiterConfigurationRepository;

    public PlaiterConfigurationResource(
        PlaiterConfigurationService plaiterConfigurationService,
        PlaiterConfigurationRepository plaiterConfigurationRepository
    ) {
        this.plaiterConfigurationService = plaiterConfigurationService;
        this.plaiterConfigurationRepository = plaiterConfigurationRepository;
    }

    /**
     * {@code POST  /plaiter-configurations} : Create a new plaiterConfiguration.
     *
     * @param plaiterConfiguration the plaiterConfiguration to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new plaiterConfiguration, or with status {@code 400 (Bad Request)} if the plaiterConfiguration has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/plaiter-configurations")
    public ResponseEntity<PlaiterConfiguration> createPlaiterConfiguration(@Valid @RequestBody PlaiterConfiguration plaiterConfiguration)
        throws URISyntaxException {
        log.debug("REST request to save PlaiterConfiguration : {}", plaiterConfiguration);
        if (plaiterConfiguration.getId() != null) {
            throw new BadRequestAlertException("A new plaiterConfiguration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlaiterConfiguration result = plaiterConfigurationService.save(plaiterConfiguration);
        return ResponseEntity
            .created(new URI("/api/plaiter-configurations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /plaiter-configurations/:id} : Updates an existing plaiterConfiguration.
     *
     * @param id the id of the plaiterConfiguration to save.
     * @param plaiterConfiguration the plaiterConfiguration to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plaiterConfiguration,
     * or with status {@code 400 (Bad Request)} if the plaiterConfiguration is not valid,
     * or with status {@code 500 (Internal Server Error)} if the plaiterConfiguration couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/plaiter-configurations/{id}")
    public ResponseEntity<PlaiterConfiguration> updatePlaiterConfiguration(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PlaiterConfiguration plaiterConfiguration
    ) throws URISyntaxException {
        log.debug("REST request to update PlaiterConfiguration : {}, {}", id, plaiterConfiguration);
        if (plaiterConfiguration.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, plaiterConfiguration.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!plaiterConfigurationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PlaiterConfiguration result = plaiterConfigurationService.save(plaiterConfiguration);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, plaiterConfiguration.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /plaiter-configurations/:id} : Partial updates given fields of an existing plaiterConfiguration, field will ignore if it is null
     *
     * @param id the id of the plaiterConfiguration to save.
     * @param plaiterConfiguration the plaiterConfiguration to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plaiterConfiguration,
     * or with status {@code 400 (Bad Request)} if the plaiterConfiguration is not valid,
     * or with status {@code 404 (Not Found)} if the plaiterConfiguration is not found,
     * or with status {@code 500 (Internal Server Error)} if the plaiterConfiguration couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/plaiter-configurations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PlaiterConfiguration> partialUpdatePlaiterConfiguration(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PlaiterConfiguration plaiterConfiguration
    ) throws URISyntaxException {
        log.debug("REST request to partial update PlaiterConfiguration partially : {}, {}", id, plaiterConfiguration);
        if (plaiterConfiguration.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, plaiterConfiguration.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!plaiterConfigurationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PlaiterConfiguration> result = plaiterConfigurationService.partialUpdate(plaiterConfiguration);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, plaiterConfiguration.getId().toString())
        );
    }

    /**
     * {@code GET  /plaiter-configurations} : get all the plaiterConfigurations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of plaiterConfigurations in body.
     */
    @GetMapping("/plaiter-configurations")
    public List<PlaiterConfiguration> getAllPlaiterConfigurations() {
        log.debug("REST request to get all PlaiterConfigurations");
        return plaiterConfigurationService.findAll();
    }

    /**
     * {@code GET  /plaiter-configurations/:id} : get the "id" plaiterConfiguration.
     *
     * @param id the id of the plaiterConfiguration to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the plaiterConfiguration, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/plaiter-configurations/{id}")
    public ResponseEntity<PlaiterConfiguration> getPlaiterConfiguration(@PathVariable Long id) {
        log.debug("REST request to get PlaiterConfiguration : {}", id);
        Optional<PlaiterConfiguration> plaiterConfiguration = plaiterConfigurationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(plaiterConfiguration);
    }

    /**
     * {@code DELETE  /plaiter-configurations/:id} : delete the "id" plaiterConfiguration.
     *
     * @param id the id of the plaiterConfiguration to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/plaiter-configurations/{id}")
    public ResponseEntity<Void> deletePlaiterConfiguration(@PathVariable Long id) {
        log.debug("REST request to delete PlaiterConfiguration : {}", id);
        plaiterConfigurationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
