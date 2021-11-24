import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './element-kind.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ElementKindDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const elementKindEntity = useAppSelector(state => state.elementKind.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="elementKindDetailsHeading">
          <Translate contentKey="lappLiApp.elementKind.detail.title">ElementKind</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{elementKindEntity.id}</dd>
          <dt>
            <span id="designation">
              <Translate contentKey="lappLiApp.elementKind.designation">Designation</Translate>
            </span>
          </dt>
          <dd>{elementKindEntity.designation}</dd>
          <dt>
            <span id="gramPerMeterMass">
              <Translate contentKey="lappLiApp.elementKind.gramPerMeterMass">Gram Per Meter Mass</Translate>
            </span>
          </dt>
          <dd>{elementKindEntity.gramPerMeterMass}</dd>
          <dt>
            <span id="milimeterDiameter">
              <Translate contentKey="lappLiApp.elementKind.milimeterDiameter">Milimeter Diameter</Translate>
            </span>
          </dt>
          <dd>{elementKindEntity.milimeterDiameter}</dd>
          <dt>
            <span id="insulationThickness">
              <Translate contentKey="lappLiApp.elementKind.insulationThickness">Insulation Thickness</Translate>
            </span>
          </dt>
          <dd>{elementKindEntity.insulationThickness}</dd>
          <dt>
            <Translate contentKey="lappLiApp.elementKind.copper">Copper</Translate>
          </dt>
          <dd>{elementKindEntity.copper ? elementKindEntity.copper.id : ''}</dd>
          <dt>
            <Translate contentKey="lappLiApp.elementKind.insulationMaterial">Insulation Material</Translate>
          </dt>
          <dd>{elementKindEntity.insulationMaterial ? elementKindEntity.insulationMaterial.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/element-kind" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/element-kind/${elementKindEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ElementKindDetail;
