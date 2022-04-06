import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './strip-laying.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const StripLayingDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const stripLayingEntity = useAppSelector(state => state.stripLaying.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="stripLayingDetailsHeading">
          <Translate contentKey="lappLiApp.stripLaying.detail.title">StripLaying</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{stripLayingEntity.id}</dd>
          <dt>
            <span id="operationLayer">
              <Translate contentKey="lappLiApp.stripLaying.operationLayer">Operation Layer</Translate>
            </span>
          </dt>
          <dd>{stripLayingEntity.operationLayer}</dd>
          <dt>
            <Translate contentKey="lappLiApp.stripLaying.ownerStrandSupply">Owner Strand Supply</Translate>
          </dt>
          <dd>{stripLayingEntity.ownerStrandSupply ? stripLayingEntity.ownerStrandSupply.designation : ''}</dd>
        </dl>
        <Button tag={Link} to="/strip-laying" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/strip-laying/${stripLayingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default StripLayingDetail;
