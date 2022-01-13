import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './sheathing.reducer';
import { ISheathing } from 'app/shared/model/sheathing.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Sheathing = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const sheathingList = useAppSelector(state => state.sheathing.entities);
  const loading = useAppSelector(state => state.sheathing.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="sheathing-heading" data-cy="SheathingHeading">
        <Translate contentKey="lappLiApp.sheathing.home.title">Sheathings</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="lappLiApp.sheathing.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="lappLiApp.sheathing.home.createLabel">Create new Sheathing</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {sheathingList && sheathingList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="lappLiApp.sheathing.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.sheathing.operationLayer">Operation Layer</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.sheathing.thickness">Thickness</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.sheathing.sheathingKind">Sheathing Kind</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.sheathing.material">Material</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.sheathing.ownerStrand">Owner Strand</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {sheathingList.map((sheathing, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${sheathing.id}`} color="link" size="sm">
                      {sheathing.id}
                    </Button>
                  </td>
                  <td>{sheathing.operationLayer}</td>
                  <td>{sheathing.thickness}</td>
                  <td>
                    <Translate contentKey={`lappLiApp.SheathingKind.${sheathing.sheathingKind}`} />
                  </td>
                  <td>
                    {sheathing.material ? <Link to={`material/${sheathing.material.id}`}>{sheathing.material.designation}</Link> : ''}
                  </td>
                  <td>
                    {sheathing.ownerStrand ? (
                      <Link to={`strand/${sheathing.ownerStrand.id}`}>{sheathing.ownerStrand.designation}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${sheathing.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${sheathing.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${sheathing.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="lappLiApp.sheathing.home.notFound">No Sheathings found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Sheathing;
