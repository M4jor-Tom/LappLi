import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './sheathing.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const SheathingDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const sheathingEntity = useAppSelector(state => state.sheathing.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="sheathingDetailsHeading">
          <Translate contentKey="lappLiApp.sheathing.detail.title">Sheathing</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{sheathingEntity.id}</dd>
          <dt>
            <span id="operationLayer">
              <Translate contentKey="lappLiApp.sheathing.operationLayer">Operation Layer</Translate>
            </span>
          </dt>
          <dd>{sheathingEntity.operationLayer}</dd>
          <dt>
            <span id="thickness">
              <Translate contentKey="lappLiApp.sheathing.thickness">Thickness</Translate>
            </span>
          </dt>
          <dd>{sheathingEntity.thickness}</dd>
          <dt>
            <span id="sheathingKind">
              <Translate contentKey="lappLiApp.sheathing.sheathingKind">Sheathing Kind</Translate>
            </span>
          </dt>
          <dd>{sheathingEntity.sheathingKind}</dd>
          <dt>
            <Translate contentKey="lappLiApp.sheathing.material">Material</Translate>
          </dt>
          <dd>{sheathingEntity.material ? sheathingEntity.material.designation : ''}</dd>
          <dt>
            <Translate contentKey="lappLiApp.sheathing.ownerStrand">Owner Strand</Translate>
          </dt>
          <dd>{sheathingEntity.ownerStrand ? sheathingEntity.ownerStrand.designation : ''}</dd>
        </dl>
        <Button tag={Link} to="/sheathing" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/sheathing/${sheathingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SheathingDetail;
