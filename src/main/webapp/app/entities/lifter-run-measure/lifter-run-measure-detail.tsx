import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './lifter-run-measure.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const LifterRunMeasureDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const lifterRunMeasureEntity = useAppSelector(state => state.lifterRunMeasure.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="lifterRunMeasureDetailsHeading">
          <Translate contentKey="lappLiApp.lifterRunMeasure.detail.title">LifterRunMeasure</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{lifterRunMeasureEntity.id}</dd>
          <dt>
            <span id="milimeterDiameter">
              <Translate contentKey="lappLiApp.lifterRunMeasure.milimeterDiameter">Milimeter Diameter</Translate>
            </span>
          </dt>
          <dd>{lifterRunMeasureEntity.milimeterDiameter}</dd>
          <dt>
            <span id="markingType">
              <Translate contentKey="lappLiApp.lifterRunMeasure.markingType">Marking Type</Translate>
            </span>
          </dt>
          <dd>{lifterRunMeasureEntity.markingType}</dd>
          <dt>
            <Translate contentKey="lappLiApp.lifterRunMeasure.lifter">Lifter</Translate>
          </dt>
          <dd>{lifterRunMeasureEntity.lifter ? lifterRunMeasureEntity.lifter.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/lifter-run-measure" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/lifter-run-measure/${lifterRunMeasureEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default LifterRunMeasureDetail;
