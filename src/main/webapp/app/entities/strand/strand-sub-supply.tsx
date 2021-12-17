import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './strand.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const StrandSubSupply = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const strandEntity = useAppSelector(state => state.strand.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="strandDetailsHeading">
          <Translate contentKey="lappLiApp.strand.detail.title">Strand</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="designation">
              <Translate contentKey="lappLiApp.strand.designation">Designation</Translate>
            </span>
          </dt>
          <dd>{strandEntity.designation}</dd>
        </dl>
        <Button tag={Link} to="/strand" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        {/* &nbsp;
        <Button tag={Link} to={`/strand/${strandEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>*/}
      </Col>
    </Row>
  );
};

export default StrandSubSupply;
