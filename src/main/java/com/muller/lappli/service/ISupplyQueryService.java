package com.muller.lappli.service;

import com.muller.lappli.domain.*; // for static metamodels
import com.muller.lappli.domain.ISupply;
import com.muller.lappli.repository.ISupplyRepository;
import com.muller.lappli.service.criteria.ISupplyCriteria;
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
 * Service for executing complex queries for {@link ISupply} entities in the database.
 * The main input is a {@link ISupplyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ISupply} or a {@link Page} of {@link ISupply} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ISupplyQueryService extends QueryService<ISupply> {

    private final Logger log = LoggerFactory.getLogger(ISupplyQueryService.class);

    private final ISupplyRepository iSupplyRepository;

    public ISupplyQueryService(ISupplyRepository iSupplyRepository) {
        this.iSupplyRepository = iSupplyRepository;
    }

    /**
     * Return a {@link List} of {@link ISupply} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ISupply> findByCriteria(ISupplyCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ISupply> specification = createSpecification(criteria);
        return iSupplyRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ISupply} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ISupply> findByCriteria(ISupplyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ISupply> specification = createSpecification(criteria);
        return iSupplyRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ISupplyCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ISupply> specification = createSpecification(criteria);
        return iSupplyRepository.count(specification);
    }

    /**
     * Function to convert {@link ISupplyCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ISupply> createSpecification(ISupplyCriteria criteria) {
        Specification<ISupply> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            /*if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ISupply_.id));
            }
            if (criteria.getApparitions() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getApparitions(), ISupply_.apparitions));
            }
            if (criteria.getMilimeterDiameter() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMilimeterDiameter(), ISupply_.milimeterDiameter));
            }
            if (criteria.getGramPerMeterLinearMass() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getGramPerMeterLinearMass(), ISupply_.gramPerMeterLinearMass));
            }
            if (criteria.getStrandId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getStrandId(), root -> root.join(ISupply_.strand, JoinType.LEFT).get(Strand_.id))
                    );
            }*/
        }
        return specification;
    }
}
