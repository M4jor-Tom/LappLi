package com.muller.lappli.web.rest.abstracts;

import com.muller.lappli.domain.abstracts.AbstractDomainObject;
import com.muller.lappli.domain.exception.PositionHasSeveralSupplyException;
import com.muller.lappli.domain.exception.PositionInSeveralAssemblyException;
import com.muller.lappli.service.IPositionCheckingService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

public abstract class AbstractPositionCheckingResource<T extends AbstractDomainObject<T>> {

    protected abstract IPositionCheckingService<T> getPositionCheckingService();

    protected abstract String getSpineCasePluralEntityName();

    private String onCreatedURIPrefixBeforeId() {
        return "/api/" + getSpineCasePluralEntityName() + "/";
    }

    protected ResponseEntity<T> onSave(T domainObject, String applicationName, String entityName, Boolean postNotPut)
        throws URISyntaxException {
        try {
            T result = getPositionCheckingService().save(domainObject);
            BodyBuilder status = postNotPut
                ? ResponseEntity.created(new URI(onCreatedURIPrefixBeforeId() + result.getId()))
                : ResponseEntity.ok();

            return status
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, entityName, result.getId().toString()))
                .body(result);
        } catch (PositionHasSeveralSupplyException | PositionInSeveralAssemblyException e) {
            return ResponseEntity
                .badRequest()
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, entityName, e.getMessage()))
                .body(null);
        }
    }

    protected ResponseEntity<T> onPartialUpdate(T domainObject, String applicationName, String entityName) {
        try {
            Optional<T> result = getPositionCheckingService().partialUpdate(domainObject);
            return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert(applicationName, true, entityName, domainObject.getId().toString())
            );
        } catch (PositionHasSeveralSupplyException | PositionInSeveralAssemblyException e) {
            return ResponseUtil.wrapOrNotFound(
                Optional.empty(),
                HeaderUtil.createEntityUpdateAlert(applicationName, true, entityName, e.getMessage())
            );
        }
    }
}
