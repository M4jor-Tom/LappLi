import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './i-supply.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ISupplyDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const iSupplyEntity = useAppSelector(state => state.iSupply.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="iSupplyDetailsHeading">
          <Translate contentKey="lappLiApp.iSupply.detail.title">ISupply</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{iSupplyEntity.id}</dd>
          <dt>
            <span id="apparitions">
              <Translate contentKey="lappLiApp.iSupply.apparitions">Apparitions</Translate>
            </span>
          </dt>
          <dd>{iSupplyEntity.apparitions}</dd>
          <dt>
            <span id="milimeterDiameter">
              <Translate contentKey="lappLiApp.iSupply.milimeterDiameter">Milimeter Diameter</Translate>
            </span>
          </dt>
          <dd>{iSupplyEntity.milimeterDiameter}</dd>
          <dt>
            <span id="gramPerMeterLinearMass">
              <Translate contentKey="lappLiApp.iSupply.gramPerMeterLinearMass">Gram Per Meter Linear Mass</Translate>
            </span>
          </dt>
          <dd>{iSupplyEntity.gramPerMeterLinearMass}</dd>
          <dt>
            <Translate contentKey="lappLiApp.iSupply.strand">Strand</Translate>
          </dt>
          <dd>{iSupplyEntity.strand ? iSupplyEntity.strand.designation : ''}</dd>
        </dl>
        <Button tag={Link} to="/i-supply" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/i-supply/${iSupplyEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ISupplyDetail;
