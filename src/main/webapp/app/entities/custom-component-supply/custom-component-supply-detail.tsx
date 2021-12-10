import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './custom-component-supply.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CustomComponentSupplyDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const customComponentSupplyEntity = useAppSelector(state => state.customComponentSupply.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="customComponentSupplyDetailsHeading">
          <Translate contentKey="lappLiApp.customComponentSupply.detail.title">CustomComponentSupply</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{customComponentSupplyEntity.id}</dd>
          <dt>
            <span id="apparitions">
              <Translate contentKey="lappLiApp.customComponentSupply.apparitions">Apparitions</Translate>
            </span>
          </dt>
          <dd>{customComponentSupplyEntity.apparitions}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="lappLiApp.customComponentSupply.description">Description</Translate>
            </span>
          </dt>
          <dd>{customComponentSupplyEntity.description}</dd>
          <dt>
            <Translate contentKey="lappLiApp.customComponentSupply.customComponent">Custom Component</Translate>
          </dt>
          <dd>{customComponentSupplyEntity.customComponent ? customComponentSupplyEntity.customComponent.designation : ''}</dd>
        </dl>
        <Button tag={Link} to="/custom-component-supply" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/custom-component-supply/${customComponentSupplyEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CustomComponentSupplyDetail;
