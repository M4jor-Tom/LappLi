import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './one-study-supply.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const OneStudySupplyDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const oneStudySupplyEntity = useAppSelector(state => state.oneStudySupply.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="oneStudySupplyDetailsHeading">
          <Translate contentKey="lappLiApp.oneStudySupply.detail.title">OneStudySupply</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{oneStudySupplyEntity.id}</dd>
          <dt>
            <span id="apparitions">
              <Translate contentKey="lappLiApp.oneStudySupply.apparitions">Apparitions</Translate>
            </span>
          </dt>
          <dd>{oneStudySupplyEntity.apparitions}</dd>
          <dt>
            <span id="number">
              <Translate contentKey="lappLiApp.oneStudySupply.number">Number</Translate>
            </span>
          </dt>
          <dd>{oneStudySupplyEntity.number}</dd>
          <dt>
            <span id="componentDesignation">
              <Translate contentKey="lappLiApp.oneStudySupply.componentDesignation">Component Designation</Translate>
            </span>
          </dt>
          <dd>{oneStudySupplyEntity.componentDesignation}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="lappLiApp.oneStudySupply.description">Description</Translate>
            </span>
          </dt>
          <dd>{oneStudySupplyEntity.description}</dd>
          <dt>
            <span id="markingType">
              <Translate contentKey="lappLiApp.oneStudySupply.markingType">Marking Type</Translate>
            </span>
          </dt>
          <dd>{oneStudySupplyEntity.markingType}</dd>
          <dt>
            <span id="gramPerMeterLinearMass">
              <Translate contentKey="lappLiApp.oneStudySupply.gramPerMeterLinearMass">Gram Per Meter Linear Mass</Translate>
            </span>
          </dt>
          <dd>{oneStudySupplyEntity.gramPerMeterLinearMass}</dd>
          <dt>
            <span id="milimeterDiameter">
              <Translate contentKey="lappLiApp.oneStudySupply.milimeterDiameter">Milimeter Diameter</Translate>
            </span>
          </dt>
          <dd>{oneStudySupplyEntity.milimeterDiameter}</dd>
          <dt>
            <span id="surfaceColor">
              <Translate contentKey="lappLiApp.oneStudySupply.surfaceColor">Surface Color</Translate>
            </span>
          </dt>
          <dd>{oneStudySupplyEntity.surfaceColor}</dd>
          <dt>
            <Translate contentKey="lappLiApp.oneStudySupply.surfaceMaterial">Surface Material</Translate>
          </dt>
          <dd>{oneStudySupplyEntity.surfaceMaterial ? oneStudySupplyEntity.surfaceMaterial.designation : ''}</dd>
          <dt>
            <Translate contentKey="lappLiApp.supply.bestLiftersNames">Best Lifters</Translate>
          </dt>
          <dd>{oneStudySupplyEntity.bestLiftersNames}</dd>
        </dl>
        <Button tag={Link} to="/one-study-supply" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/one-study-supply/${oneStudySupplyEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default OneStudySupplyDetail;
