import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './tape.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const TapeDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const tapeEntity = useAppSelector(state => state.tape.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="tapeDetailsHeading">
          <Translate contentKey="lappLiApp.tape.detail.title">Tape</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{tapeEntity.id}</dd>
          <dt>
            <span id="number">
              <Translate contentKey="lappLiApp.tape.number">Number</Translate>
            </span>
          </dt>
          <dd>{tapeEntity.number}</dd>
          <dt>
            <span id="designation">
              <Translate contentKey="lappLiApp.tape.designation">Designation</Translate>
            </span>
          </dt>
          <dd>{tapeEntity.designation}</dd>
          <dt>
            <span id="milimeterWidth">
              <Translate contentKey="lappLiApp.tape.milimeterWidth">Milimeter Width</Translate>
            </span>
          </dt>
          <dd>{tapeEntity.milimeterWidth}</dd>
          <dt>
            <span id="milimeterDiameterIncidency">
              <Translate contentKey="lappLiApp.tape.milimeterDiameterIncidency">Milimeter Diameter Incidency</Translate>
            </span>
          </dt>
          <dd>{tapeEntity.milimeterDiameterIncidency}</dd>
          <dt>
            <Translate contentKey="lappLiApp.tape.tapeKind">Tape Kind</Translate>
          </dt>
          <dd>{tapeEntity.tapeKind ? tapeEntity.tapeKind.designation : ''}</dd>
        </dl>
        <Button tag={Link} to="/tape" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/tape/${tapeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TapeDetail;
