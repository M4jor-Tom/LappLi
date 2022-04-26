import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IContinuityWire } from 'app/shared/model/continuity-wire.model';
import { getEntities as getContinuityWires } from 'app/entities/continuity-wire/continuity-wire.reducer';
import { IStrandSupply } from 'app/shared/model/strand-supply.model';
import { getEntities as getStrandSupplies } from 'app/entities/strand-supply/strand-supply.reducer';
import { getEntity, updateEntity, createEntity, reset } from './continuity-wire-longit-laying.reducer';
import { IContinuityWireLongitLaying } from 'app/shared/model/continuity-wire-longit-laying.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { MetalFiberKind } from 'app/shared/model/enumerations/metal-fiber-kind.model';
import { Flexibility } from 'app/shared/model/enumerations/flexibility.model';
import { getOutFromStudySupplyStrandContinuityWireLongitLaying } from '../index-management/index-management-lib';
import { getEntity as getStrandSupplyEntity } from 'app/entities/strand-supply/strand-supply.reducer';

export const ContinuityWireLongitLayingUpdate = (props: RouteComponentProps<{ id: string; strand_supply_id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const continuityWires = useAppSelector(state => state.continuityWire.entities);
  const strandSupplies = useAppSelector(state => state.strandSupply.entities);
  const continuityWireLongitLayingEntity = useAppSelector(state => state.continuityWireLongitLaying.entity);
  const loading = useAppSelector(state => state.continuityWireLongitLaying.loading);
  const updating = useAppSelector(state => state.continuityWireLongitLaying.updating);
  const updateSuccess = useAppSelector(state => state.continuityWireLongitLaying.updateSuccess);
  const metalFiberKindValues = Object.keys(MetalFiberKind);
  const flexibilityValues = Object.keys(Flexibility);

  //  Design for operation -- START

  const redirectionUrl = getOutFromStudySupplyStrandContinuityWireLongitLaying(props.match.url, isNew);
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

    dispatch(getContinuityWires({}));
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
      ...continuityWireLongitLayingEntity,
      ...values,
      __typeName: 'ContinuityWireLongitLaying',
      continuityWire: continuityWires.find(it => it.id.toString() === values.continuityWire.toString()),
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
          ...continuityWireLongitLayingEntity,
          continuityWire: continuityWireLongitLayingEntity?.continuityWire?.id,
          ownerStrandSupply: continuityWireLongitLayingEntity?.ownerStrandSupply?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="lappLiApp.continuityWireLongitLaying.home.createOrEditLabel" data-cy="ContinuityWireLongitLayingCreateUpdateHeading">
            <Translate contentKey="lappLiApp.continuityWireLongitLaying.home.createOrEditLabel">
              Create or edit a ContinuityWireLongitLaying
            </Translate>
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
                  id="continuity-wire-longit-laying-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              {isNew ? (
                <ValidatedField
                  label={translate('lappLiApp.continuityWireLongitLaying.operationLayer')}
                  id="continuity-wire-longit-laying-operationLayer"
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
                label={translate('lappLiApp.continuityWireLongitLaying.anonymousContinuityWireDesignation')}
                id="continuity-wire-longit-laying-anonymousContinuityWireDesignation"
                name="anonymousContinuityWireDesignation"
                data-cy="anonymousContinuityWireDesignation"
                type="text"
              />
              <ValidatedField
                label={translate('lappLiApp.continuityWireLongitLaying.anonymousContinuityWireGramPerMeterLinearMass')}
                id="continuity-wire-longit-laying-anonymousContinuityWireGramPerMeterLinearMass"
                name="anonymousContinuityWireGramPerMeterLinearMass"
                data-cy="anonymousContinuityWireGramPerMeterLinearMass"
                type="text"
              />
              <ValidatedField
                label={translate('lappLiApp.continuityWireLongitLaying.anonymousContinuityWireMetalFiberKind')}
                id="continuity-wire-longit-laying-anonymousContinuityWireMetalFiberKind"
                name="anonymousContinuityWireMetalFiberKind"
                data-cy="anonymousContinuityWireMetalFiberKind"
                type="select"
              >
                {metalFiberKindValues.map(metalFiberKind => (
                  <option value={metalFiberKind} key={metalFiberKind}>
                    {translate('lappLiApp.MetalFiberKind.' + metalFiberKind)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('lappLiApp.continuityWireLongitLaying.anonymousContinuityWireMilimeterDiameter')}
                id="continuity-wire-longit-laying-anonymousContinuityWireMilimeterDiameter"
                name="anonymousContinuityWireMilimeterDiameter"
                data-cy="anonymousContinuityWireMilimeterDiameter"
                type="text"
              />
              <ValidatedField
                label={translate('lappLiApp.continuityWireLongitLaying.anonymousContinuityWireFlexibility')}
                id="continuity-wire-longit-laying-anonymousContinuityWireFlexibility"
                name="anonymousContinuityWireFlexibility"
                data-cy="anonymousContinuityWireFlexibility"
                type="select"
              >
                {flexibilityValues.map(flexibility => (
                  <option value={flexibility} key={flexibility}>
                    {translate('lappLiApp.Flexibility.' + flexibility)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="continuity-wire-longit-laying-continuityWire"
                name="continuityWire"
                data-cy="continuityWire"
                label={translate('lappLiApp.continuityWireLongitLaying.continuityWire')}
                type="select"
              >
                <option value="" key="0" />
                {continuityWires
                  ? continuityWires.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.designation}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              {props.match.params ? (
                ''
              ) : (
                <ValidatedField
                  id="continuity-wire-longit-laying-ownerStrandSupply"
                  name="ownerStrandSupply"
                  data-cy="ownerStrandSupply"
                  label={translate('lappLiApp.continuityWireLongitLaying.ownerStrandSupply')}
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

export default ContinuityWireLongitLayingUpdate;
