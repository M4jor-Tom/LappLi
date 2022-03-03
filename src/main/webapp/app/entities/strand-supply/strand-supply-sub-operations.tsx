import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Table } from 'reactstrap';
import { translate, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from '../strand-supply/strand-supply.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getOut } from '../index-management/index-management-lib';
import { defaultValue as strandDefaultValue } from 'app/shared/model/strand.model';
import { ISheathing } from 'app/shared/model/sheathing.model';
import { OperationKind } from 'app/shared/model/enumerations/operation-kind.model';
import { IStrandSupply } from 'app/shared/model/strand-supply.model';
import CentralAssembly from '../central-assembly/central-assembly';

export const StrandSupplySubOperation = (props: RouteComponentProps<{ strand_supply_id: string; study_id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.strand_supply_id));
  }, []);

  const strandSupplyEntity: IStrandSupply = useAppSelector(state => state.strandSupply.entity);

  const { match } = props;

  const getOutCount: number = props.match.params.study_id ? 2 : 0;

  return (
    <div>
      <h2 data-cy="strandDetailsHeading">
        <Translate contentKey="lappLiApp.strand.detail.title">Strand</Translate>
      </h2>
      {strandSupplyEntity.hasAssemblies ? (
        <Button tag={Link} to={`${props.match.url}/assemblies/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
          <FontAwesomeIcon icon="trash" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="lappLiApp.assembly.detail.title">Assembly</Translate>
          </span>
        </Button>
      ) : (
        <Link
          to={`${props.match.url}/assemblies/new`}
          className="btn btn-primary jh-create-entity"
          id="jh-create-entity"
          data-cy="entityCreateButton"
        >
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="lappLiApp.assembly.detail.title">Assembly</Translate>
        </Link>
      )}
      &nbsp;
      <Link
        to={`${props.match.url}/sheathing/new`}
        className="btn btn-primary jh-create-entity"
        id="jh-create-entity"
        data-cy="entityCreateButton"
      >
        <FontAwesomeIcon icon="plus" />
        &nbsp;
        <Translate contentKey="lappLiApp.sheathing.detail.title">Sheathing</Translate>
      </Link>
      <div className="table-responsive">
        {strandSupplyEntity.centralAssembly ||
        (strandSupplyEntity.coreAssemblies && strandSupplyEntity.coreAssemblies.length > 0) ||
        (strandSupplyEntity.intersticeAssemblies && strandSupplyEntity.intersticeAssemblies.length > 0) ||
        (strandSupplyEntity.nonAssemblyOperations && strandSupplyEntity.nonAssemblyOperations.length > 0) ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="lappLiApp.strandSupply.operationKind">Operation Kind</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.operation.layer">Layer</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.assembly.suppliedComponentsCount">Components Count</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.operation.productionStep">Production Step</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.operation.productDesignation">Product Designation</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.operation.milimeterDiameterIncidency">Diameter Incidency (mm)</Translate>
                </th>
                <th>
                  <Translate contentKey="lappLiApp.operation.afterThisMilimeterDiameter">Diameter After This (mm)</Translate>
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
              {strandSupplyEntity.centralAssembly && strandSupplyEntity.centralAssembly.supplyPosition ? (
                <tr data-cy="entityTable">
                  <td>
                    <Translate contentKey="lappLiApp.centralAssembly.home.title" />
                  </td>
                  <td>
                    <Translate contentKey="lappLiApp.centralAssembly.centralOperationLayer" />
                  </td>
                  <td>
                    {translate('lappLiApp.assembly.utilitySuppliedComponentsCount') + ':'}&nbsp;
                    {strandSupplyEntity.centralAssembly.utilityComponentsCount}
                    <br />
                    {translate('lappLiApp.assembly.completionSuppliedComponentsCount') + ':'}&nbsp;
                    {strandSupplyEntity.centralAssembly.completionComponentsCount}
                  </td>
                  <td>{strandSupplyEntity.centralAssembly.productionStep}</td>
                  <td>{/* NO PRODUCT DESIGNATION (ASSEMBLY) */}</td>
                  <td>{strandSupplyEntity.centralAssembly.mullerStandardizedFormatMilimeterDiameterIncidency}</td>
                  <td>{strandSupplyEntity.centralAssembly.mullerStandardizedFormatAfterThisMilimeterDiameter}</td>
                  <td>{/* NO ASSEMBLY STEP (CENTRAL ASSEMBLY) */}</td>
                  <td>{/* NO ASSEMBLY MEAN (CENTRAL ASSEMBLY) */}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`${props.match.url}/central-assembly/${strandSupplyEntity.centralAssembly.id}/supply`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="lappLiApp.assembly.subSupply">Assembly Supply</Translate>
                        </span>
                      </Button>
                      &nbsp;
                      <Button
                        tag={Link}
                        to={`${props.match.url}/central-assembly/${strandSupplyEntity.centralAssembly.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      &nbsp;
                      <Button
                        tag={Link}
                        to={`${props.match.url}/central-assembly/${strandSupplyEntity.centralAssembly.id}/delete`}
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
              {strandSupplyEntity.coreAssemblies
                ? strandSupplyEntity.coreAssemblies.map((coreAssembly, i) => (
                    <tr key={`entity-core-assembly-${i}`} data-cy="entityTable">
                      <td>
                        <Translate contentKey="lappLiApp.coreAssembly.home.title" />
                      </td>
                      <td>{coreAssembly.operationLayer}</td>
                      <td>
                        {translate('lappLiApp.assembly.utilitySuppliedComponentsCount') + ':'}&nbsp;
                        {coreAssembly.utilityComponentsCount}
                        <br />
                        {translate('lappLiApp.assembly.completionSuppliedComponentsCount') + ':'}&nbsp;
                        {coreAssembly.completionComponentsCount}
                      </td>
                      <td>{coreAssembly.productionStep}</td>
                      <td>{/* NO PRODUCT DESIGNATION (ASSEMBLY) */}</td>
                      <td>{coreAssembly.mullerStandardizedFormatMilimeterDiameterIncidency}</td>
                      <td>
                        {coreAssembly.mullerStandardizedFormatBeforeThisMilimeterDiameter +
                          ' -> ' +
                          coreAssembly.mullerStandardizedFormatAfterThisMilimeterDiameter}
                      </td>
                      <td>{coreAssembly.diameterAssemblyStep}</td>
                      <td>{coreAssembly.assemblyMean}</td>
                      <td className="text-right">
                        <div className="btn-group flex-btn-group-container">
                          <Button
                            tag={Link}
                            to={`${props.match.url}/core-assembly/${coreAssembly.id}/supply`}
                            color="primary"
                            size="sm"
                            data-cy="entityEditButton"
                          >
                            <FontAwesomeIcon icon="pencil-alt" />{' '}
                            <span className="d-none d-md-inline">
                              <Translate contentKey="lappLiApp.assembly.subSupply">Assembly Supply</Translate>
                            </span>
                          </Button>
                          &nbsp;
                          <Button
                            tag={Link}
                            to={`${props.match.url}/core-assembly/${coreAssembly.id}/edit`}
                            color="primary"
                            size="sm"
                            data-cy="entityEditButton"
                          >
                            <FontAwesomeIcon icon="pencil-alt" />{' '}
                            <span className="d-none d-md-inline">
                              <Translate contentKey="entity.action.edit">Edit</Translate>
                            </span>
                          </Button>
                          &nbsp;
                          <Button
                            tag={Link}
                            to={`${props.match.url}/core-assembly/${coreAssembly.id}/delete`}
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
              {strandSupplyEntity.intersticeAssemblies
                ? strandSupplyEntity.intersticeAssemblies.map((intersticeAssembly, i) => (
                    <tr key={`entity-interstice-assembly-${i}`} data-cy="entityTable">
                      <td>
                        <Translate contentKey="lappLiApp.intersticeAssembly.home.title" />
                      </td>
                      <td>{intersticeAssembly.operationLayer}</td>
                      <td>
                        {translate('lappLiApp.assembly.utilitySuppliedComponentsCount') + ':'}&nbsp;
                        {intersticeAssembly.utilityComponentsCount}
                        <br />
                        {translate('lappLiApp.assembly.completionSuppliedComponentsCount') + ':'}&nbsp;
                        {intersticeAssembly.completionComponentsCount}
                      </td>
                      <td>{intersticeAssembly.productionStep}</td>
                      <td>{/* NO PRODUCT DESIGNATION (ASSEMBLY) */}</td>
                      <td>{intersticeAssembly.mullerStandardizedFormatMilimeterDiameterIncidency}</td>
                      <td>{intersticeAssembly.mullerStandardizedFormatAfterThisMilimeterDiameter}</td>
                      <td>{intersticeAssembly.diameterAssemblyStep}</td>
                      <td>{intersticeAssembly.assemblyMean}</td>
                      <td className="text-right">
                        <div className="btn-group flex-btn-group-container">
                          <Button
                            tag={Link}
                            to={`${props.match.url}/interstice-assembly/${intersticeAssembly.id}/supply`}
                            color="primary"
                            size="sm"
                            data-cy="entityEditButton"
                          >
                            <FontAwesomeIcon icon="pencil-alt" />{' '}
                            <span className="d-none d-md-inline">
                              <Translate contentKey="lappLiApp.assembly.subSupply">Assembly Supply</Translate>
                            </span>
                          </Button>
                          &nbsp;
                          <Button
                            tag={Link}
                            to={`${props.match.url}/interstice-assembly/${intersticeAssembly.id}/edit`}
                            color="primary"
                            size="sm"
                            data-cy="entityEditButton"
                          >
                            <FontAwesomeIcon icon="pencil-alt" />{' '}
                            <span className="d-none d-md-inline">
                              <Translate contentKey="entity.action.edit">Edit</Translate>
                            </span>
                          </Button>
                          &nbsp;
                          <Button
                            tag={Link}
                            to={`${props.match.url}/interstice-assembly/${intersticeAssembly.id}/delete`}
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
            {strandSupplyEntity.nonAssemblyOperations && strandSupplyEntity.nonAssemblyOperations.length > 0 ? (
              <tbody>
                {strandSupplyEntity.nonAssemblyOperations.map((operation, i) => (
                  <tr key={`entity-operation-${i}`} data-cy="entityTable">
                    <td>
                      {translate('lappLiApp.OperationKind.' + operation.operationKind)}
                      <br />
                      {operation.operationKind === OperationKind.SHEATHING
                        ? translate('lappLiApp.SheathingKind.' + (operation as ISheathing).sheathingKind)
                        : ''}
                    </td>
                    <td>{operation.operationLayer}</td>
                    <td>{operation.productionStep}</td>
                    <td>{operation.productDesignation}</td>
                    <td>{operation.mullerStandardizedFormatMilimeterDiameterIncidency}</td>
                    <td>{operation.mullerStandardizedFormatAfterThisMilimeterDiameter}</td>
                    <td>{/* NO ASSEMBLY STEP */}</td>
                    <td>{/* NO ASSEMBLY MEAN */}</td>

                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button
                          tag={Link}
                          to={`${props.match.url}/${operation.operationKind.toLowerCase()}/${operation.id}/edit`}
                          color="primary"
                          size="sm"
                          data-cy="entityEditButton"
                        >
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        &nbsp;
                        <Button
                          tag={Link}
                          to={`${props.match.url}/${operation.operationKind.toLowerCase()}/${operation.id}/delete`}
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
            ) : (
              ''
            )}
          </Table>
        ) : (
          <div className="alert alert-warning">
            <Translate contentKey="lappLiApp.operation.home.notFound">No operation found</Translate>
          </div>
        )}
        <Button tag={Link} to={getOut(props.match.url, getOutCount)} replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
      </div>
    </div>
  );
};

export default StrandSupplySubOperation;
