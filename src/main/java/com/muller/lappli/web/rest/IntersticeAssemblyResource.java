package com.muller.lappli.web.rest;

import com.muller.lappli.domain.IntersticeAssembly;
import com.muller.lappli.repository.IntersticeAssemblyRepository;
import com.muller.lappli.service.IntersticeAssemblyQueryService;
import com.muller.lappli.service.IntersticeAssemblyService;
import com.muller.lappli.service.criteria.IntersticeAssemblyCriteria;
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
 * REST controller for managing {@link com.muller.lappli.domain.IntersticeAssembly}.
 */
@RestController
@RequestMapping("/api")
public class IntersticeAssemblyResource {

    private final Logger log = LoggerFactory.getLogger(IntersticeAssemblyResource.class);

    private static final String ENTITY_NAME = "intersticeAssembly";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IntersticeAssemblyService intersticeAssemblyService;

    private final IntersticeAssemblyRepository intersticeAssemblyRepository;

    private final IntersticeAssemblyQueryService intersticeAssemblyQueryService;

    public IntersticeAssemblyResource(
        IntersticeAssemblyService intersticeAssemblyService,
        IntersticeAssemblyRepository intersticeAssemblyRepository,
        IntersticeAssemblyQueryService intersticeAssemblyQueryService
    ) {
        this.intersticeAssemblyService = intersticeAssemblyService;
        this.intersticeAssemblyRepository = intersticeAssemblyRepository;
        this.intersticeAssemblyQueryService = intersticeAssemblyQueryService;
    }

    /**
     * {@code POST  /interstice-assemblies} : Create a new intersticeAssembly.
     *
     * @param intersticeAssembly the intersticeAssembly to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new intersticeAssembly, or with status {@code 400 (Bad Request)} if the intersticeAssembly has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/interstice-assemblies")
    public ResponseEntity<IntersticeAssembly> createIntersticeAssembly(@Valid @RequestBody IntersticeAssembly intersticeAssembly)
        throws URISyntaxException {
        log.debug("REST request to save IntersticeAssembly : {}", intersticeAssembly);
        if (intersticeAssembly.getId() != null) {
            throw new BadRequestAlertException("A new intersticeAssembly cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IntersticeAssembly result = intersticeAssemblyService.save(intersticeAssembly);
        return ResponseEntity
            .created(new URI("/api/interstice-assemblies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /interstice-assemblies/:id} : Updates an existing intersticeAssembly.
     *
     * @param id the id of the intersticeAssembly to save.
     * @param intersticeAssembly the intersticeAssembly to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated intersticeAssembly,
     * or with status {@code 400 (Bad Request)} if the intersticeAssembly is not valid,
     * or with status {@code 500 (Internal Server Error)} if the intersticeAssembly couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/interstice-assemblies/{id}")
    public ResponseEntity<IntersticeAssembly> updateIntersticeAssembly(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody IntersticeAssembly intersticeAssembly
    ) throws URISyntaxException {
        log.debug("REST request to update IntersticeAssembly : {}, {}", id, intersticeAssembly);
        if (intersticeAssembly.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, intersticeAssembly.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!intersticeAssemblyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        IntersticeAssembly result = intersticeAssemblyService.save(intersticeAssembly);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, intersticeAssembly.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /interstice-assemblies/:id} : Partial updates given fields of an existing intersticeAssembly, field will ignore if it is null
     *
     * @param id the id of the intersticeAssembly to save.
     * @param intersticeAssembly the intersticeAssembly to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated intersticeAssembly,
     * or with status {@code 400 (Bad Request)} if the intersticeAssembly is not valid,
     * or with status {@code 404 (Not Found)} if the intersticeAssembly is not found,
     * or with status {@code 500 (Internal Server Error)} if the intersticeAssembly couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/interstice-assemblies/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IntersticeAssembly> partialUpdateIntersticeAssembly(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody IntersticeAssembly intersticeAssembly
    ) throws URISyntaxException {
        log.debug("REST request to partial update IntersticeAssembly partially : {}, {}", id, intersticeAssembly);
        if (intersticeAssembly.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, intersticeAssembly.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!intersticeAssemblyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<IntersticeAssembly> result = intersticeAssemblyService.partialUpdate(intersticeAssembly);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, intersticeAssembly.getId().toString())
        );
    }

    /**
     * {@code GET  /interstice-assemblies} : get all the intersticeAssemblies.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of intersticeAssemblies in body.
     */
    @GetMapping("/interstice-assemblies")
    public ResponseEntity<List<IntersticeAssembly>> getAllIntersticeAssemblies(IntersticeAssemblyCriteria criteria) {
        log.debug("REST request to get IntersticeAssemblies by criteria: {}", criteria);
        List<IntersticeAssembly> entityList = intersticeAssemblyQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /interstice-assemblies/count} : count all the intersticeAssemblies.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/interstice-assemblies/count")
    public ResponseEntity<Long> countIntersticeAssemblies(IntersticeAssemblyCriteria criteria) {
        log.debug("REST request to count IntersticeAssemblies by criteria: {}", criteria);
        return ResponseEntity.ok().body(intersticeAssemblyQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /interstice-assemblies/:id} : get the "id" intersticeAssembly.
     *
     * @param id the id of the intersticeAssembly to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the intersticeAssembly, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/interstice-assemblies/{id}")
    public ResponseEntity<IntersticeAssembly> getIntersticeAssembly(@PathVariable Long id) {
        log.debug("REST request to get IntersticeAssembly : {}", id);
        Optional<IntersticeAssembly> intersticeAssembly = intersticeAssemblyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(intersticeAssembly);
    }

    /**
     * {@code DELETE  /interstice-assemblies/:id} : delete the "id" intersticeAssembly.
     *
     * @param id the id of the intersticeAssembly to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/interstice-assemblies/{id}")
    public ResponseEntity<Void> deleteIntersticeAssembly(@PathVariable Long id) {
        log.debug("REST request to delete IntersticeAssembly : {}", id);
        intersticeAssemblyService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
