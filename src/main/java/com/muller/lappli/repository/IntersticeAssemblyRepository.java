package com.muller.lappli.repository;

import com.muller.lappli.domain.IntersticeAssembly;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the IntersticeAssembly entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IntersticeAssemblyRepository
    extends JpaRepository<IntersticeAssembly, Long>, JpaSpecificationExecutor<IntersticeAssembly> {}
