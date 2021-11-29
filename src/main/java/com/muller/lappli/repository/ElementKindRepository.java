package com.muller.lappli.repository;

import com.muller.lappli.domain.ElementKind;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ElementKind entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ElementKindRepository extends JpaRepository<ElementKind, Long> {}
