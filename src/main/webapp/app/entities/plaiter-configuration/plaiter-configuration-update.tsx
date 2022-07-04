import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IPlaiter } from 'app/shared/model/plaiter.model';
import { getEntities as getPlaiters } from 'app/entities/plaiter/plaiter.reducer';
import { getEntity, updateEntity, createEntity, reset } from './plaiter-configuration.reducer';
import { IPlaiterConfiguration } from 'app/shared/model/plaiter-configuration.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PlaiterConfigurationUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const plaiters = useAppSelector(state => state.plaiter.entities);
  const plaiterConfigurationEntity = useAppSelector(state => state.plaiterConfiguration.entity);
  const loading = useAppSelector(state => state.plaiterConfiguration.loading);
  const updating = useAppSelector(state => state.plaiterConfiguration.updating);
  const updateSuccess = useAppSelector(state => state.plaiterConfiguration.updateSuccess);
  const handleClose = () => {
    props.history.push('/plaiter-configuration');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getPlaiters({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...plaiterConfigurationEntity,
      ...values,
      plaiter: plaiters.find(it => it.id.toString() === values.plaiter.toString()),
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
          ...plaiterConfigurationEntity,
          plaiter: plaiterConfigurationEntity?.plaiter?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="lappLiApp.plaiterConfiguration.home.createOrEditLabel" data-cy="PlaiterConfigurationCreateUpdateHeading">
            <Translate contentKey="lappLiApp.plaiterConfiguration.home.createOrEditLabel">Create or edit a PlaiterConfiguration</Translate>
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
                  id="plaiter-configuration-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('lappLiApp.plaiterConfiguration.usedBobinsCount')}
                id="plaiter-configuration-usedBobinsCount"
                name="usedBobinsCount"
                data-cy="usedBobinsCount"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  min: { value: 0, message: translate('entity.validation.min', { min: 0 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                id="plaiter-configuration-plaiter"
                name="plaiter"
                data-cy="plaiter"
                label={translate('lappLiApp.plaiterConfiguration.plaiter')}
                type="select"
                required
              >
                <option value="" key="0" />
                {plaiters
                  ? plaiters.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/plaiter-configuration" replace color="info">
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

export default PlaiterConfigurationUpdate;
