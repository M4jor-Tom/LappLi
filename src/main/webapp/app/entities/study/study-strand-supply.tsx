import React, { useEffect } from 'react';
import { Link, RouteComponentProps, useParams } from 'react-router-dom';
import { Button, Row, Col, Table } from 'reactstrap';
import { Translate, TextFormat, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities, getEntity } from './study.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { createEntity as createStrand, reset } from '../strand/strand.reducer';
import { toNumber } from 'lodash';
import { AssemblyMean } from 'app/shared/model/enumerations/assembly-mean.model';
import { IStrand } from 'app/shared/model/strand.model';

export const StudyStrandSupply = (props: RouteComponentProps<{ study_id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.study_id));
  }, []);

  const studyEntity = useAppSelector(state => state.study.entity);

  //  STRAND CREATION ZONE -- START

  const strandCreationSuccess = useAppSelector(state => state.strand.updateSuccess);
  const strandEntity = useAppSelector(state => state.strand.entity);
  const strandCreating = useAppSelector(state => state.strand.updating);
  //  const studies = useAppSelector(state => state.study.entities);

  useEffect(() => {
    dispatch(reset());

    dispatch(getEntities({}));
  }, []);

  useEffect(() => {
    if (strandCreationSuccess) {
      props.history.go(0);
    }
  }, [strandCreationSuccess]);

  const saveStrandEntity = values => {
    const entity: IStrand = {
      ...strandEntity,
      ...values,
      assemblyMean: AssemblyMean.STRAIGHT,
      diameterAssemblyStep: 0.0,
      futureStudy: studyEntity, //  studies.find(it => it.id.toString() === props.match.params.study_id),
    };

    dispatch(createStrand(entity));
  };

  //  STRAND CREATION ZONE -- END
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
            <h5>
              <Translate contentKey="lappLiApp.strandSupply.home.title">StrandSupplies</Translate>
            </h5>
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
                            <Link to={`${props.match.url}/strand/${strandSupply.strand.id}`}>{strandSupply.strand.id}</Link>
                          ) : (
                            ''
                          )}
                        </td>
                        <td className="text-right">
                          <div className="btn-group flex-btn-group-container">
                            <Button
                              tag={Link}
                              to={`${props.match.url}/strand-supply/${strandSupply.id}/operation`}
                              color="primary"
                              size="sm"
                              data-cy="entityEditButton"
                            >
                              <FontAwesomeIcon icon="pencil-alt" />{' '}
                              <span className="d-none d-md-inline">
                                <Translate contentKey="lappLiApp.strandSupply.operations">Operations</Translate>
                              </span>
                            </Button>
                            &nbsp;
                            <Button
                              tag={Link}
                              to={`${props.match.url}/strand-supply/${strandSupply.id}/delete`}
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
            <h5>
              <Translate contentKey="lappLiApp.strand.home.title">Strands</Translate>
            </h5>
            <div className="table-responsive">
              {studyEntity.strands && studyEntity.strands.length > 0 ? (
                <Table>
                  <tbody>
                    {studyEntity.strands.map((strand, i) => (
                      <tr key={`entity-${i}`} data-cy="entityTable">
                        <td>{translate('lappLiApp.strand.detail.title') + '#' + strand.id}</td>
                        <td className="text-right">
                          <div className="btn-group flex-btn-group-container">
                            <Button
                              tag={Link}
                              to={`${props.match.url}/strand/${strand.id}/supply`}
                              color="primary"
                              size="sm"
                              data-cy="entityEditButton"
                            >
                              <FontAwesomeIcon icon="pencil-alt" />{' '}
                              <span className="d-none d-md-inline">
                                <Translate contentKey="lappLiApp.strand.subSupplies">Sub Supplies</Translate>
                              </span>
                            </Button>
                            &nbsp;
                            <Button
                              tag={Link}
                              to={`${props.match.url}/strand/${strand.id}/delete`}
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
                  <Translate contentKey="lappLiApp.strand.home.notFound">No Strands found</Translate>
                </div>
              )}
            </div>
          </dd>
        </dl>
        <ValidatedForm onSubmit={saveStrandEntity} defaultValues={{ futureStudy: toNumber(props.match.params.study_id) }}>
          <Button tag={Link} to={'/study/' + props.match.params.study_id} replace color="info" data-cy="entityDetailsBackButton">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={strandCreating}>
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="lappLiApp.strand.home.createLabel">Create new Strand</Translate>
          </Button>
        </ValidatedForm>
      </div>
    </div>
  );
};

export default StudyStrandSupply;
