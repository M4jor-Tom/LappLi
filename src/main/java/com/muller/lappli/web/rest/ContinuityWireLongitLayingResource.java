package com.muller.lappli.web.rest;

import com.muller.lappli.domain.ContinuityWireLongitLaying;
import com.muller.lappli.repository.ContinuityWireLongitLayingRepository;
import com.muller.lappli.service.ContinuityWireLongitLayingService;
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
 * REST controller for managing {@link com.muller.lappli.domain.ContinuityWireLongitLaying}.
 */
@RestController
@RequestMapping("/api")
public class ContinuityWireLongitLayingResource {

    private final Logger log = LoggerFactory.getLogger(ContinuityWireLongitLayingResource.class);

    private static final String ENTITY_NAME = "continuityWireLongitLaying";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContinuityWireLongitLayingService continuityWireLongitLayingService;

    private final ContinuityWireLongitLayingRepository continuityWireLongitLayingRepository;

    public ContinuityWireLongitLayingResource(
        ContinuityWireLongitLayingService continuityWireLongitLayingService,
        ContinuityWireLongitLayingRepository continuityWireLongitLayingRepository
    ) {
        this.continuityWireLongitLayingService = continuityWireLongitLayingService;
        this.continuityWireLongitLayingRepository = continuityWireLongitLayingRepository;
    }

    /**
     * {@code POST  /continuity-wire-longit-layings} : Create a new continuityWireLongitLaying.
     *
     * @param continuityWireLongitLaying the continuityWireLongitLaying to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new continuityWireLongitLaying, or with status {@code 400 (Bad Request)} if the continuityWireLongitLaying has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/continuity-wire-longit-layings")
    public ResponseEntity<ContinuityWireLongitLaying> createContinuityWireLongitLaying(
        @Valid @RequestBody ContinuityWireLongitLaying continuityWireLongitLaying
    ) throws URISyntaxException {
        log.debug("REST request to save ContinuityWireLongitLaying : {}", continuityWireLongitLaying);
        if (continuityWireLongitLaying.getId() != null) {
            throw new BadRequestAlertException("A new continuityWireLongitLaying cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContinuityWireLongitLaying result = continuityWireLongitLayingService.save(continuityWireLongitLaying);
        return ResponseEntity
            .created(new URI("/api/continuity-wire-longit-layings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /continuity-wire-longit-layings/:id} : Updates an existing continuityWireLongitLaying.
     *
     * @param id the id of the continuityWireLongitLaying to save.
     * @param continuityWireLongitLaying the continuityWireLongitLaying to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated continuityWireLongitLaying,
     * or with status {@code 400 (Bad Request)} if the continuityWireLongitLaying is not valid,
     * or with status {@code 500 (Internal Server Error)} if the continuityWireLongitLaying couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/continuity-wire-longit-layings/{id}")
    public ResponseEntity<ContinuityWireLongitLaying> updateContinuityWireLongitLaying(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ContinuityWireLongitLaying continuityWireLongitLaying
    ) throws URISyntaxException {
        log.debug("REST request to update ContinuityWireLongitLaying : {}, {}", id, continuityWireLongitLaying);
        if (continuityWireLongitLaying.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, continuityWireLongitLaying.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!continuityWireLongitLayingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ContinuityWireLongitLaying result = continuityWireLongitLayingService.save(continuityWireLongitLaying);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, continuityWireLongitLaying.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /continuity-wire-longit-layings/:id} : Partial updates given fields of an existing continuityWireLongitLaying, field will ignore if it is null
     *
     * @param id the id of the continuityWireLongitLaying to save.
     * @param continuityWireLongitLaying the continuityWireLongitLaying to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated continuityWireLongitLaying,
     * or with status {@code 400 (Bad Request)} if the continuityWireLongitLaying is not valid,
     * or with status {@code 404 (Not Found)} if the continuityWireLongitLaying is not found,
     * or with status {@code 500 (Internal Server Error)} if the continuityWireLongitLaying couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/continuity-wire-longit-layings/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ContinuityWireLongitLaying> partialUpdateContinuityWireLongitLaying(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ContinuityWireLongitLaying continuityWireLongitLaying
    ) throws URISyntaxException {
        log.debug("REST request to partial update ContinuityWireLongitLaying partially : {}, {}", id, continuityWireLongitLaying);
        if (continuityWireLongitLaying.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, continuityWireLongitLaying.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!continuityWireLongitLayingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ContinuityWireLongitLaying> result = continuityWireLongitLayingService.partialUpdate(continuityWireLongitLaying);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, continuityWireLongitLaying.getId().toString())
        );
    }

    /**
     * {@code GET  /continuity-wire-longit-layings} : get all the continuityWireLongitLayings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of continuityWireLongitLayings in body.
     */
    @GetMapping("/continuity-wire-longit-layings")
    public List<ContinuityWireLongitLaying> getAllContinuityWireLongitLayings() {
        log.debug("REST request to get all ContinuityWireLongitLayings");
        return continuityWireLongitLayingService.findAll();
    }

    /**
     * {@code GET  /continuity-wire-longit-layings/:id} : get the "id" continuityWireLongitLaying.
     *
     * @param id the id of the continuityWireLongitLaying to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the continuityWireLongitLaying, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/continuity-wire-longit-layings/{id}")
    public ResponseEntity<ContinuityWireLongitLaying> getContinuityWireLongitLaying(@PathVariable Long id) {
        log.debug("REST request to get ContinuityWireLongitLaying : {}", id);
        Optional<ContinuityWireLongitLaying> continuityWireLongitLaying = continuityWireLongitLayingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(continuityWireLongitLaying);
    }

    /**
     * {@code DELETE  /continuity-wire-longit-layings/:id} : delete the "id" continuityWireLongitLaying.
     *
     * @param id the id of the continuityWireLongitLaying to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/continuity-wire-longit-layings/{id}")
    public ResponseEntity<Void> deleteContinuityWireLongitLaying(@PathVariable Long id) {
        log.debug("REST request to delete ContinuityWireLongitLaying : {}", id);
        continuityWireLongitLayingService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
