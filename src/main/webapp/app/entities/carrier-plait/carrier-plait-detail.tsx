import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './carrier-plait.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CarrierPlaitDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const carrierPlaitEntity = useAppSelector(state => state.carrierPlait.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="carrierPlaitDetailsHeading">
          <Translate contentKey="lappLiApp.carrierPlait.detail.title">CarrierPlait</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{carrierPlaitEntity.id}</dd>
          <dt>
            <span id="operationLayer">
              <Translate contentKey="lappLiApp.carrierPlait.operationLayer">Operation Layer</Translate>
            </span>
          </dt>
          <dd>{carrierPlaitEntity.operationLayer}</dd>
          <dt>
            <span id="minimumDecaNewtonLoad">
              <Translate contentKey="lappLiApp.carrierPlait.minimumDecaNewtonLoad">Minimum Deca Newton Load</Translate>
            </span>
          </dt>
          <dd>{carrierPlaitEntity.minimumDecaNewtonLoad}</dd>
          <dt>
            <span id="degreeAssemblyAngle">
              <Translate contentKey="lappLiApp.carrierPlait.degreeAssemblyAngle">Degree Assembly Angle</Translate>
            </span>
          </dt>
          <dd>{carrierPlaitEntity.degreeAssemblyAngle}</dd>
          <dt>
            <span id="forcedEndPerBobinsCount">
              <Translate contentKey="lappLiApp.carrierPlait.forcedEndPerBobinsCount">Forced End Per Bobins Count</Translate>
            </span>
          </dt>
          <dd>{carrierPlaitEntity.forcedEndPerBobinsCount}</dd>
          <dt>
            <span id="anonymousCarrierPlaitFiberNumber">
              <Translate contentKey="lappLiApp.carrierPlait.anonymousCarrierPlaitFiberNumber">
                Anonymous Carrier Plait Fiber Number
              </Translate>
            </span>
          </dt>
          <dd>{carrierPlaitEntity.anonymousCarrierPlaitFiberNumber}</dd>
          <dt>
            <span id="anonymousCarrierPlaitFiberDesignation">
              <Translate contentKey="lappLiApp.carrierPlait.anonymousCarrierPlaitFiberDesignation">
                Anonymous Carrier Plait Fiber Designation
              </Translate>
            </span>
          </dt>
          <dd>{carrierPlaitEntity.anonymousCarrierPlaitFiberDesignation}</dd>
          <dt>
            <span id="anonymousCarrierPlaitFiberSquareMilimeterSection">
              <Translate contentKey="lappLiApp.carrierPlait.anonymousCarrierPlaitFiberSquareMilimeterSection">
                Anonymous Carrier Plait Fiber Square Milimeter Section
              </Translate>
            </span>
          </dt>
          <dd>{carrierPlaitEntity.anonymousCarrierPlaitFiberSquareMilimeterSection}</dd>
          <dt>
            <span id="anonymousCarrierPlaitFiberDecaNewtonLoad">
              <Translate contentKey="lappLiApp.carrierPlait.anonymousCarrierPlaitFiberDecaNewtonLoad">
                Anonymous Carrier Plait Fiber Deca Newton Load
              </Translate>
            </span>
          </dt>
          <dd>{carrierPlaitEntity.anonymousCarrierPlaitFiberDecaNewtonLoad}</dd>
          <dt>
            <Translate contentKey="lappLiApp.carrierPlait.carrierPlaitFiber">Carrier Plait Fiber</Translate>
          </dt>
          <dd>{carrierPlaitEntity.carrierPlaitFiber ? carrierPlaitEntity.carrierPlaitFiber.designation : ''}</dd>
          <dt>
            <Translate contentKey="lappLiApp.carrierPlait.ownerStrandSupply">Owner Strand Supply</Translate>
          </dt>
          <dd>{carrierPlaitEntity.ownerStrandSupply ? carrierPlaitEntity.ownerStrandSupply.designation : ''}</dd>
        </dl>
        <Button tag={Link} to="/carrier-plait" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/carrier-plait/${carrierPlaitEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CarrierPlaitDetail;
