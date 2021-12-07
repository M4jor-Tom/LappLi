import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './material-marking-statistic.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const MaterialMarkingStatisticDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const materialMarkingStatisticEntity = useAppSelector(state => state.materialMarkingStatistic.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="materialMarkingStatisticDetailsHeading">
          <Translate contentKey="lappLiApp.materialMarkingStatistic.detail.title">MaterialMarkingStatistic</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{materialMarkingStatisticEntity.id}</dd>
          <dt>
            <span id="markingType">
              <Translate contentKey="lappLiApp.materialMarkingStatistic.markingType">Marking Type</Translate>
            </span>
          </dt>
          <dd>{materialMarkingStatisticEntity.markingType}</dd>
          <dt>
            <span id="markingTechnique">
              <Translate contentKey="lappLiApp.materialMarkingStatistic.markingTechnique">Marking Technique</Translate>
            </span>
          </dt>
          <dd>{materialMarkingStatisticEntity.markingTechnique}</dd>
          <dt>
            <span id="meterPerSecondSpeed">
              <Translate contentKey="lappLiApp.materialMarkingStatistic.meterPerSecondSpeed">Meter Per Second Speed</Translate>
            </span>
          </dt>
          <dd>{materialMarkingStatisticEntity.meterPerSecondSpeed}</dd>
        </dl>
        <Button tag={Link} to="/material-marking-statistic" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/material-marking-statistic/${materialMarkingStatisticEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MaterialMarkingStatisticDetail;
