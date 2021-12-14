import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './custom-component.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CustomComponentDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const customComponentEntity = useAppSelector(state => state.customComponent.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="customComponentDetailsHeading">
          <Translate contentKey="lappLiApp.customComponent.detail.title">CustomComponent</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{customComponentEntity.id}</dd>
          <dt>
            <span id="number">
              <Translate contentKey="lappLiApp.customComponent.number">Number</Translate>
            </span>
          </dt>
          <dd>{customComponentEntity.number}</dd>
          <dt>
            <span id="designation">
              <Translate contentKey="lappLiApp.customComponent.designation">Designation</Translate>
            </span>
          </dt>
          <dd>{customComponentEntity.designation}</dd>
          <dt>
            <span id="gramPerMeterLinearMass">
              <Translate contentKey="lappLiApp.customComponent.gramPerMeterLinearMass">Gram Per Meter Linear Mass</Translate>
            </span>
          </dt>
          <dd>{customComponentEntity.gramPerMeterLinearMass}</dd>
          <dt>
            <span id="milimeterDiameter">
              <Translate contentKey="lappLiApp.customComponent.milimeterDiameter">Milimeter Diameter</Translate>
            </span>
          </dt>
          <dd>{customComponentEntity.milimeterDiameter}</dd>
          <dt>
            <span id="surfaceColor">
              <Translate contentKey="lappLiApp.customComponent.surfaceColor">Surface Color</Translate>
            </span>
          </dt>
          <dd>{customComponentEntity.surfaceColor}</dd>
          <dt>
            <Translate contentKey="lappLiApp.customComponent.surfaceMaterial">Surface Material</Translate>
          </dt>
          <dd>{customComponentEntity.surfaceMaterial ? customComponentEntity.surfaceMaterial.designation : ''}</dd>
        </dl>
        <Button tag={Link} to="/custom-component" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/custom-component/${customComponentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CustomComponentDetail;
