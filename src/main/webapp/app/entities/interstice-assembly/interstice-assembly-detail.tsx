import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './interstice-assembly.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const IntersticeAssemblyDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const intersticeAssemblyEntity = useAppSelector(state => state.intersticeAssembly.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="intersticeAssemblyDetailsHeading">
          <Translate contentKey="lappLiApp.intersticeAssembly.detail.title">IntersticeAssembly</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{intersticeAssemblyEntity.id}</dd>
          <dt>
            <span id="productionStep">
              <Translate contentKey="lappLiApp.intersticeAssembly.productionStep">Production Step</Translate>
            </span>
          </dt>
          <dd>{intersticeAssemblyEntity.productionStep}</dd>
          <dt>
            <Translate contentKey="lappLiApp.intersticeAssembly.strand">Strand</Translate>
          </dt>
          <dd>{intersticeAssemblyEntity.strand ? intersticeAssemblyEntity.strand.designation : ''}</dd>
          <dt>
            <Translate contentKey="lappLiApp.nonCentralAssembly.assemblyStep">Assembly Step</Translate>
          </dt>
          <dd>{intersticeAssemblyEntity.diameterAssemblyStep}</dd>
          <dt>
            <Translate contentKey="lappLiApp.nonCentralAssembly.assemblyMean">Assembly Mean</Translate>
          </dt>
          <dd>{intersticeAssemblyEntity.assemblyMean}</dd>
        </dl>
        <Button tag={Link} to="/interstice-assembly" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/interstice-assembly/${intersticeAssemblyEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default IntersticeAssemblyDetail;
