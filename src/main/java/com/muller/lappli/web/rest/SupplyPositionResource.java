package com.muller.lappli.web.rest;

import com.muller.lappli.domain.SupplyPosition;
import com.muller.lappli.repository.SupplyPositionRepository;
import com.muller.lappli.service.SupplyPositionService;
import com.muller.lappli.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
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
 * REST controller for managing {@link com.muller.lappli.domain.SupplyPosition}.
 */
@RestController
@RequestMapping("/api")
public class SupplyPositionResource {

    private final Logger log = LoggerFactory.getLogger(SupplyPositionResource.class);

    private static final String ENTITY_NAME = "supplyPosition";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SupplyPositionService supplyPositionService;

    private final SupplyPositionRepository supplyPositionRepository;

    public SupplyPositionResource(SupplyPositionService supplyPositionService, SupplyPositionRepository supplyPositionRepository) {
        this.supplyPositionService = supplyPositionService;
        this.supplyPositionRepository = supplyPositionRepository;
    }

    /**
     * {@code POST  /supply-positions} : Create a new supplyPosition.
     *
     * @param supplyPosition the supplyPosition to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new supplyPosition, or with status {@code 400 (Bad Request)} if the supplyPosition has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/supply-positions")
    public ResponseEntity<SupplyPosition> createSupplyPosition(@Valid @RequestBody SupplyPosition supplyPosition)
        throws URISyntaxException {
        log.debug("REST request to save SupplyPosition : {}", supplyPosition);
        if (supplyPosition.getId() != null) {
            throw new BadRequestAlertException("A new supplyPosition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SupplyPosition result = supplyPositionService.save(supplyPosition);
        return ResponseEntity
            .created(new URI("/api/supply-positions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /supply-positions/:id} : Updates an existing supplyPosition.
     *
     * @param id the id of the supplyPosition to save.
     * @param supplyPosition the supplyPosition to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated supplyPosition,
     * or with status {@code 400 (Bad Request)} if the supplyPosition is not valid,
     * or with status {@code 500 (Internal Server Error)} if the supplyPosition couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/supply-positions/{id}")
    public ResponseEntity<SupplyPosition> updateSupplyPosition(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SupplyPosition supplyPosition
    ) throws URISyntaxException {
        log.debug("REST request to update SupplyPosition : {}, {}", id, supplyPosition);
        if (supplyPosition.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, supplyPosition.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!supplyPositionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SupplyPosition result = supplyPositionService.save(supplyPosition);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, supplyPosition.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /supply-positions/:id} : Partial updates given fields of an existing supplyPosition, field will ignore if it is null
     *
     * @param id the id of the supplyPosition to save.
     * @param supplyPosition the supplyPosition to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated supplyPosition,
     * or with status {@code 400 (Bad Request)} if the supplyPosition is not valid,
     * or with status {@code 404 (Not Found)} if the supplyPosition is not found,
     * or with status {@code 500 (Internal Server Error)} if the supplyPosition couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/supply-positions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SupplyPosition> partialUpdateSupplyPosition(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SupplyPosition supplyPosition
    ) throws URISyntaxException {
        log.debug("REST request to partial update SupplyPosition partially : {}, {}", id, supplyPosition);
        if (supplyPosition.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, supplyPosition.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!supplyPositionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SupplyPosition> result = supplyPositionService.partialUpdate(supplyPosition);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, supplyPosition.getId().toString())
        );
    }

    /**
     * {@code GET  /supply-positions} : get all the supplyPositions.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of supplyPositions in body.
     */
    @GetMapping("/supply-positions")
    public List<SupplyPosition> getAllSupplyPositions(@RequestParam(required = false) String filter) {
        if ("ownercentralassembly-is-null".equals(filter)) {
            log.debug("REST request to get all SupplyPositions where ownerCentralAssembly is null");
            return supplyPositionService.findAllWhereOwnerCentralAssemblyIsNull();
        }
        log.debug("REST request to get all SupplyPositions");
        return supplyPositionService.findAll();
    }

    /**
     * {@code GET  /supply-positions/:id} : get the "id" supplyPosition.
     *
     * @param id the id of the supplyPosition to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the supplyPosition, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/supply-positions/{id}")
    public ResponseEntity<SupplyPosition> getSupplyPosition(@PathVariable Long id) {
        log.debug("REST request to get SupplyPosition : {}", id);
        Optional<SupplyPosition> supplyPosition = supplyPositionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(supplyPosition);
    }

    /**
     * {@code DELETE  /supply-positions/:id} : delete the "id" supplyPosition.
     *
     * @param id the id of the supplyPosition to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/supply-positions/{id}")
    public ResponseEntity<Void> deleteSupplyPosition(@PathVariable Long id) {
        log.debug("REST request to delete SupplyPosition : {}", id);
        supplyPositionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
