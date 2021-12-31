package com.muller.lappli.web.rest;

import com.muller.lappli.domain.StrandSupply;
import com.muller.lappli.repository.StrandSupplyRepository;
import com.muller.lappli.service.StrandSupplyService;
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
 * REST controller for managing {@link com.muller.lappli.domain.StrandSupply}.
 */
@RestController
@RequestMapping("/api")
public class StrandSupplyResource {

    private final Logger log = LoggerFactory.getLogger(StrandSupplyResource.class);

    private static final String ENTITY_NAME = "strandSupply";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StrandSupplyService strandSupplyService;

    private final StrandSupplyRepository strandSupplyRepository;

    public StrandSupplyResource(StrandSupplyService strandSupplyService, StrandSupplyRepository strandSupplyRepository) {
        this.strandSupplyService = strandSupplyService;
        this.strandSupplyRepository = strandSupplyRepository;
    }

    /**
     * {@code POST  /strand-supplies} : Create a new strandSupply.
     *
     * @param strandSupply the strandSupply to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new strandSupply, or with status {@code 400 (Bad Request)} if the strandSupply has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/strand-supplies")
    public ResponseEntity<StrandSupply> createStrandSupply(@Valid @RequestBody StrandSupply strandSupply) throws URISyntaxException {
        log.debug("REST request to save StrandSupply : {}", strandSupply);
        if (strandSupply.getId() != null) {
            throw new BadRequestAlertException("A new strandSupply cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StrandSupply result = strandSupplyService.save(strandSupply);
        return ResponseEntity
            .created(new URI("/api/strand-supplies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /strand-supplies/:id} : Updates an existing strandSupply.
     *
     * @param id the id of the strandSupply to save.
     * @param strandSupply the strandSupply to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated strandSupply,
     * or with status {@code 400 (Bad Request)} if the strandSupply is not valid,
     * or with status {@code 500 (Internal Server Error)} if the strandSupply couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/strand-supplies/{id}")
    public ResponseEntity<StrandSupply> updateStrandSupply(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody StrandSupply strandSupply
    ) throws URISyntaxException {
        log.debug("REST request to update StrandSupply : {}, {}", id, strandSupply);
        if (strandSupply.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, strandSupply.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!strandSupplyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        StrandSupply result = strandSupplyService.save(strandSupply);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, strandSupply.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /strand-supplies/:id} : Partial updates given fields of an existing strandSupply, field will ignore if it is null
     *
     * @param id the id of the strandSupply to save.
     * @param strandSupply the strandSupply to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated strandSupply,
     * or with status {@code 400 (Bad Request)} if the strandSupply is not valid,
     * or with status {@code 404 (Not Found)} if the strandSupply is not found,
     * or with status {@code 500 (Internal Server Error)} if the strandSupply couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/strand-supplies/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<StrandSupply> partialUpdateStrandSupply(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody StrandSupply strandSupply
    ) throws URISyntaxException {
        log.debug("REST request to partial update StrandSupply partially : {}, {}", id, strandSupply);
        if (strandSupply.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, strandSupply.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!strandSupplyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StrandSupply> result = strandSupplyService.partialUpdate(strandSupply);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, strandSupply.getId().toString())
        );
    }

    /**
     * {@code GET  /strand-supplies} : get all the strandSupplies.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of strandSupplies in body.
     */
    @GetMapping("/strand-supplies")
    public List<StrandSupply> getAllStrandSupplies() {
        log.debug("REST request to get all StrandSupplies");
        return strandSupplyService.findAll();
    }

    /**
     * {@code GET  /strand-supplies/:id} : get the "id" strandSupply.
     *
     * @param id the id of the strandSupply to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the strandSupply, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/strand-supplies/{id}")
    public ResponseEntity<StrandSupply> getStrandSupply(@PathVariable Long id) {
        log.debug("REST request to get StrandSupply : {}", id);
        Optional<StrandSupply> strandSupply = strandSupplyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(strandSupply);
    }

    /**
     * {@code DELETE  /strand-supplies/:id} : delete the "id" strandSupply.
     *
     * @param id the id of the strandSupply to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/strand-supplies/{id}")
    public ResponseEntity<Void> deleteStrandSupply(@PathVariable Long id) {
        log.debug("REST request to delete StrandSupply : {}", id);
        strandSupplyService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
