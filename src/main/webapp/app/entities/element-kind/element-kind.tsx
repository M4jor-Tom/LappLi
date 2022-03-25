import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './element-kind.reducer';
import { IElementKind } from 'app/shared/model/element-kind.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ElementKind = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const elementKindList = useAppSelector(state => state.elementKind.entities);
  const loading = useAppSelector(state => state.elementKind.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="element-kind-heading" data-cy="ElementKindHeading">
        <Translate contentKey="lappLiApp.elementKind.home.title">Element Kinds</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="lappLiApp.elementKind.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="lappLiApp.elementKind.home.createLabel">Create new Element Kind</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {elementKindList && elementKindList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="lappLiApp.elementKind.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.elementKind.designation">Designation</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.elementKind.gramPerMeterLinearMass">Gram Per Meter Linear Mass</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.elementKind.milimeterDiameter">Milimeter Diameter</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.elementKind.milimeterInsulationThickness">Milimeter Insulation Thickness</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.elementKind.copper">Copper</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.elementKind.insulationMaterial">Insulation Material</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {elementKindList.map((elementKind, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${elementKind.id}`} color="link" size="sm">
                      {elementKind.id}
                    </Button>
                  </td>
                  <td>{elementKind.designation}</td>
                  <td>{elementKind.gramPerMeterLinearMass}</td>
                  <td>{elementKind.milimeterDiameter}</td>
                  <td>{elementKind.milimeterInsulationThickness}</td>
                  <td>{elementKind.copper ? <Link to={`copper/${elementKind.copper.id}`}>{elementKind.copper.designation}</Link> : ''}</td>
                  <td>
                    {elementKind.insulationMaterial ? (
                      <Link to={`material/${elementKind.insulationMaterial.id}`}>{elementKind.insulationMaterial.designation}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${elementKind.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      {/* <Button tag={Link} to={`${match.url}/${elementKind.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>*/}
                      <Button tag={Link} to={`${match.url}/${elementKind.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="lappLiApp.elementKind.home.notFound">No Element Kinds found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default ElementKind;
