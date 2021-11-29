package com.muller.lappli.web.rest;

import com.muller.lappli.domain.ElementSupply;
import com.muller.lappli.domain.Lifter;
import com.muller.lappli.repository.ElementSupplyRepository;
import com.muller.lappli.service.ElementSupplyQueryService;
import com.muller.lappli.service.ElementSupplyService;
import com.muller.lappli.service.LifterService;
import com.muller.lappli.service.criteria.ElementSupplyCriteria;
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
 * REST controller for managing {@link com.muller.lappli.domain.ElementSupply}.
 */
@RestController
@RequestMapping("/api")
public class ElementSupplyResource {

    private final Logger log = LoggerFactory.getLogger(ElementSupplyResource.class);

    private static final String ENTITY_NAME = "elementSupply";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ElementSupplyService elementSupplyService;

    private final ElementSupplyRepository elementSupplyRepository;

    private final ElementSupplyQueryService elementSupplyQueryService;

    private final LifterService lifterService;

    public ElementSupplyResource(
        ElementSupplyService elementSupplyService,
        ElementSupplyRepository elementSupplyRepository,
        ElementSupplyQueryService elementSupplyQueryService,
        LifterService lifterService
    ) {
        this.elementSupplyService = elementSupplyService;
        this.elementSupplyRepository = elementSupplyRepository;
        this.elementSupplyQueryService = elementSupplyQueryService;
        this.lifterService = lifterService;
    }

    /**
     * {@code POST  /element-supplies} : Create a new elementSupply.
     *
     * @param elementSupply the elementSupply to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new elementSupply, or with status {@code 400 (Bad Request)} if the elementSupply has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/element-supplies")
    public ResponseEntity<ElementSupply> createElementSupply(@Valid @RequestBody ElementSupply elementSupply) throws URISyntaxException {
        log.debug("REST request to save ElementSupply : {}", elementSupply);
        if (elementSupply.getId() != null) {
            throw new BadRequestAlertException("A new elementSupply cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ElementSupply result = elementSupplyService.save(elementSupply);
        return ResponseEntity
            .created(new URI("/api/element-supplies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /element-supplies/:id} : Updates an existing elementSupply.
     *
     * @param id the id of the elementSupply to save.
     * @param elementSupply the elementSupply to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated elementSupply,
     * or with status {@code 400 (Bad Request)} if the elementSupply is not valid,
     * or with status {@code 500 (Internal Server Error)} if the elementSupply couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/element-supplies/{id}")
    public ResponseEntity<ElementSupply> updateElementSupply(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ElementSupply elementSupply
    ) throws URISyntaxException {
        log.debug("REST request to update ElementSupply : {}, {}", id, elementSupply);
        if (elementSupply.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, elementSupply.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!elementSupplyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ElementSupply result = elementSupplyService.save(elementSupply);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, elementSupply.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /element-supplies/:id} : Partial updates given fields of an existing elementSupply, field will ignore if it is null
     *
     * @param id the id of the elementSupply to save.
     * @param elementSupply the elementSupply to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated elementSupply,
     * or with status {@code 400 (Bad Request)} if the elementSupply is not valid,
     * or with status {@code 404 (Not Found)} if the elementSupply is not found,
     * or with status {@code 500 (Internal Server Error)} if the elementSupply couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/element-supplies/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ElementSupply> partialUpdateElementSupply(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ElementSupply elementSupply
    ) throws URISyntaxException {
        log.debug("REST request to partial update ElementSupply partially : {}, {}", id, elementSupply);
        if (elementSupply.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, elementSupply.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!elementSupplyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ElementSupply> result = elementSupplyService.partialUpdate(elementSupply);

        if (result.isPresent()) {
            result.get().setBestLifterList(lifterService.findBestLifterList(new ElementSupply()));
        }

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, elementSupply.getId().toString())
        );
    }

    /**
     * {@code GET  /element-supplies} : get all the elementSupplies.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of elementSupplies in body.
     */
    @GetMapping("/element-supplies")
    public ResponseEntity<List<ElementSupply>> getAllElementSupplies(ElementSupplyCriteria criteria) {
        log.debug("REST request to get ElementSupplies by criteria: {}", criteria);
        List<ElementSupply> entityList = elementSupplyQueryService.findByCriteria(criteria);

        for (ElementSupply elementSupply : entityList) {
            elementSupply.setBestLifterList(lifterService.findBestLifterList(elementSupply));
        }

        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /element-supplies/count} : count all the elementSupplies.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/element-supplies/count")
    public ResponseEntity<Long> countElementSupplies(ElementSupplyCriteria criteria) {
        log.debug("REST request to count ElementSupplies by criteria: {}", criteria);
        return ResponseEntity.ok().body(elementSupplyQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /element-supplies/:id} : get the "id" elementSupply.
     *
     * @param id the id of the elementSupply to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the elementSupply, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/element-supplies/{id}")
    public ResponseEntity<ElementSupply> getElementSupply(@PathVariable Long id) {
        log.debug("REST request to get ElementSupply : {}", id);
        Optional<ElementSupply> elementSupply = elementSupplyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(elementSupply);
    }

    /**
     * {@code DELETE  /element-supplies/:id} : delete the "id" elementSupply.
     *
     * @param id the id of the elementSupply to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/element-supplies/{id}")
    public ResponseEntity<Void> deleteElementSupply(@PathVariable Long id) {
        log.debug("REST request to delete ElementSupply : {}", id);
        elementSupplyService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
