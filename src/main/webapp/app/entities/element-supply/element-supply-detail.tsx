import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './element-supply.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ElementSupplyDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const elementSupplyEntity = useAppSelector(state => state.elementSupply.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="elementSupplyDetailsHeading">
          <Translate contentKey="lappLiApp.elementSupply.detail.title">ElementSupply</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{elementSupplyEntity.id}</dd>
          <dt>
            <span id="apparitions">
              <Translate contentKey="lappLiApp.elementSupply.apparitions">Apparitions</Translate>
            </span>
          </dt>
          <dd>{elementSupplyEntity.apparitions}</dd>
          <dt>
            <span id="forcedMarking">
              <Translate contentKey="lappLiApp.elementSupply.forcedMarking">Forced Marking</Translate>
            </span>
          </dt>
          <dd>{elementSupplyEntity.forcedMarking}</dd>
          <dt>
            <span id="markingType">
              <Translate contentKey="lappLiApp.elementSupply.markingType">Marking Type</Translate>
            </span>
          </dt>
          <dd>{elementSupplyEntity.markingType}</dd>
          <dt>
            <Translate contentKey="lappLiApp.articleNumber">Article Number</Translate>
          </dt>
          <dd>{elementSupplyEntity.element ? elementSupplyEntity.element.number : ''}</dd>
          <dt>
            <Translate contentKey="lappLiApp.elementSupply.element">Element</Translate>
          </dt>
          <dd>{elementSupplyEntity.element ? elementSupplyEntity.element.designationWithColor : ''}</dd>
        </dl>
        <Button tag={Link} to="/element-supply" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/element-supply/${elementSupplyEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ElementSupplyDetail;
