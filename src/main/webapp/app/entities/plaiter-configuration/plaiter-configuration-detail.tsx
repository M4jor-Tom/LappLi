import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './plaiter-configuration.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PlaiterConfigurationDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const plaiterConfigurationEntity = useAppSelector(state => state.plaiterConfiguration.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="plaiterConfigurationDetailsHeading">
          <Translate contentKey="lappLiApp.plaiterConfiguration.detail.title">PlaiterConfiguration</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{plaiterConfigurationEntity.id}</dd>
          <dt>
            <span id="usedBobinsCount">
              <Translate contentKey="lappLiApp.plaiterConfiguration.usedBobinsCount">Used Bobins Count</Translate>
            </span>
          </dt>
          <dd>{plaiterConfigurationEntity.usedBobinsCount}</dd>
          <dt>
            <Translate contentKey="lappLiApp.plaiterConfiguration.plaiter">Plaiter</Translate>
          </dt>
          <dd>{plaiterConfigurationEntity.plaiter ? plaiterConfigurationEntity.plaiter.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/plaiter-configuration" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/plaiter-configuration/${plaiterConfigurationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PlaiterConfigurationDetail;
