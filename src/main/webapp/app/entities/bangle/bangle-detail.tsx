import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './bangle.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const BangleDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const bangleEntity = useAppSelector(state => state.bangle.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bangleDetailsHeading">
          <Translate contentKey="lappLiApp.bangle.detail.title">Bangle</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{bangleEntity.id}</dd>
          <dt>
            <span id="number">
              <Translate contentKey="lappLiApp.bangle.number">Number</Translate>
            </span>
          </dt>
          <dd>{bangleEntity.number}</dd>
          <dt>
            <span id="designation">
              <Translate contentKey="lappLiApp.bangle.designation">Designation</Translate>
            </span>
          </dt>
          <dd>{bangleEntity.designation}</dd>
          <dt>
            <span id="gramPerMeterLinearMass">
              <Translate contentKey="lappLiApp.bangle.gramPerMeterLinearMass">Gram Per Meter Linear Mass</Translate>
            </span>
          </dt>
          <dd>{bangleEntity.gramPerMeterLinearMass}</dd>
          <dt>
            <span id="milimeterDiameter">
              <Translate contentKey="lappLiApp.bangle.milimeterDiameter">Milimeter Diameter</Translate>
            </span>
          </dt>
          <dd>{bangleEntity.milimeterDiameter}</dd>
        </dl>
        <Button tag={Link} to="/bangle" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/bangle/${bangleEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BangleDetail;
