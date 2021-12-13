import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './custom-component-supply.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CustomComponentSupplyDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const customComponentSupplyEntity = useAppSelector(state => state.customComponentSupply.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="customComponentSupplyDetailsHeading">
          <Translate contentKey="lappLiApp.customComponentSupply.detail.title">CustomComponentSupply</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{customComponentSupplyEntity.id}</dd>
          <dt>
            <span id="apparitions">
              <Translate contentKey="lappLiApp.supply.apparitions">Apparitions</Translate>
            </span>
          </dt>
          <dd>{customComponentSupplyEntity.apparitions}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="lappLiApp.supply.description">Description</Translate>
            </span>
          </dt>
          <dd>{customComponentSupplyEntity.description}</dd>
          <dt>
            <span id="markingType">
              <Translate contentKey="lappLiApp.supply.markingType">Marking Type</Translate>
            </span>
          </dt>
          <dd>{customComponentSupplyEntity.markingType}</dd>
          <dt>
            <Translate contentKey="lappLiApp.customComponentSupply.customComponent">Custom Component</Translate>
          </dt>
          <dd>{customComponentSupplyEntity.customComponent ? customComponentSupplyEntity.customComponent.designation : ''}</dd>
          <dt>
            <Translate contentKey="lappLiApp.dimension.meterQuantity">Quantity (m)</Translate>
          </dt>
          <dd>{customComponentSupplyEntity.meterQuantity}</dd>
          <dt>
            <Translate contentKey="lappLiApp.dimension.milimeterDiameter">Diameter (mm)</Translate>
          </dt>
          <dd>{customComponentSupplyEntity.customComponent?.milimeterDiameter}</dd>
          <dt>
            <Translate contentKey="lappLiApp.dimension.gramPerMeterLinearMass">Linear Mass (g/m)</Translate>
          </dt>
          <dd>{customComponentSupplyEntity.customComponent?.gramPerMeterLinearMass}</dd>
          <dt>
            <Translate contentKey="lappLiApp.supply.bestLiftersNames">Best Lifter Names</Translate>
          </dt>
          <dd>{customComponentSupplyEntity.bestLiftersNames}</dd>
          <dt>
            <Translate contentKey="lappLiApp.customComponent.surfaceMaterial">Material</Translate>
          </dt>
          <dd>{customComponentSupplyEntity.customComponent?.surfaceMaterial?.designation}</dd>
          <dt>
            <Translate contentKey="lappLiApp.customComponent.surfaceColor">Color</Translate>
          </dt>
          <dd>{customComponentSupplyEntity.customComponent?.surfaceColor}</dd>
          <dt>
            <Translate contentKey="lappLiApp.supply.meterPerHourSpeed">Speed (m/h)</Translate>
          </dt>
          <dd>{customComponentSupplyEntity.meterPerHourSpeed}</dd>
          <dt>
            <Translate contentKey="lappLiApp.supply.formatedHourPreparationTime">PreparationTime (h)</Translate>
          </dt>
          <dd>{customComponentSupplyEntity.formatedHourPreparationTime}</dd>
          <dt>
            <Translate contentKey="lappLiApp.supply.formatedHourExecutionTime">Execution Time (h)</Translate>
          </dt>
          <dd>{customComponentSupplyEntity.formatedHourExecutionTime}</dd>
          <dt>
            <Translate contentKey="lappLiApp.supply.markingTechnique">Marking Technique</Translate>
          </dt>
          <dd>{customComponentSupplyEntity.markingTechnique}</dd>
          <dt>
            <Translate contentKey="lappLiApp.customComponentSupply.strand">Strand</Translate>
          </dt>
          <dd>{customComponentSupplyEntity.strand ? customComponentSupplyEntity.strand.designation : ''}</dd>
        </dl>
        <Button tag={Link} to="/custom-component-supply" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/custom-component-supply/${customComponentSupplyEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CustomComponentSupplyDetail;
