import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './continuity-wire-longit-laying.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ContinuityWireLongitLayingDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const continuityWireLongitLayingEntity = useAppSelector(state => state.continuityWireLongitLaying.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="continuityWireLongitLayingDetailsHeading">
          <Translate contentKey="lappLiApp.continuityWireLongitLaying.detail.title">ContinuityWireLongitLaying</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{continuityWireLongitLayingEntity.id}</dd>
          <dt>
            <span id="operationLayer">
              <Translate contentKey="lappLiApp.continuityWireLongitLaying.operationLayer">Operation Layer</Translate>
            </span>
          </dt>
          <dd>{continuityWireLongitLayingEntity.operationLayer}</dd>
          <dt>
            <span id="anonymousContinuityWireMetalFiberKind">
              <Translate contentKey="lappLiApp.continuityWireLongitLaying.anonymousContinuityWireMetalFiberKind">
                Anonymous Continuity Wire Metal Fiber Kind
              </Translate>
            </span>
          </dt>
          <dd>{continuityWireLongitLayingEntity.anonymousContinuityWireMetalFiberKind}</dd>
          <dt>
            <span id="anonymousContinuityWireMilimeterDiameter">
              <Translate contentKey="lappLiApp.continuityWireLongitLaying.anonymousContinuityWireMilimeterDiameter">
                Anonymous Continuity Wire Milimeter Diameter
              </Translate>
            </span>
          </dt>
          <dd>{continuityWireLongitLayingEntity.anonymousContinuityWireMilimeterDiameter}</dd>
          <dt>
            <span id="anonymousContinuityWireFlexibility">
              <Translate contentKey="lappLiApp.continuityWireLongitLaying.anonymousContinuityWireFlexibility">
                Anonymous Continuity Wire Flexibility
              </Translate>
            </span>
          </dt>
          <dd>{continuityWireLongitLayingEntity.anonymousContinuityWireFlexibility}</dd>
          <dt>
            <Translate contentKey="lappLiApp.continuityWireLongitLaying.continuityWire">Continuity Wire</Translate>
          </dt>
          <dd>{continuityWireLongitLayingEntity.continuityWire ? continuityWireLongitLayingEntity.continuityWire.designation : ''}</dd>
          <dt>
            <Translate contentKey="lappLiApp.continuityWireLongitLaying.ownerStrandSupply">Owner Strand Supply</Translate>
          </dt>
          <dd>
            {continuityWireLongitLayingEntity.ownerStrandSupply ? continuityWireLongitLayingEntity.ownerStrandSupply.designation : ''}
          </dd>
        </dl>
        <Button tag={Link} to="/continuity-wire-longit-laying" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/continuity-wire-longit-laying/${continuityWireLongitLayingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ContinuityWireLongitLayingDetail;
