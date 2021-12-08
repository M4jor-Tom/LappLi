package com.muller.lappli.web.rest;

import com.muller.lappli.domain.Copper;
import com.muller.lappli.repository.CopperRepository;
import com.muller.lappli.service.CopperQueryService;
import com.muller.lappli.service.CopperService;
import com.muller.lappli.service.criteria.CopperCriteria;
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
 * REST controller for managing {@link com.muller.lappli.domain.Copper}.
 */
@RestController
@RequestMapping("/api")
public class CopperResource {

    private final Logger log = LoggerFactory.getLogger(CopperResource.class);

    private static final String ENTITY_NAME = "copper";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CopperService copperService;

    private final CopperRepository copperRepository;

    private final CopperQueryService copperQueryService;

    public CopperResource(CopperService copperService, CopperRepository copperRepository, CopperQueryService copperQueryService) {
        this.copperService = copperService;
        this.copperRepository = copperRepository;
        this.copperQueryService = copperQueryService;
    }

    /**
     * {@code POST  /coppers} : Create a new copper.
     *
     * @param copper the copper to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new copper, or with status {@code 400 (Bad Request)} if the copper has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/coppers")
    public ResponseEntity<Copper> createCopper(@Valid @RequestBody Copper copper) throws URISyntaxException {
        log.debug("REST request to save Copper : {}", copper);
        if (copper.getId() != null) {
            throw new BadRequestAlertException("A new copper cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Copper result = copperService.save(copper);
        return ResponseEntity
            .created(new URI("/api/coppers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /coppers/:id} : Updates an existing copper.
     *
     * @param id the id of the copper to save.
     * @param copper the copper to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated copper,
     * or with status {@code 400 (Bad Request)} if the copper is not valid,
     * or with status {@code 500 (Internal Server Error)} if the copper couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/coppers/{id}")
    public ResponseEntity<Copper> updateCopper(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Copper copper
    ) throws URISyntaxException {
        log.debug("REST request to update Copper : {}, {}", id, copper);
        if (copper.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, copper.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!copperRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Copper result = copperService.save(copper);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, copper.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /coppers/:id} : Partial updates given fields of an existing copper, field will ignore if it is null
     *
     * @param id the id of the copper to save.
     * @param copper the copper to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated copper,
     * or with status {@code 400 (Bad Request)} if the copper is not valid,
     * or with status {@code 404 (Not Found)} if the copper is not found,
     * or with status {@code 500 (Internal Server Error)} if the copper couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/coppers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Copper> partialUpdateCopper(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Copper copper
    ) throws URISyntaxException {
        log.debug("REST request to partial update Copper partially : {}, {}", id, copper);
        if (copper.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, copper.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!copperRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Copper> result = copperService.partialUpdate(copper);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, copper.getId().toString())
        );
    }

    /**
     * {@code GET  /coppers} : get all the coppers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of coppers in body.
     */
    @GetMapping("/coppers")
    public ResponseEntity<List<Copper>> getAllCoppers(CopperCriteria criteria) {
        log.debug("REST request to get Coppers by criteria: {}", criteria);
        List<Copper> entityList = copperQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /coppers/count} : count all the coppers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/coppers/count")
    public ResponseEntity<Long> countCoppers(CopperCriteria criteria) {
        log.debug("REST request to count Coppers by criteria: {}", criteria);
        return ResponseEntity.ok().body(copperQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /coppers/:id} : get the "id" copper.
     *
     * @param id the id of the copper to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the copper, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/coppers/{id}")
    public ResponseEntity<Copper> getCopper(@PathVariable Long id) {
        log.debug("REST request to get Copper : {}", id);
        Optional<Copper> copper = copperService.findOne(id);
        return ResponseUtil.wrapOrNotFound(copper);
    }

    /**
     * {@code DELETE  /coppers/:id} : delete the "id" copper.
     *
     * @param id the id of the copper to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/coppers/{id}")
    public ResponseEntity<Void> deleteCopper(@PathVariable Long id) {
        log.debug("REST request to delete Copper : {}", id);
        copperService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
