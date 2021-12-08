import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './bangle-supply.reducer';
import { IBangleSupply } from 'app/shared/model/bangle-supply.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const BangleSupply = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const bangleSupplyList = useAppSelector(state => state.bangleSupply.entities);
  const loading = useAppSelector(state => state.bangleSupply.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="bangle-supply-heading" data-cy="BangleSupplyHeading">
        <Translate contentKey="lappLiApp.bangleSupply.home.title">Bangle Supplies</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="lappLiApp.bangleSupply.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="lappLiApp.bangleSupply.home.createLabel">Create new Bangle Supply</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {bangleSupplyList && bangleSupplyList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="lappLiApp.bangleSupply.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.supply.apparitions">Apparitions</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.supply.description">Description</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.bangleSupply.bangle">Bangle</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.dimension.meterQuantity">Quantity (m)</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.dimension.milimeterDiameter">Diameter (mm)</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.dimension.gramPerMeterLinearMass">Linear Mass (g/m)</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.supply.bestLiftersNames">Best Machines Names</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.supply.meterPerHourSpeed">Speed (m/h)</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.supply.formatedHourPreparationTime">Preparation Time (h)</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.supply.formatedHourExecutionTime">Execution Time (h)</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {bangleSupplyList.map((bangleSupply, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${bangleSupply.id}`} color="link" size="sm">
                      {bangleSupply.id}
                    </Button>
                  </td>
                  <td>{bangleSupply.apparitions}</td>
                  <td>{bangleSupply.description}</td>
                  <td>
                    {bangleSupply.bangle ? <Link to={`bangle/${bangleSupply.bangle.id}`}>{bangleSupply.bangle.designation}</Link> : ''}
                  </td>
                  <td>{bangleSupply.meterQuantity}</td>
                  <td>{bangleSupply.milimeterDiameter}</td>
                  <td>{bangleSupply.gramPerMeterLinearMass}</td>
                  <td>{bangleSupply.bestLiftersNames}</td>
                  <td>{bangleSupply.meterPerHourSpeed}</td>
                  <td>{bangleSupply.formatedHourPreparationTime}</td>
                  <td>{bangleSupply.formatedHourExecutionTime}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${bangleSupply.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${bangleSupply.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${bangleSupply.id}/delete`}
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
              <Translate contentKey="lappLiApp.bangleSupply.home.notFound">No Bangle Supplies found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default BangleSupply;
