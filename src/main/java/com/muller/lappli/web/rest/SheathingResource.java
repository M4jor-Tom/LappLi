package com.muller.lappli.web.rest;

import com.muller.lappli.domain.Sheathing;
import com.muller.lappli.repository.SheathingRepository;
import com.muller.lappli.service.SheathingService;
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
 * REST controller for managing {@link com.muller.lappli.domain.Sheathing}.
 */
@RestController
@RequestMapping("/api")
public class SheathingResource {

    private final Logger log = LoggerFactory.getLogger(SheathingResource.class);

    private static final String ENTITY_NAME = "sheathing";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SheathingService sheathingService;

    private final SheathingRepository sheathingRepository;

    public SheathingResource(SheathingService sheathingService, SheathingRepository sheathingRepository) {
        this.sheathingService = sheathingService;
        this.sheathingRepository = sheathingRepository;
    }

    /**
     * {@code POST  /sheathings} : Create a new sheathing.
     *
     * @param sheathing the sheathing to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sheathing, or with status {@code 400 (Bad Request)} if the sheathing has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sheathings")
    public ResponseEntity<Sheathing> createSheathing(@Valid @RequestBody Sheathing sheathing) throws URISyntaxException {
        log.debug("REST request to save Sheathing : {}", sheathing);
        if (sheathing.getId() != null) {
            throw new BadRequestAlertException("A new sheathing cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Sheathing result = sheathingService.save(sheathing, true);
        return ResponseEntity
            .created(new URI("/api/sheathings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sheathings/:id} : Updates an existing sheathing.
     *
     * @param id the id of the sheathing to save.
     * @param sheathing the sheathing to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sheathing,
     * or with status {@code 400 (Bad Request)} if the sheathing is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sheathing couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sheathings/{id}")
    public ResponseEntity<Sheathing> updateSheathing(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Sheathing sheathing
    ) throws URISyntaxException {
        log.debug("REST request to update Sheathing : {}, {}", id, sheathing);
        if (sheathing.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sheathing.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sheathingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Sheathing result = sheathingService.save(sheathing, true);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sheathing.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sheathings/:id} : Partial updates given fields of an existing sheathing, field will ignore if it is null
     *
     * @param id the id of the sheathing to save.
     * @param sheathing the sheathing to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sheathing,
     * or with status {@code 400 (Bad Request)} if the sheathing is not valid,
     * or with status {@code 404 (Not Found)} if the sheathing is not found,
     * or with status {@code 500 (Internal Server Error)} if the sheathing couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sheathings/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Sheathing> partialUpdateSheathing(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Sheathing sheathing
    ) throws URISyntaxException {
        log.debug("REST request to partial update Sheathing partially : {}, {}", id, sheathing);
        if (sheathing.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sheathing.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sheathingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Sheathing> result = sheathingService.partialUpdate(sheathing);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sheathing.getId().toString())
        );
    }

    /**
     * {@code GET  /sheathings} : get all the sheathings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sheathings in body.
     */
    @GetMapping("/sheathings")
    public List<Sheathing> getAllSheathings() {
        log.debug("REST request to get all Sheathings");
        return sheathingService.findAll();
    }

    /**
     * {@code GET  /sheathings/:id} : get the "id" sheathing.
     *
     * @param id the id of the sheathing to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sheathing, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sheathings/{id}")
    public ResponseEntity<Sheathing> getSheathing(@PathVariable Long id) {
        log.debug("REST request to get Sheathing : {}", id);
        Optional<Sheathing> sheathing = sheathingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sheathing);
    }

    /**
     * {@code DELETE  /sheathings/:id} : delete the "id" sheathing.
     *
     * @param id the id of the sheathing to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sheathings/{id}")
    public ResponseEntity<Void> deleteSheathing(@PathVariable Long id) {
        log.debug("REST request to delete Sheathing : {}", id);
        sheathingService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
