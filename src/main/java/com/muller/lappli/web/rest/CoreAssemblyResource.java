package com.muller.lappli.web.rest;

import com.muller.lappli.domain.CoreAssembly;
import com.muller.lappli.domain.exception.PositionHasSeveralSupplyException;
import com.muller.lappli.domain.exception.PositionInSeveralAssemblyException;
import com.muller.lappli.repository.CoreAssemblyRepository;
import com.muller.lappli.service.CoreAssemblyService;
import com.muller.lappli.service.IAssemblyService;
import com.muller.lappli.web.rest.abstracts.AbstractAssemblyResource;
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
 * REST controller for managing {@link com.muller.lappli.domain.CoreAssembly}.
 */
@RestController
@RequestMapping("/api")
public class CoreAssemblyResource extends AbstractAssemblyResource<CoreAssembly> {

    private final Logger log = LoggerFactory.getLogger(CoreAssemblyResource.class);

    private static final String ENTITY_NAME = "coreAssembly";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CoreAssemblyService coreAssemblyService;

    private final CoreAssemblyRepository coreAssemblyRepository;

    public CoreAssemblyResource(CoreAssemblyService coreAssemblyService, CoreAssemblyRepository coreAssemblyRepository) {
        this.coreAssemblyService = coreAssemblyService;
        this.coreAssemblyRepository = coreAssemblyRepository;
    }

    @Override
    protected IAssemblyService<CoreAssembly> getPositionCheckingService() {
        return coreAssemblyService;
    }

    @Override
    protected String getSpineCasePluralEntityName() {
        return "core-assemblies";
    }

    /**
     * {@code POST  /core-assemblies} : Create a new coreAssembly.
     *
     * @param coreAssembly the coreAssembly to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new coreAssembly, or with status {@code 400 (Bad Request)} if the coreAssembly has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/core-assemblies")
    public ResponseEntity<CoreAssembly> createCoreAssembly(@Valid @RequestBody CoreAssembly coreAssembly) throws URISyntaxException {
        log.debug("REST request to save CoreAssembly : {}", coreAssembly);
        if (coreAssembly.getId() != null) {
            throw new BadRequestAlertException("A new coreAssembly cannot already have an ID", ENTITY_NAME, "idexists");
        }

        return onSave(coreAssembly, applicationName, ENTITY_NAME, true);
    }

    /**
     * {@code PUT  /core-assemblies/:id} : Updates an existing coreAssembly.
     *
     * @param id the id of the coreAssembly to save.
     * @param coreAssembly the coreAssembly to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated coreAssembly,
     * or with status {@code 400 (Bad Request)} if the coreAssembly is not valid,
     * or with status {@code 500 (Internal Server Error)} if the coreAssembly couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/core-assemblies/{id}")
    public ResponseEntity<CoreAssembly> updateCoreAssembly(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CoreAssembly coreAssembly
    ) throws URISyntaxException {
        log.debug("REST request to update CoreAssembly : {}, {}", id, coreAssembly);
        if (coreAssembly.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, coreAssembly.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!coreAssemblyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        return onSave(coreAssembly, applicationName, ENTITY_NAME, false);
    }

    /**
     * {@code PATCH  /core-assemblies/:id} : Partial updates given fields of an existing coreAssembly, field will ignore if it is null
     *
     * @param id the id of the coreAssembly to save.
     * @param coreAssembly the coreAssembly to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated coreAssembly,
     * or with status {@code 400 (Bad Request)} if the coreAssembly is not valid,
     * or with status {@code 404 (Not Found)} if the coreAssembly is not found,
     * or with status {@code 500 (Internal Server Error)} if the coreAssembly couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/core-assemblies/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CoreAssembly> partialUpdateCoreAssembly(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CoreAssembly coreAssembly
    ) throws URISyntaxException {
        log.debug("REST request to partial update CoreAssembly partially : {}, {}", id, coreAssembly);
        if (coreAssembly.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, coreAssembly.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!coreAssemblyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        return onPartialUpdate(coreAssembly, applicationName, ENTITY_NAME);
    }

    /**
     * {@code GET  /core-assemblies} : get all the coreAssemblies.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of coreAssemblies in body.
     */
    @GetMapping("/core-assemblies")
    public List<CoreAssembly> getAllCoreAssemblies() {
        log.debug("REST request to get all CoreAssemblies");
        return coreAssemblyService.findAll();
    }

    /**
     * {@code GET  /core-assemblies/:id} : get the "id" coreAssembly.
     *
     * @param id the id of the coreAssembly to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the coreAssembly, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/core-assemblies/{id}")
    public ResponseEntity<CoreAssembly> getCoreAssembly(@PathVariable Long id) {
        log.debug("REST request to get CoreAssembly : {}", id);
        Optional<CoreAssembly> coreAssembly = coreAssemblyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(coreAssembly);
    }

    /**
     * {@code DELETE  /core-assemblies/:id} : delete the "id" coreAssembly.
     *
     * @param id the id of the coreAssembly to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/core-assemblies/{id}")
    public ResponseEntity<Void> deleteCoreAssembly(@PathVariable Long id) {
        log.debug("REST request to delete CoreAssembly : {}", id);
        coreAssemblyService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
