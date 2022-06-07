import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './plaiter.reducer';
import { IPlaiter } from 'app/shared/model/plaiter.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Plaiter = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const plaiterList = useAppSelector(state => state.plaiter.entities);
  const loading = useAppSelector(state => state.plaiter.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="plaiter-heading" data-cy="PlaiterHeading">
        <Translate contentKey="lappLiApp.plaiter.home.title">Plaiters</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="lappLiApp.plaiter.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="lappLiApp.plaiter.home.createLabel">Create new Plaiter</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {plaiterList && plaiterList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="lappLiApp.plaiter.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.plaiter.index">Index</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.plaiter.totalBobinsCount">Total Bobins Count</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {plaiterList.map((plaiter, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${plaiter.id}`} color="link" size="sm">
                      {plaiter.id}
                    </Button>
                  </td>
                  <td>{plaiter.index}</td>
                  <td>{plaiter.totalBobinsCount}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${plaiter.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${plaiter.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${plaiter.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="lappLiApp.plaiter.home.notFound">No Plaiters found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Plaiter;
