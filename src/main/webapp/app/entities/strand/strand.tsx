import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './strand.reducer';
import { IStrand } from 'app/shared/model/strand.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Strand = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const strandList = useAppSelector(state => state.strand.entities);
  const loading = useAppSelector(state => state.strand.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="strand-heading" data-cy="StrandHeading">
        <Translate contentKey="lappLiApp.strand.home.title">Strands</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="lappLiApp.strand.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="lappLiApp.strand.home.createLabel">Create new Strand</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {strandList && strandList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="lappLiApp.strand.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.strand.designation">Designation</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.strand.futureStudy">Future Study</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {strandList.map((strand, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${strand.id}`} color="link" size="sm">
                      {strand.id}
                    </Button>
                  </td>
                  <td>{strand.designation}</td>
                  <td>{strand.futureStudy ? <Link to={`study/${strand.futureStudy.id}`}>{strand.futureStudy.number}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${strand.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${strand.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${strand.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="lappLiApp.strand.home.notFound">No Strands found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Strand;
