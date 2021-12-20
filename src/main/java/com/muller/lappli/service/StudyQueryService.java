package com.muller.lappli.service;

import com.muller.lappli.domain.*; // for static metamodels
import com.muller.lappli.domain.Study;
import com.muller.lappli.repository.StudyRepository;
import com.muller.lappli.service.criteria.StudyCriteria;
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
 * Service for executing complex queries for {@link Study} entities in the database.
 * The main input is a {@link StudyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Study} or a {@link Page} of {@link Study} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StudyQueryService extends QueryService<Study> {

    private final Logger log = LoggerFactory.getLogger(StudyQueryService.class);

    private final StudyRepository studyRepository;

    public StudyQueryService(StudyRepository studyRepository) {
        this.studyRepository = studyRepository;
    }

    /**
     * Return a {@link List} of {@link Study} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Study> findByCriteria(StudyCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Study> specification = createSpecification(criteria);
        return studyRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Study} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Study> findByCriteria(StudyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Study> specification = createSpecification(criteria);
        return studyRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StudyCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Study> specification = createSpecification(criteria);
        return studyRepository.count(specification);
    }

    /**
     * Function to convert {@link StudyCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Study> createSpecification(StudyCriteria criteria) {
        Specification<Study> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Study_.id));
            }
            if (criteria.getNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumber(), Study_.number));
            }
            if (criteria.getCreationInstant() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreationInstant(), Study_.creationInstant));
            }
            if (criteria.getStrandSuppliesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getStrandSuppliesId(),
                            root -> root.join(Study_.strandSupplies, JoinType.LEFT).get(StrandSupply_.id)
                        )
                    );
            }
            if (criteria.getAuthorId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getAuthorId(), root -> root.join(Study_.author, JoinType.LEFT).get(UserData_.id))
                    );
            }
        }
        return specification;
    }
}
