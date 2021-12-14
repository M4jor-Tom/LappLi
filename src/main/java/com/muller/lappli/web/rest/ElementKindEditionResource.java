package com.muller.lappli.web.rest;

import com.muller.lappli.domain.ElementKindEdition;
import com.muller.lappli.repository.ElementKindEditionRepository;
import com.muller.lappli.service.ElementKindEditionQueryService;
import com.muller.lappli.service.ElementKindEditionService;
import com.muller.lappli.service.criteria.ElementKindEditionCriteria;
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
 * REST controller for managing {@link com.muller.lappli.domain.ElementKindEdition}.
 */
@RestController
@RequestMapping("/api")
public class ElementKindEditionResource {

    private final Logger log = LoggerFactory.getLogger(ElementKindEditionResource.class);

    private static final String ENTITY_NAME = "elementKindEdition";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ElementKindEditionService elementKindEditionService;

    private final ElementKindEditionRepository elementKindEditionRepository;

    private final ElementKindEditionQueryService elementKindEditionQueryService;

    public ElementKindEditionResource(
        ElementKindEditionService elementKindEditionService,
        ElementKindEditionRepository elementKindEditionRepository,
        ElementKindEditionQueryService elementKindEditionQueryService
    ) {
        this.elementKindEditionService = elementKindEditionService;
        this.elementKindEditionRepository = elementKindEditionRepository;
        this.elementKindEditionQueryService = elementKindEditionQueryService;
    }

    /**
     * {@code POST  /element-kind-editions} : Create a new elementKindEdition.
     *
     * @param elementKindEdition the elementKindEdition to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new elementKindEdition, or with status {@code 400 (Bad Request)} if the elementKindEdition has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/element-kind-editions")
    public ResponseEntity<ElementKindEdition> createElementKindEdition(@Valid @RequestBody ElementKindEdition elementKindEdition)
        throws URISyntaxException {
        log.debug("REST request to save ElementKindEdition : {}", elementKindEdition);
        if (elementKindEdition.getId() != null) {
            throw new BadRequestAlertException("A new elementKindEdition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ElementKindEdition result = elementKindEditionService.save(elementKindEdition);
        return ResponseEntity
            .created(new URI("/api/element-kind-editions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /element-kind-editions/:id} : Updates an existing elementKindEdition.
     *
     * @param id the id of the elementKindEdition to save.
     * @param elementKindEdition the elementKindEdition to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated elementKindEdition,
     * or with status {@code 400 (Bad Request)} if the elementKindEdition is not valid,
     * or with status {@code 500 (Internal Server Error)} if the elementKindEdition couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/element-kind-editions/{id}")
    public ResponseEntity<ElementKindEdition> updateElementKindEdition(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ElementKindEdition elementKindEdition
    ) throws URISyntaxException {
        log.debug("REST request to update ElementKindEdition : {}, {}", id, elementKindEdition);
        if (elementKindEdition.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, elementKindEdition.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!elementKindEditionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ElementKindEdition result = elementKindEditionService.save(elementKindEdition);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, elementKindEdition.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /element-kind-editions/:id} : Partial updates given fields of an existing elementKindEdition, field will ignore if it is null
     *
     * @param id the id of the elementKindEdition to save.
     * @param elementKindEdition the elementKindEdition to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated elementKindEdition,
     * or with status {@code 400 (Bad Request)} if the elementKindEdition is not valid,
     * or with status {@code 404 (Not Found)} if the elementKindEdition is not found,
     * or with status {@code 500 (Internal Server Error)} if the elementKindEdition couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/element-kind-editions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ElementKindEdition> partialUpdateElementKindEdition(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ElementKindEdition elementKindEdition
    ) throws URISyntaxException {
        log.debug("REST request to partial update ElementKindEdition partially : {}, {}", id, elementKindEdition);
        if (elementKindEdition.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, elementKindEdition.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!elementKindEditionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ElementKindEdition> result = elementKindEditionService.partialUpdate(elementKindEdition);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, elementKindEdition.getId().toString())
        );
    }

    /**
     * {@code GET  /element-kind-editions} : get all the elementKindEditions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of elementKindEditions in body.
     */
    @GetMapping("/element-kind-editions")
    public ResponseEntity<List<ElementKindEdition>> getAllElementKindEditions(ElementKindEditionCriteria criteria) {
        log.debug("REST request to get ElementKindEditions by criteria: {}", criteria);
        List<ElementKindEdition> entityList = elementKindEditionQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /element-kind-editions/count} : count all the elementKindEditions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/element-kind-editions/count")
    public ResponseEntity<Long> countElementKindEditions(ElementKindEditionCriteria criteria) {
        log.debug("REST request to count ElementKindEditions by criteria: {}", criteria);
        return ResponseEntity.ok().body(elementKindEditionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /element-kind-editions/:id} : get the "id" elementKindEdition.
     *
     * @param id the id of the elementKindEdition to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the elementKindEdition, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/element-kind-editions/{id}")
    public ResponseEntity<ElementKindEdition> getElementKindEdition(@PathVariable Long id) {
        log.debug("REST request to get ElementKindEdition : {}", id);
        Optional<ElementKindEdition> elementKindEdition = elementKindEditionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(elementKindEdition);
    }

    /**
     * {@code DELETE  /element-kind-editions/:id} : delete the "id" elementKindEdition.
     *
     * @param id the id of the elementKindEdition to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/element-kind-editions/{id}")
    public ResponseEntity<Void> deleteElementKindEdition(@PathVariable Long id) {
        log.debug("REST request to delete ElementKindEdition : {}", id);
        elementKindEditionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
