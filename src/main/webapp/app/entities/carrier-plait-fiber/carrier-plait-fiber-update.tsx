import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './carrier-plait-fiber.reducer';
import { ICarrierPlaitFiber } from 'app/shared/model/carrier-plait-fiber.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CarrierPlaitFiberUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const carrierPlaitFiberEntity = useAppSelector(state => state.carrierPlaitFiber.entity);
  const loading = useAppSelector(state => state.carrierPlaitFiber.loading);
  const updating = useAppSelector(state => state.carrierPlaitFiber.updating);
  const updateSuccess = useAppSelector(state => state.carrierPlaitFiber.updateSuccess);
  const handleClose = () => {
    props.history.push('/carrier-plait-fiber');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...carrierPlaitFiberEntity,
      ...values,
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
          ...carrierPlaitFiberEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="lappLiApp.carrierPlaitFiber.home.createOrEditLabel" data-cy="CarrierPlaitFiberCreateUpdateHeading">
            <Translate contentKey="lappLiApp.carrierPlaitFiber.home.createOrEditLabel">Create or edit a CarrierPlaitFiber</Translate>
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
                  id="carrier-plait-fiber-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('lappLiApp.carrierPlaitFiber.number')}
                id="carrier-plait-fiber-number"
                name="number"
                data-cy="number"
                type="text"
                validate={{
                  min: { value: 0, message: translate('entity.validation.min', { min: 0 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('lappLiApp.carrierPlaitFiber.designation')}
                id="carrier-plait-fiber-designation"
                name="designation"
                data-cy="designation"
                type="text"
                validate={{}}
              />
              <ValidatedField
                label={translate('lappLiApp.carrierPlaitFiber.decitexTitration')}
                id="carrier-plait-fiber-decitexTitration"
                name="decitexTitration"
                data-cy="decitexTitration"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  min: { value: 0, message: translate('entity.validation.min', { min: 0 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('lappLiApp.carrierPlaitFiber.gramPerSquareMilimeterPerMeterDensity')}
                id="carrier-plait-fiber-gramPerSquareMilimeterPerMeterDensity"
                name="gramPerSquareMilimeterPerMeterDensity"
                data-cy="gramPerSquareMilimeterPerMeterDensity"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  min: { value: 0, message: translate('entity.validation.min', { min: 0 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('lappLiApp.carrierPlaitFiber.decaNewtonLoad')}
                id="carrier-plait-fiber-decaNewtonLoad"
                name="decaNewtonLoad"
                data-cy="decaNewtonLoad"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  min: { value: 0, message: translate('entity.validation.min', { min: 0 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/carrier-plait-fiber" replace color="info">
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

export default CarrierPlaitFiberUpdate;
