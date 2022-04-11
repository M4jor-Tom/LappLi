import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './metal-fiber.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const MetalFiberDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const metalFiberEntity = useAppSelector(state => state.metalFiber.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="metalFiberDetailsHeading">
          <Translate contentKey="lappLiApp.metalFiber.detail.title">MetalFiber</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{metalFiberEntity.id}</dd>
          <dt>
            <span id="number">
              <Translate contentKey="lappLiApp.metalFiber.number">Number</Translate>
            </span>
          </dt>
          <dd>{metalFiberEntity.number}</dd>
          <dt>
            <span id="designation">
              <Translate contentKey="lappLiApp.metalFiber.designation">Designation</Translate>
            </span>
          </dt>
          <dd>{metalFiberEntity.designation}</dd>
          <dt>
            <span id="metalFiberKind">
              <Translate contentKey="lappLiApp.metalFiber.metalFiberKind">Metal Fiber Kind</Translate>
            </span>
          </dt>
          <dd>{metalFiberEntity.metalFiberKind}</dd>
          <dt>
            <span id="milimeterDiameter">
              <Translate contentKey="lappLiApp.metalFiber.milimeterDiameter">Milimeter Diameter</Translate>
            </span>
          </dt>
          <dd>{metalFiberEntity.milimeterDiameter}</dd>
        </dl>
        <Button tag={Link} to="/metal-fiber" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/metal-fiber/${metalFiberEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MetalFiberDetail;
