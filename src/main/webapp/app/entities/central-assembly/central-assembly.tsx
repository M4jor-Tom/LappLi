import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './central-assembly.reducer';
import { ICentralAssembly } from 'app/shared/model/central-assembly.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CentralAssembly = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const centralAssemblyList = useAppSelector(state => state.centralAssembly.entities);
  const loading = useAppSelector(state => state.centralAssembly.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="central-assembly-heading" data-cy="CentralAssemblyHeading">
        <Translate contentKey="lappLiApp.centralAssembly.home.title">Central Assemblies</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="lappLiApp.centralAssembly.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="lappLiApp.centralAssembly.home.createLabel">Create new Central Assembly</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {centralAssemblyList && centralAssemblyList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="lappLiApp.centralAssembly.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.centralAssembly.productionStep">Production Step</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.assembly.ownerStrand">Owner Strand</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {centralAssemblyList.map((centralAssembly, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${centralAssembly.id}`} color="link" size="sm">
                      {centralAssembly.id}
                    </Button>
                  </td>
                  <td>{centralAssembly.productionStep}</td>
                  <td>
                    {centralAssembly.ownerStrand ? (
                      <Link to={`strand/${centralAssembly.ownerStrand.id}`}>{centralAssembly.ownerStrand.designation}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${centralAssembly.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${centralAssembly.id}/edit`}
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
                        to={`${match.url}/${centralAssembly.id}/delete`}
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
              <Translate contentKey="lappLiApp.centralAssembly.home.notFound">No Central Assemblies found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default CentralAssembly;
