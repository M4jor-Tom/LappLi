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
      <h2 data-cy="strandDetailsHeading">
        <Translate contentKey="lappLiApp.strand.detail.title">Strand</Translate>
      </h2>
      <div className="table-responsive">
        {strandEntity.centralAssembly ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="lappLiApp.strand.operationKind">Operation Kind</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.assembly.operationLayer">Operation Layer</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.assembly.productionStep">Production Step</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.assembly.diameterAssemblyStep">Assembly Step (D)</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.assembly.assemblyMean">Assembly Mean</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {strandEntity.centralAssembly ? (
                <tr data-cy="entityTable">
                  <td>
                    <Translate contentKey="lappLiApp.centralAssembly.home.title" />
                  </td>
                  <td>
                    <Translate contentKey="lappLiApp.centralAssembly.centralOperationLayer" />
                  </td>
                  <td>{strandEntity.centralAssembly.productionStep}</td>
                  <td>{/* NO ASSEMBLY STEP (CENTRAL ASSEMBLY) */}</td>
                  <td>{/* NO ASSEMBLY MEAN (CENTRAL ASSEMBLY) */}</td>
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
              ) : (
                ''
              )}
              {strandEntity.coreAssemblies
                ? strandEntity.coreAssemblies.map((coreAssembly, i) => (
                    <tr key={`entity-core-assembly-${i}`} data-cy="entityTable">
                      <td>
                        <Translate contentKey="lappLiApp.coreAssembly.home.title" />
                      </td>
                      <td>{coreAssembly.operationLayer}</td>
                      <td>{coreAssembly.productionStep}</td>
                      <td>{coreAssembly.diameterAssemblyStep}</td>
                      <td>{coreAssembly.assemblyMean}</td>
                      <td className="text-right">
                        <div className="btn-group flex-btn-group-container">
                          <Button tag={Link} to={`${match.url}/${coreAssembly.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                            <FontAwesomeIcon icon="eye" />{' '}
                            <span className="d-none d-md-inline">
                              <Translate contentKey="entity.action.view">View</Translate>
                            </span>
                          </Button>
                          <Button
                            tag={Link}
                            to={`${match.url}/${coreAssembly.id}/edit`}
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
                            to={`${match.url}/${coreAssembly.id}/delete`}
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
                  ))
                : ''}
              {strandEntity.intersticeAssemblies
                ? strandEntity.intersticeAssemblies.map((intersticialAssembly, i) => (
                    <tr key={`entity-interstice-assembly-${i}`} data-cy="entityTable">
                      <td>
                        <Translate contentKey="lappLiApp.intersticeAssembly.home.title" />
                      </td>
                      <td>{intersticialAssembly.operationLayer}</td>
                      <td>{intersticialAssembly.productionStep}</td>
                      <td>{intersticialAssembly.diameterAssemblyStep}</td>
                      <td>{intersticialAssembly.assemblyMean}</td>
                      <td className="text-right">
                        <div className="btn-group flex-btn-group-container">
                          <Button
                            tag={Link}
                            to={`${match.url}/${intersticialAssembly.id}`}
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
                            to={`${match.url}/${intersticialAssembly.id}/edit`}
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
                            to={`${match.url}/${intersticialAssembly.id}/delete`}
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
                  ))
                : ''}
            </tbody>
          </Table>
        ) : (
          <div className="alert alert-warning">
            <Translate contentKey="lappLiApp.assembly.home.notFound">No Assemblies found</Translate>
          </div>
        )}
        {strandEntity.centralAssembly ? (
          ''
        ) : (
          <>
            <Link
              to={`central-assembly/new`}
              className="btn btn-primary jh-create-entity"
              id="jh-create-entity"
              data-cy="entityCreateButton"
            >
              <FontAwesomeIcon icon="plus" />
              &nbsp;
              <Translate contentKey="lappLiApp.centralAssembly.home.createLabel">Create new Central Assembly</Translate>
            </Link>
            &nbsp;
          </>
        )}
        <Link to={`core-assembly/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="lappLiApp.coreAssembly.home.createLabel">Create new Core Assembly</Translate>
        </Link>
        &nbsp;
        <Link
          to={`interstice-assembly/new`}
          className="btn btn-primary jh-create-entity"
          id="jh-create-entity"
          data-cy="entityCreateButton"
        >
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="lappLiApp.coreAssembly.home.createLabel">Create new Core Assembly</Translate>
        </Link>
      </div>
    </div>
  );
};

export default StrandSubOperation;
