import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './copper-fiber.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CopperFiberDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const copperFiberEntity = useAppSelector(state => state.copperFiber.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="copperFiberDetailsHeading">
          <Translate contentKey="lappLiApp.copperFiber.detail.title">CopperFiber</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{copperFiberEntity.id}</dd>
          <dt>
            <span id="number">
              <Translate contentKey="lappLiApp.copperFiber.number">Number</Translate>
            </span>
          </dt>
          <dd>{copperFiberEntity.number}</dd>
          <dt>
            <span id="designation">
              <Translate contentKey="lappLiApp.copperFiber.designation">Designation</Translate>
            </span>
          </dt>
          <dd>{copperFiberEntity.designation}</dd>
          <dt>
            <span id="copperIsRedNotTinned">
              <Translate contentKey="lappLiApp.copperFiber.copperIsRedNotTinned">Copper Is Red Not Tinned</Translate>
            </span>
          </dt>
          <dd>{copperFiberEntity.copperIsRedNotTinned ? 'true' : 'false'}</dd>
          <dt>
            <span id="milimeterDiameter">
              <Translate contentKey="lappLiApp.copperFiber.milimeterDiameter">Milimeter Diameter</Translate>
            </span>
          </dt>
          <dd>{copperFiberEntity.milimeterDiameter}</dd>
        </dl>
        <Button tag={Link} to="/copper-fiber" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/copper-fiber/${copperFiberEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CopperFiberDetail;
