import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './screen.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ScreenDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const screenEntity = useAppSelector(state => state.screen.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="screenDetailsHeading">
          <Translate contentKey="lappLiApp.screen.detail.title">Screen</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{screenEntity.id}</dd>
          <dt>
            <span id="operationLayer">
              <Translate contentKey="lappLiApp.screen.operationLayer">Operation Layer</Translate>
            </span>
          </dt>
          <dd>{screenEntity.operationLayer}</dd>
          <dt>
            <span id="assemblyMeanIsSameThanAssemblys">
              <Translate contentKey="lappLiApp.screen.assemblyMeanIsSameThanAssemblys">Assembly Mean Is Same Than Assemblys</Translate>
            </span>
          </dt>
          <dd>{screenEntity.assemblyMeanIsSameThanAssemblys ? 'true' : 'false'}</dd>
          <dt>
            <span id="forcedDiameterAssemblyStep">
              <Translate contentKey="lappLiApp.screen.forcedDiameterAssemblyStep">Forced Diameter Assembly Step</Translate>
            </span>
          </dt>
          <dd>{screenEntity.forcedDiameterAssemblyStep}</dd>
          <dt>
            <Translate contentKey="lappLiApp.screen.copperFiber">Copper Fiber</Translate>
          </dt>
          <dd>{screenEntity.copperFiber ? screenEntity.copperFiber.designation : ''}</dd>
        </dl>
        <Button tag={Link} to="/screen" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/screen/${screenEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ScreenDetail;
