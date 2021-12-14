import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './bangle.reducer';
import { IBangle } from 'app/shared/model/bangle.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Bangle = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const bangleList = useAppSelector(state => state.bangle.entities);
  const loading = useAppSelector(state => state.bangle.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="bangle-heading" data-cy="BangleHeading">
        <Translate contentKey="lappLiApp.bangle.home.title">Bangles</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="lappLiApp.bangle.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="lappLiApp.bangle.home.createLabel">Create new Bangle</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {bangleList && bangleList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="lappLiApp.bangle.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.bangle.number">Number</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.bangle.designation">Designation</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.bangle.gramPerMeterLinearMass">Gram Per Meter Linear Mass</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.bangle.milimeterDiameter">Milimeter Diameter</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.bangle.material">Material</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {bangleList.map((bangle, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${bangle.id}`} color="link" size="sm">
                      {bangle.id}
                    </Button>
                  </td>
                  <td>{bangle.number}</td>
                  <td>{bangle.designation}</td>
                  <td>{bangle.gramPerMeterLinearMass}</td>
                  <td>{bangle.milimeterDiameter}</td>
                  <td>{bangle.material ? <Link to={`material/${bangle.material.id}`}>{bangle.material.designation}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${bangle.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${bangle.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${bangle.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="lappLiApp.bangle.home.notFound">No Bangles found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Bangle;
