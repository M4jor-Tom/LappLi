package com.muller.lappli.web.rest;

import com.muller.lappli.domain.MetalFiber;
import com.muller.lappli.repository.MetalFiberRepository;
import com.muller.lappli.service.MetalFiberService;
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
 * REST controller for managing {@link com.muller.lappli.domain.MetalFiber}.
 */
@RestController
@RequestMapping("/api")
public class MetalFiberResource {

    private final Logger log = LoggerFactory.getLogger(MetalFiberResource.class);

    private static final String ENTITY_NAME = "metalFiber";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MetalFiberService metalFiberService;

    private final MetalFiberRepository metalFiberRepository;

    public MetalFiberResource(MetalFiberService metalFiberService, MetalFiberRepository metalFiberRepository) {
        this.metalFiberService = metalFiberService;
        this.metalFiberRepository = metalFiberRepository;
    }

    /**
     * {@code POST  /metal-fibers} : Create a new metalFiber.
     *
     * @param metalFiber the metalFiber to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new metalFiber, or with status {@code 400 (Bad Request)} if the metalFiber has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/metal-fibers")
    public ResponseEntity<MetalFiber> createMetalFiber(@Valid @RequestBody MetalFiber metalFiber) throws URISyntaxException {
        log.debug("REST request to save MetalFiber : {}", metalFiber);
        if (metalFiber.getId() != null) {
            throw new BadRequestAlertException("A new metalFiber cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MetalFiber result = metalFiberService.save(metalFiber);
        return ResponseEntity
            .created(new URI("/api/metal-fibers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /metal-fibers/:id} : Updates an existing metalFiber.
     *
     * @param id the id of the metalFiber to save.
     * @param metalFiber the metalFiber to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated metalFiber,
     * or with status {@code 400 (Bad Request)} if the metalFiber is not valid,
     * or with status {@code 500 (Internal Server Error)} if the metalFiber couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/metal-fibers/{id}")
    public ResponseEntity<MetalFiber> updateMetalFiber(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MetalFiber metalFiber
    ) throws URISyntaxException {
        log.debug("REST request to update MetalFiber : {}, {}", id, metalFiber);
        if (metalFiber.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, metalFiber.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!metalFiberRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MetalFiber result = metalFiberService.save(metalFiber);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, metalFiber.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /metal-fibers/:id} : Partial updates given fields of an existing metalFiber, field will ignore if it is null
     *
     * @param id the id of the metalFiber to save.
     * @param metalFiber the metalFiber to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated metalFiber,
     * or with status {@code 400 (Bad Request)} if the metalFiber is not valid,
     * or with status {@code 404 (Not Found)} if the metalFiber is not found,
     * or with status {@code 500 (Internal Server Error)} if the metalFiber couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/metal-fibers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MetalFiber> partialUpdateMetalFiber(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MetalFiber metalFiber
    ) throws URISyntaxException {
        log.debug("REST request to partial update MetalFiber partially : {}, {}", id, metalFiber);
        if (metalFiber.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, metalFiber.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!metalFiberRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MetalFiber> result = metalFiberService.partialUpdate(metalFiber);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, metalFiber.getId().toString())
        );
    }

    /**
     * {@code GET  /metal-fibers} : get all the metalFibers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of metalFibers in body.
     */
    @GetMapping("/metal-fibers")
    public List<MetalFiber> getAllMetalFibers() {
        log.debug("REST request to get all MetalFibers");
        return metalFiberService.findAll();
    }

    /**
     * {@code GET  /metal-fibers/:id} : get the "id" metalFiber.
     *
     * @param id the id of the metalFiber to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the metalFiber, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/metal-fibers/{id}")
    public ResponseEntity<MetalFiber> getMetalFiber(@PathVariable Long id) {
        log.debug("REST request to get MetalFiber : {}", id);
        Optional<MetalFiber> metalFiber = metalFiberService.findOne(id);
        return ResponseUtil.wrapOrNotFound(metalFiber);
    }

    /**
     * {@code DELETE  /metal-fibers/:id} : delete the "id" metalFiber.
     *
     * @param id the id of the metalFiber to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/metal-fibers/{id}")
    public ResponseEntity<Void> deleteMetalFiber(@PathVariable Long id) {
        log.debug("REST request to delete MetalFiber : {}", id);
        metalFiberService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
