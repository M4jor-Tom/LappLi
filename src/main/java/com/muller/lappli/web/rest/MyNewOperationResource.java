package com.muller.lappli.web.rest;

import com.muller.lappli.domain.MyNewOperation;
import com.muller.lappli.repository.MyNewOperationRepository;
import com.muller.lappli.service.MyNewOperationService;
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
 * REST controller for managing {@link com.muller.lappli.domain.MyNewOperation}.
 */
@RestController
@RequestMapping("/api")
public class MyNewOperationResource {

    private final Logger log = LoggerFactory.getLogger(MyNewOperationResource.class);

    private static final String ENTITY_NAME = "myNewOperation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MyNewOperationService myNewOperationService;

    private final MyNewOperationRepository myNewOperationRepository;

    public MyNewOperationResource(MyNewOperationService myNewOperationService, MyNewOperationRepository myNewOperationRepository) {
        this.myNewOperationService = myNewOperationService;
        this.myNewOperationRepository = myNewOperationRepository;
    }

    /**
     * {@code POST  /my-new-operations} : Create a new myNewOperation.
     *
     * @param myNewOperation the myNewOperation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new myNewOperation, or with status {@code 400 (Bad Request)} if the myNewOperation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/my-new-operations")
    public ResponseEntity<MyNewOperation> createMyNewOperation(@Valid @RequestBody MyNewOperation myNewOperation)
        throws URISyntaxException {
        log.debug("REST request to save MyNewOperation : {}", myNewOperation);
        if (myNewOperation.getId() != null) {
            throw new BadRequestAlertException("A new myNewOperation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MyNewOperation result = myNewOperationService.save(myNewOperation);
        return ResponseEntity
            .created(new URI("/api/my-new-operations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /my-new-operations/:id} : Updates an existing myNewOperation.
     *
     * @param id the id of the myNewOperation to save.
     * @param myNewOperation the myNewOperation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated myNewOperation,
     * or with status {@code 400 (Bad Request)} if the myNewOperation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the myNewOperation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/my-new-operations/{id}")
    public ResponseEntity<MyNewOperation> updateMyNewOperation(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MyNewOperation myNewOperation
    ) throws URISyntaxException {
        log.debug("REST request to update MyNewOperation : {}, {}", id, myNewOperation);
        if (myNewOperation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, myNewOperation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!myNewOperationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MyNewOperation result = myNewOperationService.save(myNewOperation);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, myNewOperation.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /my-new-operations/:id} : Partial updates given fields of an existing myNewOperation, field will ignore if it is null
     *
     * @param id the id of the myNewOperation to save.
     * @param myNewOperation the myNewOperation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated myNewOperation,
     * or with status {@code 400 (Bad Request)} if the myNewOperation is not valid,
     * or with status {@code 404 (Not Found)} if the myNewOperation is not found,
     * or with status {@code 500 (Internal Server Error)} if the myNewOperation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/my-new-operations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MyNewOperation> partialUpdateMyNewOperation(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MyNewOperation myNewOperation
    ) throws URISyntaxException {
        log.debug("REST request to partial update MyNewOperation partially : {}, {}", id, myNewOperation);
        if (myNewOperation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, myNewOperation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!myNewOperationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MyNewOperation> result = myNewOperationService.partialUpdate(myNewOperation);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, myNewOperation.getId().toString())
        );
    }

    /**
     * {@code GET  /my-new-operations} : get all the myNewOperations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of myNewOperations in body.
     */
    @GetMapping("/my-new-operations")
    public List<MyNewOperation> getAllMyNewOperations() {
        log.debug("REST request to get all MyNewOperations");
        return myNewOperationService.findAll();
    }

    /**
     * {@code GET  /my-new-operations/:id} : get the "id" myNewOperation.
     *
     * @param id the id of the myNewOperation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the myNewOperation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/my-new-operations/{id}")
    public ResponseEntity<MyNewOperation> getMyNewOperation(@PathVariable Long id) {
        log.debug("REST request to get MyNewOperation : {}", id);
        Optional<MyNewOperation> myNewOperation = myNewOperationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(myNewOperation);
    }

    /**
     * {@code DELETE  /my-new-operations/:id} : delete the "id" myNewOperation.
     *
     * @param id the id of the myNewOperation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/my-new-operations/{id}")
    public ResponseEntity<Void> deleteMyNewOperation(@PathVariable Long id) {
        log.debug("REST request to delete MyNewOperation : {}", id);
        myNewOperationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
