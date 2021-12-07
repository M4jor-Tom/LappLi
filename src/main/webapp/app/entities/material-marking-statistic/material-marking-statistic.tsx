import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './material-marking-statistic.reducer';
import { IMaterialMarkingStatistic } from 'app/shared/model/material-marking-statistic.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const MaterialMarkingStatistic = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const materialMarkingStatisticList = useAppSelector(state => state.materialMarkingStatistic.entities);
  const loading = useAppSelector(state => state.materialMarkingStatistic.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="material-marking-statistic-heading" data-cy="MaterialMarkingStatisticHeading">
        <Translate contentKey="lappLiApp.materialMarkingStatistic.home.title">Material Marking Statistics</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="lappLiApp.materialMarkingStatistic.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="lappLiApp.materialMarkingStatistic.home.createLabel">Create new Material Marking Statistic</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {materialMarkingStatisticList && materialMarkingStatisticList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="lappLiApp.materialMarkingStatistic.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.materialMarkingStatistic.markingType">Marking Type</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.materialMarkingStatistic.markingTechnique">Marking Technique</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.materialMarkingStatistic.meterPerHourSpeed">Meter Per Hour Speed</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.materialMarkingStatistic.material">Material</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {materialMarkingStatisticList.map((materialMarkingStatistic, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${materialMarkingStatistic.id}`} color="link" size="sm">
                      {materialMarkingStatistic.id}
                    </Button>
                  </td>
                  <td>
                    <Translate contentKey={`lappLiApp.MarkingType.${materialMarkingStatistic.markingType}`} />
                  </td>
                  <td>
                    <Translate contentKey={`lappLiApp.MarkingTechnique.${materialMarkingStatistic.markingTechnique}`} />
                  </td>
                  <td>{materialMarkingStatistic.meterPerHourSpeed}</td>
                  <td>
                    {materialMarkingStatistic.material ? (
                      <Link to={`material/${materialMarkingStatistic.material.id}`}>{materialMarkingStatistic.material.designation}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`${match.url}/${materialMarkingStatistic.id}`}
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
                        to={`${match.url}/${materialMarkingStatistic.id}/edit`}
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
                        to={`${match.url}/${materialMarkingStatistic.id}/delete`}
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
              <Translate contentKey="lappLiApp.materialMarkingStatistic.home.notFound">No Material Marking Statistics found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default MaterialMarkingStatistic;
