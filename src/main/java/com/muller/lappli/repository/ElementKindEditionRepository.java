package com.muller.lappli.repository;

import com.muller.lappli.domain.ElementKindEdition;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ElementKindEdition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ElementKindEditionRepository extends JpaRepository<ElementKindEdition, Long> {}
