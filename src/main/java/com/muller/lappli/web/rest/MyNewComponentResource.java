package com.muller.lappli.web.rest;

import com.muller.lappli.domain.MyNewComponent;
import com.muller.lappli.repository.MyNewComponentRepository;
import com.muller.lappli.service.MyNewComponentService;
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
 * REST controller for managing {@link com.muller.lappli.domain.MyNewComponent}.
 */
@RestController
@RequestMapping("/api")
public class MyNewComponentResource {

    private final Logger log = LoggerFactory.getLogger(MyNewComponentResource.class);

    private static final String ENTITY_NAME = "myNewComponent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MyNewComponentService myNewComponentService;

    private final MyNewComponentRepository myNewComponentRepository;

    public MyNewComponentResource(MyNewComponentService myNewComponentService, MyNewComponentRepository myNewComponentRepository) {
        this.myNewComponentService = myNewComponentService;
        this.myNewComponentRepository = myNewComponentRepository;
    }

    /**
     * {@code POST  /my-new-components} : Create a new myNewComponent.
     *
     * @param myNewComponent the myNewComponent to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new myNewComponent, or with status {@code 400 (Bad Request)} if the myNewComponent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/my-new-components")
    public ResponseEntity<MyNewComponent> createMyNewComponent(@Valid @RequestBody MyNewComponent myNewComponent)
        throws URISyntaxException {
        log.debug("REST request to save MyNewComponent : {}", myNewComponent);
        if (myNewComponent.getId() != null) {
            throw new BadRequestAlertException("A new myNewComponent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MyNewComponent result = myNewComponentService.save(myNewComponent);
        return ResponseEntity
            .created(new URI("/api/my-new-components/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /my-new-components/:id} : Updates an existing myNewComponent.
     *
     * @param id the id of the myNewComponent to save.
     * @param myNewComponent the myNewComponent to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated myNewComponent,
     * or with status {@code 400 (Bad Request)} if the myNewComponent is not valid,
     * or with status {@code 500 (Internal Server Error)} if the myNewComponent couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/my-new-components/{id}")
    public ResponseEntity<MyNewComponent> updateMyNewComponent(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MyNewComponent myNewComponent
    ) throws URISyntaxException {
        log.debug("REST request to update MyNewComponent : {}, {}", id, myNewComponent);
        if (myNewComponent.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, myNewComponent.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!myNewComponentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MyNewComponent result = myNewComponentService.save(myNewComponent);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, myNewComponent.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /my-new-components/:id} : Partial updates given fields of an existing myNewComponent, field will ignore if it is null
     *
     * @param id the id of the myNewComponent to save.
     * @param myNewComponent the myNewComponent to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated myNewComponent,
     * or with status {@code 400 (Bad Request)} if the myNewComponent is not valid,
     * or with status {@code 404 (Not Found)} if the myNewComponent is not found,
     * or with status {@code 500 (Internal Server Error)} if the myNewComponent couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/my-new-components/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MyNewComponent> partialUpdateMyNewComponent(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MyNewComponent myNewComponent
    ) throws URISyntaxException {
        log.debug("REST request to partial update MyNewComponent partially : {}, {}", id, myNewComponent);
        if (myNewComponent.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, myNewComponent.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!myNewComponentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MyNewComponent> result = myNewComponentService.partialUpdate(myNewComponent);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, myNewComponent.getId().toString())
        );
    }

    /**
     * {@code GET  /my-new-components} : get all the myNewComponents.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of myNewComponents in body.
     */
    @GetMapping("/my-new-components")
    public List<MyNewComponent> getAllMyNewComponents() {
        log.debug("REST request to get all MyNewComponents");
        return myNewComponentService.findAll();
    }

    /**
     * {@code GET  /my-new-components/:id} : get the "id" myNewComponent.
     *
     * @param id the id of the myNewComponent to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the myNewComponent, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/my-new-components/{id}")
    public ResponseEntity<MyNewComponent> getMyNewComponent(@PathVariable Long id) {
        log.debug("REST request to get MyNewComponent : {}", id);
        Optional<MyNewComponent> myNewComponent = myNewComponentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(myNewComponent);
    }

    /**
     * {@code DELETE  /my-new-components/:id} : delete the "id" myNewComponent.
     *
     * @param id the id of the myNewComponent to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/my-new-components/{id}")
    public ResponseEntity<Void> deleteMyNewComponent(@PathVariable Long id) {
        log.debug("REST request to delete MyNewComponent : {}", id);
        myNewComponentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
