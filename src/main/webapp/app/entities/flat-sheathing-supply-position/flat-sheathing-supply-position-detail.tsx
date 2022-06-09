import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './flat-sheathing-supply-position.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const FlatSheathingSupplyPositionDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const flatSheathingSupplyPositionEntity = useAppSelector(state => state.flatSheathingSupplyPosition.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="flatSheathingSupplyPositionDetailsHeading">
          <Translate contentKey="lappLiApp.flatSheathingSupplyPosition.detail.title">FlatSheathingSupplyPosition</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{flatSheathingSupplyPositionEntity.id}</dd>
          <dt>
            <span id="locationInOwnerFlatSheathing">
              <Translate contentKey="lappLiApp.flatSheathingSupplyPosition.locationInOwnerFlatSheathing">
                Location In Owner Flat Sheathing
              </Translate>
            </span>
          </dt>
          <dd>{flatSheathingSupplyPositionEntity.locationInOwnerFlatSheathing}</dd>
          <dt>
            <Translate contentKey="lappLiApp.flatSheathingSupplyPosition.ownerFlatSheathing">Owner Flat Sheathing</Translate>
          </dt>
          <dd>
            {flatSheathingSupplyPositionEntity.ownerFlatSheathing ? flatSheathingSupplyPositionEntity.ownerFlatSheathing.designation : ''}
          </dd>
        </dl>
        <Button tag={Link} to="/flat-sheathing-supply-position" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/flat-sheathing-supply-position/${flatSheathingSupplyPositionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FlatSheathingSupplyPositionDetail;
