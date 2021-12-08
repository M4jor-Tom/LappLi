package com.muller.lappli.service;

import com.muller.lappli.domain.*; // for static metamodels
import com.muller.lappli.domain.ElementKindEdition;
import com.muller.lappli.repository.ElementKindEditionRepository;
import com.muller.lappli.service.criteria.ElementKindEditionCriteria;
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
 * Service for executing complex queries for {@link ElementKindEdition} entities in the database.
 * The main input is a {@link ElementKindEditionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ElementKindEdition} or a {@link Page} of {@link ElementKindEdition} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ElementKindEditionQueryService extends QueryService<ElementKindEdition> {

    private final Logger log = LoggerFactory.getLogger(ElementKindEditionQueryService.class);

    private final ElementKindEditionRepository elementKindEditionRepository;

    public ElementKindEditionQueryService(ElementKindEditionRepository elementKindEditionRepository) {
        this.elementKindEditionRepository = elementKindEditionRepository;
    }

    /**
     * Return a {@link List} of {@link ElementKindEdition} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ElementKindEdition> findByCriteria(ElementKindEditionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ElementKindEdition> specification = createSpecification(criteria);
        return elementKindEditionRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ElementKindEdition} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ElementKindEdition> findByCriteria(ElementKindEditionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ElementKindEdition> specification = createSpecification(criteria);
        return elementKindEditionRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ElementKindEditionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ElementKindEdition> specification = createSpecification(criteria);
        return elementKindEditionRepository.count(specification);
    }

    /**
     * Function to convert {@link ElementKindEditionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ElementKindEdition> createSpecification(ElementKindEditionCriteria criteria) {
        Specification<ElementKindEdition> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ElementKindEdition_.id));
            }
            if (criteria.getEditionDateTime() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getEditionDateTime(), ElementKindEdition_.editionDateTime));
            }
            if (criteria.getNewGramPerMeterLinearMass() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getNewGramPerMeterLinearMass(), ElementKindEdition_.newGramPerMeterLinearMass)
                    );
            }
            if (criteria.getNewMilimeterDiameter() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getNewMilimeterDiameter(), ElementKindEdition_.newMilimeterDiameter)
                    );
            }
            if (criteria.getNewInsulationThickness() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getNewInsulationThickness(), ElementKindEdition_.newInsulationThickness)
                    );
            }
            if (criteria.getEditedElementKindId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEditedElementKindId(),
                            root -> root.join(ElementKindEdition_.editedElementKind, JoinType.LEFT).get(ElementKind_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
