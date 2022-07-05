import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './my-new-component-supply.reducer';
import { IMyNewComponentSupply } from 'app/shared/model/my-new-component-supply.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const MyNewComponentSupply = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const myNewComponentSupplyList = useAppSelector(state => state.myNewComponentSupply.entities);
  const loading = useAppSelector(state => state.myNewComponentSupply.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="my-new-component-supply-heading" data-cy="MyNewComponentSupplyHeading">
        <Translate contentKey="lappLiApp.myNewComponentSupply.home.title">My New Component Supplies</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="lappLiApp.myNewComponentSupply.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="lappLiApp.myNewComponentSupply.home.createLabel">Create new My New Component Supply</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {myNewComponentSupplyList && myNewComponentSupplyList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="lappLiApp.myNewComponentSupply.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.myNewComponentSupply.apparitions">Apparitions</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.myNewComponentSupply.description">Description</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.myNewComponentSupply.markingType">Marking Type</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.myNewComponentSupply.myNewComponent">My New Component</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {myNewComponentSupplyList.map((myNewComponentSupply, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${myNewComponentSupply.id}`} color="link" size="sm">
                      {myNewComponentSupply.id}
                    </Button>
                  </td>
                  <td>{myNewComponentSupply.apparitions}</td>
                  <td>{myNewComponentSupply.description}</td>
                  <td>
                    <Translate contentKey={`lappLiApp.MarkingType.${myNewComponentSupply.markingType}`} />
                  </td>
                  <td>
                    {myNewComponentSupply.myNewComponent ? (
                      <Link to={`my-new-component/${myNewComponentSupply.myNewComponent.id}`}>
                        {myNewComponentSupply.myNewComponent.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`${match.url}/${myNewComponentSupply.id}`}
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
                        to={`${match.url}/${myNewComponentSupply.id}/edit`}
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
                        to={`${match.url}/${myNewComponentSupply.id}/delete`}
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
              <Translate contentKey="lappLiApp.myNewComponentSupply.home.notFound">No My New Component Supplies found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default MyNewComponentSupply;
