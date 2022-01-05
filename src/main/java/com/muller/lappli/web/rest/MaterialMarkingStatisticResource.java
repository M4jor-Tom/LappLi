package com.muller.lappli.web.rest;

import com.muller.lappli.domain.MaterialMarkingStatistic;
import com.muller.lappli.repository.MaterialMarkingStatisticRepository;
import com.muller.lappli.service.MaterialMarkingStatisticService;
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
 * REST controller for managing {@link com.muller.lappli.domain.MaterialMarkingStatistic}.
 */
@RestController
@RequestMapping("/api")
public class MaterialMarkingStatisticResource {

    private final Logger log = LoggerFactory.getLogger(MaterialMarkingStatisticResource.class);

    private static final String ENTITY_NAME = "materialMarkingStatistic";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MaterialMarkingStatisticService materialMarkingStatisticService;

    private final MaterialMarkingStatisticRepository materialMarkingStatisticRepository;

    public MaterialMarkingStatisticResource(
        MaterialMarkingStatisticService materialMarkingStatisticService,
        MaterialMarkingStatisticRepository materialMarkingStatisticRepository
    ) {
        this.materialMarkingStatisticService = materialMarkingStatisticService;
        this.materialMarkingStatisticRepository = materialMarkingStatisticRepository;
    }

    /**
     * {@code POST  /material-marking-statistics} : Create a new materialMarkingStatistic.
     *
     * @param materialMarkingStatistic the materialMarkingStatistic to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new materialMarkingStatistic, or with status {@code 400 (Bad Request)} if the materialMarkingStatistic has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/material-marking-statistics")
    public ResponseEntity<MaterialMarkingStatistic> createMaterialMarkingStatistic(
        @Valid @RequestBody MaterialMarkingStatistic materialMarkingStatistic
    ) throws URISyntaxException {
        log.debug("REST request to save MaterialMarkingStatistic : {}", materialMarkingStatistic);
        if (materialMarkingStatistic.getId() != null) {
            throw new BadRequestAlertException("A new materialMarkingStatistic cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MaterialMarkingStatistic result = materialMarkingStatisticService.save(materialMarkingStatistic);
        return ResponseEntity
            .created(new URI("/api/material-marking-statistics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /material-marking-statistics/:id} : Updates an existing materialMarkingStatistic.
     *
     * @param id the id of the materialMarkingStatistic to save.
     * @param materialMarkingStatistic the materialMarkingStatistic to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated materialMarkingStatistic,
     * or with status {@code 400 (Bad Request)} if the materialMarkingStatistic is not valid,
     * or with status {@code 500 (Internal Server Error)} if the materialMarkingStatistic couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/material-marking-statistics/{id}")
    public ResponseEntity<MaterialMarkingStatistic> updateMaterialMarkingStatistic(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MaterialMarkingStatistic materialMarkingStatistic
    ) throws URISyntaxException {
        log.debug("REST request to update MaterialMarkingStatistic : {}, {}", id, materialMarkingStatistic);
        if (materialMarkingStatistic.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, materialMarkingStatistic.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!materialMarkingStatisticRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MaterialMarkingStatistic result = materialMarkingStatisticService.save(materialMarkingStatistic);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, materialMarkingStatistic.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /material-marking-statistics/:id} : Partial updates given fields of an existing materialMarkingStatistic, field will ignore if it is null
     *
     * @param id the id of the materialMarkingStatistic to save.
     * @param materialMarkingStatistic the materialMarkingStatistic to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated materialMarkingStatistic,
     * or with status {@code 400 (Bad Request)} if the materialMarkingStatistic is not valid,
     * or with status {@code 404 (Not Found)} if the materialMarkingStatistic is not found,
     * or with status {@code 500 (Internal Server Error)} if the materialMarkingStatistic couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/material-marking-statistics/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MaterialMarkingStatistic> partialUpdateMaterialMarkingStatistic(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MaterialMarkingStatistic materialMarkingStatistic
    ) throws URISyntaxException {
        log.debug("REST request to partial update MaterialMarkingStatistic partially : {}, {}", id, materialMarkingStatistic);
        if (materialMarkingStatistic.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, materialMarkingStatistic.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!materialMarkingStatisticRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MaterialMarkingStatistic> result = materialMarkingStatisticService.partialUpdate(materialMarkingStatistic);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, materialMarkingStatistic.getId().toString())
        );
    }

    /**
     * {@code GET  /material-marking-statistics} : get all the materialMarkingStatistics.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of materialMarkingStatistics in body.
     */
    @GetMapping("/material-marking-statistics")
    public List<MaterialMarkingStatistic> getAllMaterialMarkingStatistics() {
        log.debug("REST request to get all MaterialMarkingStatistics");
        return materialMarkingStatisticService.findAll();
    }

    /**
     * {@code GET  /material-marking-statistics/:id} : get the "id" materialMarkingStatistic.
     *
     * @param id the id of the materialMarkingStatistic to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the materialMarkingStatistic, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/material-marking-statistics/{id}")
    public ResponseEntity<MaterialMarkingStatistic> getMaterialMarkingStatistic(@PathVariable Long id) {
        log.debug("REST request to get MaterialMarkingStatistic : {}", id);
        Optional<MaterialMarkingStatistic> materialMarkingStatistic = materialMarkingStatisticService.findOne(id);
        return ResponseUtil.wrapOrNotFound(materialMarkingStatistic);
    }

    /**
     * {@code DELETE  /material-marking-statistics/:id} : delete the "id" materialMarkingStatistic.
     *
     * @param id the id of the materialMarkingStatistic to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/material-marking-statistics/{id}")
    public ResponseEntity<Void> deleteMaterialMarkingStatistic(@PathVariable Long id) {
        log.debug("REST request to delete MaterialMarkingStatistic : {}", id);
        materialMarkingStatisticService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
