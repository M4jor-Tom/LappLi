package com.muller.lappli.web.rest;

import com.muller.lappli.domain.Plaiter;
import com.muller.lappli.repository.PlaiterRepository;
import com.muller.lappli.service.PlaiterService;
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
 * REST controller for managing {@link com.muller.lappli.domain.Plaiter}.
 */
@RestController
@RequestMapping("/api")
public class PlaiterResource {

    private final Logger log = LoggerFactory.getLogger(PlaiterResource.class);

    private static final String ENTITY_NAME = "plaiter";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlaiterService plaiterService;

    private final PlaiterRepository plaiterRepository;

    public PlaiterResource(PlaiterService plaiterService, PlaiterRepository plaiterRepository) {
        this.plaiterService = plaiterService;
        this.plaiterRepository = plaiterRepository;
    }

    /**
     * {@code POST  /plaiters} : Create a new plaiter.
     *
     * @param plaiter the plaiter to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new plaiter, or with status {@code 400 (Bad Request)} if the plaiter has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/plaiters")
    public ResponseEntity<Plaiter> createPlaiter(@Valid @RequestBody Plaiter plaiter) throws URISyntaxException {
        log.debug("REST request to save Plaiter : {}", plaiter);
        if (plaiter.getId() != null) {
            throw new BadRequestAlertException("A new plaiter cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Plaiter result = plaiterService.save(plaiter);
        return ResponseEntity
            .created(new URI("/api/plaiters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /plaiters/:id} : Updates an existing plaiter.
     *
     * @param id the id of the plaiter to save.
     * @param plaiter the plaiter to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plaiter,
     * or with status {@code 400 (Bad Request)} if the plaiter is not valid,
     * or with status {@code 500 (Internal Server Error)} if the plaiter couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/plaiters/{id}")
    public ResponseEntity<Plaiter> updatePlaiter(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Plaiter plaiter
    ) throws URISyntaxException {
        log.debug("REST request to update Plaiter : {}, {}", id, plaiter);
        if (plaiter.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, plaiter.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!plaiterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Plaiter result = plaiterService.save(plaiter);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, plaiter.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /plaiters/:id} : Partial updates given fields of an existing plaiter, field will ignore if it is null
     *
     * @param id the id of the plaiter to save.
     * @param plaiter the plaiter to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plaiter,
     * or with status {@code 400 (Bad Request)} if the plaiter is not valid,
     * or with status {@code 404 (Not Found)} if the plaiter is not found,
     * or with status {@code 500 (Internal Server Error)} if the plaiter couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/plaiters/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Plaiter> partialUpdatePlaiter(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Plaiter plaiter
    ) throws URISyntaxException {
        log.debug("REST request to partial update Plaiter partially : {}, {}", id, plaiter);
        if (plaiter.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, plaiter.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!plaiterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Plaiter> result = plaiterService.partialUpdate(plaiter);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, plaiter.getId().toString())
        );
    }

    /**
     * {@code GET  /plaiters} : get all the plaiters.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of plaiters in body.
     */
    @GetMapping("/plaiters")
    public List<Plaiter> getAllPlaiters() {
        log.debug("REST request to get all Plaiters");
        return plaiterService.findAll();
    }

    /**
     * {@code GET  /plaiters/:id} : get the "id" plaiter.
     *
     * @param id the id of the plaiter to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the plaiter, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/plaiters/{id}")
    public ResponseEntity<Plaiter> getPlaiter(@PathVariable Long id) {
        log.debug("REST request to get Plaiter : {}", id);
        Optional<Plaiter> plaiter = plaiterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(plaiter);
    }

    /**
     * {@code DELETE  /plaiters/:id} : delete the "id" plaiter.
     *
     * @param id the id of the plaiter to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/plaiters/{id}")
    public ResponseEntity<Void> deletePlaiter(@PathVariable Long id) {
        log.debug("REST request to delete Plaiter : {}", id);
        plaiterService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
