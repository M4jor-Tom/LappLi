package com.muller.lappli.web.rest;

import com.muller.lappli.domain.CarrierPlaitFiber;
import com.muller.lappli.repository.CarrierPlaitFiberRepository;
import com.muller.lappli.service.CarrierPlaitFiberService;
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
 * REST controller for managing {@link com.muller.lappli.domain.CarrierPlaitFiber}.
 */
@RestController
@RequestMapping("/api")
public class CarrierPlaitFiberResource {

    private final Logger log = LoggerFactory.getLogger(CarrierPlaitFiberResource.class);

    private static final String ENTITY_NAME = "carrierPlaitFiber";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CarrierPlaitFiberService carrierPlaitFiberService;

    private final CarrierPlaitFiberRepository carrierPlaitFiberRepository;

    public CarrierPlaitFiberResource(
        CarrierPlaitFiberService carrierPlaitFiberService,
        CarrierPlaitFiberRepository carrierPlaitFiberRepository
    ) {
        this.carrierPlaitFiberService = carrierPlaitFiberService;
        this.carrierPlaitFiberRepository = carrierPlaitFiberRepository;
    }

    /**
     * {@code POST  /carrier-plait-fibers} : Create a new carrierPlaitFiber.
     *
     * @param carrierPlaitFiber the carrierPlaitFiber to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new carrierPlaitFiber, or with status {@code 400 (Bad Request)} if the carrierPlaitFiber has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/carrier-plait-fibers")
    public ResponseEntity<CarrierPlaitFiber> createCarrierPlaitFiber(@Valid @RequestBody CarrierPlaitFiber carrierPlaitFiber)
        throws URISyntaxException {
        log.debug("REST request to save CarrierPlaitFiber : {}", carrierPlaitFiber);
        if (carrierPlaitFiber.getId() != null) {
            throw new BadRequestAlertException("A new carrierPlaitFiber cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CarrierPlaitFiber result = carrierPlaitFiberService.save(carrierPlaitFiber);
        return ResponseEntity
            .created(new URI("/api/carrier-plait-fibers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /carrier-plait-fibers/:id} : Updates an existing carrierPlaitFiber.
     *
     * @param id the id of the carrierPlaitFiber to save.
     * @param carrierPlaitFiber the carrierPlaitFiber to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated carrierPlaitFiber,
     * or with status {@code 400 (Bad Request)} if the carrierPlaitFiber is not valid,
     * or with status {@code 500 (Internal Server Error)} if the carrierPlaitFiber couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/carrier-plait-fibers/{id}")
    public ResponseEntity<CarrierPlaitFiber> updateCarrierPlaitFiber(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CarrierPlaitFiber carrierPlaitFiber
    ) throws URISyntaxException {
        log.debug("REST request to update CarrierPlaitFiber : {}, {}", id, carrierPlaitFiber);
        if (carrierPlaitFiber.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, carrierPlaitFiber.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!carrierPlaitFiberRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CarrierPlaitFiber result = carrierPlaitFiberService.save(carrierPlaitFiber);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, carrierPlaitFiber.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /carrier-plait-fibers/:id} : Partial updates given fields of an existing carrierPlaitFiber, field will ignore if it is null
     *
     * @param id the id of the carrierPlaitFiber to save.
     * @param carrierPlaitFiber the carrierPlaitFiber to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated carrierPlaitFiber,
     * or with status {@code 400 (Bad Request)} if the carrierPlaitFiber is not valid,
     * or with status {@code 404 (Not Found)} if the carrierPlaitFiber is not found,
     * or with status {@code 500 (Internal Server Error)} if the carrierPlaitFiber couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/carrier-plait-fibers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CarrierPlaitFiber> partialUpdateCarrierPlaitFiber(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CarrierPlaitFiber carrierPlaitFiber
    ) throws URISyntaxException {
        log.debug("REST request to partial update CarrierPlaitFiber partially : {}, {}", id, carrierPlaitFiber);
        if (carrierPlaitFiber.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, carrierPlaitFiber.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!carrierPlaitFiberRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CarrierPlaitFiber> result = carrierPlaitFiberService.partialUpdate(carrierPlaitFiber);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, carrierPlaitFiber.getId().toString())
        );
    }

    /**
     * {@code GET  /carrier-plait-fibers} : get all the carrierPlaitFibers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of carrierPlaitFibers in body.
     */
    @GetMapping("/carrier-plait-fibers")
    public List<CarrierPlaitFiber> getAllCarrierPlaitFibers() {
        log.debug("REST request to get all CarrierPlaitFibers");
        return carrierPlaitFiberService.findAll();
    }

    /**
     * {@code GET  /carrier-plait-fibers/:id} : get the "id" carrierPlaitFiber.
     *
     * @param id the id of the carrierPlaitFiber to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the carrierPlaitFiber, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/carrier-plait-fibers/{id}")
    public ResponseEntity<CarrierPlaitFiber> getCarrierPlaitFiber(@PathVariable Long id) {
        log.debug("REST request to get CarrierPlaitFiber : {}", id);
        Optional<CarrierPlaitFiber> carrierPlaitFiber = carrierPlaitFiberService.findOne(id);
        return ResponseUtil.wrapOrNotFound(carrierPlaitFiber);
    }

    /**
     * {@code DELETE  /carrier-plait-fibers/:id} : delete the "id" carrierPlaitFiber.
     *
     * @param id the id of the carrierPlaitFiber to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/carrier-plait-fibers/{id}")
    public ResponseEntity<Void> deleteCarrierPlaitFiber(@PathVariable Long id) {
        log.debug("REST request to delete CarrierPlaitFiber : {}", id);
        carrierPlaitFiberService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
