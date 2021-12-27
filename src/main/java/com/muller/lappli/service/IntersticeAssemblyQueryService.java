package com.muller.lappli.service;

import com.muller.lappli.domain.*; // for static metamodels
import com.muller.lappli.domain.IntersticeAssembly;
import com.muller.lappli.repository.IntersticeAssemblyRepository;
import com.muller.lappli.service.criteria.IntersticeAssemblyCriteria;
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
 * Service for executing complex queries for {@link IntersticeAssembly} entities in the database.
 * The main input is a {@link IntersticeAssemblyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link IntersticeAssembly} or a {@link Page} of {@link IntersticeAssembly} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class IntersticeAssemblyQueryService extends QueryService<IntersticeAssembly> {

    private final Logger log = LoggerFactory.getLogger(IntersticeAssemblyQueryService.class);

    private final IntersticeAssemblyRepository intersticeAssemblyRepository;

    public IntersticeAssemblyQueryService(IntersticeAssemblyRepository intersticeAssemblyRepository) {
        this.intersticeAssemblyRepository = intersticeAssemblyRepository;
    }

    /**
     * Return a {@link List} of {@link IntersticeAssembly} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<IntersticeAssembly> findByCriteria(IntersticeAssemblyCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<IntersticeAssembly> specification = createSpecification(criteria);
        return intersticeAssemblyRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link IntersticeAssembly} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<IntersticeAssembly> findByCriteria(IntersticeAssemblyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<IntersticeAssembly> specification = createSpecification(criteria);
        return intersticeAssemblyRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(IntersticeAssemblyCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<IntersticeAssembly> specification = createSpecification(criteria);
        return intersticeAssemblyRepository.count(specification);
    }

    /**
     * Function to convert {@link IntersticeAssemblyCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<IntersticeAssembly> createSpecification(IntersticeAssemblyCriteria criteria) {
        Specification<IntersticeAssembly> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), IntersticeAssembly_.id));
            }
            if (criteria.getProductionStep() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getProductionStep(), IntersticeAssembly_.productionStep));
            }
            if (criteria.getPositionId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPositionId(),
                            root -> root.join(IntersticeAssembly_.positions, JoinType.LEFT).get(Position_.id)
                        )
                    );
            }
            if (criteria.getStrandId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getStrandId(),
                            root -> root.join(IntersticeAssembly_.strand, JoinType.LEFT).get(Strand_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
