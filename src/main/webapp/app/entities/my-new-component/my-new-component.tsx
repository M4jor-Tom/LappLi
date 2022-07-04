import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './my-new-component.reducer';
import { IMyNewComponent } from 'app/shared/model/my-new-component.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const MyNewComponent = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const myNewComponentList = useAppSelector(state => state.myNewComponent.entities);
  const loading = useAppSelector(state => state.myNewComponent.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="my-new-component-heading" data-cy="MyNewComponentHeading">
        <Translate contentKey="lappLiApp.myNewComponent.home.title">My New Components</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="lappLiApp.myNewComponent.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="lappLiApp.myNewComponent.home.createLabel">Create new My New Component</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {myNewComponentList && myNewComponentList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="lappLiApp.myNewComponent.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.myNewComponent.number">Number</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.myNewComponent.designation">Designation</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.myNewComponent.data">Data</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {myNewComponentList.map((myNewComponent, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${myNewComponent.id}`} color="link" size="sm">
                      {myNewComponent.id}
                    </Button>
                  </td>
                  <td>{myNewComponent.number}</td>
                  <td>{myNewComponent.designation}</td>
                  <td>{myNewComponent.data}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${myNewComponent.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${myNewComponent.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${myNewComponent.id}/delete`}
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
              <Translate contentKey="lappLiApp.myNewComponent.home.notFound">No My New Components found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default MyNewComponent;
