import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './interstice-assembly.reducer';
import { IIntersticeAssembly } from 'app/shared/model/interstice-assembly.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const IntersticeAssembly = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const intersticeAssemblyList = useAppSelector(state => state.intersticeAssembly.entities);
  const loading = useAppSelector(state => state.intersticeAssembly.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="interstice-assembly-heading" data-cy="IntersticeAssemblyHeading">
        <Translate contentKey="lappLiApp.intersticeAssembly.home.title">Interstice Assemblies</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="lappLiApp.intersticeAssembly.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="lappLiApp.intersticeAssembly.home.createLabel">Create new Interstice Assembly</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {intersticeAssemblyList && intersticeAssemblyList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="lappLiApp.intersticeAssembly.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.intersticeAssembly.assemblyLayer">Assembly Layer</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.intersticeAssembly.intersticeLayer">Interstice Layer</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.intersticeAssembly.forcedMeanMilimeterComponentDiameter">
                    Forced Mean Milimeter Component Diameter
                  </Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.intersticeAssembly.ownerStrand">Owner Strand</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {intersticeAssemblyList.map((intersticeAssembly, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${intersticeAssembly.id}`} color="link" size="sm">
                      {intersticeAssembly.id}
                    </Button>
                  </td>
                  <td>{intersticeAssembly.assemblyLayer}</td>
                  <td>{intersticeAssembly.intersticeLayer}</td>
                  <td>{intersticeAssembly.forcedMeanMilimeterComponentDiameter}</td>
                  <td>
                    {intersticeAssembly.ownerStrand ? (
                      <Link to={`strand/${intersticeAssembly.ownerStrand.id}`}>{intersticeAssembly.ownerStrand.designation}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${intersticeAssembly.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${intersticeAssembly.id}/edit`}
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
                        to={`${match.url}/${intersticeAssembly.id}/delete`}
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
              <Translate contentKey="lappLiApp.intersticeAssembly.home.notFound">No Interstice Assemblies found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default IntersticeAssembly;
