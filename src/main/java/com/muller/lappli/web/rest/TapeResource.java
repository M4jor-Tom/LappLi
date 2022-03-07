package com.muller.lappli.web.rest;

import com.muller.lappli.domain.Tape;
import com.muller.lappli.repository.TapeRepository;
import com.muller.lappli.service.TapeService;
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
 * REST controller for managing {@link com.muller.lappli.domain.Tape}.
 */
@RestController
@RequestMapping("/api")
public class TapeResource {

    private final Logger log = LoggerFactory.getLogger(TapeResource.class);

    private static final String ENTITY_NAME = "tape";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TapeService tapeService;

    private final TapeRepository tapeRepository;

    public TapeResource(TapeService tapeService, TapeRepository tapeRepository) {
        this.tapeService = tapeService;
        this.tapeRepository = tapeRepository;
    }

    /**
     * {@code POST  /tapes} : Create a new tape.
     *
     * @param tape the tape to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tape, or with status {@code 400 (Bad Request)} if the tape has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tapes")
    public ResponseEntity<Tape> createTape(@Valid @RequestBody Tape tape) throws URISyntaxException {
        log.debug("REST request to save Tape : {}", tape);
        if (tape.getId() != null) {
            throw new BadRequestAlertException("A new tape cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Tape result = tapeService.save(tape);
        return ResponseEntity
            .created(new URI("/api/tapes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tapes/:id} : Updates an existing tape.
     *
     * @param id the id of the tape to save.
     * @param tape the tape to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tape,
     * or with status {@code 400 (Bad Request)} if the tape is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tape couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tapes/{id}")
    public ResponseEntity<Tape> updateTape(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Tape tape)
        throws URISyntaxException {
        log.debug("REST request to update Tape : {}, {}", id, tape);
        if (tape.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tape.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tapeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Tape result = tapeService.save(tape);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tape.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tapes/:id} : Partial updates given fields of an existing tape, field will ignore if it is null
     *
     * @param id the id of the tape to save.
     * @param tape the tape to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tape,
     * or with status {@code 400 (Bad Request)} if the tape is not valid,
     * or with status {@code 404 (Not Found)} if the tape is not found,
     * or with status {@code 500 (Internal Server Error)} if the tape couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tapes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Tape> partialUpdateTape(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Tape tape
    ) throws URISyntaxException {
        log.debug("REST request to partial update Tape partially : {}, {}", id, tape);
        if (tape.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tape.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tapeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Tape> result = tapeService.partialUpdate(tape);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tape.getId().toString())
        );
    }

    /**
     * {@code GET  /tapes} : get all the tapes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tapes in body.
     */
    @GetMapping("/tapes")
    public List<Tape> getAllTapes() {
        log.debug("REST request to get all Tapes");
        return tapeService.findAll();
    }

    /**
     * {@code GET  /tapes/:id} : get the "id" tape.
     *
     * @param id the id of the tape to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tape, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tapes/{id}")
    public ResponseEntity<Tape> getTape(@PathVariable Long id) {
        log.debug("REST request to get Tape : {}", id);
        Optional<Tape> tape = tapeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tape);
    }

    /**
     * {@code DELETE  /tapes/:id} : delete the "id" tape.
     *
     * @param id the id of the tape to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tapes/{id}")
    public ResponseEntity<Void> deleteTape(@PathVariable Long id) {
        log.debug("REST request to delete Tape : {}", id);
        tapeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
