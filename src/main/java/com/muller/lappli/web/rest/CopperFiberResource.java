package com.muller.lappli.web.rest;

import com.muller.lappli.domain.CopperFiber;
import com.muller.lappli.repository.CopperFiberRepository;
import com.muller.lappli.service.CopperFiberService;
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
 * REST controller for managing {@link com.muller.lappli.domain.CopperFiber}.
 */
@RestController
@RequestMapping("/api")
public class CopperFiberResource {

    private final Logger log = LoggerFactory.getLogger(CopperFiberResource.class);

    private static final String ENTITY_NAME = "copperFiber";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CopperFiberService copperFiberService;

    private final CopperFiberRepository copperFiberRepository;

    public CopperFiberResource(CopperFiberService copperFiberService, CopperFiberRepository copperFiberRepository) {
        this.copperFiberService = copperFiberService;
        this.copperFiberRepository = copperFiberRepository;
    }

    /**
     * {@code POST  /copper-fibers} : Create a new copperFiber.
     *
     * @param copperFiber the copperFiber to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new copperFiber, or with status {@code 400 (Bad Request)} if the copperFiber has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/copper-fibers")
    public ResponseEntity<CopperFiber> createCopperFiber(@Valid @RequestBody CopperFiber copperFiber) throws URISyntaxException {
        log.debug("REST request to save CopperFiber : {}", copperFiber);
        if (copperFiber.getId() != null) {
            throw new BadRequestAlertException("A new copperFiber cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CopperFiber result = copperFiberService.save(copperFiber);
        return ResponseEntity
            .created(new URI("/api/copper-fibers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /copper-fibers/:id} : Updates an existing copperFiber.
     *
     * @param id the id of the copperFiber to save.
     * @param copperFiber the copperFiber to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated copperFiber,
     * or with status {@code 400 (Bad Request)} if the copperFiber is not valid,
     * or with status {@code 500 (Internal Server Error)} if the copperFiber couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/copper-fibers/{id}")
    public ResponseEntity<CopperFiber> updateCopperFiber(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CopperFiber copperFiber
    ) throws URISyntaxException {
        log.debug("REST request to update CopperFiber : {}, {}", id, copperFiber);
        if (copperFiber.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, copperFiber.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!copperFiberRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CopperFiber result = copperFiberService.save(copperFiber);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, copperFiber.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /copper-fibers/:id} : Partial updates given fields of an existing copperFiber, field will ignore if it is null
     *
     * @param id the id of the copperFiber to save.
     * @param copperFiber the copperFiber to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated copperFiber,
     * or with status {@code 400 (Bad Request)} if the copperFiber is not valid,
     * or with status {@code 404 (Not Found)} if the copperFiber is not found,
     * or with status {@code 500 (Internal Server Error)} if the copperFiber couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/copper-fibers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CopperFiber> partialUpdateCopperFiber(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CopperFiber copperFiber
    ) throws URISyntaxException {
        log.debug("REST request to partial update CopperFiber partially : {}, {}", id, copperFiber);
        if (copperFiber.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, copperFiber.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!copperFiberRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CopperFiber> result = copperFiberService.partialUpdate(copperFiber);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, copperFiber.getId().toString())
        );
    }

    /**
     * {@code GET  /copper-fibers} : get all the copperFibers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of copperFibers in body.
     */
    @GetMapping("/copper-fibers")
    public List<CopperFiber> getAllCopperFibers() {
        log.debug("REST request to get all CopperFibers");
        return copperFiberService.findAll();
    }

    /**
     * {@code GET  /copper-fibers/:id} : get the "id" copperFiber.
     *
     * @param id the id of the copperFiber to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the copperFiber, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/copper-fibers/{id}")
    public ResponseEntity<CopperFiber> getCopperFiber(@PathVariable Long id) {
        log.debug("REST request to get CopperFiber : {}", id);
        Optional<CopperFiber> copperFiber = copperFiberService.findOne(id);
        return ResponseUtil.wrapOrNotFound(copperFiber);
    }

    /**
     * {@code DELETE  /copper-fibers/:id} : delete the "id" copperFiber.
     *
     * @param id the id of the copperFiber to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/copper-fibers/{id}")
    public ResponseEntity<Void> deleteCopperFiber(@PathVariable Long id) {
        log.debug("REST request to delete CopperFiber : {}", id);
        copperFiberService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
