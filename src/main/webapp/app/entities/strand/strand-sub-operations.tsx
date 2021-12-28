import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './strand.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const StrandSubOperation = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const strandEntity = useAppSelector(state => state.strand.entity);

  const { match } = props;

  return (
    <div>
      <h2 id="central-assembly-heading" data-cy="CentralAssemblyHeading">
        <Translate contentKey="lappLiApp.centralAssembly.home.title">Central Assemblies</Translate>
        <div className="d-flex justify-content-end">
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="lappLiApp.centralAssembly.home.createLabel">Create new Central Assembly</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {strandEntity.centralAssembly ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="lappLiApp.assembly.operationLayer">Operation Layer</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.assembly.productionStep">Production Step</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.assembly.position">Position</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {
                <tr data-cy="entityTable">
                  <td>
                    <Translate contentKey="lappLiApp.centralAssembly.centralOperationLayer" />
                  </td>
                  <td>{strandEntity.centralAssembly.productionStep}</td>
                  <td>
                    {strandEntity.centralAssembly.position ? (
                      <Link to={`position/${strandEntity.centralAssembly.position.id}`}>{strandEntity.centralAssembly.position.value}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`${match.url}/${strandEntity.centralAssembly.id}`}
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
                        to={`${match.url}/${strandEntity.centralAssembly.id}/edit`}
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
                        to={`${match.url}/${strandEntity.centralAssembly.id}/delete`}
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
              }
            </tbody>
          </Table>
        ) : (
          <div className="alert alert-warning">
            <Translate contentKey="lappLiApp.centralAssembly.home.notFound">No Central Assemblies found</Translate>
          </div>
        )}
      </div>
    </div>
  );
};

export default StrandSubOperation;
