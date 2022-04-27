package com.muller.lappli.web.rest;

import com.muller.lappli.domain.CarrierPlait;
import com.muller.lappli.repository.CarrierPlaitRepository;
import com.muller.lappli.service.CarrierPlaitService;
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
 * REST controller for managing {@link com.muller.lappli.domain.CarrierPlait}.
 */
@RestController
@RequestMapping("/api")
public class CarrierPlaitResource {

    private final Logger log = LoggerFactory.getLogger(CarrierPlaitResource.class);

    private static final String ENTITY_NAME = "carrierPlait";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CarrierPlaitService carrierPlaitService;

    private final CarrierPlaitRepository carrierPlaitRepository;

    public CarrierPlaitResource(CarrierPlaitService carrierPlaitService, CarrierPlaitRepository carrierPlaitRepository) {
        this.carrierPlaitService = carrierPlaitService;
        this.carrierPlaitRepository = carrierPlaitRepository;
    }

    /**
     * {@code POST  /carrier-plaits} : Create a new carrierPlait.
     *
     * @param carrierPlait the carrierPlait to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new carrierPlait, or with status {@code 400 (Bad Request)} if the carrierPlait has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/carrier-plaits")
    public ResponseEntity<CarrierPlait> createCarrierPlait(@Valid @RequestBody CarrierPlait carrierPlait) throws URISyntaxException {
        log.debug("REST request to save CarrierPlait : {}", carrierPlait);
        if (carrierPlait.getId() != null) {
            throw new BadRequestAlertException("A new carrierPlait cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CarrierPlait result = carrierPlaitService.save(carrierPlait);
        return ResponseEntity
            .created(new URI("/api/carrier-plaits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /carrier-plaits/:id} : Updates an existing carrierPlait.
     *
     * @param id the id of the carrierPlait to save.
     * @param carrierPlait the carrierPlait to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated carrierPlait,
     * or with status {@code 400 (Bad Request)} if the carrierPlait is not valid,
     * or with status {@code 500 (Internal Server Error)} if the carrierPlait couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/carrier-plaits/{id}")
    public ResponseEntity<CarrierPlait> updateCarrierPlait(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CarrierPlait carrierPlait
    ) throws URISyntaxException {
        log.debug("REST request to update CarrierPlait : {}, {}", id, carrierPlait);
        if (carrierPlait.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, carrierPlait.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!carrierPlaitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CarrierPlait result = carrierPlaitService.save(carrierPlait);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, carrierPlait.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /carrier-plaits/:id} : Partial updates given fields of an existing carrierPlait, field will ignore if it is null
     *
     * @param id the id of the carrierPlait to save.
     * @param carrierPlait the carrierPlait to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated carrierPlait,
     * or with status {@code 400 (Bad Request)} if the carrierPlait is not valid,
     * or with status {@code 404 (Not Found)} if the carrierPlait is not found,
     * or with status {@code 500 (Internal Server Error)} if the carrierPlait couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/carrier-plaits/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CarrierPlait> partialUpdateCarrierPlait(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CarrierPlait carrierPlait
    ) throws URISyntaxException {
        log.debug("REST request to partial update CarrierPlait partially : {}, {}", id, carrierPlait);
        if (carrierPlait.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, carrierPlait.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!carrierPlaitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CarrierPlait> result = carrierPlaitService.partialUpdate(carrierPlait);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, carrierPlait.getId().toString())
        );
    }

    /**
     * {@code GET  /carrier-plaits} : get all the carrierPlaits.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of carrierPlaits in body.
     */
    @GetMapping("/carrier-plaits")
    public List<CarrierPlait> getAllCarrierPlaits() {
        log.debug("REST request to get all CarrierPlaits");
        return carrierPlaitService.findAll();
    }

    /**
     * {@code GET  /carrier-plaits/:id} : get the "id" carrierPlait.
     *
     * @param id the id of the carrierPlait to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the carrierPlait, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/carrier-plaits/{id}")
    public ResponseEntity<CarrierPlait> getCarrierPlait(@PathVariable Long id) {
        log.debug("REST request to get CarrierPlait : {}", id);
        Optional<CarrierPlait> carrierPlait = carrierPlaitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(carrierPlait);
    }

    /**
     * {@code DELETE  /carrier-plaits/:id} : delete the "id" carrierPlait.
     *
     * @param id the id of the carrierPlait to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/carrier-plaits/{id}")
    public ResponseEntity<Void> deleteCarrierPlait(@PathVariable Long id) {
        log.debug("REST request to delete CarrierPlait : {}", id);
        carrierPlaitService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
