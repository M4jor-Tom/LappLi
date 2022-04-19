import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './plait.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PlaitDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const plaitEntity = useAppSelector(state => state.plait.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="plaitDetailsHeading">
          <Translate contentKey="lappLiApp.plait.detail.title">Plait</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{plaitEntity.id}</dd>
          <dt>
            <span id="operationLayer">
              <Translate contentKey="lappLiApp.plait.operationLayer">Operation Layer</Translate>
            </span>
          </dt>
          <dd>{plaitEntity.operationLayer}</dd>
          <dt>
            <span id="targetCoveringRate">
              <Translate contentKey="lappLiApp.plait.targetCoveringRate">Target Covering Rate</Translate>
            </span>
          </dt>
          <dd>{plaitEntity.targetCoveringRate}</dd>
          <dt>
            <span id="targetDegreeAngle">
              <Translate contentKey="lappLiApp.plait.targetDegreeAngle">Target Degree Angle</Translate>
            </span>
          </dt>
          <dd>{plaitEntity.targetDegreeAngle}</dd>
          <dt>
            <span id="targetingCoveringRateNotAngle">
              <Translate contentKey="lappLiApp.plait.targetingCoveringRateNotAngle">Targeting Covering Rate Not Angle</Translate>
            </span>
          </dt>
          <dd>{plaitEntity.targetingCoveringRateNotAngle ? 'true' : 'false'}</dd>
          <dt>
            <span id="anonymousMetalFiberNumber">
              <Translate contentKey="lappLiApp.plait.anonymousMetalFiberNumber">Anonymous Metal Fiber Number</Translate>
            </span>
          </dt>
          <dd>{plaitEntity.anonymousMetalFiberNumber}</dd>
          <dt>
            <span id="anonymousMetalFiberDesignation">
              <Translate contentKey="lappLiApp.plait.anonymousMetalFiberDesignation">Anonymous Metal Fiber Designation</Translate>
            </span>
          </dt>
          <dd>{plaitEntity.anonymousMetalFiberDesignation}</dd>
          <dt>
            <span id="anonymousMetalFiberMetalFiberKind">
              <Translate contentKey="lappLiApp.plait.anonymousMetalFiberMetalFiberKind">Anonymous Metal Fiber Metal Fiber Kind</Translate>
            </span>
          </dt>
          <dd>{plaitEntity.anonymousMetalFiberMetalFiberKind}</dd>
          <dt>
            <span id="anonymousMetalFiberMilimeterDiameter">
              <Translate contentKey="lappLiApp.plait.anonymousMetalFiberMilimeterDiameter">
                Anonymous Metal Fiber Milimeter Diameter
              </Translate>
            </span>
          </dt>
          <dd>{plaitEntity.anonymousMetalFiberMilimeterDiameter}</dd>
          <dt>
            <Translate contentKey="lappLiApp.plait.copperFiber">Copper Fiber</Translate>
          </dt>
          <dd>{plaitEntity.copperFiber ? plaitEntity.copperFiber.designation : ''}</dd>
          <dt>
            <Translate contentKey="lappLiApp.plait.metalFiber">Metal Fiber</Translate>
          </dt>
          <dd>{plaitEntity.metalFiber ? plaitEntity.metalFiber.designation : ''}</dd>
          <dt>
            <Translate contentKey="lappLiApp.plait.ownerStrandSupply">Owner Strand Supply</Translate>
          </dt>
          <dd>{plaitEntity.ownerStrandSupply ? plaitEntity.ownerStrandSupply.designation : ''}</dd>
        </dl>
        <Button tag={Link} to="/plait" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/plait/${plaitEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PlaitDetail;
