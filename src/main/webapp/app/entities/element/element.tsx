import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './element.reducer';
import { IElement } from 'app/shared/model/element.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Element = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const elementList = useAppSelector(state => state.element.entities);
  const loading = useAppSelector(state => state.element.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="element-heading" data-cy="ElementHeading">
        <Translate contentKey="lappLiApp.element.home.title">Elements</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="lappLiApp.element.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="lappLiApp.element.home.createLabel">Create new Element</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {elementList && elementList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="lappLiApp.element.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.element.number">Number</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.element.designationWithColor">Designation</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.element.color">Color</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.element.elementKind">Element Kind</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.elementKind.milimeterDiameter">Milimeter Diameter</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {elementList.map((element, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${element.id}`} color="link" size="sm">
                      {element.id}
                    </Button>
                  </td>
                  <td>{element.number}</td>
                  <td>{element.designationWithColor}</td>
                  <td>
                    <Translate contentKey={`lappLiApp.Color.${element.color}`} />
                  </td>
                  <td>
                    {element.elementKind ? (
                      <Link to={`element-kind/${element.elementKind.id}`}>{element.elementKind.designation}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>{element.elementKind.milimeterDiameter}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${element.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${element.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${element.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="lappLiApp.element.home.notFound">No Elements found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Element;
