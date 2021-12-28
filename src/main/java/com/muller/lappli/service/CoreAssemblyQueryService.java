package com.muller.lappli.service;

import com.muller.lappli.domain.*; // for static metamodels
import com.muller.lappli.domain.CoreAssembly;
import com.muller.lappli.repository.CoreAssemblyRepository;
import com.muller.lappli.service.criteria.CoreAssemblyCriteria;
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
 * Service for executing complex queries for {@link CoreAssembly} entities in the database.
 * The main input is a {@link CoreAssemblyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CoreAssembly} or a {@link Page} of {@link CoreAssembly} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CoreAssemblyQueryService extends QueryService<CoreAssembly> {

    private final Logger log = LoggerFactory.getLogger(CoreAssemblyQueryService.class);

    private final CoreAssemblyRepository coreAssemblyRepository;

    public CoreAssemblyQueryService(CoreAssemblyRepository coreAssemblyRepository) {
        this.coreAssemblyRepository = coreAssemblyRepository;
    }

    /**
     * Return a {@link List} of {@link CoreAssembly} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CoreAssembly> findByCriteria(CoreAssemblyCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CoreAssembly> specification = createSpecification(criteria);
        return coreAssemblyRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CoreAssembly} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CoreAssembly> findByCriteria(CoreAssemblyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CoreAssembly> specification = createSpecification(criteria);
        return coreAssemblyRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CoreAssemblyCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CoreAssembly> specification = createSpecification(criteria);
        return coreAssemblyRepository.count(specification);
    }

    /**
     * Function to convert {@link CoreAssemblyCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CoreAssembly> createSpecification(CoreAssemblyCriteria criteria) {
        Specification<CoreAssembly> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CoreAssembly_.id));
            }
            if (criteria.getOperationLayer() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOperationLayer(), CoreAssembly_.operationLayer));
            }
            if (criteria.getProductionStep() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProductionStep(), CoreAssembly_.productionStep));
            }
            if (criteria.getAssemblyStep() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAssemblyStep(), CoreAssembly_.assemblyStep));
            }
            if (criteria.getAssemblyMean() != null) {
                specification = specification.and(buildSpecification(criteria.getAssemblyMean(), CoreAssembly_.assemblyMean));
            }
            if (criteria.getPositionsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPositionsId(),
                            root -> root.join(CoreAssembly_.positions, JoinType.LEFT).get(Position_.id)
                        )
                    );
            }
            if (criteria.getStrandId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getStrandId(), root -> root.join(CoreAssembly_.strand, JoinType.LEFT).get(Strand_.id))
                    );
            }
        }
        return specification;
    }
}
