package com.muller.lappli.service;

import com.muller.lappli.domain.*; // for static metamodels
import com.muller.lappli.domain.Copper;
import com.muller.lappli.repository.CopperRepository;
import com.muller.lappli.service.criteria.CopperCriteria;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Copper} entities in the database.
 * The main input is a {@link CopperCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Copper} or a {@link Page} of {@link Copper} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CopperQueryService extends QueryService<Copper> {

    private final Logger log = LoggerFactory.getLogger(CopperQueryService.class);

    private final CopperRepository copperRepository;

    public CopperQueryService(CopperRepository copperRepository) {
        this.copperRepository = copperRepository;
    }

    /**
     * Return a {@link List} of {@link Copper} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Copper> findByCriteria(CopperCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Copper> specification = createSpecification(criteria);
        return copperRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Copper} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Copper> findByCriteria(CopperCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Copper> specification = createSpecification(criteria);
        return copperRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CopperCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Copper> specification = createSpecification(criteria);
        return copperRepository.count(specification);
    }

    /**
     * Function to convert {@link CopperCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Copper> createSpecification(CopperCriteria criteria) {
        Specification<Copper> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Copper_.id));
            }
            if (criteria.getNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumber(), Copper_.number));
            }
            if (criteria.getDesignation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDesignation(), Copper_.designation));
            }
        }
        return specification;
    }
}
