import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './lifter.reducer';
import { ILifter } from 'app/shared/model/lifter.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Lifter = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const lifterList = useAppSelector(state => state.lifter.entities);
  const loading = useAppSelector(state => state.lifter.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="lifter-heading" data-cy="LifterHeading">
        <Translate contentKey="lappLiApp.lifter.home.title">Lifters</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="lappLiApp.lifter.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="lappLiApp.lifter.home.createLabel">Create new Lifter</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {lifterList && lifterList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="lappLiApp.lifter.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.lifter.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.lifter.minimumMilimeterDiameter">Minimum Milimeter Diameter</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.lifter.maximumMilimeterDiameter">Maximum Milimeter Diameter</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.lifter.supportsSpirallyColoredMarkingType">
                    Supports Spirally Colored Marking Type
                  </Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.lifter.supportsLongitudinallyColoredMarkingType">
                    Supports Longitudinally Colored Marking Type
                  </Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.lifter.supportsNumberedMarkingType">Supports Numbered Marking Type</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {lifterList.map((lifter, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${lifter.id}`} color="link" size="sm">
                      {lifter.id}
                    </Button>
                  </td>
                  <td>{lifter.name}</td>
                  <td>{lifter.minimumMilimeterDiameter}</td>
                  <td>{lifter.maximumMilimeterDiameter}</td>
                  <td>{lifter.supportsSpirallyColoredMarkingType ? 'true' : 'false'}</td>
                  <td>{lifter.supportsLongitudinallyColoredMarkingType ? 'true' : 'false'}</td>
                  <td>{lifter.supportsNumberedMarkingType ? 'true' : 'false'}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${lifter.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${lifter.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${lifter.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="lappLiApp.lifter.home.notFound">No Lifters found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Lifter;
