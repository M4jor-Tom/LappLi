import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './lifter.reducer';
import { ILifter } from 'app/shared/model/lifter.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const LifterUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const lifterEntity = useAppSelector(state => state.lifter.entity);
  const loading = useAppSelector(state => state.lifter.loading);
  const updating = useAppSelector(state => state.lifter.updating);
  const updateSuccess = useAppSelector(state => state.lifter.updateSuccess);
  const handleClose = () => {
    props.history.push('/lifter');
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
      ...lifterEntity,
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
          ...lifterEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="lappLiApp.lifter.home.createOrEditLabel" data-cy="LifterCreateUpdateHeading">
            <Translate contentKey="lappLiApp.lifter.home.createOrEditLabel">Create or edit a Lifter</Translate>
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
                  id="lifter-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('lappLiApp.lifter.index')}
                id="lifter-index"
                name="index"
                data-cy="index"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('lappLiApp.lifter.minimumMilimeterDiameter')}
                id="lifter-minimumMilimeterDiameter"
                name="minimumMilimeterDiameter"
                data-cy="minimumMilimeterDiameter"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('lappLiApp.lifter.maximumMilimeterDiameter')}
                id="lifter-maximumMilimeterDiameter"
                name="maximumMilimeterDiameter"
                data-cy="maximumMilimeterDiameter"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('lappLiApp.lifter.supportsSpirallyColoredMarkingType')}
                id="lifter-supportsSpirallyColoredMarkingType"
                name="supportsSpirallyColoredMarkingType"
                data-cy="supportsSpirallyColoredMarkingType"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('lappLiApp.lifter.supportsLongitudinallyColoredMarkingType')}
                id="lifter-supportsLongitudinallyColoredMarkingType"
                name="supportsLongitudinallyColoredMarkingType"
                data-cy="supportsLongitudinallyColoredMarkingType"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('lappLiApp.lifter.supportsNumberedMarkingType')}
                id="lifter-supportsNumberedMarkingType"
                name="supportsNumberedMarkingType"
                data-cy="supportsNumberedMarkingType"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('lappLiApp.lifter.supportsInkJetMarkingTechnique')}
                id="lifter-supportsInkJetMarkingTechnique"
                name="supportsInkJetMarkingTechnique"
                data-cy="supportsInkJetMarkingTechnique"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('lappLiApp.lifter.supportsRsdMarkingTechnique')}
                id="lifter-supportsRsdMarkingTechnique"
                name="supportsRsdMarkingTechnique"
                data-cy="supportsRsdMarkingTechnique"
                check
                type="checkbox"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/lifter" replace color="info">
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

export default LifterUpdate;
