import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IMyNewComponent } from 'app/shared/model/my-new-component.model';
import { getEntities as getMyNewComponents } from 'app/entities/my-new-component/my-new-component.reducer';
import { IStrandSupply } from 'app/shared/model/strand-supply.model';
import { getEntities as getStrandSupplies } from 'app/entities/strand-supply/strand-supply.reducer';
import { getEntity, updateEntity, createEntity, reset } from './my-new-operation.reducer';
import { IMyNewOperation } from 'app/shared/model/my-new-operation.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const MyNewOperationUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const myNewComponents = useAppSelector(state => state.myNewComponent.entities);
  const strandSupplies = useAppSelector(state => state.strandSupply.entities);
  const myNewOperationEntity = useAppSelector(state => state.myNewOperation.entity);
  const loading = useAppSelector(state => state.myNewOperation.loading);
  const updating = useAppSelector(state => state.myNewOperation.updating);
  const updateSuccess = useAppSelector(state => state.myNewOperation.updateSuccess);
  const handleClose = () => {
    props.history.push('/my-new-operation');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getMyNewComponents({}));
    dispatch(getStrandSupplies({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...myNewOperationEntity,
      ...values,
      myNewComponent: myNewComponents.find(it => it.id.toString() === values.myNewComponent.toString()),
      ownerStrandSupply: strandSupplies.find(it => it.id.toString() === values.ownerStrandSupply.toString()),
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
          ...myNewOperationEntity,
          myNewComponent: myNewOperationEntity?.myNewComponent?.id,
          ownerStrandSupply: myNewOperationEntity?.ownerStrandSupply?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="lappLiApp.myNewOperation.home.createOrEditLabel" data-cy="MyNewOperationCreateUpdateHeading">
            <Translate contentKey="lappLiApp.myNewOperation.home.createOrEditLabel">Create or edit a MyNewOperation</Translate>
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
                  id="my-new-operation-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('lappLiApp.myNewOperation.operationLayer')}
                id="my-new-operation-operationLayer"
                name="operationLayer"
                data-cy="operationLayer"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('lappLiApp.myNewOperation.operationData')}
                id="my-new-operation-operationData"
                name="operationData"
                data-cy="operationData"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('lappLiApp.myNewOperation.anonymousMyNewComponentNumber')}
                id="my-new-operation-anonymousMyNewComponentNumber"
                name="anonymousMyNewComponentNumber"
                data-cy="anonymousMyNewComponentNumber"
                type="text"
              />
              <ValidatedField
                label={translate('lappLiApp.myNewOperation.anonymousMyNewComponentDesignation')}
                id="my-new-operation-anonymousMyNewComponentDesignation"
                name="anonymousMyNewComponentDesignation"
                data-cy="anonymousMyNewComponentDesignation"
                type="text"
              />
              <ValidatedField
                label={translate('lappLiApp.myNewOperation.anonymousMyNewComponentData')}
                id="my-new-operation-anonymousMyNewComponentData"
                name="anonymousMyNewComponentData"
                data-cy="anonymousMyNewComponentData"
                type="text"
              />
              <ValidatedField
                id="my-new-operation-myNewComponent"
                name="myNewComponent"
                data-cy="myNewComponent"
                label={translate('lappLiApp.myNewOperation.myNewComponent')}
                type="select"
              >
                <option value="" key="0" />
                {myNewComponents
                  ? myNewComponents.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.designation}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="my-new-operation-ownerStrandSupply"
                name="ownerStrandSupply"
                data-cy="ownerStrandSupply"
                label={translate('lappLiApp.myNewOperation.ownerStrandSupply')}
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
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/my-new-operation" replace color="info">
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

export default MyNewOperationUpdate;
