import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './strip.reducer';
import { IStrip } from 'app/shared/model/strip.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Strip = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const stripList = useAppSelector(state => state.strip.entities);
  const loading = useAppSelector(state => state.strip.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="strip-heading" data-cy="StripHeading">
        <Translate contentKey="lappLiApp.strip.home.title">Strips</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="lappLiApp.strip.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="lappLiApp.strip.home.createLabel">Create new Strip</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {stripList && stripList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="lappLiApp.strip.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.strip.number">Number</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.strip.designation">Designation</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.strip.milimeterThickness">Milimeter Thickness</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {stripList.map((strip, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${strip.id}`} color="link" size="sm">
                      {strip.id}
                    </Button>
                  </td>
                  <td>{strip.number}</td>
                  <td>{strip.designation}</td>
                  <td>{strip.milimeterThickness}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${strip.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${strip.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${strip.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="lappLiApp.strip.home.notFound">No Strips found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Strip;
