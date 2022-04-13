import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './continuity-wire.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ContinuityWireDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const continuityWireEntity = useAppSelector(state => state.continuityWire.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="continuityWireDetailsHeading">
          <Translate contentKey="lappLiApp.continuityWire.detail.title">ContinuityWire</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{continuityWireEntity.id}</dd>
          <dt>
            <span id="designation">
              <Translate contentKey="lappLiApp.continuityWire.designation">Designation</Translate>
            </span>
          </dt>
          <dd>{continuityWireEntity.designation}</dd>
          <dt>
            <span id="gramPerMeterLinearMass">
              <Translate contentKey="lappLiApp.continuityWire.gramPerMeterLinearMass">Gram Per Meter Linear Mass</Translate>
            </span>
          </dt>
          <dd>{continuityWireEntity.gramPerMeterLinearMass}</dd>
          <dt>
            <span id="metalFiberKind">
              <Translate contentKey="lappLiApp.continuityWire.metalFiberKind">Metal Fiber Kind</Translate>
            </span>
          </dt>
          <dd>{continuityWireEntity.metalFiberKind}</dd>
          <dt>
            <span id="milimeterDiameter">
              <Translate contentKey="lappLiApp.continuityWire.milimeterDiameter">Milimeter Diameter</Translate>
            </span>
          </dt>
          <dd>{continuityWireEntity.milimeterDiameter}</dd>
          <dt>
            <span id="flexibility">
              <Translate contentKey="lappLiApp.continuityWire.flexibility">Flexibility</Translate>
            </span>
          </dt>
          <dd>{continuityWireEntity.flexibility}</dd>
        </dl>
        <Button tag={Link} to="/continuity-wire" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/continuity-wire/${continuityWireEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ContinuityWireDetail;
