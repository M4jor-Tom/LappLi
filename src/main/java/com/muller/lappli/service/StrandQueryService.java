package com.muller.lappli.service;

import com.muller.lappli.domain.*; // for static metamodels
import com.muller.lappli.domain.Strand;
import com.muller.lappli.repository.StrandRepository;
import com.muller.lappli.service.criteria.StrandCriteria;
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
 * Service for executing complex queries for {@link Strand} entities in the database.
 * The main input is a {@link StrandCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Strand} or a {@link Page} of {@link Strand} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StrandQueryService extends QueryService<Strand> {

    private final Logger log = LoggerFactory.getLogger(StrandQueryService.class);

    private final StrandRepository strandRepository;

    public StrandQueryService(StrandRepository strandRepository) {
        this.strandRepository = strandRepository;
    }

    /**
     * Return a {@link List} of {@link Strand} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Strand> findByCriteria(StrandCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Strand> specification = createSpecification(criteria);
        return strandRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Strand} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Strand> findByCriteria(StrandCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Strand> specification = createSpecification(criteria);
        return strandRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StrandCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Strand> specification = createSpecification(criteria);
        return strandRepository.count(specification);
    }

    /**
     * Function to convert {@link StrandCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Strand> createSpecification(StrandCriteria criteria) {
        Specification<Strand> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Strand_.id));
            }
            if (criteria.getDesignation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDesignation(), Strand_.designation));
            }
            if (criteria.getElementSuppliesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getElementSuppliesId(),
                            root -> root.join(Strand_.elementSupplies, JoinType.LEFT).get(ElementSupply_.id)
                        )
                    );
            }
            if (criteria.getBangleSuppliesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getBangleSuppliesId(),
                            root -> root.join(Strand_.bangleSupplies, JoinType.LEFT).get(BangleSupply_.id)
                        )
                    );
            }
            if (criteria.getCustomComponentSuppliesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustomComponentSuppliesId(),
                            root -> root.join(Strand_.customComponentSupplies, JoinType.LEFT).get(CustomComponentSupply_.id)
                        )
                    );
            }
            if (criteria.getOneStudySuppliesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOneStudySuppliesId(),
                            root -> root.join(Strand_.oneStudySupplies, JoinType.LEFT).get(OneStudySupply_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
