package com.muller.lappli.repository;

import com.muller.lappli.domain.CustomComponent;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CustomComponent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomComponentRepository extends JpaRepository<CustomComponent, Long> {}
