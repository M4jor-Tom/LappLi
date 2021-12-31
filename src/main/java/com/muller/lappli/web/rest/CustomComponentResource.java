package com.muller.lappli.web.rest;

import com.muller.lappli.domain.CustomComponent;
import com.muller.lappli.repository.CustomComponentRepository;
import com.muller.lappli.service.CustomComponentService;
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
 * REST controller for managing {@link com.muller.lappli.domain.CustomComponent}.
 */
@RestController
@RequestMapping("/api")
public class CustomComponentResource {

    private final Logger log = LoggerFactory.getLogger(CustomComponentResource.class);

    private static final String ENTITY_NAME = "customComponent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomComponentService customComponentService;

    private final CustomComponentRepository customComponentRepository;

    public CustomComponentResource(CustomComponentService customComponentService, CustomComponentRepository customComponentRepository) {
        this.customComponentService = customComponentService;
        this.customComponentRepository = customComponentRepository;
    }

    /**
     * {@code POST  /custom-components} : Create a new customComponent.
     *
     * @param customComponent the customComponent to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customComponent, or with status {@code 400 (Bad Request)} if the customComponent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/custom-components")
    public ResponseEntity<CustomComponent> createCustomComponent(@Valid @RequestBody CustomComponent customComponent)
        throws URISyntaxException {
        log.debug("REST request to save CustomComponent : {}", customComponent);
        if (customComponent.getId() != null) {
            throw new BadRequestAlertException("A new customComponent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomComponent result = customComponentService.save(customComponent);
        return ResponseEntity
            .created(new URI("/api/custom-components/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /custom-components/:id} : Updates an existing customComponent.
     *
     * @param id the id of the customComponent to save.
     * @param customComponent the customComponent to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customComponent,
     * or with status {@code 400 (Bad Request)} if the customComponent is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customComponent couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/custom-components/{id}")
    public ResponseEntity<CustomComponent> updateCustomComponent(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CustomComponent customComponent
    ) throws URISyntaxException {
        log.debug("REST request to update CustomComponent : {}, {}", id, customComponent);
        if (customComponent.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, customComponent.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!customComponentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CustomComponent result = customComponentService.save(customComponent);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customComponent.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /custom-components/:id} : Partial updates given fields of an existing customComponent, field will ignore if it is null
     *
     * @param id the id of the customComponent to save.
     * @param customComponent the customComponent to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customComponent,
     * or with status {@code 400 (Bad Request)} if the customComponent is not valid,
     * or with status {@code 404 (Not Found)} if the customComponent is not found,
     * or with status {@code 500 (Internal Server Error)} if the customComponent couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/custom-components/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CustomComponent> partialUpdateCustomComponent(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CustomComponent customComponent
    ) throws URISyntaxException {
        log.debug("REST request to partial update CustomComponent partially : {}, {}", id, customComponent);
        if (customComponent.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, customComponent.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!customComponentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CustomComponent> result = customComponentService.partialUpdate(customComponent);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customComponent.getId().toString())
        );
    }

    /**
     * {@code GET  /custom-components} : get all the customComponents.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customComponents in body.
     */
    @GetMapping("/custom-components")
    public List<CustomComponent> getAllCustomComponents() {
        log.debug("REST request to get all CustomComponents");
        return customComponentService.findAll();
    }

    /**
     * {@code GET  /custom-components/:id} : get the "id" customComponent.
     *
     * @param id the id of the customComponent to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customComponent, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/custom-components/{id}")
    public ResponseEntity<CustomComponent> getCustomComponent(@PathVariable Long id) {
        log.debug("REST request to get CustomComponent : {}", id);
        Optional<CustomComponent> customComponent = customComponentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customComponent);
    }

    /**
     * {@code DELETE  /custom-components/:id} : delete the "id" customComponent.
     *
     * @param id the id of the customComponent to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/custom-components/{id}")
    public ResponseEntity<Void> deleteCustomComponent(@PathVariable Long id) {
        log.debug("REST request to delete CustomComponent : {}", id);
        customComponentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
