import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './element-supply.reducer';
import { IElementSupply } from 'app/shared/model/element-supply.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ElementSupply = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const elementSupplyList = useAppSelector(state => state.elementSupply.entities);
  const loading = useAppSelector(state => state.elementSupply.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="element-supply-heading" data-cy="ElementSupplyHeading">
        <Translate contentKey="lappLiApp.elementSupply.home.title">Element Supplies</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="lappLiApp.elementSupply.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="lappLiApp.elementSupply.home.createLabel">Create new Element Supply</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {elementSupplyList && elementSupplyList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="lappLiApp.elementSupply.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.elementSupply.apparitions">Apparitions</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.elementSupply.forcedMarking">Forced Marking</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.elementSupply.markingType">Marking Type</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.article.number">Article Number</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.elementSupply.element">Element</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.elementSupply.quantity">Quantity</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.elementKind.milimeterDiameter">Diameter (mm)</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.elementKind.gramPerMeterLinearMass">Linear Mass (g/m)</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.elementSupply.bestLiftersNames">Best Machines Names</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {elementSupplyList.map((elementSupply, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${elementSupply.id}`} color="link" size="sm">
                      {elementSupply.id}
                    </Button>
                  </td>
                  <td>{elementSupply.apparitions}</td>
                  <td>{elementSupply.forcedMarking}</td>
                  <td>
                    <Translate contentKey={`lappLiApp.MarkingType.${elementSupply.markingType}`} />
                  </td>
                  <td>
                    {elementSupply.element ? <Link to={`element/${elementSupply.element.id}`}>{elementSupply.element.number}</Link> : ''}
                  </td>
                  <td>
                    {elementSupply.element ? (
                      <Link to={`element/${elementSupply.element.id}`}>{elementSupply.element.designationWithColor}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>{elementSupply.quantity}</td>
                  <td>{elementSupply.milimeterDiameter}</td>
                  <td>{elementSupply.gramPerMeterLinearMass}</td>
                  <td>{elementSupply.bestLiftersNames}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${elementSupply.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${elementSupply.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${elementSupply.id}/delete`}
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
              <Translate contentKey="lappLiApp.elementSupply.home.notFound">No Element Supplies found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default ElementSupply;
