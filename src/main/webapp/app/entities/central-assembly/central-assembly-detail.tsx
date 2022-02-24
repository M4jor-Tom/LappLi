import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './central-assembly.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CentralAssemblyDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const centralAssemblyEntity = useAppSelector(state => state.centralAssembly.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="centralAssemblyDetailsHeading">
          <Translate contentKey="lappLiApp.centralAssembly.detail.title">CentralAssembly</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{centralAssemblyEntity.id}</dd>
          <dt>
            <Translate contentKey="lappLiApp.centralAssembly.ownerStrandSupply">Owner Strand Supply</Translate>
          </dt>
          <dd>{centralAssemblyEntity.ownerStrandSupply ? centralAssemblyEntity.ownerStrandSupply.designation : ''}</dd>
          <dt>
            <Translate contentKey="lappLiApp.centralAssembly.supplyPosition">Supply Position</Translate>
          </dt>
          <dd>{centralAssemblyEntity.supplyPosition ? centralAssemblyEntity.supplyPosition.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/central-assembly" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/central-assembly/${centralAssemblyEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CentralAssemblyDetail;
