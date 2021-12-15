package com.muller.lappli.service;

import com.muller.lappli.domain.*; // for static metamodels
import com.muller.lappli.domain.ElementSupply;
import com.muller.lappli.repository.ElementSupplyRepository;
import com.muller.lappli.service.criteria.ElementSupplyCriteria;
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
 * Service for executing complex queries for {@link ElementSupply} entities in the database.
 * The main input is a {@link ElementSupplyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ElementSupply} or a {@link Page} of {@link ElementSupply} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ElementSupplyQueryService extends QueryService<ElementSupply> {

    private final Logger log = LoggerFactory.getLogger(ElementSupplyQueryService.class);

    private final ElementSupplyRepository elementSupplyRepository;

    public ElementSupplyQueryService(ElementSupplyRepository elementSupplyRepository) {
        this.elementSupplyRepository = elementSupplyRepository;
    }

    /**
     * Return a {@link List} of {@link ElementSupply} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ElementSupply> findByCriteria(ElementSupplyCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ElementSupply> specification = createSpecification(criteria);
        return elementSupplyRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ElementSupply} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ElementSupply> findByCriteria(ElementSupplyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ElementSupply> specification = createSpecification(criteria);
        return elementSupplyRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ElementSupplyCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ElementSupply> specification = createSpecification(criteria);
        return elementSupplyRepository.count(specification);
    }

    /**
     * Function to convert {@link ElementSupplyCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ElementSupply> createSpecification(ElementSupplyCriteria criteria) {
        Specification<ElementSupply> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ElementSupply_.id));
            }
            if (criteria.getApparitions() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getApparitions(), ElementSupply_.apparitions));
            }
            if (criteria.getMarkingType() != null) {
                specification = specification.and(buildSpecification(criteria.getMarkingType(), ElementSupply_.markingType));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ElementSupply_.description));
            }
            if (criteria.getElementId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getElementId(),
                            root -> root.join(ElementSupply_.element, JoinType.LEFT).get(Element_.id)
                        )
                    );
            }
            if (criteria.getStrandId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getStrandId(), root -> root.join(ElementSupply_.strand, JoinType.LEFT).get(Strand_.id))
                    );
            }
        }
        return specification;
    }
}
