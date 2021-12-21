import React, { useEffect } from 'react';
import { Link, RouteComponentProps, useParams } from 'react-router-dom';
import { Button, Row, Col, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './study.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const StudyDetail = (props: RouteComponentProps<{ study_id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.study_id));
  }, []);

  const studyEntity = useAppSelector(state => state.study.entity);
  return (
    <div>
      <div>
        <h2 data-cy="studyDetailsHeading">
          <Translate contentKey="lappLiApp.study.detail.title">Study</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{studyEntity.id}</dd>
          <dt>
            <span id="number">
              <Translate contentKey="lappLiApp.study.number">Number</Translate>
            </span>
          </dt>
          <dd>{studyEntity.number}</dd>
          <dt>
            <span id="lastEditionInstant">
              <Translate contentKey="lappLiApp.study.lastEditionInstant">Last Edition Instant</Translate>
            </span>
          </dt>
          <dd>
            {studyEntity.lastEditionInstant ? (
              <TextFormat value={studyEntity.lastEditionInstant} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="lappLiApp.study.author">Author</Translate>
          </dt>
          <dd>{studyEntity.author ? studyEntity.author.user.login : ''}</dd>
          <dd>
            <div className="table-responsive">
              {studyEntity.strandSupplies && studyEntity.strandSupplies.length > 0 ? (
                <Table>
                  <thead>
                    <tr>
                      <th>
                        <Translate contentKey="lappLiApp.strandSupply.apparitions">Apparitions</Translate>
                      </th>
                      <th>
                        <Translate contentKey="lappLiApp.strandSupply.markingType">Marking Type</Translate>
                      </th>
                      <th>
                        <Translate contentKey="lappLiApp.strandSupply.description">Description</Translate>
                      </th>
                      <th>
                        <Translate contentKey="lappLiApp.strandSupply.strand">Strand</Translate>
                      </th>
                      <th />
                    </tr>
                  </thead>
                  <tbody>
                    {studyEntity.strandSupplies.map((strandSupply, i) => (
                      <tr key={`entity-${i}`} data-cy="entityTable">
                        <td>{strandSupply.apparitions}</td>
                        <td>
                          <Translate contentKey={`lappLiApp.MarkingType.${strandSupply.markingType}`} />
                        </td>
                        <td>{strandSupply.description}</td>
                        <td>
                          {strandSupply.strand ? (
                            <Link to={`/strand/${strandSupply.strand.id}`}>{strandSupply.strand.designation}</Link>
                          ) : (
                            ''
                          )}
                        </td>
                        <td className="text-right">
                          <div className="btn-group flex-btn-group-container">
                            <Button
                              tag={Link}
                              to={`/strand-supply/${strandSupply.id}`}
                              color="info"
                              size="sm"
                              data-cy="entityDetailsButton"
                            >
                              <FontAwesomeIcon icon="eye" />{' '}
                              <span className="d-none d-md-inline">
                                <Translate contentKey="entity.action.view">View</Translate>
                              </span>
                            </Button>
                            <Button
                              tag={Link}
                              to={`/strand-supply/${strandSupply.id}/edit`}
                              color="primary"
                              size="sm"
                              data-cy="entityEditButton"
                            >
                              <FontAwesomeIcon icon="pencil-alt" />{' '}
                              <span className="d-none d-md-inline">
                                <Translate contentKey="entity.action.edit">Edit</Translate>
                              </span>
                            </Button>
                            <Button
                              tag={Link}
                              to={`/strand-supply/${strandSupply.id}/delete`}
                              color="danger"
                              size="sm"
                              data-cy="entityDeleteButton"
                            >
                              <FontAwesomeIcon icon="trash" />{' '}
                              <span className="d-none d-md-inline">
                                <Translate contentKey="entity.action.delete">Delete</Translate>
                              </span>
                            </Button>
                          </div>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </Table>
              ) : (
                <div className="alert alert-warning">
                  <Translate contentKey="lappLiApp.strandSupply.home.notFound">No Strand Supplies found</Translate>
                </div>
              )}
            </div>
          </dd>
        </dl>
        <Button tag={Link} to={'/study/' + props.match.params.study_id} replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Link to={`study-supplies/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="lappLiApp.strandSupply.home.createLabel">Create new Strand Supply</Translate>
        </Link>
      </div>
    </div>
  );
};

export default StudyDetail;
