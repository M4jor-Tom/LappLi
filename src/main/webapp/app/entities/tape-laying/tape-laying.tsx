import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './tape-laying.reducer';
import { ITapeLaying } from 'app/shared/model/tape-laying.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const TapeLaying = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const tapeLayingList = useAppSelector(state => state.tapeLaying.entities);
  const loading = useAppSelector(state => state.tapeLaying.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="tape-laying-heading" data-cy="TapeLayingHeading">
        <Translate contentKey="lappLiApp.tapeLaying.home.title">Tape Layings</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="lappLiApp.tapeLaying.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="lappLiApp.tapeLaying.home.createLabel">Create new Tape Laying</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {tapeLayingList && tapeLayingList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="lappLiApp.tapeLaying.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.tapeLaying.operationLayer">Operation Layer</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.tapeLaying.assemblyMean">Assembly Mean</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.tapeLaying.tape">Tape</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {tapeLayingList.map((tapeLaying, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${tapeLaying.id}`} color="link" size="sm">
                      {tapeLaying.id}
                    </Button>
                  </td>
                  <td>{tapeLaying.operationLayer}</td>
                  <td>
                    <Translate contentKey={`lappLiApp.AssemblyMean.${tapeLaying.assemblyMean}`} />
                  </td>
                  <td>{tapeLaying.tape ? <Link to={`tape/${tapeLaying.tape.id}`}>{tapeLaying.tape.designation}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${tapeLaying.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${tapeLaying.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${tapeLaying.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="lappLiApp.tapeLaying.home.notFound">No Tape Layings found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default TapeLaying;
