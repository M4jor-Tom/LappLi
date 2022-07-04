import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './continuity-wire.reducer';
import { IContinuityWire } from 'app/shared/model/continuity-wire.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { MetalFiberKind } from 'app/shared/model/enumerations/metal-fiber-kind.model';
import { Flexibility } from 'app/shared/model/enumerations/flexibility.model';

export const ContinuityWireUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const continuityWireEntity = useAppSelector(state => state.continuityWire.entity);
  const loading = useAppSelector(state => state.continuityWire.loading);
  const updating = useAppSelector(state => state.continuityWire.updating);
  const updateSuccess = useAppSelector(state => state.continuityWire.updateSuccess);
  const metalFiberKindValues = Object.keys(MetalFiberKind);
  const flexibilityValues = Object.keys(Flexibility);
  const handleClose = () => {
    props.history.push('/continuity-wire');
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
      ...continuityWireEntity,
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
          metalFiberKind: 'RED_COPPER',
          flexibility: 'S',
          ...continuityWireEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="lappLiApp.continuityWire.home.createOrEditLabel" data-cy="ContinuityWireCreateUpdateHeading">
            <Translate contentKey="lappLiApp.continuityWire.home.createOrEditLabel">Create or edit a ContinuityWire</Translate>
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
                  id="continuity-wire-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('lappLiApp.continuityWire.designation')}
                id="continuity-wire-designation"
                name="designation"
                data-cy="designation"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('lappLiApp.continuityWire.gramPerMeterLinearMass')}
                id="continuity-wire-gramPerMeterLinearMass"
                name="gramPerMeterLinearMass"
                data-cy="gramPerMeterLinearMass"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  min: { value: 0, message: translate('entity.validation.min', { min: 0 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('lappLiApp.continuityWire.metalFiberKind')}
                id="continuity-wire-metalFiberKind"
                name="metalFiberKind"
                data-cy="metalFiberKind"
                type="select"
              >
                {metalFiberKindValues.map(metalFiberKind => (
                  <option value={metalFiberKind} key={metalFiberKind}>
                    {translate('lappLiApp.MetalFiberKind' + metalFiberKind)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('lappLiApp.continuityWire.milimeterDiameter')}
                id="continuity-wire-milimeterDiameter"
                name="milimeterDiameter"
                data-cy="milimeterDiameter"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  min: { value: 0, message: translate('entity.validation.min', { min: 0 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('lappLiApp.continuityWire.flexibility')}
                id="continuity-wire-flexibility"
                name="flexibility"
                data-cy="flexibility"
                type="select"
              >
                {flexibilityValues.map(flexibility => (
                  <option value={flexibility} key={flexibility}>
                    {translate('lappLiApp.Flexibility' + flexibility)}
                  </option>
                ))}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/continuity-wire" replace color="info">
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

export default ContinuityWireUpdate;
