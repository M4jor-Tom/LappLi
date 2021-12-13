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
              {(strandEntity.customComponentSupplies && strandEntity.customComponentSupplies.length > 0) ||
              (strandEntity.bangleSupplies && strandEntity.bangleSupplies.length > 0) ||
              (strandEntity.elementSupplies && strandEntity.elementSupplies.length > 0) ? (
                <Table responsive>
                  <thead>
                    <tr></tr>
                  </thead>
                  <tbody>
                    {' '}
                    {/*[DUPLICATE]*/}
                    {strandEntity.customComponentSupplies.map((customComponentSupply, i) => (
                      <>
                        <tr>
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
                        </tr>
                      </>
                    ))}
                    {strandEntity.bangleSupplies.map((bangleSupply, i) => (
                      <>
                        <tr>
                          <td>{bangleSupply.apparitions}</td>
                          <td>{bangleSupply.description}</td>
                          <td>
                            {bangleSupply.bangle ? (
                              <Link to={`bangle/${bangleSupply.bangle.id}`}>{bangleSupply.bangle.designation}</Link>
                            ) : (
                              ''
                            )}
                          </td>
                          <td>{bangleSupply.bangle.material.designation}</td>
                          <td>{bangleSupply.meterQuantity}</td>
                          <td>{bangleSupply.bangle.milimeterDiameter}</td>
                          <td>{bangleSupply.bangle.gramPerMeterLinearMass}</td>
                          <td>{bangleSupply.bestLiftersNames}</td>
                          <td>{bangleSupply.meterPerHourSpeed}</td>
                          <td>{bangleSupply.formatedHourPreparationTime}</td>
                          <td>{bangleSupply.formatedHourExecutionTime}</td>
                        </tr>
                      </>
                    ))}
                    {strandEntity.elementSupplies.map((elementSupply, i) => (
                      <>
                        <tr>
                          <td>{elementSupply.apparitions}</td>
                          <td>
                            <Translate contentKey={`lappLiApp.MarkingType.${elementSupply.markingType}`} />
                          </td>
                          <td>
                            {elementSupply.element ? (
                              <Link to={`element/${elementSupply.element.id}`}>{elementSupply.element.number}</Link>
                            ) : (
                              ''
                            )}
                          </td>
                          <td>
                            {elementSupply.element ? (
                              <Link to={`element/${elementSupply.element.id}`}>{elementSupply.element.designationWithColor}</Link>
                            ) : (
                              ''
                            )}
                          </td>
                          <td>{elementSupply.description}</td>
                          <td>{elementSupply.meterQuantity}</td>
                          <td>{elementSupply.element.elementKind.milimeterDiameter}</td>
                          <td>{elementSupply.element.elementKind.gramPerMeterLinearMass}</td>
                          <td>{elementSupply.bestLiftersNames}</td>
                          <td>{elementSupply.element.elementKind.insulationMaterial.designation}</td>
                          <td>{elementSupply.meterPerHourSpeed}</td>
                          <td>{elementSupply.formatedHourPreparationTime}</td>
                          <td>{elementSupply.formatedHourExecutionTime}</td>
                          <td>{elementSupply.markingTechnique}</td>
                        </tr>
                      </>
                    ))}
                  </tbody>
                </Table>
              ) : (
                <Translate contentKey="LappLiApp.strand.noSuppliesFound"> No Supplies Found</Translate>
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
