package com.muller.lappli.service;

import com.muller.lappli.domain.*; // for static metamodels
import com.muller.lappli.domain.CustomComponentSupply;
import com.muller.lappli.repository.CustomComponentSupplyRepository;
import com.muller.lappli.service.criteria.CustomComponentSupplyCriteria;
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
 * Service for executing complex queries for {@link CustomComponentSupply} entities in the database.
 * The main input is a {@link CustomComponentSupplyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CustomComponentSupply} or a {@link Page} of {@link CustomComponentSupply} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CustomComponentSupplyQueryService extends QueryService<CustomComponentSupply> {

    private final Logger log = LoggerFactory.getLogger(CustomComponentSupplyQueryService.class);

    private final CustomComponentSupplyRepository customComponentSupplyRepository;

    public CustomComponentSupplyQueryService(CustomComponentSupplyRepository customComponentSupplyRepository) {
        this.customComponentSupplyRepository = customComponentSupplyRepository;
    }

    /**
     * Return a {@link List} of {@link CustomComponentSupply} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CustomComponentSupply> findByCriteria(CustomComponentSupplyCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CustomComponentSupply> specification = createSpecification(criteria);
        return customComponentSupplyRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CustomComponentSupply} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CustomComponentSupply> findByCriteria(CustomComponentSupplyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CustomComponentSupply> specification = createSpecification(criteria);
        return customComponentSupplyRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CustomComponentSupplyCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CustomComponentSupply> specification = createSpecification(criteria);
        return customComponentSupplyRepository.count(specification);
    }

    /**
     * Function to convert {@link CustomComponentSupplyCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CustomComponentSupply> createSpecification(CustomComponentSupplyCriteria criteria) {
        Specification<CustomComponentSupply> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CustomComponentSupply_.id));
            }
            if (criteria.getApparitions() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getApparitions(), CustomComponentSupply_.apparitions));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), CustomComponentSupply_.description));
            }
            if (criteria.getMarkingType() != null) {
                specification = specification.and(buildSpecification(criteria.getMarkingType(), CustomComponentSupply_.markingType));
            }
            if (criteria.getCustomComponentId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustomComponentId(),
                            root -> root.join(CustomComponentSupply_.customComponent, JoinType.LEFT).get(CustomComponent_.id)
                        )
                    );
            }
            if (criteria.getStrandId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getStrandId(),
                            root -> root.join(CustomComponentSupply_.strand, JoinType.LEFT).get(Strand_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
