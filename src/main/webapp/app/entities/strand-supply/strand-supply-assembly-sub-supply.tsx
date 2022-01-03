import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './strand-supply.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getOut } from '../index-management/index-management-lib';
import { toNumber } from 'lodash';
import { defaultValue as strandDefaultValue, IStrand } from 'app/shared/model/strand.model';
import { IPosition } from 'app/shared/model/position.model';
import CoreAssembly from '../core-assembly/core-assembly';
import { renderSupplyTds } from '../supply/render';

export const StrandSupplyAssemblySubSupply = (props: RouteComponentProps<{ study_id: string; id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const getOutCount: number = props.match.params.study_id ? 2 : 0;

  const strandSupplyEntity = useAppSelector(state => state.strandSupply.entity);

  const strandEntity = strandSupplyEntity.strand ? strandSupplyEntity.strand : strandDefaultValue;

  const positionTable = (strand: IStrand) =>
    strandHasPositions(strand) ? (
      <Table responsive>
        <thead>
          <tr>
            <th>
              <Translate contentKey="lappLiApp.supply.position">Position</Translate>
            </th>
            <th>
              <Translate contentKey="lappLiApp.supply.apparitions">Apparitions</Translate>
            </th>
            <th>
              <Translate contentKey="lappLiApp.dimension.meterQuantity">Quantity (m)</Translate>
            </th>
            <th>
              <Translate contentKey="lappLiApp.article.number">Article Number</Translate>
            </th>
            <th>
              <Translate contentKey="lappLiApp.article.designation">Article Designation</Translate>
            </th>
            <th>
              <Translate contentKey="lappLiApp.supply.description">Description</Translate>
            </th>
            <th>
              <Translate contentKey="lappLiApp.supply.markingType">Marking Type</Translate>
            </th>
            <th>
              <Translate contentKey="lappLiApp.supply.markingTechnique">Marking Technique</Translate>
            </th>
            <th>
              <Translate contentKey="lappLiApp.dimension.gramPerMeterLinearMass">Linear Mass (g/m)</Translate>
            </th>
            <th>
              <Translate contentKey="lappLiApp.dimension.milimeterDiameter">Diameter (mm)</Translate>
            </th>
            <th>
              <Translate contentKey="lappLiApp.supply.surfaceColor">Surface Color</Translate>
            </th>
            <th>
              <Translate contentKey="lappLiApp.supply.surfaceMaterial">Surface Material</Translate>
            </th>
            <th>
              <Translate contentKey="lappLiApp.supply.bestLiftersNames">Best Lifters</Translate>
            </th>
            <th>
              <Translate contentKey="lappLiApp.supply.formatedHourPreparationTime">Preparation Time (h)</Translate>
            </th>
            <th>
              <Translate contentKey="lappLiApp.supply.formatedHourExecutionTime">Execution Time (h)</Translate>
            </th>
            <th>
              <Translate contentKey="lappLiApp.supply.meterPerHourSpeed">Speed (m/h)</Translate>
            </th>
            <th />
          </tr>
        </thead>
        {positionTbody([strand.centralAssembly.position])}
        {strand.coreAssemblies.forEach(coreAssembly => {
          positionTbody(coreAssembly.positions);
        })}
        {strand.intersticeAssemblies.forEach(intersticeAssembly => {
          positionTbody(intersticeAssembly.positions);
        })}
      </Table>
    ) : (
      <div className="alert alert-warning">
        <Translate contentKey="lappLiApp.position.home.notFound">No Positions found</Translate>
      </div>
    );

  const positionTbody = (positionList: Array<IPosition>) => (
    <tbody>
      {positionList != null
        ? positionList.map((position, i) => (
            <tr key={`entity-${i}`} data-cy="entityTable">
              <td>{position.value}</td>
              {renderSupplyTds(position.supply, props.match)}
            </tr>
          ))
        : ''}
    </tbody>
  );

  return (
    <div>
      <div>
        {/* md="8">*/}
        <h2 data-cy="strandDetailsHeading">
          <Translate contentKey="lappLiApp.strand.detail.title">Strand</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="designation">
              <Translate contentKey="lappLiApp.strand.designation">Designation</Translate>
            </span>
          </dt>
          <dd>{strandSupplyEntity.designation}</dd>
          <dd>
            <div className="table-responsive">
              {positionTable(strandEntity)}
              <Link
                to={`${props.match.url}/central-assembly/new`}
                className="btn btn-primary jh-create-entity"
                id="jh-create-entity"
                data-cy="entityCreateButton"
              >
                <FontAwesomeIcon icon="plus" />
                &nbsp;
                <Translate contentKey="lappLiApp.position.home.createLabel">Create new Position</Translate>
              </Link>
              &nbsp;
            </div>
          </dd>
        </dl>
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

function strandHasPositions(strand: IStrand): boolean {
  if (!strand) {
    return false;
  }

  if (!strand.centralAssembly && !strand.coreAssemblies && !strand.intersticeAssemblies) {
    return false;
  }

  if (strand.centralAssembly.position) {
    return true;
  }

  strand.coreAssemblies.forEach(coreAssembly => {
    if (coreAssembly.positions) {
      return true;
    }
  });

  strand.intersticeAssemblies.forEach(intersticeAssembly => {
    if (intersticeAssembly.positions) {
      return true;
    }
  });

  return false;
}

export default StrandSupplyAssemblySubSupply;
