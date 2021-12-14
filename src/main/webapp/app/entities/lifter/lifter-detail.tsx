import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './lifter.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const LifterDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const lifterEntity = useAppSelector(state => state.lifter.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="lifterDetailsHeading">
          <Translate contentKey="lappLiApp.lifter.detail.title">Lifter</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{lifterEntity.id}</dd>
          <dt>
            <span id="index">
              <Translate contentKey="lappLiApp.lifter.index">Index</Translate>
            </span>
          </dt>
          <dd>{lifterEntity.index}</dd>
          <dt>
            <span id="minimumMilimeterDiameter">
              <Translate contentKey="lappLiApp.lifter.minimumMilimeterDiameter">Minimum Milimeter Diameter</Translate>
            </span>
          </dt>
          <dd>{lifterEntity.minimumMilimeterDiameter}</dd>
          <dt>
            <span id="maximumMilimeterDiameter">
              <Translate contentKey="lappLiApp.lifter.maximumMilimeterDiameter">Maximum Milimeter Diameter</Translate>
            </span>
          </dt>
          <dd>{lifterEntity.maximumMilimeterDiameter}</dd>
          <dt>
            <span id="supportsSpirallyColoredMarkingType">
              <Translate contentKey="lappLiApp.lifter.supportsSpirallyColoredMarkingType">Supports Spirally Colored Marking Type</Translate>
            </span>
          </dt>
          <dd>{lifterEntity.supportsSpirallyColoredMarkingType ? 'true' : 'false'}</dd>
          <dt>
            <span id="supportsLongitudinallyColoredMarkingType">
              <Translate contentKey="lappLiApp.lifter.supportsLongitudinallyColoredMarkingType">
                Supports Longitudinally Colored Marking Type
              </Translate>
            </span>
          </dt>
          <dd>{lifterEntity.supportsLongitudinallyColoredMarkingType ? 'true' : 'false'}</dd>
          <dt>
            <span id="supportsNumberedMarkingType">
              <Translate contentKey="lappLiApp.lifter.supportsNumberedMarkingType">Supports Numbered Marking Type</Translate>
            </span>
          </dt>
          <dd>{lifterEntity.supportsNumberedMarkingType ? 'true' : 'false'}</dd>
          <dt>
            <span id="supportsInkJetMarkingTechnique">
              <Translate contentKey="lappLiApp.lifter.supportsInkJetMarkingTechnique">Supports Ink Jet Marking Technique</Translate>
            </span>
          </dt>
          <dd>{lifterEntity.supportsInkJetMarkingTechnique ? 'true' : 'false'}</dd>
          <dt>
            <span id="supportsRsdMarkingTechnique">
              <Translate contentKey="lappLiApp.lifter.supportsRsdMarkingTechnique">Supports Rsd Marking Technique</Translate>
            </span>
          </dt>
          <dd>{lifterEntity.supportsRsdMarkingTechnique ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/lifter" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/lifter/${lifterEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default LifterDetail;
