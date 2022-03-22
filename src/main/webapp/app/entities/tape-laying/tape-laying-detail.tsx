import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './tape-laying.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const TapeLayingDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const tapeLayingEntity = useAppSelector(state => state.tapeLaying.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="tapeLayingDetailsHeading">
          <Translate contentKey="lappLiApp.tapeLaying.detail.title">TapeLaying</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{tapeLayingEntity.id}</dd>
          <dt>
            <span id="operationLayer">
              <Translate contentKey="lappLiApp.tapeLaying.operationLayer">Operation Layer</Translate>
            </span>
          </dt>
          <dd>{tapeLayingEntity.operationLayer}</dd>
          <dt>
            <span id="assemblyMean">
              <Translate contentKey="lappLiApp.tapeLaying.assemblyMean">Assembly Mean</Translate>
            </span>
          </dt>
          <dd>{tapeLayingEntity.assemblyMean}</dd>
          <dt>
            <Translate contentKey="lappLiApp.tapeLaying.tape">Tape</Translate>
          </dt>
          <dd>{tapeLayingEntity.tape ? tapeLayingEntity.tape.designation : ''}</dd>
          <dt>
            <Translate contentKey="lappLiApp.tapeLaying.ownerStrandSupply">Owner Strand Supply</Translate>
          </dt>
          <dd>{tapeLayingEntity.ownerStrandSupply ? tapeLayingEntity.ownerStrandSupply.designation : ''}</dd>
        </dl>
        <Button tag={Link} to="/tape-laying" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/tape-laying/${tapeLayingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TapeLayingDetail;
