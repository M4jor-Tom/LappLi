package com.muller.lappli.repository;

import com.muller.lappli.domain.TapeKind;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TapeKind entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TapeKindRepository extends JpaRepository<TapeKind, Long> {}
