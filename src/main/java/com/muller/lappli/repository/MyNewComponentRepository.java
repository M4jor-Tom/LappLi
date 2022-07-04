package com.muller.lappli.repository;

import com.muller.lappli.domain.MyNewComponent;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MyNewComponent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MyNewComponentRepository extends JpaRepository<MyNewComponent, Long> {}
