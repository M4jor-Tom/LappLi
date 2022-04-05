import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { ICopperFiber } from 'app/shared/model/copper-fiber.model';
import { getEntities as getCopperFibers } from 'app/entities/copper-fiber/copper-fiber.reducer';
import { getEntity, updateEntity, createEntity, reset } from './screen.reducer';
import { IScreen } from 'app/shared/model/screen.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ScreenUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const copperFibers = useAppSelector(state => state.copperFiber.entities);
  const screenEntity = useAppSelector(state => state.screen.entity);
  const loading = useAppSelector(state => state.screen.loading);
  const updating = useAppSelector(state => state.screen.updating);
  const updateSuccess = useAppSelector(state => state.screen.updateSuccess);
  const handleClose = () => {
    props.history.push('/screen');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getCopperFibers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...screenEntity,
      ...values,
      copperFiber: copperFibers.find(it => it.id.toString() === values.copperFiber.toString()),
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
          ...screenEntity,
          copperFiber: screenEntity?.copperFiber?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="lappLiApp.screen.home.createOrEditLabel" data-cy="ScreenCreateUpdateHeading">
            <Translate contentKey="lappLiApp.screen.home.createOrEditLabel">Create or edit a Screen</Translate>
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
                  id="screen-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('lappLiApp.screen.operationLayer')}
                id="screen-operationLayer"
                name="operationLayer"
                data-cy="operationLayer"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('lappLiApp.screen.assemblyMeanIsSameThanAssemblys')}
                id="screen-assemblyMeanIsSameThanAssemblys"
                name="assemblyMeanIsSameThanAssemblys"
                data-cy="assemblyMeanIsSameThanAssemblys"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('lappLiApp.screen.forcedDiameterAssemblyStep')}
                id="screen-forcedDiameterAssemblyStep"
                name="forcedDiameterAssemblyStep"
                data-cy="forcedDiameterAssemblyStep"
                type="text"
              />
              <ValidatedField
                id="screen-copperFiber"
                name="copperFiber"
                data-cy="copperFiber"
                label={translate('lappLiApp.screen.copperFiber')}
                type="select"
                required
              >
                <option value="" key="0" />
                {copperFibers
                  ? copperFibers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.designation}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/screen" replace color="info">
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

export default ScreenUpdate;
