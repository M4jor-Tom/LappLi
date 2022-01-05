package com.muller.lappli.web.rest;

import com.muller.lappli.domain.Lifter;
import com.muller.lappli.repository.LifterRepository;
import com.muller.lappli.service.LifterService;
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
 * REST controller for managing {@link com.muller.lappli.domain.Lifter}.
 */
@RestController
@RequestMapping("/api")
public class LifterResource {

    private final Logger log = LoggerFactory.getLogger(LifterResource.class);

    private static final String ENTITY_NAME = "lifter";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LifterService lifterService;

    private final LifterRepository lifterRepository;

    public LifterResource(LifterService lifterService, LifterRepository lifterRepository) {
        this.lifterService = lifterService;
        this.lifterRepository = lifterRepository;
    }

    /**
     * {@code POST  /lifters} : Create a new lifter.
     *
     * @param lifter the lifter to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lifter, or with status {@code 400 (Bad Request)} if the lifter has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/lifters")
    public ResponseEntity<Lifter> createLifter(@Valid @RequestBody Lifter lifter) throws URISyntaxException {
        log.debug("REST request to save Lifter : {}", lifter);
        if (lifter.getId() != null) {
            throw new BadRequestAlertException("A new lifter cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Lifter result = lifterService.save(lifter);
        return ResponseEntity
            .created(new URI("/api/lifters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /lifters/:id} : Updates an existing lifter.
     *
     * @param id the id of the lifter to save.
     * @param lifter the lifter to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lifter,
     * or with status {@code 400 (Bad Request)} if the lifter is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lifter couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/lifters/{id}")
    public ResponseEntity<Lifter> updateLifter(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Lifter lifter
    ) throws URISyntaxException {
        log.debug("REST request to update Lifter : {}, {}", id, lifter);
        if (lifter.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lifter.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lifterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Lifter result = lifterService.save(lifter);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, lifter.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /lifters/:id} : Partial updates given fields of an existing lifter, field will ignore if it is null
     *
     * @param id the id of the lifter to save.
     * @param lifter the lifter to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lifter,
     * or with status {@code 400 (Bad Request)} if the lifter is not valid,
     * or with status {@code 404 (Not Found)} if the lifter is not found,
     * or with status {@code 500 (Internal Server Error)} if the lifter couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/lifters/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Lifter> partialUpdateLifter(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Lifter lifter
    ) throws URISyntaxException {
        log.debug("REST request to partial update Lifter partially : {}, {}", id, lifter);
        if (lifter.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lifter.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lifterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Lifter> result = lifterService.partialUpdate(lifter);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, lifter.getId().toString())
        );
    }

    /**
     * {@code GET  /lifters} : get all the lifters.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lifters in body.
     */
    @GetMapping("/lifters")
    public List<Lifter> getAllLifters() {
        log.debug("REST request to get all Lifters");
        return lifterService.findAll();
    }

    /**
     * {@code GET  /lifters/:id} : get the "id" lifter.
     *
     * @param id the id of the lifter to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lifter, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/lifters/{id}")
    public ResponseEntity<Lifter> getLifter(@PathVariable Long id) {
        log.debug("REST request to get Lifter : {}", id);
        Optional<Lifter> lifter = lifterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(lifter);
    }

    /**
     * {@code DELETE  /lifters/:id} : delete the "id" lifter.
     *
     * @param id the id of the lifter to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/lifters/{id}")
    public ResponseEntity<Void> deleteLifter(@PathVariable Long id) {
        log.debug("REST request to delete Lifter : {}", id);
        lifterService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
