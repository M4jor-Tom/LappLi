package com.muller.lappli.service;

import com.muller.lappli.domain.*; // for static metamodels
import com.muller.lappli.domain.Position;
import com.muller.lappli.repository.PositionRepository;
import com.muller.lappli.service.criteria.PositionCriteria;
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
 * Service for executing complex queries for {@link Position} entities in the database.
 * The main input is a {@link PositionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Position} or a {@link Page} of {@link Position} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PositionQueryService extends QueryService<Position> {

    private final Logger log = LoggerFactory.getLogger(PositionQueryService.class);

    private final PositionRepository positionRepository;

    public PositionQueryService(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    /**
     * Return a {@link List} of {@link Position} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Position> findByCriteria(PositionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Position> specification = createSpecification(criteria);
        return positionRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Position} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Position> findByCriteria(PositionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Position> specification = createSpecification(criteria);
        return positionRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PositionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Position> specification = createSpecification(criteria);
        return positionRepository.count(specification);
    }

    /**
     * Function to convert {@link PositionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Position> createSpecification(PositionCriteria criteria) {
        Specification<Position> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Position_.id));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValue(), Position_.value));
            }
            if (criteria.getElementSupplyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getElementSupplyId(),
                            root -> root.join(Position_.elementSupply, JoinType.LEFT).get(ElementSupply_.id)
                        )
                    );
            }
            if (criteria.getBangleSupplyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getBangleSupplyId(),
                            root -> root.join(Position_.bangleSupply, JoinType.LEFT).get(BangleSupply_.id)
                        )
                    );
            }
            if (criteria.getCustomComponentSupplyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustomComponentSupplyId(),
                            root -> root.join(Position_.customComponentSupply, JoinType.LEFT).get(CustomComponentSupply_.id)
                        )
                    );
            }
            if (criteria.getOneStudySupplyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOneStudySupplyId(),
                            root -> root.join(Position_.oneStudySupply, JoinType.LEFT).get(OneStudySupply_.id)
                        )
                    );
            }
            if (criteria.getOwnerCentralAssemblyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOwnerCentralAssemblyId(),
                            root -> root.join(Position_.ownerCentralAssembly, JoinType.LEFT).get(CentralAssembly_.id)
                        )
                    );
            }
            if (criteria.getOwnerCoreAssemblyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOwnerCoreAssemblyId(),
                            root -> root.join(Position_.ownerCoreAssembly, JoinType.LEFT).get(CoreAssembly_.id)
                        )
                    );
            }
            if (criteria.getOwnerIntersticeAssemblyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOwnerIntersticeAssemblyId(),
                            root -> root.join(Position_.ownerIntersticeAssembly, JoinType.LEFT).get(IntersticeAssembly_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
