import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './continuity-wire-longit-laying.reducer';
import { IContinuityWireLongitLaying } from 'app/shared/model/continuity-wire-longit-laying.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ContinuityWireLongitLaying = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const continuityWireLongitLayingList = useAppSelector(state => state.continuityWireLongitLaying.entities);
  const loading = useAppSelector(state => state.continuityWireLongitLaying.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="continuity-wire-longit-laying-heading" data-cy="ContinuityWireLongitLayingHeading">
        <Translate contentKey="lappLiApp.continuityWireLongitLaying.home.title">Continuity Wire Longit Layings</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="lappLiApp.continuityWireLongitLaying.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="lappLiApp.continuityWireLongitLaying.home.createLabel">
              Create new Continuity Wire Longit Laying
            </Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {continuityWireLongitLayingList && continuityWireLongitLayingList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="lappLiApp.continuityWireLongitLaying.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.continuityWireLongitLaying.operationLayer">Operation Layer</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.continuityWireLongitLaying.anonymousContinuityWireDesignation">
                    Anonymous Continuity Wire Designation
                  </Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.continuityWireLongitLaying.anonymousContinuityWireGramPerMeterLinearMass">
                    Anonymous Continuity Wire Gram Per Meter Linear Mass
                  </Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.continuityWireLongitLaying.anonymousContinuityWireMetalFiberKind">
                    Anonymous Continuity Wire Metal Fiber Kind
                  </Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.continuityWireLongitLaying.anonymousContinuityWireMilimeterDiameter">
                    Anonymous Continuity Wire Milimeter Diameter
                  </Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.continuityWireLongitLaying.anonymousContinuityWireFlexibility">
                    Anonymous Continuity Wire Flexibility
                  </Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.continuityWireLongitLaying.continuityWire">Continuity Wire</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.continuityWireLongitLaying.ownerStrandSupply">Owner Strand Supply</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {continuityWireLongitLayingList.map((continuityWireLongitLaying, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${continuityWireLongitLaying.id}`} color="link" size="sm">
                      {continuityWireLongitLaying.id}
                    </Button>
                  </td>
                  <td>{continuityWireLongitLaying.operationLayer}</td>
                  <td>{continuityWireLongitLaying.anonymousContinuityWireDesignation}</td>
                  <td>{continuityWireLongitLaying.anonymousContinuityWireGramPerMeterLinearMass}</td>
                  <td>
                    <Translate
                      contentKey={`lappLiApp.MetalFiberKind.${continuityWireLongitLaying.anonymousContinuityWireMetalFiberKind}`}
                    />
                  </td>
                  <td>{continuityWireLongitLaying.anonymousContinuityWireMilimeterDiameter}</td>
                  <td>
                    <Translate contentKey={`lappLiApp.Flexibility.${continuityWireLongitLaying.anonymousContinuityWireFlexibility}`} />
                  </td>
                  <td>
                    {continuityWireLongitLaying.continuityWire ? (
                      <Link to={`continuity-wire/${continuityWireLongitLaying.continuityWire.id}`}>
                        {continuityWireLongitLaying.continuityWire.designation}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {continuityWireLongitLaying.ownerStrandSupply ? (
                      <Link to={`strand-supply/${continuityWireLongitLaying.ownerStrandSupply.id}`}>
                        {continuityWireLongitLaying.ownerStrandSupply.designation}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`${match.url}/${continuityWireLongitLaying.id}`}
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
                        to={`${match.url}/${continuityWireLongitLaying.id}/edit`}
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
                        to={`${match.url}/${continuityWireLongitLaying.id}/delete`}
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
              <Translate contentKey="lappLiApp.continuityWireLongitLaying.home.notFound">No Continuity Wire Longit Layings found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default ContinuityWireLongitLaying;
