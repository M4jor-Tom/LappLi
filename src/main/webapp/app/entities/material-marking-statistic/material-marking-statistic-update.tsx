import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './material-marking-statistic.reducer';
import { IMaterialMarkingStatistic } from 'app/shared/model/material-marking-statistic.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { MarkingType } from 'app/shared/model/enumerations/marking-type.model';
import { MarkingTechnique } from 'app/shared/model/enumerations/marking-technique.model';

export const MaterialMarkingStatisticUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const materialMarkingStatisticEntity = useAppSelector(state => state.materialMarkingStatistic.entity);
  const loading = useAppSelector(state => state.materialMarkingStatistic.loading);
  const updating = useAppSelector(state => state.materialMarkingStatistic.updating);
  const updateSuccess = useAppSelector(state => state.materialMarkingStatistic.updateSuccess);
  const markingTypeValues = Object.keys(MarkingType);
  const markingTechniqueValues = Object.keys(MarkingTechnique);
  const handleClose = () => {
    props.history.push('/material-marking-statistic');
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
      ...materialMarkingStatisticEntity,
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
          markingType: 'LIFTING',
          markingTechnique: 'NONE',
          ...materialMarkingStatisticEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="lappLiApp.materialMarkingStatistic.home.createOrEditLabel" data-cy="MaterialMarkingStatisticCreateUpdateHeading">
            <Translate contentKey="lappLiApp.materialMarkingStatistic.home.createOrEditLabel">
              Create or edit a MaterialMarkingStatistic
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
                  id="material-marking-statistic-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('lappLiApp.materialMarkingStatistic.markingType')}
                id="material-marking-statistic-markingType"
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
                label={translate('lappLiApp.materialMarkingStatistic.markingTechnique')}
                id="material-marking-statistic-markingTechnique"
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
                label={translate('lappLiApp.materialMarkingStatistic.meterPerSecondSpeed')}
                id="material-marking-statistic-meterPerSecondSpeed"
                name="meterPerSecondSpeed"
                data-cy="meterPerSecondSpeed"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/material-marking-statistic" replace color="info">
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

export default MaterialMarkingStatisticUpdate;
