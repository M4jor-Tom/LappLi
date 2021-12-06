package com.muller.lappli.service;

import com.muller.lappli.domain.*; // for static metamodels
import com.muller.lappli.domain.Bangle;
import com.muller.lappli.repository.BangleRepository;
import com.muller.lappli.service.criteria.BangleCriteria;
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
 * Service for executing complex queries for {@link Bangle} entities in the database.
 * The main input is a {@link BangleCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Bangle} or a {@link Page} of {@link Bangle} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BangleQueryService extends QueryService<Bangle> {

    private final Logger log = LoggerFactory.getLogger(BangleQueryService.class);

    private final BangleRepository bangleRepository;

    public BangleQueryService(BangleRepository bangleRepository) {
        this.bangleRepository = bangleRepository;
    }

    /**
     * Return a {@link List} of {@link Bangle} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Bangle> findByCriteria(BangleCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Bangle> specification = createSpecification(criteria);
        return bangleRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Bangle} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Bangle> findByCriteria(BangleCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Bangle> specification = createSpecification(criteria);
        return bangleRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BangleCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Bangle> specification = createSpecification(criteria);
        return bangleRepository.count(specification);
    }

    /**
     * Function to convert {@link BangleCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Bangle> createSpecification(BangleCriteria criteria) {
        Specification<Bangle> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Bangle_.id));
            }
            if (criteria.getNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumber(), Bangle_.number));
            }
            if (criteria.getDesignation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDesignation(), Bangle_.designation));
            }
            if (criteria.getGramPerMeterLinearMass() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getGramPerMeterLinearMass(), Bangle_.gramPerMeterLinearMass));
            }
            if (criteria.getMilimeterDiameter() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMilimeterDiameter(), Bangle_.milimeterDiameter));
            }
        }
        return specification;
    }
}
