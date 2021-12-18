import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IMaterial } from 'app/shared/model/material.model';
import { getEntities as getMaterials } from 'app/entities/material/material.reducer';
import { getEntity, updateEntity, createEntity, reset } from './custom-component.reducer';
import { ICustomComponent } from 'app/shared/model/custom-component.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { Color } from 'app/shared/model/enumerations/color.model';

export const CustomComponentUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const materials = useAppSelector(state => state.material.entities);
  const customComponentEntity = useAppSelector(state => state.customComponent.entity);
  const loading = useAppSelector(state => state.customComponent.loading);
  const updating = useAppSelector(state => state.customComponent.updating);
  const updateSuccess = useAppSelector(state => state.customComponent.updateSuccess);
  const colorValues = Object.keys(Color);
  const handleClose = () => {
    props.history.push('/custom-component');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getMaterials({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...customComponentEntity,
      ...values,
      surfaceMaterial: materials.find(it => it.id.toString() === values.surfaceMaterial.toString()),
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
          surfaceColor: 'NATURAL',
          ...customComponentEntity,
          surfaceMaterial: customComponentEntity?.surfaceMaterial?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="lappLiApp.customComponent.home.createOrEditLabel" data-cy="CustomComponentCreateUpdateHeading">
            <Translate contentKey="lappLiApp.customComponent.home.createOrEditLabel">Create or edit a CustomComponent</Translate>
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
                  id="custom-component-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('lappLiApp.customComponent.number')}
                id="custom-component-number"
                name="number"
                data-cy="number"
                type="text"
              />
              <ValidatedField
                label={translate('lappLiApp.customComponent.designation')}
                id="custom-component-designation"
                name="designation"
                data-cy="designation"
                type="text"
              />
              <ValidatedField
                label={translate('lappLiApp.customComponent.gramPerMeterLinearMass')}
                id="custom-component-gramPerMeterLinearMass"
                name="gramPerMeterLinearMass"
                data-cy="gramPerMeterLinearMass"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('lappLiApp.customComponent.milimeterDiameter')}
                id="custom-component-milimeterDiameter"
                name="milimeterDiameter"
                data-cy="milimeterDiameter"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('lappLiApp.customComponent.surfaceColor')}
                id="custom-component-surfaceColor"
                name="surfaceColor"
                data-cy="surfaceColor"
                type="select"
              >
                {colorValues.map(color => (
                  <option value={color} key={color}>
                    {translate('lappLiApp.Color.' + color)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="custom-component-surfaceMaterial"
                name="surfaceMaterial"
                data-cy="surfaceMaterial"
                label={translate('lappLiApp.customComponent.surfaceMaterial')}
                type="select"
                required
              >
                <option value="" key="0" />
                {materials
                  ? materials.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.designation}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/custom-component" replace color="info">
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

export default CustomComponentUpdate;
