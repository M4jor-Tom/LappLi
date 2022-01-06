import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './core-assembly.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CoreAssemblyDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const coreAssemblyEntity = useAppSelector(state => state.coreAssembly.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="coreAssemblyDetailsHeading">
          <Translate contentKey="lappLiApp.coreAssembly.detail.title">CoreAssembly</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{coreAssemblyEntity.id}</dd>
          <dt>
            <span id="operationLayer">
              <Translate contentKey="lappLiApp.coreAssembly.operationLayer">Operation Layer</Translate>
            </span>
          </dt>
          <dd>{coreAssemblyEntity.operationLayer}</dd>
          <dt>
            <span id="productionStep">
              <Translate contentKey="lappLiApp.coreAssembly.productionStep">Production Step</Translate>
            </span>
          </dt>
          <dd>{coreAssemblyEntity.productionStep}</dd>
          <dt>
            <span id="diameterAssemblyStep">
              <Translate contentKey="lappLiApp.coreAssembly.diameterAssemblyStep">Diameter Assembly Step</Translate>
            </span>
          </dt>
          <dd>{coreAssemblyEntity.diameterAssemblyStep}</dd>
          <dt>
            <span id="assemblyMean">
              <Translate contentKey="lappLiApp.coreAssembly.assemblyMean">Assembly Mean</Translate>
            </span>
          </dt>
          <dd>{coreAssemblyEntity.assemblyMean}</dd>
          <dt>
            <Translate contentKey="lappLiApp.assembly.ownerStrand">Owner Strand</Translate>
          </dt>
          <dd>{coreAssemblyEntity.ownerStrand ? coreAssemblyEntity.ownerStrand.designation : ''}</dd>
        </dl>
        <Button tag={Link} to="/core-assembly" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/core-assembly/${coreAssemblyEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CoreAssemblyDetail;
