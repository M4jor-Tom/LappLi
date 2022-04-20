package com.muller.lappli.web.rest;

import com.muller.lappli.domain.ContinuityWire;
import com.muller.lappli.repository.ContinuityWireRepository;
import com.muller.lappli.service.ContinuityWireService;
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
 * REST controller for managing {@link com.muller.lappli.domain.ContinuityWire}.
 */
@RestController
@RequestMapping("/api")
public class ContinuityWireResource {

    private final Logger log = LoggerFactory.getLogger(ContinuityWireResource.class);

    private static final String ENTITY_NAME = "continuityWire";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContinuityWireService continuityWireService;

    private final ContinuityWireRepository continuityWireRepository;

    public ContinuityWireResource(ContinuityWireService continuityWireService, ContinuityWireRepository continuityWireRepository) {
        this.continuityWireService = continuityWireService;
        this.continuityWireRepository = continuityWireRepository;
    }

    /**
     * {@code POST  /continuity-wires} : Create a new continuityWire.
     *
     * @param continuityWire the continuityWire to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new continuityWire, or with status {@code 400 (Bad Request)} if the continuityWire has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/continuity-wires")
    public ResponseEntity<ContinuityWire> createContinuityWire(@Valid @RequestBody ContinuityWire continuityWire)
        throws URISyntaxException {
        log.debug("REST request to save ContinuityWire : {}", continuityWire);
        if (continuityWire.getId() != null) {
            throw new BadRequestAlertException("A new continuityWire cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContinuityWire result = continuityWireService.save(continuityWire);
        return ResponseEntity
            .created(new URI("/api/continuity-wires/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /continuity-wires/:id} : Updates an existing continuityWire.
     *
     * @param id the id of the continuityWire to save.
     * @param continuityWire the continuityWire to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated continuityWire,
     * or with status {@code 400 (Bad Request)} if the continuityWire is not valid,
     * or with status {@code 500 (Internal Server Error)} if the continuityWire couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/continuity-wires/{id}")
    public ResponseEntity<ContinuityWire> updateContinuityWire(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ContinuityWire continuityWire
    ) throws URISyntaxException {
        log.debug("REST request to update ContinuityWire : {}, {}", id, continuityWire);
        if (continuityWire.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, continuityWire.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!continuityWireRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ContinuityWire result = continuityWireService.save(continuityWire);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, continuityWire.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /continuity-wires/:id} : Partial updates given fields of an existing continuityWire, field will ignore if it is null
     *
     * @param id the id of the continuityWire to save.
     * @param continuityWire the continuityWire to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated continuityWire,
     * or with status {@code 400 (Bad Request)} if the continuityWire is not valid,
     * or with status {@code 404 (Not Found)} if the continuityWire is not found,
     * or with status {@code 500 (Internal Server Error)} if the continuityWire couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/continuity-wires/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ContinuityWire> partialUpdateContinuityWire(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ContinuityWire continuityWire
    ) throws URISyntaxException {
        log.debug("REST request to partial update ContinuityWire partially : {}, {}", id, continuityWire);
        if (continuityWire.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, continuityWire.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!continuityWireRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ContinuityWire> result = continuityWireService.partialUpdate(continuityWire);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, continuityWire.getId().toString())
        );
    }

    /**
     * {@code GET  /continuity-wires} : get all the continuityWires.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of continuityWires in body.
     */
    @GetMapping("/continuity-wires")
    public List<ContinuityWire> getAllContinuityWires() {
        log.debug("REST request to get all ContinuityWires");
        return continuityWireService.findAll();
    }

    /**
     * {@code GET  /continuity-wires/:id} : get the "id" continuityWire.
     *
     * @param id the id of the continuityWire to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the continuityWire, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/continuity-wires/{id}")
    public ResponseEntity<ContinuityWire> getContinuityWire(@PathVariable Long id) {
        log.debug("REST request to get ContinuityWire : {}", id);
        Optional<ContinuityWire> continuityWire = continuityWireService.findOne(id);
        return ResponseUtil.wrapOrNotFound(continuityWire);
    }

    /**
     * {@code DELETE  /continuity-wires/:id} : delete the "id" continuityWire.
     *
     * @param id the id of the continuityWire to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/continuity-wires/{id}")
    public ResponseEntity<Void> deleteContinuityWire(@PathVariable Long id) {
        log.debug("REST request to delete ContinuityWire : {}", id);
        continuityWireService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
