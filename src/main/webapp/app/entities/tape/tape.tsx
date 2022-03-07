import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './tape.reducer';
import { ITape } from 'app/shared/model/tape.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Tape = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const tapeList = useAppSelector(state => state.tape.entities);
  const loading = useAppSelector(state => state.tape.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="tape-heading" data-cy="TapeHeading">
        <Translate contentKey="lappLiApp.tape.home.title">Tapes</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="lappLiApp.tape.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="lappLiApp.tape.home.createLabel">Create new Tape</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {tapeList && tapeList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="lappLiApp.tape.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.tape.number">Number</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.tape.designation">Designation</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.tape.milimeterWidth">Milimeter Width</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.tape.milimeterDiameterIncidency">Milimeter Diameter Incidency</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.tape.tapeKind">Tape Kind</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {tapeList.map((tape, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${tape.id}`} color="link" size="sm">
                      {tape.id}
                    </Button>
                  </td>
                  <td>{tape.number}</td>
                  <td>{tape.designation}</td>
                  <td>{tape.milimeterWidth}</td>
                  <td>{tape.milimeterDiameterIncidency}</td>
                  <td>{tape.tapeKind ? <Link to={`tape-kind/${tape.tapeKind.id}`}>{tape.tapeKind.designation}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${tape.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${tape.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${tape.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="lappLiApp.tape.home.notFound">No Tapes found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Tape;
