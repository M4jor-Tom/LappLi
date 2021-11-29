import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './material.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const MaterialDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const materialEntity = useAppSelector(state => state.material.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="materialDetailsHeading">
          <Translate contentKey="lappLiApp.material.detail.title">Material</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{materialEntity.id}</dd>
          <dt>
            <span id="number">
              <Translate contentKey="lappLiApp.material.number">Number</Translate>
            </span>
          </dt>
          <dd>{materialEntity.number}</dd>
          <dt>
            <span id="designation">
              <Translate contentKey="lappLiApp.material.designation">Designation</Translate>
            </span>
          </dt>
          <dd>{materialEntity.designation}</dd>
          <dt>
            <span id="isMarkable">
              <Translate contentKey="lappLiApp.material.isMarkable">Is Markable</Translate>
            </span>
          </dt>
          <dd>{materialEntity.isMarkable ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/material" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/material/${materialEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MaterialDetail;
