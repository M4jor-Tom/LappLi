import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './one-study-supply.reducer';
import { IOneStudySupply } from 'app/shared/model/one-study-supply.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const OneStudySupply = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const oneStudySupplyList = useAppSelector(state => state.oneStudySupply.entities);
  const loading = useAppSelector(state => state.oneStudySupply.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="one-study-supply-heading" data-cy="OneStudySupplyHeading">
        <Translate contentKey="lappLiApp.oneStudySupply.home.title">One Study Supplies</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="lappLiApp.oneStudySupply.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="lappLiApp.oneStudySupply.home.createLabel">Create new One Study Supply</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {oneStudySupplyList && oneStudySupplyList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="lappLiApp.oneStudySupply.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.oneStudySupply.apparitions">Apparitions</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.oneStudySupply.number">Number</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.oneStudySupply.componentDesignation">Component Designation</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.oneStudySupply.description">Description</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.oneStudySupply.markingType">Marking Type</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.oneStudySupply.gramPerMeterLinearMass">Gram Per Meter Linear Mass</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.oneStudySupply.milimeterDiameter">Milimeter Diameter</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.oneStudySupply.surfaceColor">Surface Color</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.oneStudySupply.surfaceMaterial">Surface Material</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.supply.bestLiftersNames">Best Lifters</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {oneStudySupplyList.map((oneStudySupply, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${oneStudySupply.id}`} color="link" size="sm">
                      {oneStudySupply.id}
                    </Button>
                  </td>
                  <td>{oneStudySupply.apparitions}</td>
                  <td>{oneStudySupply.number}</td>
                  <td>{oneStudySupply.componentDesignation}</td>
                  <td>{oneStudySupply.description}</td>
                  <td>
                    <Translate contentKey={`lappLiApp.MarkingType.${oneStudySupply.markingType}`} />
                  </td>
                  <td>{oneStudySupply.gramPerMeterLinearMass}</td>
                  <td>{oneStudySupply.milimeterDiameter}</td>
                  <td>
                    <Translate contentKey={`lappLiApp.Color.${oneStudySupply.surfaceColor}`} />
                  </td>
                  <td>
                    {oneStudySupply.surfaceMaterial ? (
                      <Link to={`material/${oneStudySupply.surfaceMaterial.id}`}>{oneStudySupply.surfaceMaterial.designation}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>{oneStudySupply.bestLiftersNames}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${oneStudySupply.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${oneStudySupply.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${oneStudySupply.id}/delete`}
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
              <Translate contentKey="lappLiApp.oneStudySupply.home.notFound">No One Study Supplies found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default OneStudySupply;
