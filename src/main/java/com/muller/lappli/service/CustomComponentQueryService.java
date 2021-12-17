package com.muller.lappli.service;

import com.muller.lappli.domain.*; // for static metamodels
import com.muller.lappli.domain.CustomComponent;
import com.muller.lappli.repository.CustomComponentRepository;
import com.muller.lappli.service.criteria.CustomComponentCriteria;
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
 * Service for executing complex queries for {@link CustomComponent} entities in the database.
 * The main input is a {@link CustomComponentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CustomComponent} or a {@link Page} of {@link CustomComponent} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CustomComponentQueryService extends QueryService<CustomComponent> {

    private final Logger log = LoggerFactory.getLogger(CustomComponentQueryService.class);

    private final CustomComponentRepository customComponentRepository;

    public CustomComponentQueryService(CustomComponentRepository customComponentRepository) {
        this.customComponentRepository = customComponentRepository;
    }

    /**
     * Return a {@link List} of {@link CustomComponent} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CustomComponent> findByCriteria(CustomComponentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CustomComponent> specification = createSpecification(criteria);
        return customComponentRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CustomComponent} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CustomComponent> findByCriteria(CustomComponentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CustomComponent> specification = createSpecification(criteria);
        return customComponentRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CustomComponentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CustomComponent> specification = createSpecification(criteria);
        return customComponentRepository.count(specification);
    }

    /**
     * Function to convert {@link CustomComponentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CustomComponent> createSpecification(CustomComponentCriteria criteria) {
        Specification<CustomComponent> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CustomComponent_.id));
            }
            if (criteria.getNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumber(), CustomComponent_.number));
            }
            if (criteria.getDesignation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDesignation(), CustomComponent_.designation));
            }
            if (criteria.getGramPerMeterLinearMass() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getGramPerMeterLinearMass(), CustomComponent_.gramPerMeterLinearMass)
                    );
            }
            if (criteria.getMilimeterDiameter() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getMilimeterDiameter(), CustomComponent_.milimeterDiameter));
            }
            if (criteria.getSurfaceColor() != null) {
                specification = specification.and(buildSpecification(criteria.getSurfaceColor(), CustomComponent_.surfaceColor));
            }
            if (criteria.getSurfaceMaterialId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSurfaceMaterialId(),
                            root -> root.join(CustomComponent_.surfaceMaterial, JoinType.LEFT).get(Material_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
