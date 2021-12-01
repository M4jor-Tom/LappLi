import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './element-kind-edition.reducer';
import { IElementKindEdition } from 'app/shared/model/element-kind-edition.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ElementKindEdition = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const elementKindEditionList = useAppSelector(state => state.elementKindEdition.entities);
  const loading = useAppSelector(state => state.elementKindEdition.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="element-kind-edition-heading" data-cy="ElementKindEditionHeading">
        <Translate contentKey="lappLiApp.elementKindEdition.home.title">Element Kind Editions</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="lappLiApp.elementKindEdition.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="lappLiApp.elementKindEdition.home.createLabel">Create new Element Kind Edition</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {elementKindEditionList && elementKindEditionList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="lappLiApp.elementKindEdition.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.elementKindEdition.editionDateTime">Edition Date Time</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.elementKindEdition.newGramPerMeterLinearMass">New Gram Per Meter Linear Mass</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.elementKindEdition.newMilimeterDiameter">New Milimeter Diameter</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.elementKindEdition.newInsulationThickness">New Insulation Thickness</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.elementKindEdition.editedElementKind">Edited Element Kind</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {elementKindEditionList.map((elementKindEdition, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${elementKindEdition.id}`} color="link" size="sm">
                      {elementKindEdition.id}
                    </Button>
                  </td>
                  <td>
                    {elementKindEdition.editionDateTime ? (
                      <TextFormat type="date" value={elementKindEdition.editionDateTime} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{elementKindEdition.newGramPerMeterLinearMass}</td>
                  <td>{elementKindEdition.newMilimeterDiameter}</td>
                  <td>{elementKindEdition.newInsulationThickness}</td>
                  <td>
                    <Link to={`element-kind/${elementKindEdition.editedElementKind.id}`}>
                      {elementKindEdition.editedElementKind.designation}
                    </Link>
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${elementKindEdition.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${elementKindEdition.id}/edit`}
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
                        to={`${match.url}/${elementKindEdition.id}/delete`}
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
              <Translate contentKey="lappLiApp.elementKindEdition.home.notFound">No Element Kind Editions found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default ElementKindEdition;
