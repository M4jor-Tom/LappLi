package com.muller.lappli.web.rest;

import com.muller.lappli.domain.MyNewComponentSupply;
import com.muller.lappli.repository.MyNewComponentSupplyRepository;
import com.muller.lappli.service.MyNewComponentSupplyService;
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
 * REST controller for managing {@link com.muller.lappli.domain.MyNewComponentSupply}.
 */
@RestController
@RequestMapping("/api")
public class MyNewComponentSupplyResource {

    private final Logger log = LoggerFactory.getLogger(MyNewComponentSupplyResource.class);

    private static final String ENTITY_NAME = "myNewComponentSupply";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MyNewComponentSupplyService myNewComponentSupplyService;

    private final MyNewComponentSupplyRepository myNewComponentSupplyRepository;

    public MyNewComponentSupplyResource(
        MyNewComponentSupplyService myNewComponentSupplyService,
        MyNewComponentSupplyRepository myNewComponentSupplyRepository
    ) {
        this.myNewComponentSupplyService = myNewComponentSupplyService;
        this.myNewComponentSupplyRepository = myNewComponentSupplyRepository;
    }

    /**
     * {@code POST  /my-new-component-supplies} : Create a new myNewComponentSupply.
     *
     * @param myNewComponentSupply the myNewComponentSupply to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new myNewComponentSupply, or with status {@code 400 (Bad Request)} if the myNewComponentSupply has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/my-new-component-supplies")
    public ResponseEntity<MyNewComponentSupply> createMyNewComponentSupply(@Valid @RequestBody MyNewComponentSupply myNewComponentSupply)
        throws URISyntaxException {
        log.debug("REST request to save MyNewComponentSupply : {}", myNewComponentSupply);
        if (myNewComponentSupply.getId() != null) {
            throw new BadRequestAlertException("A new myNewComponentSupply cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MyNewComponentSupply result = myNewComponentSupplyService.save(myNewComponentSupply);
        return ResponseEntity
            .created(new URI("/api/my-new-component-supplies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /my-new-component-supplies/:id} : Updates an existing myNewComponentSupply.
     *
     * @param id the id of the myNewComponentSupply to save.
     * @param myNewComponentSupply the myNewComponentSupply to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated myNewComponentSupply,
     * or with status {@code 400 (Bad Request)} if the myNewComponentSupply is not valid,
     * or with status {@code 500 (Internal Server Error)} if the myNewComponentSupply couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/my-new-component-supplies/{id}")
    public ResponseEntity<MyNewComponentSupply> updateMyNewComponentSupply(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MyNewComponentSupply myNewComponentSupply
    ) throws URISyntaxException {
        log.debug("REST request to update MyNewComponentSupply : {}, {}", id, myNewComponentSupply);
        if (myNewComponentSupply.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, myNewComponentSupply.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!myNewComponentSupplyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MyNewComponentSupply result = myNewComponentSupplyService.save(myNewComponentSupply);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, myNewComponentSupply.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /my-new-component-supplies/:id} : Partial updates given fields of an existing myNewComponentSupply, field will ignore if it is null
     *
     * @param id the id of the myNewComponentSupply to save.
     * @param myNewComponentSupply the myNewComponentSupply to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated myNewComponentSupply,
     * or with status {@code 400 (Bad Request)} if the myNewComponentSupply is not valid,
     * or with status {@code 404 (Not Found)} if the myNewComponentSupply is not found,
     * or with status {@code 500 (Internal Server Error)} if the myNewComponentSupply couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/my-new-component-supplies/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MyNewComponentSupply> partialUpdateMyNewComponentSupply(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MyNewComponentSupply myNewComponentSupply
    ) throws URISyntaxException {
        log.debug("REST request to partial update MyNewComponentSupply partially : {}, {}", id, myNewComponentSupply);
        if (myNewComponentSupply.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, myNewComponentSupply.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!myNewComponentSupplyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MyNewComponentSupply> result = myNewComponentSupplyService.partialUpdate(myNewComponentSupply);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, myNewComponentSupply.getId().toString())
        );
    }

    /**
     * {@code GET  /my-new-component-supplies} : get all the myNewComponentSupplies.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of myNewComponentSupplies in body.
     */
    @GetMapping("/my-new-component-supplies")
    public List<MyNewComponentSupply> getAllMyNewComponentSupplies() {
        log.debug("REST request to get all MyNewComponentSupplies");
        return myNewComponentSupplyService.findAll();
    }

    /**
     * {@code GET  /my-new-component-supplies/:id} : get the "id" myNewComponentSupply.
     *
     * @param id the id of the myNewComponentSupply to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the myNewComponentSupply, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/my-new-component-supplies/{id}")
    public ResponseEntity<MyNewComponentSupply> getMyNewComponentSupply(@PathVariable Long id) {
        log.debug("REST request to get MyNewComponentSupply : {}", id);
        Optional<MyNewComponentSupply> myNewComponentSupply = myNewComponentSupplyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(myNewComponentSupply);
    }

    /**
     * {@code DELETE  /my-new-component-supplies/:id} : delete the "id" myNewComponentSupply.
     *
     * @param id the id of the myNewComponentSupply to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/my-new-component-supplies/{id}")
    public ResponseEntity<Void> deleteMyNewComponentSupply(@PathVariable Long id) {
        log.debug("REST request to delete MyNewComponentSupply : {}", id);
        myNewComponentSupplyService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
