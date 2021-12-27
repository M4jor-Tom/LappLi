package com.muller.lappli.web.rest;

import com.muller.lappli.domain.BangleSupply;
import com.muller.lappli.repository.BangleSupplyRepository;
import com.muller.lappli.service.BangleSupplyService;
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
 * REST controller for managing {@link com.muller.lappli.domain.BangleSupply}.
 */
@RestController
@RequestMapping("/api")
public class BangleSupplyResource {

    private final Logger log = LoggerFactory.getLogger(BangleSupplyResource.class);

    private static final String ENTITY_NAME = "bangleSupply";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BangleSupplyService bangleSupplyService;

    private final BangleSupplyRepository bangleSupplyRepository;

    public BangleSupplyResource(BangleSupplyService bangleSupplyService, BangleSupplyRepository bangleSupplyRepository) {
        this.bangleSupplyService = bangleSupplyService;
        this.bangleSupplyRepository = bangleSupplyRepository;
    }

    /**
     * {@code POST  /bangle-supplies} : Create a new bangleSupply.
     *
     * @param bangleSupply the bangleSupply to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bangleSupply, or with status {@code 400 (Bad Request)} if the bangleSupply has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bangle-supplies")
    public ResponseEntity<BangleSupply> createBangleSupply(@Valid @RequestBody BangleSupply bangleSupply) throws URISyntaxException {
        log.debug("REST request to save BangleSupply : {}", bangleSupply);
        if (bangleSupply.getId() != null) {
            throw new BadRequestAlertException("A new bangleSupply cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BangleSupply result = bangleSupplyService.save(bangleSupply);
        return ResponseEntity
            .created(new URI("/api/bangle-supplies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bangle-supplies/:id} : Updates an existing bangleSupply.
     *
     * @param id the id of the bangleSupply to save.
     * @param bangleSupply the bangleSupply to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bangleSupply,
     * or with status {@code 400 (Bad Request)} if the bangleSupply is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bangleSupply couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bangle-supplies/{id}")
    public ResponseEntity<BangleSupply> updateBangleSupply(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BangleSupply bangleSupply
    ) throws URISyntaxException {
        log.debug("REST request to update BangleSupply : {}, {}", id, bangleSupply);
        if (bangleSupply.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bangleSupply.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bangleSupplyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BangleSupply result = bangleSupplyService.save(bangleSupply);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bangleSupply.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bangle-supplies/:id} : Partial updates given fields of an existing bangleSupply, field will ignore if it is null
     *
     * @param id the id of the bangleSupply to save.
     * @param bangleSupply the bangleSupply to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bangleSupply,
     * or with status {@code 400 (Bad Request)} if the bangleSupply is not valid,
     * or with status {@code 404 (Not Found)} if the bangleSupply is not found,
     * or with status {@code 500 (Internal Server Error)} if the bangleSupply couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bangle-supplies/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BangleSupply> partialUpdateBangleSupply(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BangleSupply bangleSupply
    ) throws URISyntaxException {
        log.debug("REST request to partial update BangleSupply partially : {}, {}", id, bangleSupply);
        if (bangleSupply.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bangleSupply.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bangleSupplyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BangleSupply> result = bangleSupplyService.partialUpdate(bangleSupply);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bangleSupply.getId().toString())
        );
    }

    /**
     * {@code GET  /bangle-supplies} : get all the bangleSupplies.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bangleSupplies in body.
     */
    @GetMapping("/bangle-supplies")
    public List<BangleSupply> getAllBangleSupplies(@RequestParam(required = false) String filter) {
        if ("position-is-null".equals(filter)) {
            log.debug("REST request to get all BangleSupplys where position is null");
            return bangleSupplyService.findAllWherePositionIsNull();
        }
        log.debug("REST request to get all BangleSupplies");
        return bangleSupplyService.findAll();
    }

    /**
     * {@code GET  /bangle-supplies/:id} : get the "id" bangleSupply.
     *
     * @param id the id of the bangleSupply to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bangleSupply, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bangle-supplies/{id}")
    public ResponseEntity<BangleSupply> getBangleSupply(@PathVariable Long id) {
        log.debug("REST request to get BangleSupply : {}", id);
        Optional<BangleSupply> bangleSupply = bangleSupplyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bangleSupply);
    }

    /**
     * {@code DELETE  /bangle-supplies/:id} : delete the "id" bangleSupply.
     *
     * @param id the id of the bangleSupply to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bangle-supplies/{id}")
    public ResponseEntity<Void> deleteBangleSupply(@PathVariable Long id) {
        log.debug("REST request to delete BangleSupply : {}", id);
        bangleSupplyService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
