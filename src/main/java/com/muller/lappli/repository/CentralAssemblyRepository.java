package com.muller.lappli.repository;

import com.muller.lappli.domain.CentralAssembly;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CentralAssembly entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CentralAssemblyRepository extends JpaRepository<CentralAssembly, Long> {}
