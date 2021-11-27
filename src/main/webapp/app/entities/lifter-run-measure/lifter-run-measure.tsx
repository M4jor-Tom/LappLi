import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './lifter-run-measure.reducer';
import { ILifterRunMeasure } from 'app/shared/model/lifter-run-measure.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const LifterRunMeasure = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const lifterRunMeasureList = useAppSelector(state => state.lifterRunMeasure.entities);
  const loading = useAppSelector(state => state.lifterRunMeasure.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="lifter-run-measure-heading" data-cy="LifterRunMeasureHeading">
        <Translate contentKey="lappLiApp.lifterRunMeasure.home.title">Lifter Run Measures</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="lappLiApp.lifterRunMeasure.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="lappLiApp.lifterRunMeasure.home.createLabel">Create new Lifter Run Measure</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {lifterRunMeasureList && lifterRunMeasureList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="lappLiApp.lifterRunMeasure.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.lifterRunMeasure.milimeterDiameter">Milimeter Diameter</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.lifterRunMeasure.markingType">Marking Type</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.lifterRunMeasure.lifter">Lifter</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {lifterRunMeasureList.map((lifterRunMeasure, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${lifterRunMeasure.id}`} color="link" size="sm">
                      {lifterRunMeasure.id}
                    </Button>
                  </td>
                  <td>{lifterRunMeasure.milimeterDiameter}</td>
                  <td>
                    <Translate contentKey={`lappLiApp.MarkingType.${lifterRunMeasure.markingType}`} />
                  </td>
                  <td>
                    {lifterRunMeasure.lifter ? <Link to={`lifter/${lifterRunMeasure.lifter.id}`}>{lifterRunMeasure.lifter.id}</Link> : ''}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${lifterRunMeasure.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${lifterRunMeasure.id}/edit`}
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
                        to={`${match.url}/${lifterRunMeasure.id}/delete`}
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
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="lappLiApp.lifterRunMeasure.home.notFound">No Lifter Run Measures found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default LifterRunMeasure;
