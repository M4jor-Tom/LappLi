package com.muller.lappli.web.rest;

import com.muller.lappli.domain.CustomComponentSupply;
import com.muller.lappli.repository.CustomComponentSupplyRepository;
import com.muller.lappli.service.CustomComponentSupplyQueryService;
import com.muller.lappli.service.CustomComponentSupplyService;
import com.muller.lappli.service.criteria.CustomComponentSupplyCriteria;
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
 * REST controller for managing {@link com.muller.lappli.domain.CustomComponentSupply}.
 */
@RestController
@RequestMapping("/api")
public class CustomComponentSupplyResource {

    private final Logger log = LoggerFactory.getLogger(CustomComponentSupplyResource.class);

    private static final String ENTITY_NAME = "customComponentSupply";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomComponentSupplyService customComponentSupplyService;

    private final CustomComponentSupplyRepository customComponentSupplyRepository;

    private final CustomComponentSupplyQueryService customComponentSupplyQueryService;

    public CustomComponentSupplyResource(
        CustomComponentSupplyService customComponentSupplyService,
        CustomComponentSupplyRepository customComponentSupplyRepository,
        CustomComponentSupplyQueryService customComponentSupplyQueryService
    ) {
        this.customComponentSupplyService = customComponentSupplyService;
        this.customComponentSupplyRepository = customComponentSupplyRepository;
        this.customComponentSupplyQueryService = customComponentSupplyQueryService;
    }

    /**
     * {@code POST  /custom-component-supplies} : Create a new customComponentSupply.
     *
     * @param customComponentSupply the customComponentSupply to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customComponentSupply, or with status {@code 400 (Bad Request)} if the customComponentSupply has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/custom-component-supplies")
    public ResponseEntity<CustomComponentSupply> createCustomComponentSupply(
        @Valid @RequestBody CustomComponentSupply customComponentSupply
    ) throws URISyntaxException {
        log.debug("REST request to save CustomComponentSupply : {}", customComponentSupply);
        if (customComponentSupply.getId() != null) {
            throw new BadRequestAlertException("A new customComponentSupply cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomComponentSupply result = customComponentSupplyService.save(customComponentSupply);
        return ResponseEntity
            .created(new URI("/api/custom-component-supplies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /custom-component-supplies/:id} : Updates an existing customComponentSupply.
     *
     * @param id the id of the customComponentSupply to save.
     * @param customComponentSupply the customComponentSupply to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customComponentSupply,
     * or with status {@code 400 (Bad Request)} if the customComponentSupply is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customComponentSupply couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/custom-component-supplies/{id}")
    public ResponseEntity<CustomComponentSupply> updateCustomComponentSupply(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CustomComponentSupply customComponentSupply
    ) throws URISyntaxException {
        log.debug("REST request to update CustomComponentSupply : {}, {}", id, customComponentSupply);
        if (customComponentSupply.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, customComponentSupply.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!customComponentSupplyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CustomComponentSupply result = customComponentSupplyService.save(customComponentSupply);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customComponentSupply.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /custom-component-supplies/:id} : Partial updates given fields of an existing customComponentSupply, field will ignore if it is null
     *
     * @param id the id of the customComponentSupply to save.
     * @param customComponentSupply the customComponentSupply to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customComponentSupply,
     * or with status {@code 400 (Bad Request)} if the customComponentSupply is not valid,
     * or with status {@code 404 (Not Found)} if the customComponentSupply is not found,
     * or with status {@code 500 (Internal Server Error)} if the customComponentSupply couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/custom-component-supplies/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CustomComponentSupply> partialUpdateCustomComponentSupply(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CustomComponentSupply customComponentSupply
    ) throws URISyntaxException {
        log.debug("REST request to partial update CustomComponentSupply partially : {}, {}", id, customComponentSupply);
        if (customComponentSupply.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, customComponentSupply.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!customComponentSupplyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CustomComponentSupply> result = customComponentSupplyService.partialUpdate(customComponentSupply);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customComponentSupply.getId().toString())
        );
    }

    /**
     * {@code GET  /custom-component-supplies} : get all the customComponentSupplies.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customComponentSupplies in body.
     */
    @GetMapping("/custom-component-supplies")
    public ResponseEntity<List<CustomComponentSupply>> getAllCustomComponentSupplies(CustomComponentSupplyCriteria criteria) {
        log.debug("REST request to get CustomComponentSupplies by criteria: {}", criteria);
        List<CustomComponentSupply> entityList = customComponentSupplyQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /custom-component-supplies/count} : count all the customComponentSupplies.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/custom-component-supplies/count")
    public ResponseEntity<Long> countCustomComponentSupplies(CustomComponentSupplyCriteria criteria) {
        log.debug("REST request to count CustomComponentSupplies by criteria: {}", criteria);
        return ResponseEntity.ok().body(customComponentSupplyQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /custom-component-supplies/:id} : get the "id" customComponentSupply.
     *
     * @param id the id of the customComponentSupply to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customComponentSupply, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/custom-component-supplies/{id}")
    public ResponseEntity<CustomComponentSupply> getCustomComponentSupply(@PathVariable Long id) {
        log.debug("REST request to get CustomComponentSupply : {}", id);
        Optional<CustomComponentSupply> customComponentSupply = customComponentSupplyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customComponentSupply);
    }

    /**
     * {@code DELETE  /custom-component-supplies/:id} : delete the "id" customComponentSupply.
     *
     * @param id the id of the customComponentSupply to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/custom-component-supplies/{id}")
    public ResponseEntity<Void> deleteCustomComponentSupply(@PathVariable Long id) {
        log.debug("REST request to delete CustomComponentSupply : {}", id);
        customComponentSupplyService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
