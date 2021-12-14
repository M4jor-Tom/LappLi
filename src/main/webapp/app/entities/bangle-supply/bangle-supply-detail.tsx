import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './bangle-supply.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const BangleSupplyDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const bangleSupplyEntity = useAppSelector(state => state.bangleSupply.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bangleSupplyDetailsHeading">
          <Translate contentKey="lappLiApp.bangleSupply.detail.title">BangleSupply</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{bangleSupplyEntity.id}</dd>
          <dt>
            <span id="apparitions">
              <Translate contentKey="lappLiApp.bangleSupply.apparitions">Apparitions</Translate>
            </span>
          </dt>
          <dd>{bangleSupplyEntity.apparitions}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="lappLiApp.bangleSupply.description">Description</Translate>
            </span>
          </dt>
          <dd>{bangleSupplyEntity.description}</dd>
          <dt>
            <Translate contentKey="lappLiApp.bangleSupply.bangle">Bangle</Translate>
          </dt>
          <dd>{bangleSupplyEntity.bangle ? bangleSupplyEntity.bangle.designation : ''}</dd>
          <dt>
            <Translate contentKey="lappLiApp.bangleSupply.strand">Strand</Translate>
          </dt>
          <dd>{bangleSupplyEntity.strand ? bangleSupplyEntity.strand.designation : ''}</dd>
        </dl>
        <Button tag={Link} to="/bangle-supply" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/bangle-supply/${bangleSupplyEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BangleSupplyDetail;
