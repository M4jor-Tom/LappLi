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
import { ISheathing, isSheathing } from 'app/shared/model/sheathing.model';
import { OperationKind } from 'app/shared/model/enumerations/operation-kind.model';
import { IStrandSupply } from 'app/shared/model/strand-supply.model';
import CentralAssembly from '../central-assembly/central-assembly';

import { IAbstractNonCentralAssembly, isNonCentralAssembly } from 'app/shared/model/abstract-non-central-assembly.model';
import { isCoreAssembly } from 'app/shared/model/core-assembly.model';
import { IAbstractOperation } from 'app/shared/model/abstract-operation.model';
import { isAssembly } from 'app/shared/model/abstract-assembly.model';
import { isAssemblableOperation } from 'app/shared/model/assemblable-operation.model';
import { isMeanedAssemblableOperation } from 'app/shared/model/meaned-assemblable-operation.model';
import { escapeRegExp } from 'lodash';
import {
  IAbstractBobinsCountOwnerOperation,
  isBobinsCountOwnerOperation,
} from 'app/shared/model/abstract-bobins-count-owner-operation.model';
import { isFlatSheathing } from 'app/shared/model/flat-sheathing.model';
import { isAbstractSheathing } from 'app/shared/model/abstract-sheathing.model';

function replaceAll(str: string, find: string, replace: string): string {
  return str.replace(new RegExp(escapeRegExp(find), 'g'), replace);
}

export const StrandSupplySubOperation = (props: RouteComponentProps<{ strand_supply_id: string; study_id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.strand_supply_id));
  }, []);

  const strandSupplyEntity: IStrandSupply = useAppSelector(state => state.strandSupply.entity);

  const { match } = props;

  const getOutCount: number = props.match.params.study_id ? 2 : 0;

  const componentsRender = (operation: IAbstractOperation) => {
    if (isAssembly(operation)) {
      return componentsCountRender(operation);
    } else if (isBobinsCountOwnerOperation(operation)) {
      return bobinsCountRender(operation);
    }

    return '';
  };

  const componentsCountRender = (operation: IAbstractNonCentralAssembly) => {
    return (
      translate('lappLiApp.assembly.utilitySuppliedComponentsCount') +
      ':' +
      String.fromCharCode(160) +
      operation.utilityComponentsCount +
      '\n' +
      translate('lappLiApp.assembly.completionSuppliedComponentsCount') +
      ':' +
      String.fromCharCode(160) +
      operation.completionComponentsCount
    );
  };

  const bobinsCountRender = (operation: IAbstractBobinsCountOwnerOperation) => {
    return (
      translate('lappLiApp.operation.componentsInformationsDescription.bobinsCount') +
      ':' +
      String.fromCharCode(160) +
      operation.bobinsCount
    );
  };

  const strandSupplyOperationListRender = (strandSupply: IStrandSupply) => {
    if (strandSupply.isFlat) {
      return flatStrandSupplyOperationListRender(strandSupply);
    }

    return cylindricStrandSupplyOperationListRender(strandSupply);
  };

  const operationKindRender = (operation: IAbstractOperation) => {
    return (
      <>
        {translate('lappLiApp.OperationKind.' + operation.operationKind)}
        <br />
        {isAbstractSheathing(operation)
          ? translate('lappLiApp.SheathingKind.' + operation.sheathingKind) + '\n' + operation.material.designation
          : ''}
      </>
    );
  };

  const flatStrandSupplyOperationListRender = (strandSupply: IStrandSupply) => {
    return (
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
              <Translate contentKey="lappLiApp.operation.operatingMachine">Operating Machine</Translate>
            </th>
            <th>
              <Translate contentKey="lappLiApp.operation.hourPreparationTime">Preparation Time (h)</Translate>
            </th>
            <th>
              <Translate contentKey="lappLiApp.operation.hourExecutionTime">Execution Time (h)</Translate>
            </th>
            <th>
              <Translate contentKey="lappLiApp.operation.milimeterWidth">Width (mm)</Translate>
            </th>
            <th>
              <Translate contentKey="lappLiApp.operation.milimeterHeight">Height (mm)</Translate>
            </th>
            <th>
              <Translate contentKey="lappLiApp.operation.kilogramPerKilometerLinearMass">Linear Mass (kg/km)</Translate>
            </th>
            <th>
              <Translate contentKey="lappLiApp.sheathing.squareMilimeterSurfaceToSheath">Surface to sheath (mm²)</Translate>
            </th>
          </tr>
        </thead>
        <tbody>
          {strandSupply.nonCentralOperations && strandSupply.nonCentralOperations.length > 0
            ? strandSupply.nonCentralOperations.map((operation, i) => (
                <tr key={`entity-operation-${i}`} data-cy="entityTable">
                  <td>{operationKindRender(operation)}</td>
                  <td>{operation.operationLayer}</td>
                  <td>{operation.operatingMachine?.name}</td>
                  <td>{operation.mullerStandardizedFormatHourPreparationTime}</td>
                  <td>{operation.mullerStandardizedFormatHourExecutionTimeForAllStrandSupplies}</td>
                  <td>{isFlatSheathing(operation) ? operation.milimeterWidth : ''}</td>
                  <td>{isFlatSheathing(operation) ? operation.milimeterHeight : ''}</td>
                  <td>{isFlatSheathing(operation) ? operation.mullerStandardizedFormatKilogramPerKilometerLinearMass : ''}</td>
                  <td>{isFlatSheathing(operation) ? operation.mullerStandardizedFormatSquareMilimeterSurfaceToSheath : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`${props.match.url}/${replaceAll(operation.operationKind.toLowerCase(), '_', '-')}/${operation.id}/edit`}
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
                        to={`${props.match.url}/${replaceAll(operation.operationKind.toLowerCase(), '_', '-')}/${operation.id}/delete`}
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
    );
  };

  const cylindricStrandSupplyOperationListRender = (strandSupply: IStrandSupply) => {
    return (
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
              <Translate contentKey="lappLiApp.operation.operatingMachine">Operating Machine</Translate>
            </th>
            <th>
              <Translate contentKey="lappLiApp.operation.hourPreparationTime">Preparation Time (h)</Translate>
            </th>
            <th>
              <Translate contentKey="lappLiApp.operation.hourExecutionTime">Execution Time (h)</Translate>
            </th>
            <th>
              <Translate contentKey="lappLiApp.operation.componentsInformations">Components Informations</Translate>
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
            <th>
              <Translate contentKey="lappLiApp.assembly.milimeterAssemblyVoid">Assembly Void</Translate>
            </th>
            <th />
          </tr>
        </thead>
        <tbody>
          {strandSupply.centralAssembly && strandSupply.centralAssembly.supplyPosition ? (
            <tr data-cy="entityTable">
              <td>
                <Translate contentKey="lappLiApp.centralAssembly.home.title" />
              </td>
              <td>
                <Translate contentKey="lappLiApp.centralAssembly.centralOperationLayer" />
              </td>
              <td>{strandSupply.centralAssembly.operatingMachine?.name}</td>
              <td>{strandSupply.centralAssembly.mullerStandardizedFormatHourPreparationTime}</td>
              <td>{strandSupply.centralAssembly.mullerStandardizedFormatHourExecutionTimeForAllStrandSupplies}</td>
              <td>
                {translate('lappLiApp.assembly.utilitySuppliedComponentsCount') + ':'}&nbsp;
                {strandSupply.centralAssembly.utilityComponentsCount}
                <br />
                {translate('lappLiApp.assembly.completionSuppliedComponentsCount') + ':'}&nbsp;
                {strandSupply.centralAssembly.completionComponentsCount}
              </td>
              <td>{strandSupply.centralAssembly.productionStep}</td>
              <td>{/* NO PRODUCT DESIGNATION (ASSEMBLY) */}</td>
              <td>{strandSupply.centralAssembly.mullerStandardizedFormatMilimeterDiameterIncidency}</td>
              <td>{strandSupply.centralAssembly.mullerStandardizedFormatAfterThisMilimeterDiameter}</td>
              <td>{/* NO ASSEMBLY STEP (CENTRAL ASSEMBLY) */}</td>
              <td>{/* NO ASSEMBLY MEAN (CENTRAL ASSEMBLY) */}</td>
              <td>{/* NO ASSEMBLY VOID (CENTRAL ASSEMBLY) */}</td>
              <td className="text-right">
                <div className="btn-group flex-btn-group-container">
                  <Button
                    tag={Link}
                    to={`${props.match.url}/central-assembly/${strandSupply.centralAssembly.id}/supply`}
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
                    to={`${props.match.url}/central-assembly/${strandSupply.centralAssembly.id}/edit`}
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
                    to={`${props.match.url}/central-assembly/${strandSupply.centralAssembly.id}/delete`}
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
        </tbody>
        {strandSupply.nonCentralOperations && strandSupply.nonCentralOperations.length > 0 ? (
          <tbody>
            {strandSupply.nonCentralOperations.map((operation, i) => (
              <tr key={`entity-operation-${i}`} data-cy="entityTable">
                <td>{operationKindRender(operation)}</td>
                <td>{operation.operationLayer}</td>
                <td>{operation.operatingMachine?.name}</td>
                <td>{operation.mullerStandardizedFormatHourPreparationTime}</td>
                <td>{operation.mullerStandardizedFormatHourExecutionTimeForAllStrandSupplies}</td>
                <td>{componentsRender(operation)}</td>
                <td>{operation.productionStep}</td>
                <td>{operation.productDesignation}</td>
                <td>{operation.mullerStandardizedFormatMilimeterDiameterIncidency}</td>
                <td>
                  {operation.mullerStandardizedFormatBeforeThisMilimeterDiameter}
                  &nbsp;&#62;&nbsp;
                  {operation.mullerStandardizedFormatAfterThisMilimeterDiameter}
                </td>
                <td>{isAssemblableOperation(operation) ? operation.mullerStandardizedFormatDiameterAssemblyStep : ''}</td>
                <td>{isMeanedAssemblableOperation(operation) ? operation.assemblyMean : ''}</td>
                <td>{isCoreAssembly(operation) ? operation.mullerStandardizedFormatMilimeterAssemblyVoid : ''}</td>
                <td className="text-right">
                  <div className="btn-group flex-btn-group-container">
                    <Button
                      tag={Link}
                      to={`${props.match.url}/${replaceAll(operation.operationKind.toLowerCase(), '_', '-')}/${operation.id}/edit`}
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
                      to={`${props.match.url}/${replaceAll(operation.operationKind.toLowerCase(), '_', '-')}/${operation.id}/delete`}
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
    );
  };

  return (
    <div>
      <h2 data-cy="strandDetailsHeading">
        <Translate contentKey="lappLiApp.strand.detail.title">Strand</Translate>
      </h2>
      {strandSupplyEntity.hasAssemblies ? (
        <>
          <Button tag={Link} to={`${props.match.url}/assemblies/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
            <FontAwesomeIcon icon="trash" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="lappLiApp.assembly.detail.title">Assembly</Translate>
            </span>
          </Button>
          &nbsp;
          <Button
            tag={Link}
            to={`${props.match.url}/strand-supply/${strandSupplyEntity.id}/edit`}
            color="primary"
            size="sm"
            data-cy="entityEditButton"
          >
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="lappLiApp.assembly.detail.title">Assembly</Translate>
            </span>
          </Button>
        </>
      ) : strandSupplyEntity.couldBeCylindric ? (
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
      ) : (
        ''
      )}
      {/* [NON_CENTRAL_OPERATION] */}
      &nbsp;
      <Link
        to={`${props.match.url}/tape-laying/new`}
        className="btn btn-primary jh-create-entity"
        id="jh-create-entity"
        data-cy="entityCreateButton"
      >
        <FontAwesomeIcon icon="plus" />
        &nbsp;
        <Translate contentKey="lappLiApp.tapeLaying.detail.title">Tape Laying</Translate>
      </Link>
      &nbsp;
      <Link
        to={`${props.match.url}/screen/new`}
        className="btn btn-primary jh-create-entity"
        id="jh-create-entity"
        data-cy="entityCreateButton"
      >
        <FontAwesomeIcon icon="plus" />
        &nbsp;
        <Translate contentKey="lappLiApp.screen.detail.title">Screen</Translate>
      </Link>
      &nbsp;
      <Link
        to={`${props.match.url}/strip-laying/new`}
        className="btn btn-primary jh-create-entity"
        id="jh-create-entity"
        data-cy="entityCreateButton"
      >
        <FontAwesomeIcon icon="plus" />
        &nbsp;
        <Translate contentKey="lappLiApp.stripLaying.detail.title">Strip Laying</Translate>
      </Link>
      &nbsp;
      <Link
        to={`${props.match.url}/plait/new`}
        className="btn btn-primary jh-create-entity"
        id="jh-create-entity"
        data-cy="entityCreateButton"
      >
        <FontAwesomeIcon icon="plus" />
        &nbsp;
        <Translate contentKey="lappLiApp.plait.detail.title">Plait</Translate>
      </Link>
      &nbsp;
      <Link
        to={`${props.match.url}/carrier-plait/new`}
        className="btn btn-primary jh-create-entity"
        id="jh-create-entity"
        data-cy="entityCreateButton"
      >
        <FontAwesomeIcon icon="plus" />
        &nbsp;
        <Translate contentKey="lappLiApp.carrierPlait.detail.title">Carrier Plait</Translate>
      </Link>
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
      &nbsp;
      {strandSupplyEntity.couldBeFlat ? (
        <Link
          to={`${props.match.url}/flat-sheathing/new`}
          className="btn btn-primary jh-create-entity"
          id="jh-create-entity"
          data-cy="entityCreateButton"
        >
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="lappLiApp.flatSheathing.detail.title">Flat Sheathing</Translate>
        </Link>
      ) : (
        ''
      )}
      {strandSupplyEntity.couldBeFlat ? <>&nbsp;</> : ''}
      <Link
        to={`${props.match.url}/continuity-wire-longit-laying/new`}
        className="btn btn-primary jh-create-entity"
        id="jh-create-entity"
        data-cy="entityCreateButton"
      >
        <FontAwesomeIcon icon="plus" />
        &nbsp;
        <Translate contentKey="lappLiApp.continuityWireLongitLaying.detail.title">Continuity Wire Longitudinal Laying</Translate>
      </Link>
      <div className="table-responsive">
        {strandSupplyEntity.centralAssembly ||
        (strandSupplyEntity.nonCentralOperations && strandSupplyEntity.nonCentralOperations.length > 0) ? (
          strandSupplyOperationListRender(strandSupplyEntity)
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
