import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IMyNewComponent } from 'app/shared/model/my-new-component.model';
import { getEntities as getMyNewComponents } from 'app/entities/my-new-component/my-new-component.reducer';
import { getEntity, updateEntity, createEntity, reset } from './my-new-component-supply.reducer';
import { IMyNewComponentSupply } from 'app/shared/model/my-new-component-supply.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { MarkingType } from 'app/shared/model/enumerations/marking-type.model';

export const MyNewComponentSupplyUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const myNewComponents = useAppSelector(state => state.myNewComponent.entities);
  const myNewComponentSupplyEntity = useAppSelector(state => state.myNewComponentSupply.entity);
  const loading = useAppSelector(state => state.myNewComponentSupply.loading);
  const updating = useAppSelector(state => state.myNewComponentSupply.updating);
  const updateSuccess = useAppSelector(state => state.myNewComponentSupply.updateSuccess);
  const markingTypeValues = Object.keys(MarkingType);
  const handleClose = () => {
    props.history.push('/my-new-component-supply');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getMyNewComponents({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...myNewComponentSupplyEntity,
      ...values,
      myNewComponent: myNewComponents.find(it => it.id.toString() === values.myNewComponent.toString()),
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
          ...myNewComponentSupplyEntity,
          myNewComponent: myNewComponentSupplyEntity?.myNewComponent?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="lappLiApp.myNewComponentSupply.home.createOrEditLabel" data-cy="MyNewComponentSupplyCreateUpdateHeading">
            <Translate contentKey="lappLiApp.myNewComponentSupply.home.createOrEditLabel">Create or edit a MyNewComponentSupply</Translate>
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
                  id="my-new-component-supply-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('lappLiApp.myNewComponentSupply.apparitions')}
                id="my-new-component-supply-apparitions"
                name="apparitions"
                data-cy="apparitions"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('lappLiApp.myNewComponentSupply.description')}
                id="my-new-component-supply-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('lappLiApp.myNewComponentSupply.markingType')}
                id="my-new-component-supply-markingType"
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
                id="my-new-component-supply-myNewComponent"
                name="myNewComponent"
                data-cy="myNewComponent"
                label={translate('lappLiApp.myNewComponentSupply.myNewComponent')}
                type="select"
                required
              >
                <option value="" key="0" />
                {myNewComponents
                  ? myNewComponents.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/my-new-component-supply" replace color="info">
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

export default MyNewComponentSupplyUpdate;
