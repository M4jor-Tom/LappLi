package com.muller.lappli.service;

import com.muller.lappli.domain.*; // for static metamodels
import com.muller.lappli.domain.LifterRunMeasure;
import com.muller.lappli.repository.LifterRunMeasureRepository;
import com.muller.lappli.service.criteria.LifterRunMeasureCriteria;
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
 * Service for executing complex queries for {@link LifterRunMeasure} entities in the database.
 * The main input is a {@link LifterRunMeasureCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LifterRunMeasure} or a {@link Page} of {@link LifterRunMeasure} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LifterRunMeasureQueryService extends QueryService<LifterRunMeasure> {

    private final Logger log = LoggerFactory.getLogger(LifterRunMeasureQueryService.class);

    private final LifterRunMeasureRepository lifterRunMeasureRepository;

    public LifterRunMeasureQueryService(LifterRunMeasureRepository lifterRunMeasureRepository) {
        this.lifterRunMeasureRepository = lifterRunMeasureRepository;
    }

    /**
     * Return a {@link List} of {@link LifterRunMeasure} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LifterRunMeasure> findByCriteria(LifterRunMeasureCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LifterRunMeasure> specification = createSpecification(criteria);
        return lifterRunMeasureRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link LifterRunMeasure} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LifterRunMeasure> findByCriteria(LifterRunMeasureCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LifterRunMeasure> specification = createSpecification(criteria);
        return lifterRunMeasureRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LifterRunMeasureCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LifterRunMeasure> specification = createSpecification(criteria);
        return lifterRunMeasureRepository.count(specification);
    }

    /**
     * Function to convert {@link LifterRunMeasureCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LifterRunMeasure> createSpecification(LifterRunMeasureCriteria criteria) {
        Specification<LifterRunMeasure> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LifterRunMeasure_.id));
            }
            if (criteria.getMilimeterDiameter() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getMilimeterDiameter(), LifterRunMeasure_.milimeterDiameter));
            }
            if (criteria.getMeterPerSecondSpeed() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getMeterPerSecondSpeed(), LifterRunMeasure_.meterPerSecondSpeed));
            }
            if (criteria.getMarkingType() != null) {
                specification = specification.and(buildSpecification(criteria.getMarkingType(), LifterRunMeasure_.markingType));
            }
            if (criteria.getMarkingTechnique() != null) {
                specification = specification.and(buildSpecification(criteria.getMarkingTechnique(), LifterRunMeasure_.markingTechnique));
            }
            if (criteria.getFormatedHourPreparationTime() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getFormatedHourPreparationTime(), LifterRunMeasure_.formatedHourPreparationTime)
                    );
            }
            if (criteria.getLifterId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLifterId(),
                            root -> root.join(LifterRunMeasure_.lifter, JoinType.LEFT).get(Lifter_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
