import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './metal-fiber.reducer';
import { IMetalFiber } from 'app/shared/model/metal-fiber.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const MetalFiber = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const metalFiberList = useAppSelector(state => state.metalFiber.entities);
  const loading = useAppSelector(state => state.metalFiber.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="metal-fiber-heading" data-cy="MetalFiberHeading">
        <Translate contentKey="lappLiApp.metalFiber.home.title">Metal Fibers</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="lappLiApp.metalFiber.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="lappLiApp.metalFiber.home.createLabel">Create new Metal Fiber</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {metalFiberList && metalFiberList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="lappLiApp.metalFiber.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.metalFiber.number">Number</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.metalFiber.designation">Designation</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.metalFiber.metalFiberKind">Metal Fiber Kind</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.metalFiber.milimeterDiameter">Milimeter Diameter</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {metalFiberList.map((metalFiber, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${metalFiber.id}`} color="link" size="sm">
                      {metalFiber.id}
                    </Button>
                  </td>
                  <td>{metalFiber.number}</td>
                  <td>{metalFiber.designation}</td>
                  <td>
                    <Translate contentKey={`lappLiApp.MetalFiberKind.${metalFiber.metalFiberKind}`} />
                  </td>
                  <td>{metalFiber.milimeterDiameter}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${metalFiber.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${metalFiber.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${metalFiber.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="lappLiApp.metalFiber.home.notFound">No Metal Fibers found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default MetalFiber;
