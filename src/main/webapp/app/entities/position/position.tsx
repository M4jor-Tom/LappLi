import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './position.reducer';
import { IPosition } from 'app/shared/model/position.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Position = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const positionList = useAppSelector(state => state.position.entities);
  const loading = useAppSelector(state => state.position.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="position-heading" data-cy="PositionHeading">
        <Translate contentKey="lappLiApp.position.home.title">Positions</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="lappLiApp.position.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="lappLiApp.position.home.createLabel">Create new Position</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {positionList && positionList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="lappLiApp.position.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.position.value">Value</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.position.elementSupply">Element Supply</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.position.bangleSupply">Bangle Supply</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.position.customComponentSupply">Custom Component Supply</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.position.oneStudySupply">One Study Supply</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.position.ownerCentralAssembly">Owner Central Assembly</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.position.ownerCoreAssembly">Owner Core Assembly</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.position.ownerIntersticeAssembly">Owner Interstice Assembly</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {positionList.map((position, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${position.id}`} color="link" size="sm">
                      {position.id}
                    </Button>
                  </td>
                  <td>{position.value}</td>
                  <td>
                    {position.elementSupply ? (
                      <Link to={`element-supply/${position.elementSupply.id}`}>{position.elementSupply.designation}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {position.bangleSupply ? (
                      <Link to={`bangle-supply/${position.bangleSupply.id}`}>{position.bangleSupply.designation}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {position.customComponentSupply ? (
                      <Link to={`custom-component-supply/${position.customComponentSupply.id}`}>
                        {position.customComponentSupply.designation}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {position.oneStudySupply ? (
                      <Link to={`one-study-supply/${position.oneStudySupply.id}`}>{position.oneStudySupply.designation}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {position.ownerCentralAssembly ? (
                      <Link to={`central-assembly/${position.ownerCentralAssembly.id}`}>{position.ownerCentralAssembly.designation}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {position.ownerCoreAssembly ? (
                      <Link to={`core-assembly/${position.ownerCoreAssembly.id}`}>{position.ownerCoreAssembly.designation}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {position.ownerIntersticeAssembly ? (
                      <Link to={`interstice-assembly/${position.ownerIntersticeAssembly.id}`}>
                        {position.ownerIntersticeAssembly.designation}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${position.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${position.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${position.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="lappLiApp.position.home.notFound">No Positions found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Position;
