package com.muller.lappli.web.rest;

import com.muller.lappli.domain.OneStudySupply;
import com.muller.lappli.repository.OneStudySupplyRepository;
import com.muller.lappli.service.OneStudySupplyQueryService;
import com.muller.lappli.service.OneStudySupplyService;
import com.muller.lappli.service.criteria.OneStudySupplyCriteria;
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
 * REST controller for managing {@link com.muller.lappli.domain.OneStudySupply}.
 */
@RestController
@RequestMapping("/api")
public class OneStudySupplyResource {

    private final Logger log = LoggerFactory.getLogger(OneStudySupplyResource.class);

    private static final String ENTITY_NAME = "oneStudySupply";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OneStudySupplyService oneStudySupplyService;

    private final OneStudySupplyRepository oneStudySupplyRepository;

    private final OneStudySupplyQueryService oneStudySupplyQueryService;

    public OneStudySupplyResource(
        OneStudySupplyService oneStudySupplyService,
        OneStudySupplyRepository oneStudySupplyRepository,
        OneStudySupplyQueryService oneStudySupplyQueryService
    ) {
        this.oneStudySupplyService = oneStudySupplyService;
        this.oneStudySupplyRepository = oneStudySupplyRepository;
        this.oneStudySupplyQueryService = oneStudySupplyQueryService;
    }

    /**
     * {@code POST  /one-study-supplies} : Create a new oneStudySupply.
     *
     * @param oneStudySupply the oneStudySupply to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new oneStudySupply, or with status {@code 400 (Bad Request)} if the oneStudySupply has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/one-study-supplies")
    public ResponseEntity<OneStudySupply> createOneStudySupply(@Valid @RequestBody OneStudySupply oneStudySupply)
        throws URISyntaxException {
        log.debug("REST request to save OneStudySupply : {}", oneStudySupply);
        if (oneStudySupply.getId() != null) {
            throw new BadRequestAlertException("A new oneStudySupply cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OneStudySupply result = oneStudySupplyService.save(oneStudySupply);
        return ResponseEntity
            .created(new URI("/api/one-study-supplies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /one-study-supplies/:id} : Updates an existing oneStudySupply.
     *
     * @param id the id of the oneStudySupply to save.
     * @param oneStudySupply the oneStudySupply to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated oneStudySupply,
     * or with status {@code 400 (Bad Request)} if the oneStudySupply is not valid,
     * or with status {@code 500 (Internal Server Error)} if the oneStudySupply couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/one-study-supplies/{id}")
    public ResponseEntity<OneStudySupply> updateOneStudySupply(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody OneStudySupply oneStudySupply
    ) throws URISyntaxException {
        log.debug("REST request to update OneStudySupply : {}, {}", id, oneStudySupply);
        if (oneStudySupply.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, oneStudySupply.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!oneStudySupplyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OneStudySupply result = oneStudySupplyService.save(oneStudySupply);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, oneStudySupply.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /one-study-supplies/:id} : Partial updates given fields of an existing oneStudySupply, field will ignore if it is null
     *
     * @param id the id of the oneStudySupply to save.
     * @param oneStudySupply the oneStudySupply to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated oneStudySupply,
     * or with status {@code 400 (Bad Request)} if the oneStudySupply is not valid,
     * or with status {@code 404 (Not Found)} if the oneStudySupply is not found,
     * or with status {@code 500 (Internal Server Error)} if the oneStudySupply couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/one-study-supplies/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OneStudySupply> partialUpdateOneStudySupply(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody OneStudySupply oneStudySupply
    ) throws URISyntaxException {
        log.debug("REST request to partial update OneStudySupply partially : {}, {}", id, oneStudySupply);
        if (oneStudySupply.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, oneStudySupply.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!oneStudySupplyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OneStudySupply> result = oneStudySupplyService.partialUpdate(oneStudySupply);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, oneStudySupply.getId().toString())
        );
    }

    /**
     * {@code GET  /one-study-supplies} : get all the oneStudySupplies.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of oneStudySupplies in body.
     */
    @GetMapping("/one-study-supplies")
    public ResponseEntity<List<OneStudySupply>> getAllOneStudySupplies(OneStudySupplyCriteria criteria) {
        log.debug("REST request to get OneStudySupplies by criteria: {}", criteria);
        List<OneStudySupply> entityList = oneStudySupplyQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /one-study-supplies/count} : count all the oneStudySupplies.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/one-study-supplies/count")
    public ResponseEntity<Long> countOneStudySupplies(OneStudySupplyCriteria criteria) {
        log.debug("REST request to count OneStudySupplies by criteria: {}", criteria);
        return ResponseEntity.ok().body(oneStudySupplyQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /one-study-supplies/:id} : get the "id" oneStudySupply.
     *
     * @param id the id of the oneStudySupply to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the oneStudySupply, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/one-study-supplies/{id}")
    public ResponseEntity<OneStudySupply> getOneStudySupply(@PathVariable Long id) {
        log.debug("REST request to get OneStudySupply : {}", id);
        Optional<OneStudySupply> oneStudySupply = oneStudySupplyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(oneStudySupply);
    }

    /**
     * {@code DELETE  /one-study-supplies/:id} : delete the "id" oneStudySupply.
     *
     * @param id the id of the oneStudySupply to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/one-study-supplies/{id}")
    public ResponseEntity<Void> deleteOneStudySupply(@PathVariable Long id) {
        log.debug("REST request to delete OneStudySupply : {}", id);
        oneStudySupplyService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
