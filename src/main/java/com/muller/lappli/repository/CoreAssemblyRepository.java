package com.muller.lappli.repository;

import com.muller.lappli.domain.CoreAssembly;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CoreAssembly entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CoreAssemblyRepository extends JpaRepository<CoreAssembly, Long> {}
