package com.muller.lappli.web.rest.abstracts;

import com.muller.lappli.domain.abstracts.AbstractAssembly;
import com.muller.lappli.domain.exception.PositionHasSeveralSupplyException;
import com.muller.lappli.domain.exception.PositionInSeveralAssemblyException;
import com.muller.lappli.service.IAssemblyService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

public abstract class AbstractAssemblyResource<T extends AbstractAssembly<T>> {

    protected abstract IAssemblyService<T> getAssemblyService();

    protected abstract String getSpineCaseEntityName();

    private String onCreatedURIPrefixBeforeId() {
        return "/api/" + getSpineCaseEntityName() + "/";
    }

    protected ResponseEntity<T> onSave(T assembly, String applicationName, String entityName) throws URISyntaxException {
        try {
            T result = getAssemblyService().save(assembly);
            return ResponseEntity
                .created(new URI(onCreatedURIPrefixBeforeId() + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, entityName, result.getId().toString()))
                .body(result);
        } catch (PositionHasSeveralSupplyException | PositionInSeveralAssemblyException e) {
            return ResponseEntity
                .badRequest()
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, entityName, e.getMessage()))
                .body(null);
        }
    }

    protected ResponseEntity<T> onPartialUpdate(T assembly, String applicationName, String entityName) {
        try {
            Optional<T> result = getAssemblyService().partialUpdate(assembly);
            return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert(applicationName, true, entityName, assembly.getId().toString())
            );
        } catch (PositionHasSeveralSupplyException | PositionInSeveralAssemblyException e) {
            return ResponseUtil.wrapOrNotFound(
                Optional.empty(),
                HeaderUtil.createEntityUpdateAlert(applicationName, true, entityName, e.getMessage())
            );
        }
    }
}
