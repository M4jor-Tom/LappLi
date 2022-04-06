package com.muller.lappli.web.rest;

import com.muller.lappli.domain.Strip;
import com.muller.lappli.repository.StripRepository;
import com.muller.lappli.service.StripService;
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
 * REST controller for managing {@link com.muller.lappli.domain.Strip}.
 */
@RestController
@RequestMapping("/api")
public class StripResource {

    private final Logger log = LoggerFactory.getLogger(StripResource.class);

    private static final String ENTITY_NAME = "strip";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StripService stripService;

    private final StripRepository stripRepository;

    public StripResource(StripService stripService, StripRepository stripRepository) {
        this.stripService = stripService;
        this.stripRepository = stripRepository;
    }

    /**
     * {@code POST  /strips} : Create a new strip.
     *
     * @param strip the strip to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new strip, or with status {@code 400 (Bad Request)} if the strip has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/strips")
    public ResponseEntity<Strip> createStrip(@Valid @RequestBody Strip strip) throws URISyntaxException {
        log.debug("REST request to save Strip : {}", strip);
        if (strip.getId() != null) {
            throw new BadRequestAlertException("A new strip cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Strip result = stripService.save(strip);
        return ResponseEntity
            .created(new URI("/api/strips/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /strips/:id} : Updates an existing strip.
     *
     * @param id the id of the strip to save.
     * @param strip the strip to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated strip,
     * or with status {@code 400 (Bad Request)} if the strip is not valid,
     * or with status {@code 500 (Internal Server Error)} if the strip couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/strips/{id}")
    public ResponseEntity<Strip> updateStrip(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Strip strip)
        throws URISyntaxException {
        log.debug("REST request to update Strip : {}, {}", id, strip);
        if (strip.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, strip.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stripRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Strip result = stripService.save(strip);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, strip.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /strips/:id} : Partial updates given fields of an existing strip, field will ignore if it is null
     *
     * @param id the id of the strip to save.
     * @param strip the strip to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated strip,
     * or with status {@code 400 (Bad Request)} if the strip is not valid,
     * or with status {@code 404 (Not Found)} if the strip is not found,
     * or with status {@code 500 (Internal Server Error)} if the strip couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/strips/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Strip> partialUpdateStrip(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Strip strip
    ) throws URISyntaxException {
        log.debug("REST request to partial update Strip partially : {}, {}", id, strip);
        if (strip.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, strip.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stripRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Strip> result = stripService.partialUpdate(strip);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, strip.getId().toString())
        );
    }

    /**
     * {@code GET  /strips} : get all the strips.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of strips in body.
     */
    @GetMapping("/strips")
    public List<Strip> getAllStrips() {
        log.debug("REST request to get all Strips");
        return stripService.findAll();
    }

    /**
     * {@code GET  /strips/:id} : get the "id" strip.
     *
     * @param id the id of the strip to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the strip, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/strips/{id}")
    public ResponseEntity<Strip> getStrip(@PathVariable Long id) {
        log.debug("REST request to get Strip : {}", id);
        Optional<Strip> strip = stripService.findOne(id);
        return ResponseUtil.wrapOrNotFound(strip);
    }

    /**
     * {@code DELETE  /strips/:id} : delete the "id" strip.
     *
     * @param id the id of the strip to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/strips/{id}")
    public ResponseEntity<Void> deleteStrip(@PathVariable Long id) {
        log.debug("REST request to delete Strip : {}", id);
        stripService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
