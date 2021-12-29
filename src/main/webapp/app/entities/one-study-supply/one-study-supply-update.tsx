import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IMaterial } from 'app/shared/model/material.model';
import { getEntities as getMaterials } from 'app/entities/material/material.reducer';
import { IPosition } from 'app/shared/model/position.model';
import { getEntities as getPositions } from 'app/entities/position/position.reducer';
import { IStrand } from 'app/shared/model/strand.model';
import { getEntities as getStrands } from 'app/entities/strand/strand.reducer';
import { getEntity, updateEntity, createEntity, reset } from './one-study-supply.reducer';
import { IOneStudySupply } from 'app/shared/model/one-study-supply.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { MarkingType } from 'app/shared/model/enumerations/marking-type.model';
import { Color } from 'app/shared/model/enumerations/color.model';
import {
  getOutFromStudySupplyStrandSupplyComponent,
  getStrandSupplyRedirectionUrl,
  getStrandValidateField,
  isStrandSupply,
  SupplyKind,
} from '../index-management/index-management-lib';

export const OneStudySupplyUpdate = (props: RouteComponentProps<{ strand_id: string; id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const redirectionUrl = getOutFromStudySupplyStrandSupplyComponent(props.match.url, isNew);

  const materials = useAppSelector(state => state.material.entities);
  const positions = useAppSelector(state => state.position.entities);
  const strands = useAppSelector(state => state.strand.entities);
  const oneStudySupplyEntity = useAppSelector(state => state.oneStudySupply.entity);
  const loading = useAppSelector(state => state.oneStudySupply.loading);
  const updating = useAppSelector(state => state.oneStudySupply.updating);
  const updateSuccess = useAppSelector(state => state.oneStudySupply.updateSuccess);
  const markingTypeValues = Object.keys(MarkingType);
  const colorValues = Object.keys(Color);
  const handleClose = () => {
    props.history.push(redirectionUrl);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getMaterials({}));
    dispatch(getPositions({}));
    dispatch(getStrands({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...oneStudySupplyEntity,
      ...values,
      surfaceMaterial: materials.find(it => it.id.toString() === values.surfaceMaterial.toString()),
      strand: strands.find(it => it.id.toString() === values.strand.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const strandValidateField = getStrandValidateField(props, strands, SupplyKind.ONE_STUDY);

  const defaultValues = () =>
    isNew
      ? {}
      : {
          markingType: 'LIFTING',
          surfaceColor: 'NATURAL',
          ...oneStudySupplyEntity,
          surfaceMaterial: oneStudySupplyEntity?.surfaceMaterial?.id,
          strand: oneStudySupplyEntity?.strand?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="lappLiApp.oneStudySupply.home.createOrEditLabel" data-cy="OneStudySupplyCreateUpdateHeading">
            <Translate contentKey="lappLiApp.oneStudySupply.home.createOrEditLabel">Create or edit a OneStudySupply</Translate>
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
                  id="one-study-supply-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('lappLiApp.oneStudySupply.apparitions')}
                id="one-study-supply-apparitions"
                name="apparitions"
                data-cy="apparitions"
                type="text"
              />
              <ValidatedField
                label={translate('lappLiApp.oneStudySupply.number')}
                id="one-study-supply-number"
                name="number"
                data-cy="number"
                type="text"
              />
              <ValidatedField
                label={translate('lappLiApp.oneStudySupply.designation')}
                id="one-study-supply-designation"
                name="designation"
                data-cy="designation"
                type="text"
              />
              <ValidatedField
                label={translate('lappLiApp.oneStudySupply.description')}
                id="one-study-supply-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('lappLiApp.oneStudySupply.markingType')}
                id="one-study-supply-markingType"
                name="markingType"
                data-cy="markingType"
                type="select"
              >
                {markingTypeValues.map(markingType => (
                  <option value={markingType} key={markingType}>
                    {translate('lappLiApp.MarkingType.' + markingType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('lappLiApp.oneStudySupply.gramPerMeterLinearMass')}
                id="one-study-supply-gramPerMeterLinearMass"
                name="gramPerMeterLinearMass"
                data-cy="gramPerMeterLinearMass"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('lappLiApp.oneStudySupply.milimeterDiameter')}
                id="one-study-supply-milimeterDiameter"
                name="milimeterDiameter"
                data-cy="milimeterDiameter"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('lappLiApp.oneStudySupply.surfaceColor')}
                id="one-study-supply-surfaceColor"
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
                id="one-study-supply-surfaceMaterial"
                name="surfaceMaterial"
                data-cy="surfaceMaterial"
                label={translate('lappLiApp.oneStudySupply.surfaceMaterial')}
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
              {strandValidateField}
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

export default OneStudySupplyUpdate;
