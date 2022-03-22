import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './tape-kind.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const TapeKindDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const tapeKindEntity = useAppSelector(state => state.tapeKind.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="tapeKindDetailsHeading">
          <Translate contentKey="lappLiApp.tapeKind.detail.title">TapeKind</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{tapeKindEntity.id}</dd>
          <dt>
            <span id="targetCoveringRate">
              <Translate contentKey="lappLiApp.tapeKind.targetCoveringRate">Target Covering Rate</Translate>
            </span>
          </dt>
          <dd>{tapeKindEntity.targetCoveringRate}</dd>
          <dt>
            <span id="designation">
              <Translate contentKey="lappLiApp.tapeKind.designation">Designation</Translate>
            </span>
          </dt>
          <dd>{tapeKindEntity.designation}</dd>
        </dl>
        <Button tag={Link} to="/tape-kind" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/tape-kind/${tapeKindEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TapeKindDetail;
