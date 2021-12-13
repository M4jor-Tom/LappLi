import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './i-supply.reducer';
import { IISupply } from 'app/shared/model/i-supply.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ISupply = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const iSupplyList = useAppSelector(state => state.iSupply.entities);
  const loading = useAppSelector(state => state.iSupply.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="i-supply-heading" data-cy="ISupplyHeading">
        <Translate contentKey="lappLiApp.iSupply.home.title">I Supplies</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="lappLiApp.iSupply.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="lappLiApp.iSupply.home.createLabel">Create new I Supply</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {iSupplyList && iSupplyList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="lappLiApp.iSupply.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.iSupply.apparitions">Apparitions</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.iSupply.milimeterDiameter">Milimeter Diameter</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.iSupply.gramPerMeterLinearMass">Gram Per Meter Linear Mass</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.iSupply.strand">Strand</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {iSupplyList.map((iSupply, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${iSupply.id}`} color="link" size="sm">
                      {iSupply.id}
                    </Button>
                  </td>
                  <td>{iSupply.apparitions}</td>
                  <td>{iSupply.milimeterDiameter}</td>
                  <td>{iSupply.gramPerMeterLinearMass}</td>
                  <td>{iSupply.strand ? <Link to={`strand/${iSupply.strand.id}`}>{iSupply.strand.designation}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${iSupply.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${iSupply.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${iSupply.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="lappLiApp.iSupply.home.notFound">No I Supplies found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default ISupply;
