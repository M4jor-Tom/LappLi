package com.muller.lappli.service;

import com.muller.lappli.domain.*; // for static metamodels
import com.muller.lappli.domain.StrandSupply;
import com.muller.lappli.repository.StrandSupplyRepository;
import com.muller.lappli.service.criteria.StrandSupplyCriteria;
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
 * Service for executing complex queries for {@link StrandSupply} entities in the database.
 * The main input is a {@link StrandSupplyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StrandSupply} or a {@link Page} of {@link StrandSupply} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StrandSupplyQueryService extends QueryService<StrandSupply> {

    private final Logger log = LoggerFactory.getLogger(StrandSupplyQueryService.class);

    private final StrandSupplyRepository strandSupplyRepository;

    public StrandSupplyQueryService(StrandSupplyRepository strandSupplyRepository) {
        this.strandSupplyRepository = strandSupplyRepository;
    }

    /**
     * Return a {@link List} of {@link StrandSupply} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StrandSupply> findByCriteria(StrandSupplyCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<StrandSupply> specification = createSpecification(criteria);
        return strandSupplyRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link StrandSupply} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StrandSupply> findByCriteria(StrandSupplyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<StrandSupply> specification = createSpecification(criteria);
        return strandSupplyRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StrandSupplyCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<StrandSupply> specification = createSpecification(criteria);
        return strandSupplyRepository.count(specification);
    }

    /**
     * Function to convert {@link StrandSupplyCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<StrandSupply> createSpecification(StrandSupplyCriteria criteria) {
        Specification<StrandSupply> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), StrandSupply_.id));
            }
            if (criteria.getApparitions() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getApparitions(), StrandSupply_.apparitions));
            }
            if (criteria.getMarkingType() != null) {
                specification = specification.and(buildSpecification(criteria.getMarkingType(), StrandSupply_.markingType));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), StrandSupply_.description));
            }
            if (criteria.getStrandId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getStrandId(), root -> root.join(StrandSupply_.strand, JoinType.LEFT).get(Strand_.id))
                    );
            }
            if (criteria.getStudyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getStudyId(), root -> root.join(StrandSupply_.study, JoinType.LEFT).get(Study_.id))
                    );
            }
        }
        return specification;
    }
}
