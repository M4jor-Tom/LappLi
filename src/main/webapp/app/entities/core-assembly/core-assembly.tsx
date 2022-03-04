import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './core-assembly.reducer';
import { ICoreAssembly } from 'app/shared/model/core-assembly.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CoreAssembly = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const coreAssemblyList = useAppSelector(state => state.coreAssembly.entities);
  const loading = useAppSelector(state => state.coreAssembly.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="core-assembly-heading" data-cy="CoreAssemblyHeading">
        <Translate contentKey="lappLiApp.coreAssembly.home.title">Core Assemblies</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="lappLiApp.coreAssembly.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="lappLiApp.coreAssembly.home.createLabel">Create new Core Assembly</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {coreAssemblyList && coreAssemblyList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="lappLiApp.coreAssembly.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.coreAssembly.operationLayer">Operation Layer</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.coreAssembly.forcedMeanMilimeterComponentDiameter">
                    Forced Mean Milimeter Component Diameter
                  </Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.coreAssembly.componentsCount">Components Count</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.coreAssembly.ownerStrand">Owner Strand</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {coreAssemblyList.map((coreAssembly, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${coreAssembly.id}`} color="link" size="sm">
                      {coreAssembly.id}
                    </Button>
                  </td>
                  <td>{coreAssembly.operationLayer}</td>
                  <td>{coreAssembly.forcedMeanMilimeterComponentDiameter}</td>
                  <td>{coreAssembly.componentsCount}</td>
                  <td>
                    {coreAssembly.ownerStrandSupply ? (
                      <Link to={`strand-supply/${coreAssembly.ownerStrandSupply.id}`}>{coreAssembly.ownerStrandSupply.designation}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${coreAssembly.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${coreAssembly.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${coreAssembly.id}/delete`}
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
              <Translate contentKey="lappLiApp.coreAssembly.home.notFound">No Core Assemblies found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default CoreAssembly;
