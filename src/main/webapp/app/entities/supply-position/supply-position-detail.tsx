import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './supply-position.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const SupplyPositionDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const supplyPositionEntity = useAppSelector(state => state.supplyPosition.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="supplyPositionDetailsHeading">
          <Translate contentKey="lappLiApp.supplyPosition.detail.title">SupplyPosition</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{supplyPositionEntity.id}</dd>
          <dt>
            <span id="supplyApparitionsUsage">
              <Translate contentKey="lappLiApp.supplyPosition.supplyApparitionsUsage">Supply Apparitions Usage</Translate>
            </span>
          </dt>
          <dd>{supplyPositionEntity.supplyApparitionsUsage}</dd>
          <dt>
            <Translate contentKey="lappLiApp.supplyPosition.elementSupply">Element Supply</Translate>
          </dt>
          <dd>{supplyPositionEntity.elementSupply ? supplyPositionEntity.elementSupply.designation : ''}</dd>
          <dt>
            <Translate contentKey="lappLiApp.supplyPosition.bangleSupply">Bangle Supply</Translate>
          </dt>
          <dd>{supplyPositionEntity.bangleSupply ? supplyPositionEntity.bangleSupply.designation : ''}</dd>
          <dt>
            <Translate contentKey="lappLiApp.supplyPosition.customComponentSupply">Custom Component Supply</Translate>
          </dt>
          <dd>{supplyPositionEntity.customComponentSupply ? supplyPositionEntity.customComponentSupply.designation : ''}</dd>
          <dt>
            <Translate contentKey="lappLiApp.supplyPosition.oneStudySupply">One Study Supply</Translate>
          </dt>
          <dd>{supplyPositionEntity.oneStudySupply ? supplyPositionEntity.oneStudySupply.designation : ''}</dd>
          <dt>
            <Translate contentKey="lappLiApp.supplyPosition.ownerStrand">Owner Strand</Translate>
          </dt>
          <dd>{supplyPositionEntity.ownerStrand ? supplyPositionEntity.ownerStrand.designation : ''}</dd>
          <dt>
            <Translate contentKey="lappLiApp.supplyPosition.ownerIntersticeAssembly">Owner Interstice Assembly</Translate>
          </dt>
          <dd>{supplyPositionEntity.ownerIntersticeAssembly ? supplyPositionEntity.ownerIntersticeAssembly.productDesignation : ''}</dd>
        </dl>
        <Button tag={Link} to="/supply-position" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/supply-position/${supplyPositionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SupplyPositionDetail;
