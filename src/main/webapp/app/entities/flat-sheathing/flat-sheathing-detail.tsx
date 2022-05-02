import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './flat-sheathing.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const FlatSheathingDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const flatSheathingEntity = useAppSelector(state => state.flatSheathing.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="flatSheathingDetailsHeading">
          <Translate contentKey="lappLiApp.flatSheathing.detail.title">FlatSheathing</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{flatSheathingEntity.id}</dd>
          <dt>
            <span id="operationLayer">
              <Translate contentKey="lappLiApp.flatSheathing.operationLayer">Operation Layer</Translate>
            </span>
          </dt>
          <dd>{flatSheathingEntity.operationLayer}</dd>
          <dt>
            <span id="milimeterWidth">
              <Translate contentKey="lappLiApp.flatSheathing.milimeterWidth">Milimeter Width</Translate>
            </span>
          </dt>
          <dd>{flatSheathingEntity.milimeterWidth}</dd>
          <dt>
            <span id="milimeterHeight">
              <Translate contentKey="lappLiApp.flatSheathing.milimeterHeight">Milimeter Height</Translate>
            </span>
          </dt>
          <dd>{flatSheathingEntity.milimeterHeight}</dd>
          <dt>
            <Translate contentKey="lappLiApp.flatSheathing.material">Material</Translate>
          </dt>
          <dd>{flatSheathingEntity.material ? flatSheathingEntity.material.designation : ''}</dd>
          <dt>
            <Translate contentKey="lappLiApp.flatSheathing.ownerStrandSupply">Owner Strand Supply</Translate>
          </dt>
          <dd>{flatSheathingEntity.ownerStrandSupply ? flatSheathingEntity.ownerStrandSupply.designation : ''}</dd>
        </dl>
        <Button tag={Link} to="/flat-sheathing" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/flat-sheathing/${flatSheathingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FlatSheathingDetail;
