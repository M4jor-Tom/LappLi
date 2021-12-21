import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './study.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const StudyDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const studyEntity = useAppSelector(state => state.study.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="studyDetailsHeading">
          <Translate contentKey="lappLiApp.study.detail.title">Study</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{studyEntity.id}</dd>
          <dt>
            <span id="number">
              <Translate contentKey="lappLiApp.study.number">Number</Translate>
            </span>
          </dt>
          <dd>{studyEntity.number}</dd>
          <dt>
            <span id="lastEditionInstant">
              <Translate contentKey="lappLiApp.study.lastEditionInstant">Last Edition Instant</Translate>
            </span>
          </dt>
          <dd>
            {studyEntity.lastEditionInstant ? (
              <TextFormat value={studyEntity.lastEditionInstant} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="lappLiApp.study.author">Author</Translate>
          </dt>
          <dd>{studyEntity.author ? studyEntity.author.user.login : ''}</dd>
        </dl>
        <Button tag={Link} to="/study" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/study/${studyEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default StudyDetail;
