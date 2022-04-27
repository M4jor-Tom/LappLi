import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './carrier-plait-fiber.reducer';
import { ICarrierPlaitFiber } from 'app/shared/model/carrier-plait-fiber.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CarrierPlaitFiber = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const carrierPlaitFiberList = useAppSelector(state => state.carrierPlaitFiber.entities);
  const loading = useAppSelector(state => state.carrierPlaitFiber.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="carrier-plait-fiber-heading" data-cy="CarrierPlaitFiberHeading">
        <Translate contentKey="lappLiApp.carrierPlaitFiber.home.title">Carrier Plait Fibers</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="lappLiApp.carrierPlaitFiber.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="lappLiApp.carrierPlaitFiber.home.createLabel">Create new Carrier Plait Fiber</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {carrierPlaitFiberList && carrierPlaitFiberList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="lappLiApp.carrierPlaitFiber.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.carrierPlaitFiber.number">Number</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.carrierPlaitFiber.designation">Designation</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.carrierPlaitFiber.squareMilimeterSection">Square Milimeter Section</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.carrierPlaitFiber.decaNewtonLoad">Deca Newton Load</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {carrierPlaitFiberList.map((carrierPlaitFiber, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${carrierPlaitFiber.id}`} color="link" size="sm">
                      {carrierPlaitFiber.id}
                    </Button>
                  </td>
                  <td>{carrierPlaitFiber.number}</td>
                  <td>{carrierPlaitFiber.designation}</td>
                  <td>{carrierPlaitFiber.squareMilimeterSection}</td>
                  <td>{carrierPlaitFiber.decaNewtonLoad}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${carrierPlaitFiber.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${carrierPlaitFiber.id}/edit`}
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
                        to={`${match.url}/${carrierPlaitFiber.id}/delete`}
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
              <Translate contentKey="lappLiApp.carrierPlaitFiber.home.notFound">No Carrier Plait Fibers found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default CarrierPlaitFiber;
