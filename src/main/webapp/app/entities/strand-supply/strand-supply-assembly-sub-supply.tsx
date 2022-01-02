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
              <Translate contentKey="lappLiApp.position.id">ID</Translate>
            </th>
            <th>
              <Translate contentKey="lappLiApp.position.value">Value</Translate>
            </th>
            <th>
              <Translate contentKey="lappLiApp.position.elementSupply">Element Supply</Translate>
            </th>
            <th>
              <Translate contentKey="lappLiApp.position.bangleSupply">Bangle Supply</Translate>
            </th>
            <th>
              <Translate contentKey="lappLiApp.position.customComponentSupply">Custom Component Supply</Translate>
            </th>
            <th>
              <Translate contentKey="lappLiApp.position.oneStudySupply">One Study Supply</Translate>
            </th>
            <th>
              <Translate contentKey="lappLiApp.position.ownerCentralAssembly">Owner Central Assembly</Translate>
            </th>
            <th>
              <Translate contentKey="lappLiApp.position.ownerCoreAssembly">Owner Core Assembly</Translate>
            </th>
            <th>
              <Translate contentKey="lappLiApp.position.ownerIntersticeAssembly">Owner Interstice Assembly</Translate>
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
              <td>
                <Button tag={Link} to={`positions/${position.id}`} color="link" size="sm">
                  {position.id}
                </Button>
              </td>
              <td>{position.value}</td>
              <td>
                {position.elementSupply ? (
                  <Link to={`element-supply/${position.elementSupply.id}`}>{position.elementSupply.designation}</Link>
                ) : (
                  ''
                )}
              </td>
              <td>
                {position.bangleSupply ? (
                  <Link to={`bangle-supply/${position.bangleSupply.id}`}>{position.bangleSupply.designation}</Link>
                ) : (
                  ''
                )}
              </td>
              <td>
                {position.customComponentSupply ? (
                  <Link to={`custom-component-supply/${position.customComponentSupply.id}`}>
                    {position.customComponentSupply.designation}
                  </Link>
                ) : (
                  ''
                )}
              </td>
              <td>
                {position.oneStudySupply ? (
                  <Link to={`one-study-supply/${position.oneStudySupply.id}`}>{position.oneStudySupply.designation}</Link>
                ) : (
                  ''
                )}
              </td>
              <td>
                {position.ownerCentralAssembly ? (
                  <Link to={`central-assembly/${position.ownerCentralAssembly.id}`}>{position.ownerCentralAssembly.designation}</Link>
                ) : (
                  ''
                )}
              </td>
              <td>
                {position.ownerCoreAssembly ? (
                  <Link to={`core-assembly/${position.ownerCoreAssembly.id}`}>{position.ownerCoreAssembly.designation}</Link>
                ) : (
                  ''
                )}
              </td>
              <td>
                {position.ownerIntersticeAssembly ? (
                  <Link to={`interstice-assembly/${position.ownerIntersticeAssembly.id}`}>
                    {position.ownerIntersticeAssembly.designation}
                  </Link>
                ) : (
                  ''
                )}
              </td>
              <td className="text-right">
                <div className="btn-group flex-btn-group-container">
                  <Button tag={Link} to={`positions/${position.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                    <FontAwesomeIcon icon="eye" />{' '}
                    <span className="d-none d-md-inline">
                      <Translate contentKey="entity.action.view">View</Translate>
                    </span>
                  </Button>
                  <Button tag={Link} to={`positions/${position.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                    <FontAwesomeIcon icon="pencil-alt" />{' '}
                    <span className="d-none d-md-inline">
                      <Translate contentKey="entity.action.edit">Edit</Translate>
                    </span>
                  </Button>
                  <Button tag={Link} to={`positions/${position.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
