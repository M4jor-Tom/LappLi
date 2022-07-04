import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './my-new-component-supply.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const MyNewComponentSupplyDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const myNewComponentSupplyEntity = useAppSelector(state => state.myNewComponentSupply.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="myNewComponentSupplyDetailsHeading">
          <Translate contentKey="lappLiApp.myNewComponentSupply.detail.title">MyNewComponentSupply</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{myNewComponentSupplyEntity.id}</dd>
          <dt>
            <span id="apparitions">
              <Translate contentKey="lappLiApp.myNewComponentSupply.apparitions">Apparitions</Translate>
            </span>
          </dt>
          <dd>{myNewComponentSupplyEntity.apparitions}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="lappLiApp.myNewComponentSupply.description">Description</Translate>
            </span>
          </dt>
          <dd>{myNewComponentSupplyEntity.description}</dd>
          <dt>
            <span id="markingType">
              <Translate contentKey="lappLiApp.myNewComponentSupply.markingType">Marking Type</Translate>
            </span>
          </dt>
          <dd>{myNewComponentSupplyEntity.markingType}</dd>
          <dt>
            <Translate contentKey="lappLiApp.myNewComponentSupply.myNewComponent">My New Component</Translate>
          </dt>
          <dd>{myNewComponentSupplyEntity.myNewComponent ? myNewComponentSupplyEntity.myNewComponent.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/my-new-component-supply" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/my-new-component-supply/${myNewComponentSupplyEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MyNewComponentSupplyDetail;
