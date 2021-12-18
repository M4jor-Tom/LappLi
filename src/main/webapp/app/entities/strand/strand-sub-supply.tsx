import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Table } from 'reactstrap';
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
    <div>
      <div>
        {/* md="8">*/}
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
          <dd>
            <div className="table-responsive">
              {/* [DUPLICATE] */}
              {(strandEntity.customComponentSupplies && strandEntity.customComponentSupplies.length > 0) ||
              (strandEntity.bangleSupplies && strandEntity.bangleSupplies.length > 0) ||
              (strandEntity.elementSupplies && strandEntity.elementSupplies.length > 0) ? (
                <Table responsive>
                  <thead>
                    <tr>
                      <th>
                        <Translate contentKey="lappLiApp.supply.apparitions">Apparitions</Translate>
                      </th>
                      <th>
                        <Translate contentKey="lappLiApp.supply.markingType">Marking Type</Translate>
                      </th>
                      <th>
                        <Translate contentKey="lappLiApp.article.number">Article Number</Translate>
                      </th>
                      <th>
                        <Translate contentKey="lappLiApp.article.designation">Designation</Translate>
                      </th>
                      <th>
                        <Translate contentKey="lappLiApp.supply.description">Description</Translate>
                      </th>
                      <th>
                        <Translate contentKey="lappLiApp.dimension.meterQuantity">Quantity (m)</Translate>
                      </th>
                      <th>
                        <Translate contentKey="lappLiApp.dimension.milimeterDiameter">Diameter (mm)</Translate>
                      </th>
                      <th>
                        <Translate contentKey="lappLiApp.dimension.gramPerMeterLinearMass">Linear Mass (g/m)</Translate>
                      </th>
                      <th>
                        <Translate contentKey="lappLiApp.supply.bestLiftersNames">Best Machines Names</Translate>
                      </th>
                      <th>
                        <Translate contentKey="lappLiApp.supply.surfaceMaterial">Surface Material</Translate>
                      </th>
                      <th>
                        <Translate contentKey="lappLiApp.supply.surfaceColor">Surface Color</Translate>
                      </th>
                      <th>
                        <Translate contentKey="lappLiApp.supply.meterPerHourSpeed">Speed (m/h)</Translate>
                      </th>
                      <th>
                        <Translate contentKey="lappLiApp.supply.formatedHourPreparationTime">Preparation Time (h)</Translate>
                      </th>
                      <th>
                        <Translate contentKey="lappLiApp.supply.formatedHourExecutionTime">Execution Time (h)</Translate>
                      </th>
                      <th>
                        <Translate contentKey="lappLiApp.supply.markingTechnique">Marking Technique</Translate>
                      </th>
                      <th />
                    </tr>
                  </thead>
                  <tbody>
                    {strandEntity.customComponentSupplies.map((customComponentSupply, i) => (
                      <>
                        <tr>
                          <td>{customComponentSupply.apparitions}</td>
                          <td>
                            <Translate contentKey={`lappLiApp.MarkingType.${customComponentSupply.markingType}`} />
                          </td>
                          <td>
                            <Link to={`/custom-component/${customComponentSupply.customComponent.id}`}>
                              {customComponentSupply.customComponent.number}
                            </Link>
                          </td>
                          <td>
                            <Link to={`/custom-component/${customComponentSupply.customComponent.id}`}>
                              {customComponentSupply.customComponent.designation}
                            </Link>
                          </td>
                          <td>{customComponentSupply.description}</td>
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
                          <td>
                            <Button
                              tag={Link}
                              to={`${'custom-component-supply'}/${customComponentSupply.id}/edit`}
                              color="primary"
                              size="sm"
                              data-cy="entityEditButton"
                            >
                              <FontAwesomeIcon icon="pencil-alt" />{' '}
                              <span className="d-none d-md-inline">
                                <Translate contentKey="entity.action.edit">Edit</Translate>
                              </span>
                            </Button>
                          </td>
                          <td>
                            <Button
                              tag={Link}
                              to={`${'custom-component-supply'}/${customComponentSupply.id}/delete`}
                              color="danger"
                              size="sm"
                              data-cy="entityDeleteButton"
                            >
                              <FontAwesomeIcon icon="trash" />{' '}
                              <span className="d-none d-md-inline">
                                <Translate contentKey="entity.action.delete">Delete</Translate>
                              </span>
                            </Button>
                          </td>
                        </tr>
                      </>
                    ))}
                    {strandEntity.bangleSupplies.map((bangleSupply, i) => (
                      <>
                        <tr>
                          <td>{bangleSupply.apparitions}</td>
                          <td>{/* MarkingType, absent for bangles */}</td>
                          <td>
                            <Link to={`/bangle/${bangleSupply.bangle.id}`}>{bangleSupply.bangle.number}</Link>
                          </td>
                          <td>
                            <Link to={`/bangle/${bangleSupply.bangle.id}`}>{bangleSupply.bangle.designation}</Link>
                          </td>
                          <td>{bangleSupply.description}</td>
                          <td>{bangleSupply.meterQuantity}</td>
                          <td>{bangleSupply.bangle.milimeterDiameter}</td>
                          <td>{bangleSupply.bangle.gramPerMeterLinearMass}</td>
                          <td>{bangleSupply.bestLiftersNames}</td>
                          <td>{bangleSupply.bangle.material.designation}</td>
                          <td>{/* surfaceColor, absent for bangles */}</td>
                          <td>{bangleSupply.meterPerHourSpeed}</td>
                          <td>{bangleSupply.formatedHourPreparationTime}</td>
                          <td>{bangleSupply.formatedHourExecutionTime}</td>
                          <td>{/* MarkingTechnique, absent for bangles */}</td>
                          <td>
                            <Button
                              tag={Link}
                              to={`${/* bangleSupplyImport.bangleSupplyMatchUrl */ 'bangle-supply'}/${bangleSupply.id}/edit`}
                              color="primary"
                              size="sm"
                              data-cy="entityEditButton"
                            >
                              <FontAwesomeIcon icon="pencil-alt" />{' '}
                              <span className="d-none d-md-inline">
                                <Translate contentKey="entity.action.edit">Edit</Translate>
                              </span>
                            </Button>
                          </td>
                          <td>
                            <Button
                              tag={Link}
                              to={`${'bangle-supply'}/${bangleSupply.id}/delete`}
                              color="danger"
                              size="sm"
                              data-cy="entityDeleteButton"
                            >
                              <FontAwesomeIcon icon="trash" />{' '}
                              <span className="d-none d-md-inline">
                                <Translate contentKey="entity.action.delete">Delete</Translate>
                              </span>
                            </Button>
                          </td>
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
                            <Link to={`/element/${elementSupply.element.id}`}>{elementSupply.element.number}</Link>
                          </td>
                          <td>
                            <Link to={`/element/${elementSupply.element.id}`}>{elementSupply.element.designationWithColor}</Link>
                          </td>
                          <td>{elementSupply.description}</td>
                          <td>{elementSupply.meterQuantity}</td>
                          <td>{elementSupply.element.elementKind.milimeterDiameter}</td>
                          <td>{elementSupply.element.elementKind.gramPerMeterLinearMass}</td>
                          <td>{elementSupply.bestLiftersNames}</td>
                          <td>{elementSupply.element.elementKind.insulationMaterial.designation}</td>
                          <td>{elementSupply.element.color}</td>
                          <td>{elementSupply.meterPerHourSpeed}</td>
                          <td>{elementSupply.formatedHourPreparationTime}</td>
                          <td>{elementSupply.formatedHourExecutionTime}</td>
                          <td>{elementSupply.markingTechnique}</td>
                          <td>
                            <Button
                              tag={Link}
                              to={`${'element-supply'}/${elementSupply.id}/edit`}
                              color="primary"
                              size="sm"
                              data-cy="entityEditButton"
                            >
                              <FontAwesomeIcon icon="pencil-alt" />{' '}
                              <span className="d-none d-md-inline">
                                <Translate contentKey="entity.action.edit">Edit</Translate>
                              </span>
                            </Button>
                          </td>
                          <td>
                            <Button
                              tag={Link}
                              to={`${'element-supply'}/${elementSupply.id}/delete`}
                              color="danger"
                              size="sm"
                              data-cy="entityDeleteButton"
                            >
                              <FontAwesomeIcon icon="trash" />{' '}
                              <span className="d-none d-md-inline">
                                <Translate contentKey="entity.action.delete">Delete</Translate>
                              </span>
                            </Button>
                          </td>
                        </tr>
                      </>
                    ))}
                  </tbody>
                </Table>
              ) : (
                <div className="alert alert-warning">
                  <Translate contentKey="lappLiApp.strand.noSuppliesFound">No Supplies Found</Translate>
                </div>
              )}
              <Link
                to={`bangle-supply/new`}
                className="btn btn-primary jh-create-entity"
                id="jh-create-entity"
                data-cy="entityCreateButton"
              >
                <FontAwesomeIcon icon="plus" />
                &nbsp;
                <Translate contentKey="lappLiApp.bangleSupply.home.createLabel">Create new Bangle Supply</Translate>
              </Link>
              <Link
                to={`custom-component-supply/new`}
                className="btn btn-primary jh-create-entity"
                id="jh-create-entity"
                data-cy="entityCreateButton"
              >
                <FontAwesomeIcon icon="plus" />
                &nbsp;
                <Translate contentKey="lappLiApp.customComponentSupply.home.createLabel">Create new Custom Component Supply</Translate>
              </Link>
              <Link
                to={`element-supply/new`}
                className="btn btn-primary jh-create-entity"
                id="jh-create-entity"
                data-cy="entityCreateButton"
              >
                <FontAwesomeIcon icon="plus" />
                &nbsp;
                <Translate contentKey="lappLiApp.elementSupply.home.createLabel">Create new Element Supply</Translate>
              </Link>
            </div>
          </dd>
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
      </div>
    </div>
  );
};

export default StrandSubSupply;
