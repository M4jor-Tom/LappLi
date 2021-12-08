import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { ILifter } from 'app/shared/model/lifter.model';
import { getEntities as getLifters } from 'app/entities/lifter/lifter.reducer';
import { getEntity, updateEntity, createEntity, reset } from './lifter-run-measure.reducer';
import { ILifterRunMeasure } from 'app/shared/model/lifter-run-measure.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { MarkingType } from 'app/shared/model/enumerations/marking-type.model';
import { MarkingTechnique } from 'app/shared/model/enumerations/marking-technique.model';

export const LifterRunMeasureUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const lifters = useAppSelector(state => state.lifter.entities);
  const lifterRunMeasureEntity = useAppSelector(state => state.lifterRunMeasure.entity);
  const loading = useAppSelector(state => state.lifterRunMeasure.loading);
  const updating = useAppSelector(state => state.lifterRunMeasure.updating);
  const updateSuccess = useAppSelector(state => state.lifterRunMeasure.updateSuccess);
  const markingTypeValues = Object.keys(MarkingType);
  const markingTechniqueValues = Object.keys(MarkingTechnique);
  const handleClose = () => {
    props.history.push('/lifter-run-measure');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getLifters({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...lifterRunMeasureEntity,
      ...values,
      lifter: lifters.find(it => it.id.toString() === values.lifter.toString()),
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
          markingType: 'LIFTING',
          markingTechnique: 'NONE',
          ...lifterRunMeasureEntity,
          lifter: lifterRunMeasureEntity?.lifter?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="lappLiApp.lifterRunMeasure.home.createOrEditLabel" data-cy="LifterRunMeasureCreateUpdateHeading">
            <Translate contentKey="lappLiApp.lifterRunMeasure.home.createOrEditLabel">Create or edit a LifterRunMeasure</Translate>
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
                  id="lifter-run-measure-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('lappLiApp.dimension.milimeterDiameter')}
                id="lifter-run-measure-milimeterDiameter"
                name="milimeterDiameter"
                data-cy="milimeterDiameter"
                type="text"
              />
              <ValidatedField
                label={translate('lappLiApp.supply.meterPerSecondSpeed')}
                id="lifter-run-measure-meterPerSecondSpeed"
                name="meterPerSecondSpeed"
                data-cy="meterPerSecondSpeed"
                type="text"
              />
              <ValidatedField
                label={translate('lappLiApp.lifterRunMeasure.markingType')}
                id="lifter-run-measure-markingType"
                name="markingType"
                data-cy="markingType"
                type="select"
              >
                {markingTypeValues.map(markingType => (
                  <option value={markingType} key={markingType}>
                    {translate('lappLiApp.MarkingType' + markingType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('lappLiApp.lifterRunMeasure.markingTechnique')}
                id="lifter-run-measure-markingTechnique"
                name="markingTechnique"
                data-cy="markingTechnique"
                type="select"
              >
                {markingTechniqueValues.map(markingTechnique => (
                  <option value={markingTechnique} key={markingTechnique}>
                    {translate('lappLiApp.MarkingTechnique' + markingTechnique)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('lappLiApp.lifterRunMeasure.hourPreparationTime')}
                id="lifter-run-measure-hourPreparationTime"
                name="hourPreparationTime"
                data-cy="hourPreparationTime"
                type="text"
              />
              <ValidatedField
                id="lifter-run-measure-lifter"
                name="lifter"
                data-cy="lifter"
                label={translate('lappLiApp.lifterRunMeasure.lifter')}
                type="select"
                required
              >
                <option value="" key="0" />
                {lifters
                  ? lifters.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/lifter-run-measure" replace color="info">
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

export default LifterRunMeasureUpdate;
