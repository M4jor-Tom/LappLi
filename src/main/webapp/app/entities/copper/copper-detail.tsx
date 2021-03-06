import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './copper.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CopperDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const copperEntity = useAppSelector(state => state.copper.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="copperDetailsHeading">
          <Translate contentKey="lappLiApp.copper.detail.title">Copper</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{copperEntity.id}</dd>
          <dt>
            <span id="number">
              <Translate contentKey="lappLiApp.copper.number">Number</Translate>
            </span>
          </dt>
          <dd>{copperEntity.number}</dd>
          <dt>
            <span id="designation">
              <Translate contentKey="lappLiApp.copper.designation">Designation</Translate>
            </span>
          </dt>
          <dd>{copperEntity.designation}</dd>
        </dl>
        <Button tag={Link} to="/copper" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/copper/${copperEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CopperDetail;
