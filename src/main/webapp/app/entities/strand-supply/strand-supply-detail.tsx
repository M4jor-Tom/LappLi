import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './strand-supply.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const StrandSupplyDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const strandSupplyEntity = useAppSelector(state => state.strandSupply.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="strandSupplyDetailsHeading">
          <Translate contentKey="lappLiApp.strandSupply.detail.title">StrandSupply</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{strandSupplyEntity.id}</dd>
          <dt>
            <span id="apparitions">
              <Translate contentKey="lappLiApp.strandSupply.apparitions">Apparitions</Translate>
            </span>
          </dt>
          <dd>{strandSupplyEntity.apparitions}</dd>
          <dt>
            <span id="markingType">
              <Translate contentKey="lappLiApp.strandSupply.markingType">Marking Type</Translate>
            </span>
          </dt>
          <dd>{strandSupplyEntity.markingType}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="lappLiApp.strandSupply.description">Description</Translate>
            </span>
          </dt>
          <dd>{strandSupplyEntity.description}</dd>
          <dt>
            <span id="diameterAssemblyStep">
              <Translate contentKey="lappLiApp.strandSupply.diameterAssemblyStep">Diameter Assembly Step</Translate>
            </span>
          </dt>
          <dd>{strandSupplyEntity.diameterAssemblyStep}</dd>
          <dt>
            <span id="assemblyMean">
              <Translate contentKey="lappLiApp.strandSupply.assemblyMean">Assembly Mean</Translate>
            </span>
          </dt>
          <dd>{strandSupplyEntity.assemblyMean}</dd>
          <dt>
            <span id="forceCentralUtilityComponent">
              <Translate contentKey="lappLiApp.strandSupply.forceCentralUtilityComponent">Force Central Utility Component</Translate>
            </span>
          </dt>
          <dd>{strandSupplyEntity.forceCentralUtilityComponent ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="lappLiApp.strandSupply.strand">Strand</Translate>
          </dt>
          <dd>{strandSupplyEntity.strand ? strandSupplyEntity.strand.id : ''}</dd>
          <dt>
            <Translate contentKey="lappLiApp.strandSupply.study">Study</Translate>
          </dt>
          <dd>{strandSupplyEntity.study ? strandSupplyEntity.study.number : ''}</dd>
        </dl>
        <Button tag={Link} to="/strand-supply" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/strand-supply/${strandSupplyEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default StrandSupplyDetail;
