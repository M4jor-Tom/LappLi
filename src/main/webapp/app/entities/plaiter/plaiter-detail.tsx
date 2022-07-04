import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './plaiter.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PlaiterDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const plaiterEntity = useAppSelector(state => state.plaiter.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="plaiterDetailsHeading">
          <Translate contentKey="lappLiApp.plaiter.detail.title">Plaiter</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{plaiterEntity.id}</dd>
          <dt>
            <span id="index">
              <Translate contentKey="lappLiApp.plaiter.index">Index</Translate>
            </span>
          </dt>
          <dd>{plaiterEntity.index}</dd>
          <dt>
            <span id="totalBobinsCount">
              <Translate contentKey="lappLiApp.plaiter.totalBobinsCount">Total Bobins Count</Translate>
            </span>
          </dt>
          <dd>{plaiterEntity.totalBobinsCount}</dd>
        </dl>
        <Button tag={Link} to="/plaiter" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/plaiter/${plaiterEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PlaiterDetail;
