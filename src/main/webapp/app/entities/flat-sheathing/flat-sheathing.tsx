import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './flat-sheathing.reducer';
import { IFlatSheathing } from 'app/shared/model/flat-sheathing.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const FlatSheathing = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const flatSheathingList = useAppSelector(state => state.flatSheathing.entities);
  const loading = useAppSelector(state => state.flatSheathing.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="flat-sheathing-heading" data-cy="FlatSheathingHeading">
        <Translate contentKey="lappLiApp.flatSheathing.home.title">Flat Sheathings</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="lappLiApp.flatSheathing.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="lappLiApp.flatSheathing.home.createLabel">Create new Flat Sheathing</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {flatSheathingList && flatSheathingList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="lappLiApp.flatSheathing.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.flatSheathing.operationLayer">Operation Layer</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.flatSheathing.sheathingKind">Sheathing Kind</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.flatSheathing.milimeterWidth">Milimeter Width</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.flatSheathing.milimeterHeight">Milimeter Height</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.flatSheathing.material">Material</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.flatSheathing.ownerStrandSupply">Owner Strand Supply</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {flatSheathingList.map((flatSheathing, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${flatSheathing.id}`} color="link" size="sm">
                      {flatSheathing.id}
                    </Button>
                  </td>
                  <td>{flatSheathing.operationLayer}</td>
                  <td>
                    <Translate contentKey={`lappLiApp.SheathingKind.${flatSheathing.sheathingKind}`} />
                  </td>
                  <td>{flatSheathing.milimeterWidth}</td>
                  <td>{flatSheathing.milimeterHeight}</td>
                  <td>
                    {flatSheathing.material ? (
                      <Link to={`material/${flatSheathing.material.id}`}>{flatSheathing.material.designation}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {flatSheathing.ownerStrandSupply ? (
                      <Link to={`strand-supply/${flatSheathing.ownerStrandSupply.id}`}>{flatSheathing.ownerStrandSupply.designation}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${flatSheathing.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${flatSheathing.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${flatSheathing.id}/delete`}
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
              <Translate contentKey="lappLiApp.flatSheathing.home.notFound">No Flat Sheathings found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default FlatSheathing;
