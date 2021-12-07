package com.muller.lappli.service;

import com.muller.lappli.domain.*; // for static metamodels
import com.muller.lappli.domain.Material;
import com.muller.lappli.repository.MaterialRepository;
import com.muller.lappli.service.criteria.MaterialCriteria;
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
 * Service for executing complex queries for {@link Material} entities in the database.
 * The main input is a {@link MaterialCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Material} or a {@link Page} of {@link Material} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MaterialQueryService extends QueryService<Material> {

    private final Logger log = LoggerFactory.getLogger(MaterialQueryService.class);

    private final MaterialRepository materialRepository;

    public MaterialQueryService(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    /**
     * Return a {@link List} of {@link Material} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Material> findByCriteria(MaterialCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Material> specification = createSpecification(criteria);
        return materialRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Material} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Material> findByCriteria(MaterialCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Material> specification = createSpecification(criteria);
        return materialRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MaterialCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Material> specification = createSpecification(criteria);
        return materialRepository.count(specification);
    }

    /**
     * Function to convert {@link MaterialCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Material> createSpecification(MaterialCriteria criteria) {
        Specification<Material> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Material_.id));
            }
            if (criteria.getNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumber(), Material_.number));
            }
            if (criteria.getDesignation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDesignation(), Material_.designation));
            }
            if (criteria.getMaterialMarkingStatisticId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getMaterialMarkingStatisticId(),
                            root -> root.join(Material_.materialMarkingStatistics, JoinType.LEFT).get(MaterialMarkingStatistic_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
