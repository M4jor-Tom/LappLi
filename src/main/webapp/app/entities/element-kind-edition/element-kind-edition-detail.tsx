import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './element-kind-edition.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ElementKindEditionDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const elementKindEditionEntity = useAppSelector(state => state.elementKindEdition.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="elementKindEditionDetailsHeading">
          <Translate contentKey="lappLiApp.elementKindEdition.detail.title">ElementKindEdition</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{elementKindEditionEntity.id}</dd>
          <dt>
            <span id="editionDateTime">
              <Translate contentKey="lappLiApp.elementKindEdition.editionDateTime">Edition Date Time</Translate>
            </span>
          </dt>
          <dd>
            {elementKindEditionEntity.editionDateTime ? (
              <TextFormat value={elementKindEditionEntity.editionDateTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="newGramPerMeterLinearMass">
              <Translate contentKey="lappLiApp.elementKindEdition.newGramPerMeterLinearMass">New Gram Per Meter Linear Mass</Translate>
            </span>
          </dt>
          <dd>{elementKindEditionEntity.newGramPerMeterLinearMass}</dd>
          <dt>
            <span id="newMilimeterDiameter">
              <Translate contentKey="lappLiApp.elementKindEdition.newMilimeterDiameter">New Milimeter Diameter</Translate>
            </span>
          </dt>
          <dd>{elementKindEditionEntity.newMilimeterDiameter}</dd>
          <dt>
            <span id="newInsulationThickness">
              <Translate contentKey="lappLiApp.elementKindEdition.newInsulationThickness">New Insulation Thickness</Translate>
            </span>
          </dt>
          <dd>{elementKindEditionEntity.newInsulationThickness}</dd>
          <dt>
            <Translate contentKey="lappLiApp.elementKindEdition.editedElementKind">Edited Element Kind</Translate>
          </dt>
          <dd>{elementKindEditionEntity.editedElementKind ? elementKindEditionEntity.editedElementKind.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/element-kind-edition" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/element-kind-edition/${elementKindEditionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ElementKindEditionDetail;
