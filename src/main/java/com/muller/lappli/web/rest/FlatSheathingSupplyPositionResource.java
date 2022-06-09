package com.muller.lappli.web.rest;

import com.muller.lappli.domain.FlatSheathingSupplyPosition;
import com.muller.lappli.repository.FlatSheathingSupplyPositionRepository;
import com.muller.lappli.service.FlatSheathingSupplyPositionService;
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
 * REST controller for managing {@link com.muller.lappli.domain.FlatSheathingSupplyPosition}.
 */
@RestController
@RequestMapping("/api")
public class FlatSheathingSupplyPositionResource {

    private final Logger log = LoggerFactory.getLogger(FlatSheathingSupplyPositionResource.class);

    private static final String ENTITY_NAME = "flatSheathingSupplyPosition";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FlatSheathingSupplyPositionService flatSheathingSupplyPositionService;

    private final FlatSheathingSupplyPositionRepository flatSheathingSupplyPositionRepository;

    public FlatSheathingSupplyPositionResource(
        FlatSheathingSupplyPositionService flatSheathingSupplyPositionService,
        FlatSheathingSupplyPositionRepository flatSheathingSupplyPositionRepository
    ) {
        this.flatSheathingSupplyPositionService = flatSheathingSupplyPositionService;
        this.flatSheathingSupplyPositionRepository = flatSheathingSupplyPositionRepository;
    }

    /**
     * {@code POST  /flat-sheathing-supply-positions} : Create a new flatSheathingSupplyPosition.
     *
     * @param flatSheathingSupplyPosition the flatSheathingSupplyPosition to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new flatSheathingSupplyPosition, or with status {@code 400 (Bad Request)} if the flatSheathingSupplyPosition has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/flat-sheathing-supply-positions")
    public ResponseEntity<FlatSheathingSupplyPosition> createFlatSheathingSupplyPosition(
        @Valid @RequestBody FlatSheathingSupplyPosition flatSheathingSupplyPosition
    ) throws URISyntaxException {
        log.debug("REST request to save FlatSheathingSupplyPosition : {}", flatSheathingSupplyPosition);
        if (flatSheathingSupplyPosition.getId() != null) {
            throw new BadRequestAlertException("A new flatSheathingSupplyPosition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (Objects.isNull(flatSheathingSupplyPosition.getSupplyPosition())) {
            throw new BadRequestAlertException("Invalid association value provided", ENTITY_NAME, "null");
        }
        FlatSheathingSupplyPosition result = flatSheathingSupplyPositionService.save(flatSheathingSupplyPosition);
        return ResponseEntity
            .created(new URI("/api/flat-sheathing-supply-positions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /flat-sheathing-supply-positions/:id} : Updates an existing flatSheathingSupplyPosition.
     *
     * @param id the id of the flatSheathingSupplyPosition to save.
     * @param flatSheathingSupplyPosition the flatSheathingSupplyPosition to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated flatSheathingSupplyPosition,
     * or with status {@code 400 (Bad Request)} if the flatSheathingSupplyPosition is not valid,
     * or with status {@code 500 (Internal Server Error)} if the flatSheathingSupplyPosition couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/flat-sheathing-supply-positions/{id}")
    public ResponseEntity<FlatSheathingSupplyPosition> updateFlatSheathingSupplyPosition(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FlatSheathingSupplyPosition flatSheathingSupplyPosition
    ) throws URISyntaxException {
        log.debug("REST request to update FlatSheathingSupplyPosition : {}, {}", id, flatSheathingSupplyPosition);
        if (flatSheathingSupplyPosition.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, flatSheathingSupplyPosition.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!flatSheathingSupplyPositionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FlatSheathingSupplyPosition result = flatSheathingSupplyPositionService.save(flatSheathingSupplyPosition);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, flatSheathingSupplyPosition.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /flat-sheathing-supply-positions/:id} : Partial updates given fields of an existing flatSheathingSupplyPosition, field will ignore if it is null
     *
     * @param id the id of the flatSheathingSupplyPosition to save.
     * @param flatSheathingSupplyPosition the flatSheathingSupplyPosition to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated flatSheathingSupplyPosition,
     * or with status {@code 400 (Bad Request)} if the flatSheathingSupplyPosition is not valid,
     * or with status {@code 404 (Not Found)} if the flatSheathingSupplyPosition is not found,
     * or with status {@code 500 (Internal Server Error)} if the flatSheathingSupplyPosition couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/flat-sheathing-supply-positions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FlatSheathingSupplyPosition> partialUpdateFlatSheathingSupplyPosition(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FlatSheathingSupplyPosition flatSheathingSupplyPosition
    ) throws URISyntaxException {
        log.debug("REST request to partial update FlatSheathingSupplyPosition partially : {}, {}", id, flatSheathingSupplyPosition);
        if (flatSheathingSupplyPosition.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, flatSheathingSupplyPosition.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!flatSheathingSupplyPositionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FlatSheathingSupplyPosition> result = flatSheathingSupplyPositionService.partialUpdate(flatSheathingSupplyPosition);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, flatSheathingSupplyPosition.getId().toString())
        );
    }

    /**
     * {@code GET  /flat-sheathing-supply-positions} : get all the flatSheathingSupplyPositions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of flatSheathingSupplyPositions in body.
     */
    @GetMapping("/flat-sheathing-supply-positions")
    public List<FlatSheathingSupplyPosition> getAllFlatSheathingSupplyPositions() {
        log.debug("REST request to get all FlatSheathingSupplyPositions");
        return flatSheathingSupplyPositionService.findAll();
    }

    /**
     * {@code GET  /flat-sheathing-supply-positions/:id} : get the "id" flatSheathingSupplyPosition.
     *
     * @param id the id of the flatSheathingSupplyPosition to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the flatSheathingSupplyPosition, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/flat-sheathing-supply-positions/{id}")
    public ResponseEntity<FlatSheathingSupplyPosition> getFlatSheathingSupplyPosition(@PathVariable Long id) {
        log.debug("REST request to get FlatSheathingSupplyPosition : {}", id);
        Optional<FlatSheathingSupplyPosition> flatSheathingSupplyPosition = flatSheathingSupplyPositionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(flatSheathingSupplyPosition);
    }

    /**
     * {@code DELETE  /flat-sheathing-supply-positions/:id} : delete the "id" flatSheathingSupplyPosition.
     *
     * @param id the id of the flatSheathingSupplyPosition to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/flat-sheathing-supply-positions/{id}")
    public ResponseEntity<Void> deleteFlatSheathingSupplyPosition(@PathVariable Long id) {
        log.debug("REST request to delete FlatSheathingSupplyPosition : {}", id);
        flatSheathingSupplyPositionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
