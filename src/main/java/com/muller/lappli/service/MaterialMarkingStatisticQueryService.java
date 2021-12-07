package com.muller.lappli.service;

import com.muller.lappli.domain.*; // for static metamodels
import com.muller.lappli.domain.MaterialMarkingStatistic;
import com.muller.lappli.repository.MaterialMarkingStatisticRepository;
import com.muller.lappli.service.criteria.MaterialMarkingStatisticCriteria;
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
 * Service for executing complex queries for {@link MaterialMarkingStatistic} entities in the database.
 * The main input is a {@link MaterialMarkingStatisticCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MaterialMarkingStatistic} or a {@link Page} of {@link MaterialMarkingStatistic} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MaterialMarkingStatisticQueryService extends QueryService<MaterialMarkingStatistic> {

    private final Logger log = LoggerFactory.getLogger(MaterialMarkingStatisticQueryService.class);

    private final MaterialMarkingStatisticRepository materialMarkingStatisticRepository;

    public MaterialMarkingStatisticQueryService(MaterialMarkingStatisticRepository materialMarkingStatisticRepository) {
        this.materialMarkingStatisticRepository = materialMarkingStatisticRepository;
    }

    /**
     * Return a {@link List} of {@link MaterialMarkingStatistic} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MaterialMarkingStatistic> findByCriteria(MaterialMarkingStatisticCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MaterialMarkingStatistic> specification = createSpecification(criteria);
        return materialMarkingStatisticRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link MaterialMarkingStatistic} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MaterialMarkingStatistic> findByCriteria(MaterialMarkingStatisticCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MaterialMarkingStatistic> specification = createSpecification(criteria);
        return materialMarkingStatisticRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MaterialMarkingStatisticCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MaterialMarkingStatistic> specification = createSpecification(criteria);
        return materialMarkingStatisticRepository.count(specification);
    }

    /**
     * Function to convert {@link MaterialMarkingStatisticCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MaterialMarkingStatistic> createSpecification(MaterialMarkingStatisticCriteria criteria) {
        Specification<MaterialMarkingStatistic> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MaterialMarkingStatistic_.id));
            }
            if (criteria.getMarkingType() != null) {
                specification = specification.and(buildSpecification(criteria.getMarkingType(), MaterialMarkingStatistic_.markingType));
            }
            if (criteria.getMarkingTechnique() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getMarkingTechnique(), MaterialMarkingStatistic_.markingTechnique));
            }
            if (criteria.getMeterPerHourSpeed() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getMeterPerHourSpeed(), MaterialMarkingStatistic_.meterPerHourSpeed)
                    );
            }
            if (criteria.getMaterialId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getMaterialId(),
                            root -> root.join(MaterialMarkingStatistic_.material, JoinType.LEFT).get(Material_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
