package com.muller.lappli.web.rest;

import com.muller.lappli.domain.LifterRunMeasure;
import com.muller.lappli.repository.LifterRunMeasureRepository;
import com.muller.lappli.service.LifterRunMeasureService;
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
 * REST controller for managing {@link com.muller.lappli.domain.LifterRunMeasure}.
 */
@RestController
@RequestMapping("/api")
public class LifterRunMeasureResource {

    private final Logger log = LoggerFactory.getLogger(LifterRunMeasureResource.class);

    private static final String ENTITY_NAME = "lifterRunMeasure";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LifterRunMeasureService lifterRunMeasureService;

    private final LifterRunMeasureRepository lifterRunMeasureRepository;

    public LifterRunMeasureResource(
        LifterRunMeasureService lifterRunMeasureService,
        LifterRunMeasureRepository lifterRunMeasureRepository
    ) {
        this.lifterRunMeasureService = lifterRunMeasureService;
        this.lifterRunMeasureRepository = lifterRunMeasureRepository;
    }

    /**
     * {@code POST  /lifter-run-measures} : Create a new lifterRunMeasure.
     *
     * @param lifterRunMeasure the lifterRunMeasure to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lifterRunMeasure, or with status {@code 400 (Bad Request)} if the lifterRunMeasure has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/lifter-run-measures")
    public ResponseEntity<LifterRunMeasure> createLifterRunMeasure(@Valid @RequestBody LifterRunMeasure lifterRunMeasure)
        throws URISyntaxException {
        log.debug("REST request to save LifterRunMeasure : {}", lifterRunMeasure);
        if (lifterRunMeasure.getId() != null) {
            throw new BadRequestAlertException("A new lifterRunMeasure cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LifterRunMeasure result = lifterRunMeasureService.save(lifterRunMeasure);
        return ResponseEntity
            .created(new URI("/api/lifter-run-measures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /lifter-run-measures/:id} : Updates an existing lifterRunMeasure.
     *
     * @param id the id of the lifterRunMeasure to save.
     * @param lifterRunMeasure the lifterRunMeasure to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lifterRunMeasure,
     * or with status {@code 400 (Bad Request)} if the lifterRunMeasure is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lifterRunMeasure couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/lifter-run-measures/{id}")
    public ResponseEntity<LifterRunMeasure> updateLifterRunMeasure(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LifterRunMeasure lifterRunMeasure
    ) throws URISyntaxException {
        log.debug("REST request to update LifterRunMeasure : {}, {}", id, lifterRunMeasure);
        if (lifterRunMeasure.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lifterRunMeasure.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lifterRunMeasureRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LifterRunMeasure result = lifterRunMeasureService.save(lifterRunMeasure);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, lifterRunMeasure.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /lifter-run-measures/:id} : Partial updates given fields of an existing lifterRunMeasure, field will ignore if it is null
     *
     * @param id the id of the lifterRunMeasure to save.
     * @param lifterRunMeasure the lifterRunMeasure to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lifterRunMeasure,
     * or with status {@code 400 (Bad Request)} if the lifterRunMeasure is not valid,
     * or with status {@code 404 (Not Found)} if the lifterRunMeasure is not found,
     * or with status {@code 500 (Internal Server Error)} if the lifterRunMeasure couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/lifter-run-measures/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LifterRunMeasure> partialUpdateLifterRunMeasure(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LifterRunMeasure lifterRunMeasure
    ) throws URISyntaxException {
        log.debug("REST request to partial update LifterRunMeasure partially : {}, {}", id, lifterRunMeasure);
        if (lifterRunMeasure.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lifterRunMeasure.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lifterRunMeasureRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LifterRunMeasure> result = lifterRunMeasureService.partialUpdate(lifterRunMeasure);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, lifterRunMeasure.getId().toString())
        );
    }

    /**
     * {@code GET  /lifter-run-measures} : get all the lifterRunMeasures.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lifterRunMeasures in body.
     */
    @GetMapping("/lifter-run-measures")
    public List<LifterRunMeasure> getAllLifterRunMeasures() {
        log.debug("REST request to get all LifterRunMeasures");
        return lifterRunMeasureService.findAll();
    }

    /**
     * {@code GET  /lifter-run-measures/:id} : get the "id" lifterRunMeasure.
     *
     * @param id the id of the lifterRunMeasure to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lifterRunMeasure, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/lifter-run-measures/{id}")
    public ResponseEntity<LifterRunMeasure> getLifterRunMeasure(@PathVariable Long id) {
        log.debug("REST request to get LifterRunMeasure : {}", id);
        Optional<LifterRunMeasure> lifterRunMeasure = lifterRunMeasureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(lifterRunMeasure);
    }

    /**
     * {@code DELETE  /lifter-run-measures/:id} : delete the "id" lifterRunMeasure.
     *
     * @param id the id of the lifterRunMeasure to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/lifter-run-measures/{id}")
    public ResponseEntity<Void> deleteLifterRunMeasure(@PathVariable Long id) {
        log.debug("REST request to delete LifterRunMeasure : {}", id);
        lifterRunMeasureService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
