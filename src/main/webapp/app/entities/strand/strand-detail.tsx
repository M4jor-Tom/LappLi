import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './strand.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const StrandDetail = (props: RouteComponentProps<{ id: string }>) => {
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
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{strandEntity.id}</dd>
          <dt>
            <span id="designation">
              <Translate contentKey="lappLiApp.strand.designation">Designation</Translate>
            </span>
          </dt>
          <dd>{strandEntity.designation}</dd>
          <dt>
            <span id="housingOperationType">
              <Translate contentKey="lappLiApp.strand.housingOperationType">Housing Operation Type</Translate>
            </span>
          </dt>
          <dd>{strandEntity.housingOperationType}</dd>
          <dt>
            <span id="customComponentSupplies">
              <Translate contentKey="lappLiApp.strand.customComponentSupplies">Custom component supplies</Translate>
            </span>
          </dt>
          <dd>
            <div className="table-responsive">
              {strandEntity.customComponentSupplies && strandEntity.customComponentSupplies.length > 0 ? (
                <Table responsive>
                  <tbody>
                    {strandEntity.customComponentSupplies.map((customComponentSupply, i) => (
                      <>
                        <td>{customComponentSupply.apparitions}</td>
                        <td>{customComponentSupply.description}</td>
                        <td>
                          <Translate contentKey={`lappLiApp.MarkingType.${customComponentSupply.markingType}`} />
                        </td>
                        <td>
                          {customComponentSupply.customComponent ? (
                            <Link to={`custom-component/${customComponentSupply.customComponent.id}`}>
                              {customComponentSupply.customComponent.designation}
                            </Link>
                          ) : (
                            ''
                          )}
                        </td>
                        <td>{customComponentSupply.meterQuantity}</td>
                        <td>{customComponentSupply.customComponent.milimeterDiameter}</td>
                        <td>{customComponentSupply.customComponent.gramPerMeterLinearMass}</td>
                        <td>{customComponentSupply.bestLiftersNames}</td>
                        <td>{customComponentSupply.customComponent.surfaceMaterial?.designation}</td>
                        <td>{customComponentSupply.customComponent.surfaceColor}</td>
                        <td>{customComponentSupply.meterPerHourSpeed}</td>
                        <td>{customComponentSupply.formatedHourPreparationTime}</td>
                        <td>{customComponentSupply.formatedHourExecutionTime}</td>
                        <td>{customComponentSupply.markingTechnique}</td>
                      </>
                    ))}
                  </tbody>
                </Table>
              ) : (
                <div>No custom components</div>
              )}
            </div>
          </dd>
        </dl>
        <Button tag={Link} to="/strand" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/strand/${strandEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default StrandDetail;
