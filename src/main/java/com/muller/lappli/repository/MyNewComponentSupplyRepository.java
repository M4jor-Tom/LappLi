package com.muller.lappli.repository;

import com.muller.lappli.domain.MyNewComponentSupply;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MyNewComponentSupply entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MyNewComponentSupplyRepository extends JpaRepository<MyNewComponentSupply, Long> {}
