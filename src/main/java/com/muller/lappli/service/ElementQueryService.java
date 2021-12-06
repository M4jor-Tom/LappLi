package com.muller.lappli.service;

import com.muller.lappli.domain.*; // for static metamodels
import com.muller.lappli.domain.Element;
import com.muller.lappli.repository.ElementRepository;
import com.muller.lappli.service.criteria.ElementCriteria;
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
 * Service for executing complex queries for {@link Element} entities in the database.
 * The main input is a {@link ElementCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Element} or a {@link Page} of {@link Element} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ElementQueryService extends QueryService<Element> {

    private final Logger log = LoggerFactory.getLogger(ElementQueryService.class);

    private final ElementRepository elementRepository;

    public ElementQueryService(ElementRepository elementRepository) {
        this.elementRepository = elementRepository;
    }

    /**
     * Return a {@link List} of {@link Element} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Element> findByCriteria(ElementCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Element> specification = createSpecification(criteria);
        return elementRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Element} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Element> findByCriteria(ElementCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Element> specification = createSpecification(criteria);
        return elementRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ElementCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Element> specification = createSpecification(criteria);
        return elementRepository.count(specification);
    }

    /**
     * Function to convert {@link ElementCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Element> createSpecification(ElementCriteria criteria) {
        Specification<Element> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Element_.id));
            }
            if (criteria.getNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumber(), Element_.number));
            }
            if (criteria.getColor() != null) {
                specification = specification.and(buildSpecification(criteria.getColor(), Element_.color));
            }
            if (criteria.getElementKindId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getElementKindId(),
                            root -> root.join(Element_.elementKind, JoinType.LEFT).get(ElementKind_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
