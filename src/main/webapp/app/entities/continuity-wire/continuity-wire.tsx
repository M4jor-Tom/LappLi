import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './continuity-wire.reducer';
import { IContinuityWire } from 'app/shared/model/continuity-wire.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ContinuityWire = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const continuityWireList = useAppSelector(state => state.continuityWire.entities);
  const loading = useAppSelector(state => state.continuityWire.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="continuity-wire-heading" data-cy="ContinuityWireHeading">
        <Translate contentKey="lappLiApp.continuityWire.home.title">Continuity Wires</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="lappLiApp.continuityWire.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="lappLiApp.continuityWire.home.createLabel">Create new Continuity Wire</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {continuityWireList && continuityWireList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="lappLiApp.continuityWire.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.continuityWire.designation">Designation</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.continuityWire.gramPerMeterLinearMass">Gram Per Meter Linear Mass</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.continuityWire.metalFiberKind">Metal Fiber Kind</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.continuityWire.milimeterDiameter">Milimeter Diameter</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.continuityWire.flexibility">Flexibility</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {continuityWireList.map((continuityWire, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${continuityWire.id}`} color="link" size="sm">
                      {continuityWire.id}
                    </Button>
                  </td>
                  <td>{continuityWire.designation}</td>
                  <td>{continuityWire.gramPerMeterLinearMass}</td>
                  <td>
                    <Translate contentKey={`lappLiApp.MetalFiberKind.${continuityWire.metalFiberKind}`} />
                  </td>
                  <td>{continuityWire.milimeterDiameter}</td>
                  <td>
                    <Translate contentKey={`lappLiApp.Flexibility.${continuityWire.flexibility}`} />
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${continuityWire.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${continuityWire.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${continuityWire.id}/delete`}
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
              <Translate contentKey="lappLiApp.continuityWire.home.notFound">No Continuity Wires found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default ContinuityWire;
