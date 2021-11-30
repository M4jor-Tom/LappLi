package com.muller.lappli.service;

import com.muller.lappli.domain.*; // for static metamodels
import com.muller.lappli.domain.BangleSupply;
import com.muller.lappli.repository.BangleSupplyRepository;
import com.muller.lappli.service.criteria.BangleSupplyCriteria;
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
 * Service for executing complex queries for {@link BangleSupply} entities in the database.
 * The main input is a {@link BangleSupplyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BangleSupply} or a {@link Page} of {@link BangleSupply} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BangleSupplyQueryService extends QueryService<BangleSupply> {

    private final Logger log = LoggerFactory.getLogger(BangleSupplyQueryService.class);

    private final BangleSupplyRepository bangleSupplyRepository;

    public BangleSupplyQueryService(BangleSupplyRepository bangleSupplyRepository) {
        this.bangleSupplyRepository = bangleSupplyRepository;
    }

    /**
     * Return a {@link List} of {@link BangleSupply} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BangleSupply> findByCriteria(BangleSupplyCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BangleSupply> specification = createSpecification(criteria);
        return bangleSupplyRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link BangleSupply} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BangleSupply> findByCriteria(BangleSupplyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BangleSupply> specification = createSpecification(criteria);
        return bangleSupplyRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BangleSupplyCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BangleSupply> specification = createSpecification(criteria);
        return bangleSupplyRepository.count(specification);
    }

    /**
     * Function to convert {@link BangleSupplyCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<BangleSupply> createSpecification(BangleSupplyCriteria criteria) {
        Specification<BangleSupply> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), BangleSupply_.id));
            }
            if (criteria.getApparitions() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getApparitions(), BangleSupply_.apparitions));
            }
            if (criteria.getBangleId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getBangleId(), root -> root.join(BangleSupply_.bangle, JoinType.LEFT).get(Bangle_.id))
                    );
            }
        }
        return specification;
    }
}
