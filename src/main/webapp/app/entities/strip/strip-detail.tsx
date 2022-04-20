import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './strip.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const StripDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const stripEntity = useAppSelector(state => state.strip.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="stripDetailsHeading">
          <Translate contentKey="lappLiApp.strip.detail.title">Strip</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{stripEntity.id}</dd>
          <dt>
            <span id="number">
              <Translate contentKey="lappLiApp.strip.number">Number</Translate>
            </span>
          </dt>
          <dd>{stripEntity.number}</dd>
          <dt>
            <span id="designation">
              <Translate contentKey="lappLiApp.strip.designation">Designation</Translate>
            </span>
          </dt>
          <dd>{stripEntity.designation}</dd>
          <dt>
            <span id="milimeterThickness">
              <Translate contentKey="lappLiApp.strip.milimeterThickness">Milimeter Thickness</Translate>
            </span>
          </dt>
          <dd>{stripEntity.milimeterThickness}</dd>
        </dl>
        <Button tag={Link} to="/strip" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/strip/${stripEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default StripDetail;
