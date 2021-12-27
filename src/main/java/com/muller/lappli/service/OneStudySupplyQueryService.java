package com.muller.lappli.service;

import com.muller.lappli.domain.*; // for static metamodels
import com.muller.lappli.domain.OneStudySupply;
import com.muller.lappli.repository.OneStudySupplyRepository;
import com.muller.lappli.service.criteria.OneStudySupplyCriteria;
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
 * Service for executing complex queries for {@link OneStudySupply} entities in the database.
 * The main input is a {@link OneStudySupplyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OneStudySupply} or a {@link Page} of {@link OneStudySupply} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OneStudySupplyQueryService extends QueryService<OneStudySupply> {

    private final Logger log = LoggerFactory.getLogger(OneStudySupplyQueryService.class);

    private final OneStudySupplyRepository oneStudySupplyRepository;

    public OneStudySupplyQueryService(OneStudySupplyRepository oneStudySupplyRepository) {
        this.oneStudySupplyRepository = oneStudySupplyRepository;
    }

    /**
     * Return a {@link List} of {@link OneStudySupply} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OneStudySupply> findByCriteria(OneStudySupplyCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OneStudySupply> specification = createSpecification(criteria);
        return oneStudySupplyRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link OneStudySupply} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OneStudySupply> findByCriteria(OneStudySupplyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OneStudySupply> specification = createSpecification(criteria);
        return oneStudySupplyRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OneStudySupplyCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OneStudySupply> specification = createSpecification(criteria);
        return oneStudySupplyRepository.count(specification);
    }

    /**
     * Function to convert {@link OneStudySupplyCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OneStudySupply> createSpecification(OneStudySupplyCriteria criteria) {
        Specification<OneStudySupply> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OneStudySupply_.id));
            }
            if (criteria.getApparitions() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getApparitions(), OneStudySupply_.apparitions));
            }
            if (criteria.getNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumber(), OneStudySupply_.number));
            }
            if (criteria.getDesignation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDesignation(), OneStudySupply_.designation));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), OneStudySupply_.description));
            }
            if (criteria.getMarkingType() != null) {
                specification = specification.and(buildSpecification(criteria.getMarkingType(), OneStudySupply_.markingType));
            }
            if (criteria.getGramPerMeterLinearMass() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getGramPerMeterLinearMass(), OneStudySupply_.gramPerMeterLinearMass)
                    );
            }
            if (criteria.getMilimeterDiameter() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getMilimeterDiameter(), OneStudySupply_.milimeterDiameter));
            }
            if (criteria.getSurfaceColor() != null) {
                specification = specification.and(buildSpecification(criteria.getSurfaceColor(), OneStudySupply_.surfaceColor));
            }
            if (criteria.getSurfaceMaterialId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSurfaceMaterialId(),
                            root -> root.join(OneStudySupply_.surfaceMaterial, JoinType.LEFT).get(Material_.id)
                        )
                    );
            }
            if (criteria.getPositionId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPositionId(),
                            root -> root.join(OneStudySupply_.position, JoinType.LEFT).get(Position_.id)
                        )
                    );
            }
            if (criteria.getStrandId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getStrandId(), root -> root.join(OneStudySupply_.strand, JoinType.LEFT).get(Strand_.id))
                    );
            }
        }
        return specification;
    }
}
