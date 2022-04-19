import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './plait.reducer';
import { IPlait } from 'app/shared/model/plait.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Plait = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const plaitList = useAppSelector(state => state.plait.entities);
  const loading = useAppSelector(state => state.plait.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="plait-heading" data-cy="PlaitHeading">
        <Translate contentKey="lappLiApp.plait.home.title">Plaits</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="lappLiApp.plait.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="lappLiApp.plait.home.createLabel">Create new Plait</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {plaitList && plaitList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="lappLiApp.plait.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.plait.operationLayer">Operation Layer</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.plait.targetCoveringRate">Target Covering Rate</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.plait.targetDegreeAngle">Target Degree Angle</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.plait.targetingCoveringRateNotAngle">Targeting Covering Rate Not Angle</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.plait.anonymousMetalFiberNumber">Anonymous Metal Fiber Number</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.plait.anonymousMetalFiberDesignation">Anonymous Metal Fiber Designation</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.plait.anonymousMetalFiberMetalFiberKind">
                    Anonymous Metal Fiber Metal Fiber Kind
                  </Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.plait.anonymousMetalFiberMilimeterDiameter">
                    Anonymous Metal Fiber Milimeter Diameter
                  </Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.plait.copperFiber">Copper Fiber</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.plait.metalFiber">Metal Fiber</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.plait.ownerStrandSupply">Owner Strand Supply</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {plaitList.map((plait, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${plait.id}`} color="link" size="sm">
                      {plait.id}
                    </Button>
                  </td>
                  <td>{plait.operationLayer}</td>
                  <td>{plait.targetCoveringRate}</td>
                  <td>{plait.targetDegreeAngle}</td>
                  <td>{plait.targetingCoveringRateNotAngle ? 'true' : 'false'}</td>
                  <td>{plait.anonymousMetalFiberNumber}</td>
                  <td>{plait.anonymousMetalFiberDesignation}</td>
                  <td>
                    <Translate contentKey={`lappLiApp.MetalFiberKind.${plait.anonymousMetalFiberMetalFiberKind}`} />
                  </td>
                  <td>{plait.anonymousMetalFiberMilimeterDiameter}</td>
                  <td>
                    {plait.copperFiber ? <Link to={`copper-fiber/${plait.copperFiber.id}`}>{plait.copperFiber.designation}</Link> : ''}
                  </td>
                  <td>{plait.metalFiber ? <Link to={`metal-fiber/${plait.metalFiber.id}`}>{plait.metalFiber.designation}</Link> : ''}</td>
                  <td>
                    {plait.ownerStrandSupply ? (
                      <Link to={`strand-supply/${plait.ownerStrandSupply.id}`}>{plait.ownerStrandSupply.designation}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${plait.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${plait.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${plait.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="lappLiApp.plait.home.notFound">No Plaits found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Plait;
