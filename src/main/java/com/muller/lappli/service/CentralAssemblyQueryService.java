package com.muller.lappli.service;

import com.muller.lappli.domain.*; // for static metamodels
import com.muller.lappli.domain.CentralAssembly;
import com.muller.lappli.repository.CentralAssemblyRepository;
import com.muller.lappli.service.criteria.CentralAssemblyCriteria;
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
 * Service for executing complex queries for {@link CentralAssembly} entities in the database.
 * The main input is a {@link CentralAssemblyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CentralAssembly} or a {@link Page} of {@link CentralAssembly} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CentralAssemblyQueryService extends QueryService<CentralAssembly> {

    private final Logger log = LoggerFactory.getLogger(CentralAssemblyQueryService.class);

    private final CentralAssemblyRepository centralAssemblyRepository;

    public CentralAssemblyQueryService(CentralAssemblyRepository centralAssemblyRepository) {
        this.centralAssemblyRepository = centralAssemblyRepository;
    }

    /**
     * Return a {@link List} of {@link CentralAssembly} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CentralAssembly> findByCriteria(CentralAssemblyCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CentralAssembly> specification = createSpecification(criteria);
        return centralAssemblyRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CentralAssembly} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CentralAssembly> findByCriteria(CentralAssemblyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CentralAssembly> specification = createSpecification(criteria);
        return centralAssemblyRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CentralAssemblyCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CentralAssembly> specification = createSpecification(criteria);
        return centralAssemblyRepository.count(specification);
    }

    /**
     * Function to convert {@link CentralAssemblyCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CentralAssembly> createSpecification(CentralAssemblyCriteria criteria) {
        Specification<CentralAssembly> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CentralAssembly_.id));
            }
            if (criteria.getProductionStep() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProductionStep(), CentralAssembly_.productionStep));
            }
            if (criteria.getStrandId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getStrandId(),
                            root -> root.join(CentralAssembly_.strand, JoinType.LEFT).get(Strand_.id)
                        )
                    );
            }
            if (criteria.getPositionId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPositionId(),
                            root -> root.join(CentralAssembly_.position, JoinType.LEFT).get(Position_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
