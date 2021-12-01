package com.muller.lappli.web.rest;

import com.muller.lappli.domain.ElementKind;
import com.muller.lappli.repository.ElementKindRepository;
import com.muller.lappli.service.ElementKindEditionService;
import com.muller.lappli.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.muller.lappli.domain.ElementKind}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ElementKindResource {

    private final Logger log = LoggerFactory.getLogger(ElementKindResource.class);

    private static final String ENTITY_NAME = "elementKind";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ElementKindRepository elementKindRepository;

    private final ElementKindEditionService elementKindEditionService;

    public ElementKindResource(ElementKindRepository elementKindRepository, ElementKindEditionService elementKindEditionService) {
        this.elementKindRepository = elementKindRepository;
        this.elementKindEditionService = elementKindEditionService;
    }

    /**
     * {@code POST  /element-kinds} : Create a new elementKind.
     *
     * @param elementKind the elementKind to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new elementKind, or with status {@code 400 (Bad Request)} if the elementKind has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/element-kinds")
    public ResponseEntity<ElementKind> createElementKind(@Valid @RequestBody ElementKind elementKind) throws URISyntaxException {
        log.debug("REST request to save ElementKind : {}", elementKind);
        if (elementKind.getId() != null) {
            throw new BadRequestAlertException("A new elementKind cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ElementKind result = elementKindRepository.save(elementKind);
        return ResponseEntity
            .created(new URI("/api/element-kinds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /element-kinds/:id} : Updates an existing elementKind.
     *
     * @param id the id of the elementKind to save.
     * @param elementKind the elementKind to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated elementKind,
     * or with status {@code 400 (Bad Request)} if the elementKind is not valid,
     * or with status {@code 500 (Internal Server Error)} if the elementKind couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/element-kinds/{id}")
    public ResponseEntity<ElementKind> updateElementKind(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ElementKind elementKind
    ) throws URISyntaxException {
        /*log.debug("REST request to update ElementKind : {}, {}", id, elementKind);
        if (elementKind.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, elementKind.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!elementKindRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ElementKind result = elementKindRepository.save(elementKind);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, elementKind.getId().toString()))
            .body(result);
        */
        return null;
    }

    /**
     * {@code PATCH  /element-kinds/:id} : Partial updates given fields of an existing elementKind, field will ignore if it is null
     *
     * @param id the id of the elementKind to save.
     * @param elementKind the elementKind to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated elementKind,
     * or with status {@code 400 (Bad Request)} if the elementKind is not valid,
     * or with status {@code 404 (Not Found)} if the elementKind is not found,
     * or with status {@code 500 (Internal Server Error)} if the elementKind couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/element-kinds/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ElementKind> partialUpdateElementKind(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ElementKind elementKind
    ) throws URISyntaxException {
        /*log.debug("REST request to partial update ElementKind partially : {}, {}", id, elementKind);
        if (elementKind.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, elementKind.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!elementKindRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ElementKind> result = elementKindRepository
            .findById(elementKind.getId())
            .map(existingElementKind -> {
                if (elementKind.getDesignation() != null) {
                    existingElementKind.setDesignation(elementKind.getDesignation());
                }
                if (elementKind.getGramPerMeterLinearMass() != null) {
                    existingElementKind.setGramPerMeterLinearMass(elementKind.getGramPerMeterLinearMass());
                }
                if (elementKind.getMilimeterDiameter() != null) {
                    existingElementKind.setMilimeterDiameter(elementKind.getMilimeterDiameter());
                }
                if (elementKind.getInsulationThickness() != null) {
                    existingElementKind.setInsulationThickness(elementKind.getInsulationThickness());
                }

                return existingElementKind;
            })
            .map(elementKindRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, elementKind.getId().toString())
        );*/
        return null;
    }

    /**
     * {@code GET  /element-kinds} : get all the elementKinds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of elementKinds in body.
     */
    @GetMapping("/element-kinds")
    public List<ElementKind> getAllElementKinds() {
        log.debug("REST request to get all ElementKinds");
        return elementKindEditionService.update(elementKindRepository.findAll());
    }

    /**
     * {@code GET  /element-kinds/:id} : get the "id" elementKind.
     *
     * @param id the id of the elementKind to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the elementKind, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/element-kinds/{id}")
    public ResponseEntity<ElementKind> getElementKind(@PathVariable Long id) {
        log.debug("REST request to get ElementKind : {}", id);
        Optional<ElementKind> elementKind = elementKindRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(elementKind);
    }

    /**
     * {@code DELETE  /element-kinds/:id} : delete the "id" elementKind.
     *
     * @param id the id of the elementKind to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/element-kinds/{id}")
    public ResponseEntity<Void> deleteElementKind(@PathVariable Long id) {
        log.debug("REST request to delete ElementKind : {}", id);
        elementKindRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
