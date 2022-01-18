package com.muller.lappli.web.rest;

import com.muller.lappli.domain.CentralAssembly;
import com.muller.lappli.domain.exception.PositionHasSeveralSupplyException;
import com.muller.lappli.domain.exception.PositionInSeveralAssemblyException;
import com.muller.lappli.repository.CentralAssemblyRepository;
import com.muller.lappli.service.CentralAssemblyService;
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
 * REST controller for managing {@link com.muller.lappli.domain.CentralAssembly}.
 */
@RestController
@RequestMapping("/api")
public class CentralAssemblyResource extends AbstractAssemblyResource<CentralAssembly> {

    private final Logger log = LoggerFactory.getLogger(CentralAssemblyResource.class);

    private static final String ENTITY_NAME = "centralAssembly";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CentralAssemblyService centralAssemblyService;

    private final CentralAssemblyRepository centralAssemblyRepository;

    public CentralAssemblyResource(CentralAssemblyService centralAssemblyService, CentralAssemblyRepository centralAssemblyRepository) {
        this.centralAssemblyService = centralAssemblyService;
        this.centralAssemblyRepository = centralAssemblyRepository;
    }

    @Override
    protected String getSpineCasePluralEntityName() {
        return "central-assemblies";
    }

    /**
     * {@code POST  /central-assemblies} : Create a new centralAssembly.
     *
     * @param centralAssembly the centralAssembly to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new centralAssembly, or with status {@code 400 (Bad Request)} if the centralAssembly has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/central-assemblies")
    public ResponseEntity<CentralAssembly> createCentralAssembly(@Valid @RequestBody CentralAssembly centralAssembly)
        throws URISyntaxException {
        log.debug("REST request to save CentralAssembly : {}", centralAssembly);
        if (centralAssembly.getId() != null) {
            throw new BadRequestAlertException("A new centralAssembly cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (Objects.isNull(centralAssembly.getOwnerStrand())) {
            throw new BadRequestAlertException("Invalid association value provided", ENTITY_NAME, "null");
        }

        return onSave(centralAssembly, applicationName, ENTITY_NAME, true);
    }

    /**
     * {@code PUT  /central-assemblies/:id} : Updates an existing centralAssembly.
     *
     * @param id the id of the centralAssembly to save.
     * @param centralAssembly the centralAssembly to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated centralAssembly,
     * or with status {@code 400 (Bad Request)} if the centralAssembly is not valid,
     * or with status {@code 500 (Internal Server Error)} if the centralAssembly couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/central-assemblies/{id}")
    public ResponseEntity<CentralAssembly> updateCentralAssembly(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CentralAssembly centralAssembly
    ) throws URISyntaxException {
        log.debug("REST request to update CentralAssembly : {}, {}", id, centralAssembly);
        if (centralAssembly.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, centralAssembly.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!centralAssemblyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        return onSave(centralAssembly, applicationName, ENTITY_NAME, false);
    }

    /**
     * {@code PATCH  /central-assemblies/:id} : Partial updates given fields of an existing centralAssembly, field will ignore if it is null
     *
     * @param id the id of the centralAssembly to save.
     * @param centralAssembly the centralAssembly to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated centralAssembly,
     * or with status {@code 400 (Bad Request)} if the centralAssembly is not valid,
     * or with status {@code 404 (Not Found)} if the centralAssembly is not found,
     * or with status {@code 500 (Internal Server Error)} if the centralAssembly couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/central-assemblies/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CentralAssembly> partialUpdateCentralAssembly(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CentralAssembly centralAssembly
    ) throws URISyntaxException {
        log.debug("REST request to partial update CentralAssembly partially : {}, {}", id, centralAssembly);
        if (centralAssembly.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, centralAssembly.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!centralAssemblyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        return onPartialUpdate(centralAssembly, applicationName, ENTITY_NAME);
    }

    /**
     * {@code GET  /central-assemblies} : get all the centralAssemblies.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of centralAssemblies in body.
     */
    @GetMapping("/central-assemblies")
    public List<CentralAssembly> getAllCentralAssemblies() {
        log.debug("REST request to get all CentralAssemblies");
        return centralAssemblyService.findAll();
    }

    /**
     * {@code GET  /central-assemblies/:id} : get the "id" centralAssembly.
     *
     * @param id the id of the centralAssembly to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the centralAssembly, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/central-assemblies/{id}")
    public ResponseEntity<CentralAssembly> getCentralAssembly(@PathVariable Long id) {
        log.debug("REST request to get CentralAssembly : {}", id);
        Optional<CentralAssembly> centralAssembly = centralAssemblyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(centralAssembly);
    }

    /**
     * {@code DELETE  /central-assemblies/:id} : delete the "id" centralAssembly.
     *
     * @param id the id of the centralAssembly to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/central-assemblies/{id}")
    public ResponseEntity<Void> deleteCentralAssembly(@PathVariable Long id) {
        log.debug("REST request to delete CentralAssembly : {}", id);
        centralAssemblyService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
