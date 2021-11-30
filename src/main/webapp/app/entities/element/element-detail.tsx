import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './element.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ElementDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const elementEntity = useAppSelector(state => state.element.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="elementDetailsHeading">
          <Translate contentKey="lappLiApp.element.detail.title">Element</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{elementEntity.id}</dd>
          <dt>
            <span id="number">
              <Translate contentKey="lappLiApp.element.number">Number</Translate>
            </span>
          </dt>
          <dd>{elementEntity.number}</dd>
          <dt>
            <span id="designation">
              <Translate contentKey="lappLiApp.element.designation">Designation</Translate>
            </span>
          </dt>
          <dd>{elementEntity.designationWithColor}</dd>
          <dt>
            <span id="color">
              <Translate contentKey="lappLiApp.element.color">Color</Translate>
            </span>
          </dt>
          <dd>{elementEntity.color}</dd>
          <dt>
            <Translate contentKey="lappLiApp.element.elementKind">Element Kind</Translate>
          </dt>
          <dd>{elementEntity.elementKind ? elementEntity.elementKind.designation : ''}</dd>
          <dt>
            <Translate contentKey="lappLiApp.dimension.milimeterDiameter"></Translate>
          </dt>
          <dd>{elementEntity.elementKind?.milimeterDiameter}</dd>
        </dl>
        <Button tag={Link} to="/element" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/element/${elementEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ElementDetail;
