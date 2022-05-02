package com.muller.lappli.web.rest;

import com.muller.lappli.domain.FlatSheathing;
import com.muller.lappli.repository.FlatSheathingRepository;
import com.muller.lappli.service.FlatSheathingService;
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
 * REST controller for managing {@link com.muller.lappli.domain.FlatSheathing}.
 */
@RestController
@RequestMapping("/api")
public class FlatSheathingResource {

    private final Logger log = LoggerFactory.getLogger(FlatSheathingResource.class);

    private static final String ENTITY_NAME = "flatSheathing";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FlatSheathingService flatSheathingService;

    private final FlatSheathingRepository flatSheathingRepository;

    public FlatSheathingResource(FlatSheathingService flatSheathingService, FlatSheathingRepository flatSheathingRepository) {
        this.flatSheathingService = flatSheathingService;
        this.flatSheathingRepository = flatSheathingRepository;
    }

    /**
     * {@code POST  /flat-sheathings} : Create a new flatSheathing.
     *
     * @param flatSheathing the flatSheathing to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new flatSheathing, or with status {@code 400 (Bad Request)} if the flatSheathing has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/flat-sheathings")
    public ResponseEntity<FlatSheathing> createFlatSheathing(@Valid @RequestBody FlatSheathing flatSheathing) throws URISyntaxException {
        log.debug("REST request to save FlatSheathing : {}", flatSheathing);
        if (flatSheathing.getId() != null) {
            throw new BadRequestAlertException("A new flatSheathing cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FlatSheathing result = flatSheathingService.save(flatSheathing, true, true);
        return ResponseEntity
            .created(new URI("/api/flat-sheathings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /flat-sheathings/:id} : Updates an existing flatSheathing.
     *
     * @param id the id of the flatSheathing to save.
     * @param flatSheathing the flatSheathing to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated flatSheathing,
     * or with status {@code 400 (Bad Request)} if the flatSheathing is not valid,
     * or with status {@code 500 (Internal Server Error)} if the flatSheathing couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/flat-sheathings/{id}")
    public ResponseEntity<FlatSheathing> updateFlatSheathing(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FlatSheathing flatSheathing
    ) throws URISyntaxException {
        log.debug("REST request to update FlatSheathing : {}, {}", id, flatSheathing);
        if (flatSheathing.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, flatSheathing.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!flatSheathingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FlatSheathing result = flatSheathingService.save(flatSheathing, true, true);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, flatSheathing.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /flat-sheathings/:id} : Partial updates given fields of an existing flatSheathing, field will ignore if it is null
     *
     * @param id the id of the flatSheathing to save.
     * @param flatSheathing the flatSheathing to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated flatSheathing,
     * or with status {@code 400 (Bad Request)} if the flatSheathing is not valid,
     * or with status {@code 404 (Not Found)} if the flatSheathing is not found,
     * or with status {@code 500 (Internal Server Error)} if the flatSheathing couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/flat-sheathings/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FlatSheathing> partialUpdateFlatSheathing(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FlatSheathing flatSheathing
    ) throws URISyntaxException {
        log.debug("REST request to partial update FlatSheathing partially : {}, {}", id, flatSheathing);
        if (flatSheathing.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, flatSheathing.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!flatSheathingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FlatSheathing> result = flatSheathingService.partialUpdate(flatSheathing);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, flatSheathing.getId().toString())
        );
    }

    /**
     * {@code GET  /flat-sheathings} : get all the flatSheathings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of flatSheathings in body.
     */
    @GetMapping("/flat-sheathings")
    public List<FlatSheathing> getAllFlatSheathings() {
        log.debug("REST request to get all FlatSheathings");
        return flatSheathingService.findAll();
    }

    /**
     * {@code GET  /flat-sheathings/:id} : get the "id" flatSheathing.
     *
     * @param id the id of the flatSheathing to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the flatSheathing, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/flat-sheathings/{id}")
    public ResponseEntity<FlatSheathing> getFlatSheathing(@PathVariable Long id) {
        log.debug("REST request to get FlatSheathing : {}", id);
        Optional<FlatSheathing> flatSheathing = flatSheathingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(flatSheathing);
    }

    /**
     * {@code DELETE  /flat-sheathings/:id} : delete the "id" flatSheathing.
     *
     * @param id the id of the flatSheathing to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/flat-sheathings/{id}")
    public ResponseEntity<Void> deleteFlatSheathing(@PathVariable Long id) {
        log.debug("REST request to delete FlatSheathing : {}", id);
        flatSheathingService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
