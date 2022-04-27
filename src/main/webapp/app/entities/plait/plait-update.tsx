import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { ICopperFiber } from 'app/shared/model/copper-fiber.model';
import { getEntities as getCopperFibers } from 'app/entities/copper-fiber/copper-fiber.reducer';
import { ISteelFiber } from 'app/shared/model/steel-fiber.model';
import { getEntities as getSteelFibers } from 'app/entities/steel-fiber/steel-fiber.reducer';
import { IStrandSupply } from 'app/shared/model/strand-supply.model';
import { getEntities as getStrandSupplies, getEntity as getStrandSupplyEntity } from 'app/entities/strand-supply/strand-supply.reducer';
import { getEntity, updateEntity, createEntity, reset } from './plait.reducer';
import { IPlait } from 'app/shared/model/plait.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { MetalFiberKind } from 'app/shared/model/enumerations/metal-fiber-kind.model';
import { getOutFromStudySupplyStrandPlait } from '../index-management/index-management-lib';

export const PlaitUpdate = (props: RouteComponentProps<{ id: string; strand_supply_id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const copperFibers = useAppSelector(state => state.copperFiber.entities);
  const steelFibers = useAppSelector(state => state.steelFiber.entities);
  const strandSupplies = useAppSelector(state => state.strandSupply.entities);
  const plaitEntity = useAppSelector(state => state.plait.entity);
  const loading = useAppSelector(state => state.plait.loading);
  const updating = useAppSelector(state => state.plait.updating);
  const updateSuccess = useAppSelector(state => state.plait.updateSuccess);
  const metalFiberKindValues = Object.keys(MetalFiberKind);

  //  Design for operation -- START

  const redirectionUrl = getOutFromStudySupplyStrandPlait(props.match.url, isNew);
  const futureOwnerStrandSupplyEntity = useAppSelector(state => state.strandSupply.entity);

  //  Design for operation -- END

  const handleClose = () => {
    props.history.push(redirectionUrl);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getCopperFibers({}));
    dispatch(getSteelFibers({}));
    dispatch(getStrandSupplies({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    //  Design for operation -- START

    let futureOwnerStrandSupply: IStrandSupply = {};

    if (values.ownerStrandSupply) {
      futureOwnerStrandSupply = strandSupplies.find(it => it.id.toString() === values.ownerStrandSupply.toString());
    } else {
      dispatch(getStrandSupplyEntity(props.match.params.strand_supply_id));
      futureOwnerStrandSupply = futureOwnerStrandSupplyEntity;
    }

    //  Design for operation -- END

    const entity = {
      ...plaitEntity,
      ...values,
      __typeName: 'Plait',
      copperFiber: copperFibers.find(it => it.id.toString() === values.copperFiber.toString()),
      steelFiber: steelFibers.find(it => it.id.toString() === values.steelFiber.toString()),
      ownerStrandSupply: futureOwnerStrandSupply,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          anonymousMetalFiberMetalFiberKind: 'RED_COPPER',
          ...plaitEntity,
          copperFiber: plaitEntity?.copperFiber?.id,
          steelFiber: plaitEntity?.steelFiber?.id,
          ownerStrandSupply: plaitEntity?.ownerStrandSupply?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="lappLiApp.plait.home.createOrEditLabel" data-cy="PlaitCreateUpdateHeading">
            <Translate contentKey="lappLiApp.plait.home.createOrEditLabel">Create or edit a Plait</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="plait-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              {isNew ? (
                <ValidatedField
                  label={translate('lappLiApp.plait.operationLayer')}
                  id="plait-operationLayer"
                  name="operationLayer"
                  data-cy="operationLayer"
                  type="text"
                  defaultValue={-2}
                  validate={{
                    required: { value: true, message: translate('entity.validation.required') },
                    validate: v => isNumber(v) || translate('entity.validation.number'),
                  }}
                />
              ) : null}
              <ValidatedField
                label={translate('lappLiApp.plait.targetCoveringRate')}
                id="plait-targetCoveringRate"
                name="targetCoveringRate"
                data-cy="targetCoveringRate"
                type="text"
              />
              <ValidatedField
                label={translate('lappLiApp.plait.targetDegreeAngle')}
                id="plait-targetDegreeAngle"
                name="targetDegreeAngle"
                data-cy="targetDegreeAngle"
                type="text"
              />
              <ValidatedField
                label={translate('lappLiApp.plait.targetingCoveringRateNotAngle')}
                id="plait-targetingCoveringRateNotAngle"
                name="targetingCoveringRateNotAngle"
                data-cy="targetingCoveringRateNotAngle"
                check
                type="checkbox"
              />
              <ValidatedField
                id="plait-copperFiber"
                name="copperFiber"
                data-cy="copperFiber"
                label={translate('lappLiApp.plait.copperFiber')}
                type="select"
              >
                <option value="" key="0" />
                {copperFibers
                  ? copperFibers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.designation}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="plait-steelFiber"
                name="steelFiber"
                data-cy="steelFiber"
                label={translate('lappLiApp.plait.steelFiber')}
                type="select"
              >
                <option value="" key="0" />
                {steelFibers
                  ? steelFibers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.designation}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              {props.match.params ? (
                ''
              ) : (
                <ValidatedField
                  id="plait-ownerStrandSupply"
                  name="ownerStrandSupply"
                  data-cy="ownerStrandSupply"
                  label={translate('lappLiApp.plait.ownerStrandSupply')}
                  type="select"
                  required
                >
                  <option value="" key="0" />
                  {strandSupplies
                    ? strandSupplies.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.designation}
                        </option>
                      ))
                    : null}
                </ValidatedField>
              )}
              {props.match.params ? (
                ''
              ) : (
                <FormText>
                  <Translate contentKey="entity.validation.required">This field is required.</Translate>
                </FormText>
              )}
              <ValidatedField
                label={translate('lappLiApp.plait.anonymousMetalFiberNumber')}
                id="plait-anonymousMetalFiberNumber"
                name="anonymousMetalFiberNumber"
                data-cy="anonymousMetalFiberNumber"
                type="text"
              />
              <ValidatedField
                label={translate('lappLiApp.plait.anonymousMetalFiberDesignation')}
                id="plait-anonymousMetalFiberDesignation"
                name="anonymousMetalFiberDesignation"
                data-cy="anonymousMetalFiberDesignation"
                type="text"
              />
              <ValidatedField
                label={translate('lappLiApp.plait.anonymousMetalFiberMetalFiberKind')}
                id="plait-anonymousMetalFiberMetalFiberKind"
                name="anonymousMetalFiberMetalFiberKind"
                data-cy="anonymousMetalFiberMetalFiberKind"
                type="select"
              >
                {metalFiberKindValues.map(metalFiberKind => (
                  <option value={metalFiberKind} key={metalFiberKind}>
                    {translate('lappLiApp.MetalFiberKind.' + metalFiberKind)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('lappLiApp.plait.anonymousMetalFiberMilimeterDiameter')}
                id="plait-anonymousMetalFiberMilimeterDiameter"
                name="anonymousMetalFiberMilimeterDiameter"
                data-cy="anonymousMetalFiberMilimeterDiameter"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to={redirectionUrl} replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default PlaitUpdate;
