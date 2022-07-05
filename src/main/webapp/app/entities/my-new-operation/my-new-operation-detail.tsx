import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './my-new-operation.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const MyNewOperationDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const myNewOperationEntity = useAppSelector(state => state.myNewOperation.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="myNewOperationDetailsHeading">
          <Translate contentKey="lappLiApp.myNewOperation.detail.title">MyNewOperation</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{myNewOperationEntity.id}</dd>
          <dt>
            <span id="operationLayer">
              <Translate contentKey="lappLiApp.myNewOperation.operationLayer">Operation Layer</Translate>
            </span>
          </dt>
          <dd>{myNewOperationEntity.operationLayer}</dd>
          <dt>
            <span id="operationData">
              <Translate contentKey="lappLiApp.myNewOperation.operationData">Operation Data</Translate>
            </span>
          </dt>
          <dd>{myNewOperationEntity.operationData}</dd>
          <dt>
            <span id="anonymousMyNewComponentNumber">
              <Translate contentKey="lappLiApp.myNewOperation.anonymousMyNewComponentNumber">Anonymous My New Component Number</Translate>
            </span>
          </dt>
          <dd>{myNewOperationEntity.anonymousMyNewComponentNumber}</dd>
          <dt>
            <span id="anonymousMyNewComponentDesignation">
              <Translate contentKey="lappLiApp.myNewOperation.anonymousMyNewComponentDesignation">
                Anonymous My New Component Designation
              </Translate>
            </span>
          </dt>
          <dd>{myNewOperationEntity.anonymousMyNewComponentDesignation}</dd>
          <dt>
            <span id="anonymousMyNewComponentData">
              <Translate contentKey="lappLiApp.myNewOperation.anonymousMyNewComponentData">Anonymous My New Component Data</Translate>
            </span>
          </dt>
          <dd>{myNewOperationEntity.anonymousMyNewComponentData}</dd>
          <dt>
            <Translate contentKey="lappLiApp.myNewOperation.myNewComponent">My New Component</Translate>
          </dt>
          <dd>{myNewOperationEntity.myNewComponent ? myNewOperationEntity.myNewComponent.designation : ''}</dd>
          <dt>
            <Translate contentKey="lappLiApp.myNewOperation.ownerStrandSupply">Owner Strand Supply</Translate>
          </dt>
          <dd>{myNewOperationEntity.ownerStrandSupply ? myNewOperationEntity.ownerStrandSupply.designation : ''}</dd>
        </dl>
        <Button tag={Link} to="/my-new-operation" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/my-new-operation/${myNewOperationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MyNewOperationDetail;
