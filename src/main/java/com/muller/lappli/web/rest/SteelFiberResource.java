package com.muller.lappli.web.rest;

import com.muller.lappli.domain.SteelFiber;
import com.muller.lappli.repository.SteelFiberRepository;
import com.muller.lappli.service.SteelFiberService;
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
 * REST controller for managing {@link com.muller.lappli.domain.SteelFiber}.
 */
@RestController
@RequestMapping("/api")
public class SteelFiberResource {

    private final Logger log = LoggerFactory.getLogger(SteelFiberResource.class);

    private static final String ENTITY_NAME = "steelFiber";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SteelFiberService steelFiberService;

    private final SteelFiberRepository steelFiberRepository;

    public SteelFiberResource(SteelFiberService steelFiberService, SteelFiberRepository steelFiberRepository) {
        this.steelFiberService = steelFiberService;
        this.steelFiberRepository = steelFiberRepository;
    }

    /**
     * {@code POST  /steel-fibers} : Create a new steelFiber.
     *
     * @param steelFiber the steelFiber to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new steelFiber, or with status {@code 400 (Bad Request)} if the steelFiber has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/steel-fibers")
    public ResponseEntity<SteelFiber> createSteelFiber(@Valid @RequestBody SteelFiber steelFiber) throws URISyntaxException {
        log.debug("REST request to save SteelFiber : {}", steelFiber);
        if (steelFiber.getId() != null) {
            throw new BadRequestAlertException("A new steelFiber cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SteelFiber result = steelFiberService.save(steelFiber);
        return ResponseEntity
            .created(new URI("/api/steel-fibers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /steel-fibers/:id} : Updates an existing steelFiber.
     *
     * @param id the id of the steelFiber to save.
     * @param steelFiber the steelFiber to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated steelFiber,
     * or with status {@code 400 (Bad Request)} if the steelFiber is not valid,
     * or with status {@code 500 (Internal Server Error)} if the steelFiber couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/steel-fibers/{id}")
    public ResponseEntity<SteelFiber> updateSteelFiber(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SteelFiber steelFiber
    ) throws URISyntaxException {
        log.debug("REST request to update SteelFiber : {}, {}", id, steelFiber);
        if (steelFiber.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, steelFiber.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!steelFiberRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SteelFiber result = steelFiberService.save(steelFiber);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, steelFiber.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /steel-fibers/:id} : Partial updates given fields of an existing steelFiber, field will ignore if it is null
     *
     * @param id the id of the steelFiber to save.
     * @param steelFiber the steelFiber to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated steelFiber,
     * or with status {@code 400 (Bad Request)} if the steelFiber is not valid,
     * or with status {@code 404 (Not Found)} if the steelFiber is not found,
     * or with status {@code 500 (Internal Server Error)} if the steelFiber couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/steel-fibers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SteelFiber> partialUpdateSteelFiber(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SteelFiber steelFiber
    ) throws URISyntaxException {
        log.debug("REST request to partial update SteelFiber partially : {}, {}", id, steelFiber);
        if (steelFiber.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, steelFiber.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!steelFiberRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SteelFiber> result = steelFiberService.partialUpdate(steelFiber);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, steelFiber.getId().toString())
        );
    }

    /**
     * {@code GET  /steel-fibers} : get all the steelFibers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of steelFibers in body.
     */
    @GetMapping("/steel-fibers")
    public List<SteelFiber> getAllSteelFibers() {
        log.debug("REST request to get all SteelFibers");
        return steelFiberService.findAll();
    }

    /**
     * {@code GET  /steel-fibers/:id} : get the "id" steelFiber.
     *
     * @param id the id of the steelFiber to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the steelFiber, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/steel-fibers/{id}")
    public ResponseEntity<SteelFiber> getSteelFiber(@PathVariable Long id) {
        log.debug("REST request to get SteelFiber : {}", id);
        Optional<SteelFiber> steelFiber = steelFiberService.findOne(id);
        return ResponseUtil.wrapOrNotFound(steelFiber);
    }

    /**
     * {@code DELETE  /steel-fibers/:id} : delete the "id" steelFiber.
     *
     * @param id the id of the steelFiber to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/steel-fibers/{id}")
    public ResponseEntity<Void> deleteSteelFiber(@PathVariable Long id) {
        log.debug("REST request to delete SteelFiber : {}", id);
        steelFiberService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
