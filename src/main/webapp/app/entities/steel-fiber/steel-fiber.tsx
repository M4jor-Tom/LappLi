import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './steel-fiber.reducer';
import { ISteelFiber } from 'app/shared/model/steel-fiber.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const SteelFiber = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const steelFiberList = useAppSelector(state => state.steelFiber.entities);
  const loading = useAppSelector(state => state.steelFiber.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="steel-fiber-heading" data-cy="SteelFiberHeading">
        <Translate contentKey="lappLiApp.steelFiber.home.title">Steel Fibers</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="lappLiApp.steelFiber.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="lappLiApp.steelFiber.home.createLabel">Create new Steel Fiber</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {steelFiberList && steelFiberList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="lappLiApp.steelFiber.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.steelFiber.number">Number</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.steelFiber.designation">Designation</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.steelFiber.metalFiberKind">Metal Fiber Kind</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.steelFiber.milimeterDiameter">Milimeter Diameter</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {steelFiberList.map((steelFiber, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${steelFiber.id}`} color="link" size="sm">
                      {steelFiber.id}
                    </Button>
                  </td>
                  <td>{steelFiber.number}</td>
                  <td>{steelFiber.designation}</td>
                  <td>
                    <Translate contentKey={`lappLiApp.MetalFiberKind.${steelFiber.metalFiberKind}`} />
                  </td>
                  <td>{steelFiber.milimeterDiameter}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${steelFiber.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${steelFiber.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${steelFiber.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="lappLiApp.steelFiber.home.notFound">No Steel Fibers found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default SteelFiber;
