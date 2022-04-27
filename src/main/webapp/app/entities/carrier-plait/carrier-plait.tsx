import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './carrier-plait.reducer';
import { ICarrierPlait } from 'app/shared/model/carrier-plait.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CarrierPlait = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const carrierPlaitList = useAppSelector(state => state.carrierPlait.entities);
  const loading = useAppSelector(state => state.carrierPlait.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="carrier-plait-heading" data-cy="CarrierPlaitHeading">
        <Translate contentKey="lappLiApp.carrierPlait.home.title">Carrier Plaits</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="lappLiApp.carrierPlait.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="lappLiApp.carrierPlait.home.createLabel">Create new Carrier Plait</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {carrierPlaitList && carrierPlaitList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="lappLiApp.carrierPlait.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.carrierPlait.operationLayer">Operation Layer</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.carrierPlait.minimumDecaNewtonLoad">Minimum Deca Newton Load</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.carrierPlait.degreeAssemblyAngle">Degree Assembly Angle</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.carrierPlait.forcedEndPerBobinsCount">Forced End Per Bobins Count</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.carrierPlait.carrierPlaitFiber">Carrier Plait Fiber</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.carrierPlait.ownerStrandSupply">Owner Strand Supply</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {carrierPlaitList.map((carrierPlait, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${carrierPlait.id}`} color="link" size="sm">
                      {carrierPlait.id}
                    </Button>
                  </td>
                  <td>{carrierPlait.operationLayer}</td>
                  <td>{carrierPlait.minimumDecaNewtonLoad}</td>
                  <td>{carrierPlait.degreeAssemblyAngle}</td>
                  <td>{carrierPlait.forcedEndPerBobinsCount}</td>
                  <td>
                    {carrierPlait.carrierPlaitFiber ? (
                      <Link to={`carrier-plait-fiber/${carrierPlait.carrierPlaitFiber.id}`}>
                        {carrierPlait.carrierPlaitFiber.designation}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {carrierPlait.ownerStrandSupply ? (
                      <Link to={`strand-supply/${carrierPlait.ownerStrandSupply.id}`}>{carrierPlait.ownerStrandSupply.designation}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${carrierPlait.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${carrierPlait.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${carrierPlait.id}/delete`}
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
              <Translate contentKey="lappLiApp.carrierPlait.home.notFound">No Carrier Plaits found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default CarrierPlait;
