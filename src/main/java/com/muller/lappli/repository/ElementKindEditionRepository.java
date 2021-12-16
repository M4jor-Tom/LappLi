package com.muller.lappli.repository;

import com.muller.lappli.domain.ElementKindEdition;
import java.time.Instant;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ElementKindEdition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ElementKindEditionRepository
    extends JpaRepository<ElementKindEdition, Long>, JpaSpecificationExecutor<ElementKindEdition> {
    public List<ElementKindEdition> findByEditedElementKindIdAndEditionDateTimeBefore(Long editedElementKindId, Instant editionDateTime);
}
