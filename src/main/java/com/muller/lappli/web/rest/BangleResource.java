package com.muller.lappli.web.rest;

import com.muller.lappli.domain.Bangle;
import com.muller.lappli.repository.BangleRepository;
import com.muller.lappli.service.BangleQueryService;
import com.muller.lappli.service.BangleService;
import com.muller.lappli.service.criteria.BangleCriteria;
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
 * REST controller for managing {@link com.muller.lappli.domain.Bangle}.
 */
@RestController
@RequestMapping("/api")
public class BangleResource {

    private final Logger log = LoggerFactory.getLogger(BangleResource.class);

    private static final String ENTITY_NAME = "bangle";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BangleService bangleService;

    private final BangleRepository bangleRepository;

    private final BangleQueryService bangleQueryService;

    public BangleResource(BangleService bangleService, BangleRepository bangleRepository, BangleQueryService bangleQueryService) {
        this.bangleService = bangleService;
        this.bangleRepository = bangleRepository;
        this.bangleQueryService = bangleQueryService;
    }

    /**
     * {@code POST  /bangles} : Create a new bangle.
     *
     * @param bangle the bangle to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bangle, or with status {@code 400 (Bad Request)} if the bangle has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bangles")
    public ResponseEntity<Bangle> createBangle(@Valid @RequestBody Bangle bangle) throws URISyntaxException {
        log.debug("REST request to save Bangle : {}", bangle);
        if (bangle.getId() != null) {
            throw new BadRequestAlertException("A new bangle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Bangle result = bangleService.save(bangle);
        return ResponseEntity
            .created(new URI("/api/bangles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bangles/:id} : Updates an existing bangle.
     *
     * @param id the id of the bangle to save.
     * @param bangle the bangle to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bangle,
     * or with status {@code 400 (Bad Request)} if the bangle is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bangle couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bangles/{id}")
    public ResponseEntity<Bangle> updateBangle(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Bangle bangle
    ) throws URISyntaxException {
        log.debug("REST request to update Bangle : {}, {}", id, bangle);
        if (bangle.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bangle.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bangleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Bangle result = bangleService.save(bangle);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bangle.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bangles/:id} : Partial updates given fields of an existing bangle, field will ignore if it is null
     *
     * @param id the id of the bangle to save.
     * @param bangle the bangle to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bangle,
     * or with status {@code 400 (Bad Request)} if the bangle is not valid,
     * or with status {@code 404 (Not Found)} if the bangle is not found,
     * or with status {@code 500 (Internal Server Error)} if the bangle couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bangles/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Bangle> partialUpdateBangle(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Bangle bangle
    ) throws URISyntaxException {
        log.debug("REST request to partial update Bangle partially : {}, {}", id, bangle);
        if (bangle.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bangle.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bangleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Bangle> result = bangleService.partialUpdate(bangle);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bangle.getId().toString())
        );
    }

    /**
     * {@code GET  /bangles} : get all the bangles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bangles in body.
     */
    @GetMapping("/bangles")
    public ResponseEntity<List<Bangle>> getAllBangles(BangleCriteria criteria) {
        log.debug("REST request to get Bangles by criteria: {}", criteria);
        List<Bangle> entityList = bangleQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /bangles/count} : count all the bangles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/bangles/count")
    public ResponseEntity<Long> countBangles(BangleCriteria criteria) {
        log.debug("REST request to count Bangles by criteria: {}", criteria);
        return ResponseEntity.ok().body(bangleQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /bangles/:id} : get the "id" bangle.
     *
     * @param id the id of the bangle to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bangle, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bangles/{id}")
    public ResponseEntity<Bangle> getBangle(@PathVariable Long id) {
        log.debug("REST request to get Bangle : {}", id);
        Optional<Bangle> bangle = bangleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bangle);
    }

    /**
     * {@code DELETE  /bangles/:id} : delete the "id" bangle.
     *
     * @param id the id of the bangle to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bangles/{id}")
    public ResponseEntity<Void> deleteBangle(@PathVariable Long id) {
        log.debug("REST request to delete Bangle : {}", id);
        bangleService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
