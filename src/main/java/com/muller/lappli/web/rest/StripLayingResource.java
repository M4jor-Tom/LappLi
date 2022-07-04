package com.muller.lappli.web.rest;

import com.muller.lappli.domain.StripLaying;
import com.muller.lappli.repository.StripLayingRepository;
import com.muller.lappli.service.StripLayingService;
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
 * REST controller for managing {@link com.muller.lappli.domain.StripLaying}.
 */
@RestController
@RequestMapping("/api")
public class StripLayingResource {

    private final Logger log = LoggerFactory.getLogger(StripLayingResource.class);

    private static final String ENTITY_NAME = "stripLaying";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StripLayingService stripLayingService;

    private final StripLayingRepository stripLayingRepository;

    public StripLayingResource(StripLayingService stripLayingService, StripLayingRepository stripLayingRepository) {
        this.stripLayingService = stripLayingService;
        this.stripLayingRepository = stripLayingRepository;
    }

    /**
     * {@code POST  /strip-layings} : Create a new stripLaying.
     *
     * @param stripLaying the stripLaying to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stripLaying, or with status {@code 400 (Bad Request)} if the stripLaying has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/strip-layings")
    public ResponseEntity<StripLaying> createStripLaying(@Valid @RequestBody StripLaying stripLaying) throws URISyntaxException {
        log.debug("REST request to save StripLaying : {}", stripLaying);
        if (stripLaying.getId() != null) {
            throw new BadRequestAlertException("A new stripLaying cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StripLaying result = stripLayingService.save(stripLaying, true, true);
        return ResponseEntity
            .created(new URI("/api/strip-layings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /strip-layings/:id} : Updates an existing stripLaying.
     *
     * @param id the id of the stripLaying to save.
     * @param stripLaying the stripLaying to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stripLaying,
     * or with status {@code 400 (Bad Request)} if the stripLaying is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stripLaying couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/strip-layings/{id}")
    public ResponseEntity<StripLaying> updateStripLaying(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody StripLaying stripLaying
    ) throws URISyntaxException {
        log.debug("REST request to update StripLaying : {}, {}", id, stripLaying);
        if (stripLaying.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stripLaying.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stripLayingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        StripLaying result = stripLayingService.save(stripLaying, true, true);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stripLaying.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /strip-layings/:id} : Partial updates given fields of an existing stripLaying, field will ignore if it is null
     *
     * @param id the id of the stripLaying to save.
     * @param stripLaying the stripLaying to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stripLaying,
     * or with status {@code 400 (Bad Request)} if the stripLaying is not valid,
     * or with status {@code 404 (Not Found)} if the stripLaying is not found,
     * or with status {@code 500 (Internal Server Error)} if the stripLaying couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/strip-layings/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<StripLaying> partialUpdateStripLaying(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody StripLaying stripLaying
    ) throws URISyntaxException {
        log.debug("REST request to partial update StripLaying partially : {}, {}", id, stripLaying);
        if (stripLaying.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stripLaying.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stripLayingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StripLaying> result = stripLayingService.partialUpdate(stripLaying);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stripLaying.getId().toString())
        );
    }

    /**
     * {@code GET  /strip-layings} : get all the stripLayings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stripLayings in body.
     */
    @GetMapping("/strip-layings")
    public List<StripLaying> getAllStripLayings() {
        log.debug("REST request to get all StripLayings");
        return stripLayingService.findAll();
    }

    /**
     * {@code GET  /strip-layings/:id} : get the "id" stripLaying.
     *
     * @param id the id of the stripLaying to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stripLaying, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/strip-layings/{id}")
    public ResponseEntity<StripLaying> getStripLaying(@PathVariable Long id) {
        log.debug("REST request to get StripLaying : {}", id);
        Optional<StripLaying> stripLaying = stripLayingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stripLaying);
    }

    /**
     * {@code DELETE  /strip-layings/:id} : delete the "id" stripLaying.
     *
     * @param id the id of the stripLaying to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/strip-layings/{id}")
    public ResponseEntity<Void> deleteStripLaying(@PathVariable Long id) {
        log.debug("REST request to delete StripLaying : {}", id);
        stripLayingService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
