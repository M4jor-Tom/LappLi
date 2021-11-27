package com.muller.lappli.service;

import com.muller.lappli.domain.*; // for static metamodels
import com.muller.lappli.domain.Lifter;
import com.muller.lappli.repository.LifterRepository;
import com.muller.lappli.service.criteria.LifterCriteria;
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
 * Service for executing complex queries for {@link Lifter} entities in the database.
 * The main input is a {@link LifterCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Lifter} or a {@link Page} of {@link Lifter} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LifterQueryService extends QueryService<Lifter> {

    private final Logger log = LoggerFactory.getLogger(LifterQueryService.class);

    private final LifterRepository lifterRepository;

    public LifterQueryService(LifterRepository lifterRepository) {
        this.lifterRepository = lifterRepository;
    }

    /**
     * Return a {@link List} of {@link Lifter} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Lifter> findByCriteria(LifterCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Lifter> specification = createSpecification(criteria);
        return lifterRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Lifter} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Lifter> findByCriteria(LifterCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Lifter> specification = createSpecification(criteria);
        return lifterRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LifterCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Lifter> specification = createSpecification(criteria);
        return lifterRepository.count(specification);
    }

    /**
     * Function to convert {@link LifterCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Lifter> createSpecification(LifterCriteria criteria) {
        Specification<Lifter> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Lifter_.id));
            }
            if (criteria.getIndex() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIndex(), Lifter_.index));
            }
            if (criteria.getMinimumMilimeterDiameter() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getMinimumMilimeterDiameter(), Lifter_.minimumMilimeterDiameter));
            }
            if (criteria.getMaximumMilimeterDiameter() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getMaximumMilimeterDiameter(), Lifter_.maximumMilimeterDiameter));
            }
            if (criteria.getSupportsSpirallyColoredMarkingType() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getSupportsSpirallyColoredMarkingType(), Lifter_.supportsSpirallyColoredMarkingType)
                    );
            }
            if (criteria.getSupportsLongitudinallyColoredMarkingType() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSupportsLongitudinallyColoredMarkingType(),
                            Lifter_.supportsLongitudinallyColoredMarkingType
                        )
                    );
            }
            if (criteria.getSupportsNumberedMarkingType() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getSupportsNumberedMarkingType(), Lifter_.supportsNumberedMarkingType));
            }
        }
        return specification;
    }
}
