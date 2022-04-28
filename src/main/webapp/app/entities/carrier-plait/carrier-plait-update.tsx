import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { ICarrierPlaitFiber } from 'app/shared/model/carrier-plait-fiber.model';
import { getEntities as getCarrierPlaitFibers } from 'app/entities/carrier-plait-fiber/carrier-plait-fiber.reducer';
import { IStrandSupply } from 'app/shared/model/strand-supply.model';
import { getEntities as getStrandSupplies, getEntity as getStrandSupplyEntity } from 'app/entities/strand-supply/strand-supply.reducer';
import { getEntity, updateEntity, createEntity, reset } from './carrier-plait.reducer';
import { ICarrierPlait } from 'app/shared/model/carrier-plait.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getOutFromStudySupplyStrandCarrierPlait } from '../index-management/index-management-lib';

export const CarrierPlaitUpdate = (props: RouteComponentProps<{ id: string; strand_supply_id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const carrierPlaitFibers = useAppSelector(state => state.carrierPlaitFiber.entities);
  const strandSupplies = useAppSelector(state => state.strandSupply.entities);
  const carrierPlaitEntity = useAppSelector(state => state.carrierPlait.entity);
  const loading = useAppSelector(state => state.carrierPlait.loading);
  const updating = useAppSelector(state => state.carrierPlait.updating);
  const updateSuccess = useAppSelector(state => state.carrierPlait.updateSuccess);

  //  Design for operation -- START

  const redirectionUrl = getOutFromStudySupplyStrandCarrierPlait(props.match.url, isNew);
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

    dispatch(getCarrierPlaitFibers({}));
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
      ...carrierPlaitEntity,
      ...values,
      __typeName: 'CarrierPlait',
      carrierPlaitFiber: carrierPlaitFibers.find(it => it.id.toString() === values.carrierPlaitFiber.toString()),
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
          ...carrierPlaitEntity,
          carrierPlaitFiber: carrierPlaitEntity?.carrierPlaitFiber?.id,
          ownerStrandSupply: carrierPlaitEntity?.ownerStrandSupply?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="lappLiApp.carrierPlait.home.createOrEditLabel" data-cy="CarrierPlaitCreateUpdateHeading">
            <Translate contentKey="lappLiApp.carrierPlait.home.createOrEditLabel">Create or edit a CarrierPlait</Translate>
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
                  id="carrier-plait-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              {isNew ? (
                <ValidatedField
                  label={translate('lappLiApp.carrierPlait.operationLayer')}
                  id="carrier-plait-operationLayer"
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
                label={translate('lappLiApp.carrierPlait.minimumDecaNewtonLoad')}
                id="carrier-plait-minimumDecaNewtonLoad"
                name="minimumDecaNewtonLoad"
                data-cy="minimumDecaNewtonLoad"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  min: { value: 0, message: translate('entity.validation.min', { min: 0 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('lappLiApp.carrierPlait.degreeAssemblyAngle')}
                id="carrier-plait-degreeAssemblyAngle"
                name="degreeAssemblyAngle"
                data-cy="degreeAssemblyAngle"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  min: { value: 0, message: translate('entity.validation.min', { min: 0 }) },
                  max: { value: 89, message: translate('entity.validation.max', { max: 89 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('lappLiApp.carrierPlait.forcedEndPerBobinsCount')}
                id="carrier-plait-forcedEndPerBobinsCount"
                name="forcedEndPerBobinsCount"
                data-cy="forcedEndPerBobinsCount"
                type="text"
                validate={{
                  min: { value: 0, message: translate('entity.validation.min', { min: 0 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                id="carrier-plait-carrierPlaitFiber"
                name="carrierPlaitFiber"
                data-cy="carrierPlaitFiber"
                label={translate('lappLiApp.carrierPlait.carrierPlaitFiber')}
                type="select"
                required
              >
                <option value="" key="0" />
                {carrierPlaitFibers
                  ? carrierPlaitFibers.map(otherEntity => (
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
                  id="carrier-plait-ownerStrandSupply"
                  name="ownerStrandSupply"
                  data-cy="ownerStrandSupply"
                  label={translate('lappLiApp.carrierPlait.ownerStrandSupply')}
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

export default CarrierPlaitUpdate;
