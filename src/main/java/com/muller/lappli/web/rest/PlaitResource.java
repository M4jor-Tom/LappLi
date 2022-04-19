package com.muller.lappli.web.rest;

import com.muller.lappli.domain.Plait;
import com.muller.lappli.repository.PlaitRepository;
import com.muller.lappli.service.PlaitService;
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
 * REST controller for managing {@link com.muller.lappli.domain.Plait}.
 */
@RestController
@RequestMapping("/api")
public class PlaitResource {

    private final Logger log = LoggerFactory.getLogger(PlaitResource.class);

    private static final String ENTITY_NAME = "plait";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlaitService plaitService;

    private final PlaitRepository plaitRepository;

    public PlaitResource(PlaitService plaitService, PlaitRepository plaitRepository) {
        this.plaitService = plaitService;
        this.plaitRepository = plaitRepository;
    }

    /**
     * {@code POST  /plaits} : Create a new plait.
     *
     * @param plait the plait to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new plait, or with status {@code 400 (Bad Request)} if the plait has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/plaits")
    public ResponseEntity<Plait> createPlait(@Valid @RequestBody Plait plait) throws URISyntaxException {
        log.debug("REST request to save Plait : {}", plait);
        if (plait.getId() != null) {
            throw new BadRequestAlertException("A new plait cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Plait result = plaitService.save(plait);
        return ResponseEntity
            .created(new URI("/api/plaits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /plaits/:id} : Updates an existing plait.
     *
     * @param id the id of the plait to save.
     * @param plait the plait to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plait,
     * or with status {@code 400 (Bad Request)} if the plait is not valid,
     * or with status {@code 500 (Internal Server Error)} if the plait couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/plaits/{id}")
    public ResponseEntity<Plait> updatePlait(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Plait plait)
        throws URISyntaxException {
        log.debug("REST request to update Plait : {}, {}", id, plait);
        if (plait.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, plait.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!plaitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Plait result = plaitService.save(plait);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, plait.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /plaits/:id} : Partial updates given fields of an existing plait, field will ignore if it is null
     *
     * @param id the id of the plait to save.
     * @param plait the plait to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plait,
     * or with status {@code 400 (Bad Request)} if the plait is not valid,
     * or with status {@code 404 (Not Found)} if the plait is not found,
     * or with status {@code 500 (Internal Server Error)} if the plait couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/plaits/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Plait> partialUpdatePlait(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Plait plait
    ) throws URISyntaxException {
        log.debug("REST request to partial update Plait partially : {}, {}", id, plait);
        if (plait.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, plait.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!plaitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Plait> result = plaitService.partialUpdate(plait);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, plait.getId().toString())
        );
    }

    /**
     * {@code GET  /plaits} : get all the plaits.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of plaits in body.
     */
    @GetMapping("/plaits")
    public List<Plait> getAllPlaits() {
        log.debug("REST request to get all Plaits");
        return plaitService.findAll();
    }

    /**
     * {@code GET  /plaits/:id} : get the "id" plait.
     *
     * @param id the id of the plait to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the plait, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/plaits/{id}")
    public ResponseEntity<Plait> getPlait(@PathVariable Long id) {
        log.debug("REST request to get Plait : {}", id);
        Optional<Plait> plait = plaitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(plait);
    }

    /**
     * {@code DELETE  /plaits/:id} : delete the "id" plait.
     *
     * @param id the id of the plait to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/plaits/{id}")
    public ResponseEntity<Void> deletePlait(@PathVariable Long id) {
        log.debug("REST request to delete Plait : {}", id);
        plaitService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
