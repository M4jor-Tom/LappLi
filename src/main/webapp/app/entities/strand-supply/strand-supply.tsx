import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './strand-supply.reducer';
import { IStrandSupply } from 'app/shared/model/strand-supply.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const StrandSupply = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const strandSupplyList = useAppSelector(state => state.strandSupply.entities);
  const loading = useAppSelector(state => state.strandSupply.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="strand-supply-heading" data-cy="StrandSupplyHeading">
        <Translate contentKey="lappLiApp.strandSupply.home.title">Strand Supplies</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="lappLiApp.strandSupply.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="lappLiApp.strandSupply.home.createLabel">Create new Strand Supply</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {strandSupplyList && strandSupplyList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="lappLiApp.strandSupply.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.strandSupply.supplyState">Supply State</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.strandSupply.apparitions">Apparitions</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.strandSupply.markingType">Marking Type</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.strandSupply.description">Description</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.strandSupply.strand">Strand</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.strandSupply.study">Study</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {strandSupplyList.map((strandSupply, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${strandSupply.id}`} color="link" size="sm">
                      {strandSupply.id}
                    </Button>
                  </td>
                  <td>
                    <Translate contentKey={`lappLiApp.SupplyState.${strandSupply.supplyState}`} />
                  </td>
                  <td>{strandSupply.apparitions}</td>
                  <td>
                    <Translate contentKey={`lappLiApp.MarkingType.${strandSupply.markingType}`} />
                  </td>
                  <td>{strandSupply.description}</td>
                  <td>
                    {strandSupply.strand ? <Link to={`strand/${strandSupply.strand.id}`}>{strandSupply.strand.designation}</Link> : ''}
                  </td>
                  <td>{strandSupply.study ? <Link to={`study/${strandSupply.study.id}`}>{strandSupply.study.number}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${strandSupply.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${strandSupply.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${strandSupply.id}/delete`}
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
              <Translate contentKey="lappLiApp.strandSupply.home.notFound">No Strand Supplies found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default StrandSupply;
