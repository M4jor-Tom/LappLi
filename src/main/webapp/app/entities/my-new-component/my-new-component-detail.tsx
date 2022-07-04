import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './my-new-component.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const MyNewComponentDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const myNewComponentEntity = useAppSelector(state => state.myNewComponent.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="myNewComponentDetailsHeading">
          <Translate contentKey="lappLiApp.myNewComponent.detail.title">MyNewComponent</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{myNewComponentEntity.id}</dd>
          <dt>
            <span id="number">
              <Translate contentKey="lappLiApp.myNewComponent.number">Number</Translate>
            </span>
          </dt>
          <dd>{myNewComponentEntity.number}</dd>
          <dt>
            <span id="designation">
              <Translate contentKey="lappLiApp.myNewComponent.designation">Designation</Translate>
            </span>
          </dt>
          <dd>{myNewComponentEntity.designation}</dd>
          <dt>
            <span id="data">
              <Translate contentKey="lappLiApp.myNewComponent.data">Data</Translate>
            </span>
          </dt>
          <dd>{myNewComponentEntity.data}</dd>
        </dl>
        <Button tag={Link} to="/my-new-component" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/my-new-component/${myNewComponentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MyNewComponentDetail;
