import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './position.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PositionDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const positionEntity = useAppSelector(state => state.position.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="positionDetailsHeading">
          <Translate contentKey="lappLiApp.position.detail.title">Position</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{positionEntity.id}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="lappLiApp.position.value">Value</Translate>
            </span>
          </dt>
          <dd>{positionEntity.value}</dd>
          <dt>
            <Translate contentKey="lappLiApp.position.elementSupply">Element Supply</Translate>
          </dt>
          <dd>{positionEntity.elementSupply ? positionEntity.elementSupply.designation : ''}</dd>
          <dt>
            <Translate contentKey="lappLiApp.position.bangleSupply">Bangle Supply</Translate>
          </dt>
          <dd>{positionEntity.bangleSupply ? positionEntity.bangleSupply.designation : ''}</dd>
          <dt>
            <Translate contentKey="lappLiApp.position.customComponentSupply">Custom Component Supply</Translate>
          </dt>
          <dd>{positionEntity.customComponentSupply ? positionEntity.customComponentSupply.designation : ''}</dd>
          <dt>
            <Translate contentKey="lappLiApp.position.oneStudySupply">One Study Supply</Translate>
          </dt>
          <dd>{positionEntity.oneStudySupply ? positionEntity.oneStudySupply.designation : ''}</dd>
          <dt>
            <Translate contentKey="lappLiApp.position.ownerCentralAssembly">Owner Central Assembly</Translate>
          </dt>
          <dd>{positionEntity.ownerCentralAssembly ? positionEntity.ownerCentralAssembly.designation : ''}</dd>
          <dt>
            <Translate contentKey="lappLiApp.position.ownerCoreAssembly">Owner Core Assembly</Translate>
          </dt>
          <dd>{positionEntity.ownerCoreAssembly ? positionEntity.ownerCoreAssembly.designation : ''}</dd>
          <dt>
            <Translate contentKey="lappLiApp.position.ownerIntersticeAssembly">Owner Interstice Assembly</Translate>
          </dt>
          <dd>{positionEntity.ownerIntersticeAssembly ? positionEntity.ownerIntersticeAssembly.designation : ''}</dd>
        </dl>
        <Button tag={Link} to="/position" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/position/${positionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PositionDetail;
