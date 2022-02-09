import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './supply-position.reducer';
import { ISupplyPosition } from 'app/shared/model/supply-position.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const SupplyPosition = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const supplyPositionList = useAppSelector(state => state.supplyPosition.entities);
  const loading = useAppSelector(state => state.supplyPosition.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="supply-position-heading" data-cy="SupplyPositionHeading">
        <Translate contentKey="lappLiApp.supplyPosition.home.title">Supply Positions</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="lappLiApp.supplyPosition.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="lappLiApp.supplyPosition.home.createLabel">Create new Supply Position</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {supplyPositionList && supplyPositionList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="lappLiApp.supplyPosition.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.supplyPosition.supplyApparitionsUsage">Supply Apparitions Usage</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.supplyPosition.elementSupply">Element Supply</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.supplyPosition.bangleSupply">Bangle Supply</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.supplyPosition.customComponentSupply">Custom Component Supply</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.supplyPosition.oneStudySupply">One Study Supply</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.supplyPosition.ownerStrand">Owner Strand</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.supplyPosition.ownerIntersticeAssembly">Owner Interstice Assembly</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {supplyPositionList.map((supplyPosition, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${supplyPosition.id}`} color="link" size="sm">
                      {supplyPosition.id}
                    </Button>
                  </td>
                  <td>{supplyPosition.supplyApparitionsUsage}</td>
                  <td>
                    {supplyPosition.elementSupply ? (
                      <Link to={`element-supply/${supplyPosition.elementSupply.id}`}>{supplyPosition.elementSupply.designation}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {supplyPosition.bangleSupply ? (
                      <Link to={`bangle-supply/${supplyPosition.bangleSupply.id}`}>{supplyPosition.bangleSupply.designation}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {supplyPosition.customComponentSupply ? (
                      <Link to={`custom-component-supply/${supplyPosition.customComponentSupply.id}`}>
                        {supplyPosition.customComponentSupply.designation}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {supplyPosition.oneStudySupply ? (
                      <Link to={`one-study-supply/${supplyPosition.oneStudySupply.id}`}>{supplyPosition.oneStudySupply.designation}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {supplyPosition.ownerStrand ? (
                      <Link to={`strand/${supplyPosition.ownerStrand.id}`}>{supplyPosition.ownerStrand.designation}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {supplyPosition.ownerIntersticeAssembly ? (
                      <Link to={`interstice-assembly/${supplyPosition.ownerIntersticeAssembly.id}`}>
                        {supplyPosition.ownerIntersticeAssembly.productDesignation}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${supplyPosition.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${supplyPosition.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${supplyPosition.id}/delete`}
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
              <Translate contentKey="lappLiApp.supplyPosition.home.notFound">No Supply Positions found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default SupplyPosition;
