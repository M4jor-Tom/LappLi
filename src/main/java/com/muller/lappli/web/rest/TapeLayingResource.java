package com.muller.lappli.web.rest;

import com.muller.lappli.domain.TapeLaying;
import com.muller.lappli.repository.TapeLayingRepository;
import com.muller.lappli.service.TapeLayingService;
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
 * REST controller for managing {@link com.muller.lappli.domain.TapeLaying}.
 */
@RestController
@RequestMapping("/api")
public class TapeLayingResource {

    private final Logger log = LoggerFactory.getLogger(TapeLayingResource.class);

    private static final String ENTITY_NAME = "tapeLaying";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TapeLayingService tapeLayingService;

    private final TapeLayingRepository tapeLayingRepository;

    public TapeLayingResource(TapeLayingService tapeLayingService, TapeLayingRepository tapeLayingRepository) {
        this.tapeLayingService = tapeLayingService;
        this.tapeLayingRepository = tapeLayingRepository;
    }

    /**
     * {@code POST  /tape-layings} : Create a new tapeLaying.
     *
     * @param tapeLaying the tapeLaying to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tapeLaying, or with status {@code 400 (Bad Request)} if the tapeLaying has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tape-layings")
    public ResponseEntity<TapeLaying> createTapeLaying(@Valid @RequestBody TapeLaying tapeLaying) throws URISyntaxException {
        log.debug("REST request to save TapeLaying : {}", tapeLaying);
        if (tapeLaying.getId() != null) {
            throw new BadRequestAlertException("A new tapeLaying cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TapeLaying result = tapeLayingService.save(tapeLaying);
        return ResponseEntity
            .created(new URI("/api/tape-layings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tape-layings/:id} : Updates an existing tapeLaying.
     *
     * @param id the id of the tapeLaying to save.
     * @param tapeLaying the tapeLaying to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tapeLaying,
     * or with status {@code 400 (Bad Request)} if the tapeLaying is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tapeLaying couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tape-layings/{id}")
    public ResponseEntity<TapeLaying> updateTapeLaying(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TapeLaying tapeLaying
    ) throws URISyntaxException {
        log.debug("REST request to update TapeLaying : {}, {}", id, tapeLaying);
        if (tapeLaying.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tapeLaying.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tapeLayingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TapeLaying result = tapeLayingService.save(tapeLaying);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tapeLaying.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tape-layings/:id} : Partial updates given fields of an existing tapeLaying, field will ignore if it is null
     *
     * @param id the id of the tapeLaying to save.
     * @param tapeLaying the tapeLaying to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tapeLaying,
     * or with status {@code 400 (Bad Request)} if the tapeLaying is not valid,
     * or with status {@code 404 (Not Found)} if the tapeLaying is not found,
     * or with status {@code 500 (Internal Server Error)} if the tapeLaying couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tape-layings/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TapeLaying> partialUpdateTapeLaying(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TapeLaying tapeLaying
    ) throws URISyntaxException {
        log.debug("REST request to partial update TapeLaying partially : {}, {}", id, tapeLaying);
        if (tapeLaying.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tapeLaying.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tapeLayingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TapeLaying> result = tapeLayingService.partialUpdate(tapeLaying, true);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tapeLaying.getId().toString())
        );
    }

    /**
     * {@code GET  /tape-layings} : get all the tapeLayings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tapeLayings in body.
     */
    @GetMapping("/tape-layings")
    public List<TapeLaying> getAllTapeLayings() {
        log.debug("REST request to get all TapeLayings");
        return tapeLayingService.findAll();
    }

    /**
     * {@code GET  /tape-layings/:id} : get the "id" tapeLaying.
     *
     * @param id the id of the tapeLaying to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tapeLaying, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tape-layings/{id}")
    public ResponseEntity<TapeLaying> getTapeLaying(@PathVariable Long id) {
        log.debug("REST request to get TapeLaying : {}", id);
        Optional<TapeLaying> tapeLaying = tapeLayingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tapeLaying);
    }

    /**
     * {@code DELETE  /tape-layings/:id} : delete the "id" tapeLaying.
     *
     * @param id the id of the tapeLaying to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tape-layings/{id}")
    public ResponseEntity<Void> deleteTapeLaying(@PathVariable Long id) {
        log.debug("REST request to delete TapeLaying : {}", id);
        tapeLayingService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
