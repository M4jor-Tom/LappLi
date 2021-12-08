import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './element-supply.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ElementSupplyDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const elementSupplyEntity = useAppSelector(state => state.elementSupply.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="elementSupplyDetailsHeading">
          <Translate contentKey="lappLiApp.elementSupply.detail.title">ElementSupply</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{elementSupplyEntity.id}</dd>
          <dt>
            <span id="apparitions">
              <Translate contentKey="lappLiApp.supply.apparitions">Apparitions</Translate>
            </span>
          </dt>
          <dd>{elementSupplyEntity.apparitions}</dd>
          <dt>
            <span id="markingType">
              <Translate contentKey="lappLiApp.elementSupply.markingType">Marking Type</Translate>
            </span>
          </dt>
          <dd>{elementSupplyEntity.markingType}</dd>
          <dt>
            <Translate contentKey="lappLiApp.article.number">Article Number</Translate>
          </dt>
          <dd>{elementSupplyEntity.element?.number}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="lappLiApp.supply.description">Description</Translate>
            </span>
          </dt>
          <dd>{elementSupplyEntity.description}</dd>
          <dt>
            <Translate contentKey="lappLiApp.elementSupply.element">Element</Translate>
          </dt>
          <dd>{elementSupplyEntity.element?.designationWithColor}</dd>
          <dt>
            <Translate contentKey="lappLiApp.dimension.meterQuantity">Quantity (m)</Translate>
          </dt>
          <dd>{elementSupplyEntity.meterQuantity}</dd>
          <dt>
            <Translate contentKey="lappLiApp.dimension.milimeterDiameter">Diameter (mm)</Translate>
          </dt>
          <dd>{elementSupplyEntity.milimeterDiameter}</dd>
          <dt>
            <Translate contentKey="lappLiApp.dimension.gramPerMeterLinearMass">Linear Mass (g/m)</Translate>
          </dt>
          <dd>{elementSupplyEntity.gramPerMeterLinearMass}</dd>
          <dt>
            <Translate contentKey="lappLiApp.supply.bestLiftersNames">Best Lifter Names</Translate>
          </dt>
          <dd>{elementSupplyEntity.bestLiftersNames}</dd>
          <dt>
            <Translate contentKey="lappLiApp.elementKind.insulationMaterial">Material</Translate>
          </dt>
          <dd>{elementSupplyEntity.insulationMaterialDesignation}</dd>
          <dt>
            <Translate contentKey="lappLiApp.supply.meterPerHourSpeed">Speed (m/h)</Translate>
          </dt>
          <dd>{elementSupplyEntity.meterPerHourSpeed}</dd>
          <dt>
            <Translate contentKey="lappLiApp.supply.formatedHourPreparationTime">PreparationTime (h)</Translate>
          </dt>
          <dd>{elementSupplyEntity.formatedHourPreparationTime}</dd>
          <dt>
            <Translate contentKey="lappLiApp.supply.formatedFormatedHourExecutionTime">Execution Time (h)</Translate>
          </dt>
          <dd>{elementSupplyEntity.formatedFormatedHourExecutionTime}</dd>
          <dt>
            <Translate contentKey="lappLiApp.elementSupply.markingTechnique">Marking Technique</Translate>
          </dt>
          <dd>{elementSupplyEntity.markingTechnique}</dd>
        </dl>
        <Button tag={Link} to="/element-supply" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/element-supply/${elementSupplyEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ElementSupplyDetail;
