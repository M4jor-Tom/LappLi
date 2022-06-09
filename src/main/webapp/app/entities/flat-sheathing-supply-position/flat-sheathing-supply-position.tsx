import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './flat-sheathing-supply-position.reducer';
import { IFlatSheathingSupplyPosition } from 'app/shared/model/flat-sheathing-supply-position.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const FlatSheathingSupplyPosition = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const flatSheathingSupplyPositionList = useAppSelector(state => state.flatSheathingSupplyPosition.entities);
  const loading = useAppSelector(state => state.flatSheathingSupplyPosition.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="flat-sheathing-supply-position-heading" data-cy="FlatSheathingSupplyPositionHeading">
        <Translate contentKey="lappLiApp.flatSheathingSupplyPosition.home.title">Flat Sheathing Supply Positions</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="lappLiApp.flatSheathingSupplyPosition.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="lappLiApp.flatSheathingSupplyPosition.home.createLabel">
              Create new Flat Sheathing Supply Position
            </Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {flatSheathingSupplyPositionList && flatSheathingSupplyPositionList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="lappLiApp.flatSheathingSupplyPosition.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.flatSheathingSupplyPosition.locationInOwnerFlatSheathing">
                    Location In Owner Flat Sheathing
                  </Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.flatSheathingSupplyPosition.supplyPosition">Supply Position</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.flatSheathingSupplyPosition.ownerFlatSheathing">Owner Flat Sheathing</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {flatSheathingSupplyPositionList.map((flatSheathingSupplyPosition, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${flatSheathingSupplyPosition.id}`} color="link" size="sm">
                      {flatSheathingSupplyPosition.id}
                    </Button>
                  </td>
                  <td>{flatSheathingSupplyPosition.locationInOwnerFlatSheathing}</td>
                  <td>
                    {flatSheathingSupplyPosition.supplyPosition ? (
                      <Link to={`supply-position/${flatSheathingSupplyPosition.supplyPosition.id}`}>
                        {flatSheathingSupplyPosition.supplyPosition.designation}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {flatSheathingSupplyPosition.ownerFlatSheathing ? (
                      <Link to={`flat-sheathing/${flatSheathingSupplyPosition.ownerFlatSheathing.id}`}>
                        {flatSheathingSupplyPosition.ownerFlatSheathing.designation}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`${match.url}/${flatSheathingSupplyPosition.id}`}
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
                        to={`${match.url}/${flatSheathingSupplyPosition.id}/edit`}
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
                        to={`${match.url}/${flatSheathingSupplyPosition.id}/delete`}
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
              <Translate contentKey="lappLiApp.flatSheathingSupplyPosition.home.notFound">
                No Flat Sheathing Supply Positions found
              </Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default FlatSheathingSupplyPosition;
