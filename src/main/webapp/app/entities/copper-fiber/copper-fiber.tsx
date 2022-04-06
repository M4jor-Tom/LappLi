import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './copper-fiber.reducer';
import { ICopperFiber } from 'app/shared/model/copper-fiber.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CopperFiber = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const copperFiberList = useAppSelector(state => state.copperFiber.entities);
  const loading = useAppSelector(state => state.copperFiber.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="copper-fiber-heading" data-cy="CopperFiberHeading">
        <Translate contentKey="lappLiApp.copperFiber.home.title">Copper Fibers</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="lappLiApp.copperFiber.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="lappLiApp.copperFiber.home.createLabel">Create new Copper Fiber</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {copperFiberList && copperFiberList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="lappLiApp.copperFiber.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.copperFiber.number">Number</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.copperFiber.designation">Designation</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.copperFiber.copperIsRedNotTinned">Copper Is Red Not Tinned</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.copperFiber.milimeterDiameter">Milimeter Diameter</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {copperFiberList.map((copperFiber, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${copperFiber.id}`} color="link" size="sm">
                      {copperFiber.id}
                    </Button>
                  </td>
                  <td>{copperFiber.number}</td>
                  <td>{copperFiber.designation}</td>
                  <td>{copperFiber.copperIsRedNotTinned ? 'true' : 'false'}</td>
                  <td>{copperFiber.milimeterDiameter}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${copperFiber.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${copperFiber.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${copperFiber.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="lappLiApp.copperFiber.home.notFound">No Copper Fibers found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default CopperFiber;
