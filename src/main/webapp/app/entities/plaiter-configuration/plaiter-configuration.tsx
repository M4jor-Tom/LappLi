import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './plaiter-configuration.reducer';
import { IPlaiterConfiguration } from 'app/shared/model/plaiter-configuration.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PlaiterConfiguration = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const plaiterConfigurationList = useAppSelector(state => state.plaiterConfiguration.entities);
  const loading = useAppSelector(state => state.plaiterConfiguration.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="plaiter-configuration-heading" data-cy="PlaiterConfigurationHeading">
        <Translate contentKey="lappLiApp.plaiterConfiguration.home.title">Plaiter Configurations</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="lappLiApp.plaiterConfiguration.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="lappLiApp.plaiterConfiguration.home.createLabel">Create new Plaiter Configuration</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {plaiterConfigurationList && plaiterConfigurationList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="lappLiApp.plaiterConfiguration.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.plaiterConfiguration.usedBobinsCount">Used Bobins Count</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.plaiterConfiguration.plaiter">Plaiter</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {plaiterConfigurationList.map((plaiterConfiguration, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${plaiterConfiguration.id}`} color="link" size="sm">
                      {plaiterConfiguration.id}
                    </Button>
                  </td>
                  <td>{plaiterConfiguration.usedBobinsCount}</td>
                  <td>
                    {plaiterConfiguration.plaiter ? (
                      <Link to={`plaiter/${plaiterConfiguration.plaiter.id}`}>{plaiterConfiguration.plaiter.name}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`${match.url}/${plaiterConfiguration.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${plaiterConfiguration.id}/edit`}
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
                        to={`${match.url}/${plaiterConfiguration.id}/delete`}
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
              <Translate contentKey="lappLiApp.plaiterConfiguration.home.notFound">No Plaiter Configurations found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default PlaiterConfiguration;
