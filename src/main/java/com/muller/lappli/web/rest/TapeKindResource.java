package com.muller.lappli.web.rest;

import com.muller.lappli.domain.TapeKind;
import com.muller.lappli.repository.TapeKindRepository;
import com.muller.lappli.service.TapeKindService;
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
 * REST controller for managing {@link com.muller.lappli.domain.TapeKind}.
 */
@RestController
@RequestMapping("/api")
public class TapeKindResource {

    private final Logger log = LoggerFactory.getLogger(TapeKindResource.class);

    private static final String ENTITY_NAME = "tapeKind";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TapeKindService tapeKindService;

    private final TapeKindRepository tapeKindRepository;

    public TapeKindResource(TapeKindService tapeKindService, TapeKindRepository tapeKindRepository) {
        this.tapeKindService = tapeKindService;
        this.tapeKindRepository = tapeKindRepository;
    }

    /**
     * {@code POST  /tape-kinds} : Create a new tapeKind.
     *
     * @param tapeKind the tapeKind to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tapeKind, or with status {@code 400 (Bad Request)} if the tapeKind has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tape-kinds")
    public ResponseEntity<TapeKind> createTapeKind(@Valid @RequestBody TapeKind tapeKind) throws URISyntaxException {
        log.debug("REST request to save TapeKind : {}", tapeKind);
        if (tapeKind.getId() != null) {
            throw new BadRequestAlertException("A new tapeKind cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TapeKind result = tapeKindService.save(tapeKind);
        return ResponseEntity
            .created(new URI("/api/tape-kinds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tape-kinds/:id} : Updates an existing tapeKind.
     *
     * @param id the id of the tapeKind to save.
     * @param tapeKind the tapeKind to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tapeKind,
     * or with status {@code 400 (Bad Request)} if the tapeKind is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tapeKind couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tape-kinds/{id}")
    public ResponseEntity<TapeKind> updateTapeKind(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TapeKind tapeKind
    ) throws URISyntaxException {
        log.debug("REST request to update TapeKind : {}, {}", id, tapeKind);
        if (tapeKind.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tapeKind.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tapeKindRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TapeKind result = tapeKindService.save(tapeKind);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tapeKind.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tape-kinds/:id} : Partial updates given fields of an existing tapeKind, field will ignore if it is null
     *
     * @param id the id of the tapeKind to save.
     * @param tapeKind the tapeKind to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tapeKind,
     * or with status {@code 400 (Bad Request)} if the tapeKind is not valid,
     * or with status {@code 404 (Not Found)} if the tapeKind is not found,
     * or with status {@code 500 (Internal Server Error)} if the tapeKind couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tape-kinds/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TapeKind> partialUpdateTapeKind(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TapeKind tapeKind
    ) throws URISyntaxException {
        log.debug("REST request to partial update TapeKind partially : {}, {}", id, tapeKind);
        if (tapeKind.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tapeKind.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tapeKindRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TapeKind> result = tapeKindService.partialUpdate(tapeKind);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tapeKind.getId().toString())
        );
    }

    /**
     * {@code GET  /tape-kinds} : get all the tapeKinds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tapeKinds in body.
     */
    @GetMapping("/tape-kinds")
    public List<TapeKind> getAllTapeKinds() {
        log.debug("REST request to get all TapeKinds");
        return tapeKindService.findAll();
    }

    /**
     * {@code GET  /tape-kinds/:id} : get the "id" tapeKind.
     *
     * @param id the id of the tapeKind to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tapeKind, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tape-kinds/{id}")
    public ResponseEntity<TapeKind> getTapeKind(@PathVariable Long id) {
        log.debug("REST request to get TapeKind : {}", id);
        Optional<TapeKind> tapeKind = tapeKindService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tapeKind);
    }

    /**
     * {@code DELETE  /tape-kinds/:id} : delete the "id" tapeKind.
     *
     * @param id the id of the tapeKind to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tape-kinds/{id}")
    public ResponseEntity<Void> deleteTapeKind(@PathVariable Long id) {
        log.debug("REST request to delete TapeKind : {}", id);
        tapeKindService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
