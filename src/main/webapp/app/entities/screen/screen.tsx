import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './screen.reducer';
import { IScreen } from 'app/shared/model/screen.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Screen = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const screenList = useAppSelector(state => state.screen.entities);
  const loading = useAppSelector(state => state.screen.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="screen-heading" data-cy="ScreenHeading">
        <Translate contentKey="lappLiApp.screen.home.title">Screens</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="lappLiApp.screen.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="lappLiApp.screen.home.createLabel">Create new Screen</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {screenList && screenList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="lappLiApp.screen.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.screen.operationLayer">Operation Layer</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.screen.assemblyMeanIsSameThanAssemblys">Assembly Mean Is Same Than Assemblys</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.screen.forcedDiameterAssemblyStep">Forced Diameter Assembly Step</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.screen.copperFiber">Copper Fiber</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.screen.ownerStrandSupply">Owner Strand Supply</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {screenList.map((screen, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${screen.id}`} color="link" size="sm">
                      {screen.id}
                    </Button>
                  </td>
                  <td>{screen.operationLayer}</td>
                  <td>{screen.assemblyMeanIsSameThanAssemblys ? 'true' : 'false'}</td>
                  <td>{screen.forcedDiameterAssemblyStep}</td>
                  <td>
                    {screen.copperFiber ? <Link to={`copper-fiber/${screen.copperFiber.id}`}>{screen.copperFiber.designation}</Link> : ''}
                  </td>
                  <td>
                    {screen.ownerStrandSupply ? (
                      <Link to={`strand-supply/${screen.ownerStrandSupply.id}`}>{screen.ownerStrandSupply.designation}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${screen.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${screen.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${screen.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="lappLiApp.screen.home.notFound">No Screens found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Screen;
