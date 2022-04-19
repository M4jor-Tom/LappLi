import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './steel-fiber.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const SteelFiberDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const steelFiberEntity = useAppSelector(state => state.steelFiber.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="steelFiberDetailsHeading">
          <Translate contentKey="lappLiApp.steelFiber.detail.title">SteelFiber</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{steelFiberEntity.id}</dd>
          <dt>
            <span id="number">
              <Translate contentKey="lappLiApp.steelFiber.number">Number</Translate>
            </span>
          </dt>
          <dd>{steelFiberEntity.number}</dd>
          <dt>
            <span id="designation">
              <Translate contentKey="lappLiApp.steelFiber.designation">Designation</Translate>
            </span>
          </dt>
          <dd>{steelFiberEntity.designation}</dd>
          <dt>
            <span id="metalFiberKind">
              <Translate contentKey="lappLiApp.steelFiber.metalFiberKind">Metal Fiber Kind</Translate>
            </span>
          </dt>
          <dd>{steelFiberEntity.metalFiberKind}</dd>
          <dt>
            <span id="milimeterDiameter">
              <Translate contentKey="lappLiApp.steelFiber.milimeterDiameter">Milimeter Diameter</Translate>
            </span>
          </dt>
          <dd>{steelFiberEntity.milimeterDiameter}</dd>
        </dl>
        <Button tag={Link} to="/steel-fiber" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/steel-fiber/${steelFiberEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SteelFiberDetail;
