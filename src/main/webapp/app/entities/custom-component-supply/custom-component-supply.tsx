import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './custom-component-supply.reducer';
import { ICustomComponentSupply } from 'app/shared/model/custom-component-supply.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CustomComponentSupply = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const customComponentSupplyList = useAppSelector(state => state.customComponentSupply.entities);
  const loading = useAppSelector(state => state.customComponentSupply.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="custom-component-supply-heading" data-cy="CustomComponentSupplyHeading">
        <Translate contentKey="lappLiApp.customComponentSupply.home.title">Custom Component Supplies</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="lappLiApp.customComponentSupply.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="lappLiApp.customComponentSupply.home.createLabel">Create new Custom Component Supply</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {customComponentSupplyList && customComponentSupplyList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="lappLiApp.customComponentSupply.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.supply.apparitions">Apparitions</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.supply.description">Description</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.supply.markingType">Marking Type</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.customComponentSupply.customComponent">Custom Component</Translate>
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
                  <Translate contentKey="lappLiApp.customComponent.surfaceMaterial">Material</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.customComponent.surfaceColor">Color</Translate>
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
                <th>
                  <Translate contentKey="lappLiApp.supply.markingTechnique">Marking Technique</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.supply.ownerStrand">Owner Strand</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {customComponentSupplyList.map((customComponentSupply, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${customComponentSupply.id}`} color="link" size="sm">
                      {customComponentSupply.id}
                    </Button>
                  </td>
                  <td>{customComponentSupply.apparitions}</td>
                  <td>{customComponentSupply.description}</td>
                  <td>
                    <Translate contentKey={`lappLiApp.MarkingType.${customComponentSupply.markingType}`} />
                  </td>
                  <td>
                    {customComponentSupply.customComponent ? (
                      <Link to={`custom-component/${customComponentSupply.customComponent.id}`}>
                        {customComponentSupply.customComponent.designation}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>{customComponentSupply.meterQuantity}</td>
                  <td>{customComponentSupply.customComponent.milimeterDiameter}</td>
                  <td>{customComponentSupply.customComponent.gramPerMeterLinearMass}</td>
                  <td>{customComponentSupply.bestLiftersNames}</td>
                  <td>{customComponentSupply.customComponent.surfaceMaterial?.designation}</td>
                  <td>{customComponentSupply.customComponent.surfaceColor}</td>
                  <td>{customComponentSupply.meterPerHourSpeed}</td>
                  <td>{customComponentSupply.formatedHourPreparationTime}</td>
                  <td>{customComponentSupply.formatedHourExecutionTime}</td>
                  <td>{customComponentSupply.markingTechnique}</td>
                  <td>
                    {customComponentSupply.ownerStrand ? (
                      <Link to={`strand/${customComponentSupply.ownerStrand.id}`}>{customComponentSupply.ownerStrand.designation}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`${match.url}/${customComponentSupply.id}`}
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
                        to={`${match.url}/${customComponentSupply.id}/edit`}
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
                        to={`${match.url}/${customComponentSupply.id}/delete`}
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
              <Translate contentKey="lappLiApp.customComponentSupply.home.notFound">No Custom Component Supplies found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default CustomComponentSupply;
