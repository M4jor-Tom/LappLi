package com.muller.lappli.repository;

import com.muller.lappli.domain.MyNewOperation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MyNewOperation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MyNewOperationRepository extends JpaRepository<MyNewOperation, Long> {}
