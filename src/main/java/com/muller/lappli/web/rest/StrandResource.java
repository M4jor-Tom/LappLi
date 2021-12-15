package com.muller.lappli.web.rest;

import com.muller.lappli.domain.Strand;
import com.muller.lappli.repository.StrandRepository;
import com.muller.lappli.service.StrandQueryService;
import com.muller.lappli.service.StrandService;
import com.muller.lappli.service.criteria.StrandCriteria;
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
 * REST controller for managing {@link com.muller.lappli.domain.Strand}.
 */
@RestController
@RequestMapping("/api")
public class StrandResource {

    private final Logger log = LoggerFactory.getLogger(StrandResource.class);

    private static final String ENTITY_NAME = "strand";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StrandService strandService;

    private final StrandRepository strandRepository;

    private final StrandQueryService strandQueryService;

    public StrandResource(StrandService strandService, StrandRepository strandRepository, StrandQueryService strandQueryService) {
        this.strandService = strandService;
        this.strandRepository = strandRepository;
        this.strandQueryService = strandQueryService;
    }

    /**
     * {@code POST  /strands} : Create a new strand.
     *
     * @param strand the strand to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new strand, or with status {@code 400 (Bad Request)} if the strand has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/strands")
    public ResponseEntity<Strand> createStrand(@Valid @RequestBody Strand strand) throws URISyntaxException {
        log.debug("REST request to save Strand : {}", strand);
        if (strand.getId() != null) {
            throw new BadRequestAlertException("A new strand cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Strand result = strandService.save(strand);
        return ResponseEntity
            .created(new URI("/api/strands/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /strands/:id} : Updates an existing strand.
     *
     * @param id the id of the strand to save.
     * @param strand the strand to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated strand,
     * or with status {@code 400 (Bad Request)} if the strand is not valid,
     * or with status {@code 500 (Internal Server Error)} if the strand couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/strands/{id}")
    public ResponseEntity<Strand> updateStrand(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Strand strand
    ) throws URISyntaxException {
        log.debug("REST request to update Strand : {}, {}", id, strand);
        if (strand.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, strand.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!strandRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Strand result = strandService.save(strand);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, strand.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /strands/:id} : Partial updates given fields of an existing strand, field will ignore if it is null
     *
     * @param id the id of the strand to save.
     * @param strand the strand to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated strand,
     * or with status {@code 400 (Bad Request)} if the strand is not valid,
     * or with status {@code 404 (Not Found)} if the strand is not found,
     * or with status {@code 500 (Internal Server Error)} if the strand couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/strands/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Strand> partialUpdateStrand(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Strand strand
    ) throws URISyntaxException {
        log.debug("REST request to partial update Strand partially : {}, {}", id, strand);
        if (strand.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, strand.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!strandRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Strand> result = strandService.partialUpdate(strand);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, strand.getId().toString())
        );
    }

    /**
     * {@code GET  /strands} : get all the strands.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of strands in body.
     */
    @GetMapping("/strands")
    public ResponseEntity<List<Strand>> getAllStrands(StrandCriteria criteria) {
        log.debug("REST request to get Strands by criteria: {}", criteria);
        List<Strand> entityList = strandQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /strands/count} : count all the strands.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/strands/count")
    public ResponseEntity<Long> countStrands(StrandCriteria criteria) {
        log.debug("REST request to count Strands by criteria: {}", criteria);
        return ResponseEntity.ok().body(strandQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /strands/:id} : get the "id" strand.
     *
     * @param id the id of the strand to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the strand, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/strands/{id}")
    public ResponseEntity<Strand> getStrand(@PathVariable Long id) {
        log.debug("REST request to get Strand : {}", id);
        Optional<Strand> strand = strandService.findOne(id);
        return ResponseUtil.wrapOrNotFound(strand);
    }

    /**
     * {@code DELETE  /strands/:id} : delete the "id" strand.
     *
     * @param id the id of the strand to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/strands/{id}")
    public ResponseEntity<Void> deleteStrand(@PathVariable Long id) {
        log.debug("REST request to delete Strand : {}", id);
        strandService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
