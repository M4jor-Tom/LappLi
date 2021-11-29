import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IElementKind } from 'app/shared/model/element-kind.model';
import { getEntities as getElementKinds } from 'app/entities/element-kind/element-kind.reducer';
import { getEntity, updateEntity, createEntity, reset } from './element.reducer';
import { IElement } from 'app/shared/model/element.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { Color } from 'app/shared/model/enumerations/color.model';

export const ElementUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const elementKinds = useAppSelector(state => state.elementKind.entities);
  const elementEntity = useAppSelector(state => state.element.entity);
  const loading = useAppSelector(state => state.element.loading);
  const updating = useAppSelector(state => state.element.updating);
  const updateSuccess = useAppSelector(state => state.element.updateSuccess);
  const colorValues = Object.keys(Color);
  const handleClose = () => {
    props.history.push('/element');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getElementKinds({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...elementEntity,
      ...values,
      elementKind: elementKinds.find(it => it.id.toString() === values.elementKind.toString()),
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
          color: 'WHITE',
          ...elementEntity,
          elementKind: elementEntity?.elementKind?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="lappLiApp.element.home.createOrEditLabel" data-cy="ElementCreateUpdateHeading">
            <Translate contentKey="lappLiApp.element.home.createOrEditLabel">Create or edit a Element</Translate>
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
                  id="element-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('lappLiApp.element.number')}
                id="element-number"
                name="number"
                data-cy="number"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField label={translate('lappLiApp.element.color')} id="element-color" name="color" data-cy="color" type="select">
                {colorValues.map(color => (
                  <option value={color} key={color}>
                    {translate('lappLiApp.Color' + color)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="element-elementKind"
                name="elementKind"
                data-cy="elementKind"
                label={translate('lappLiApp.element.elementKind')}
                type="select"
              >
                <option value="" key="0" />
                {elementKinds
                  ? elementKinds.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.designation}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/element" replace color="info">
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

export default ElementUpdate;
