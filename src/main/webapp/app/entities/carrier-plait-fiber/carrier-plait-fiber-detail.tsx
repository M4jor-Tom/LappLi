import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './carrier-plait-fiber.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CarrierPlaitFiberDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const carrierPlaitFiberEntity = useAppSelector(state => state.carrierPlaitFiber.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="carrierPlaitFiberDetailsHeading">
          <Translate contentKey="lappLiApp.carrierPlaitFiber.detail.title">CarrierPlaitFiber</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{carrierPlaitFiberEntity.id}</dd>
          <dt>
            <span id="number">
              <Translate contentKey="lappLiApp.carrierPlaitFiber.number">Number</Translate>
            </span>
          </dt>
          <dd>{carrierPlaitFiberEntity.number}</dd>
          <dt>
            <span id="designation">
              <Translate contentKey="lappLiApp.carrierPlaitFiber.designation">Designation</Translate>
            </span>
          </dt>
          <dd>{carrierPlaitFiberEntity.designation}</dd>
          <dt>
            <span id="squareMilimeterSection">
              <Translate contentKey="lappLiApp.carrierPlaitFiber.squareMilimeterSection">Square Milimeter Section</Translate>
            </span>
          </dt>
          <dd>{carrierPlaitFiberEntity.squareMilimeterSection}</dd>
          <dt>
            <span id="decaNewtonLoad">
              <Translate contentKey="lappLiApp.carrierPlaitFiber.decaNewtonLoad">Deca Newton Load</Translate>
            </span>
          </dt>
          <dd>{carrierPlaitFiberEntity.decaNewtonLoad}</dd>
        </dl>
        <Button tag={Link} to="/carrier-plait-fiber" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/carrier-plait-fiber/${carrierPlaitFiberEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CarrierPlaitFiberDetail;
