import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IElementKind } from 'app/shared/model/element-kind.model';
import { getEntities as getElementKinds } from 'app/entities/element-kind/element-kind.reducer';
import { getEntity, updateEntity, createEntity, reset } from './element-kind-edition.reducer';
import { IElementKindEdition } from 'app/shared/model/element-kind-edition.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ElementKindEditionUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const elementKinds = useAppSelector(state => state.elementKind.entities);
  const elementKindEditionEntity = useAppSelector(state => state.elementKindEdition.entity);
  const loading = useAppSelector(state => state.elementKindEdition.loading);
  const updating = useAppSelector(state => state.elementKindEdition.updating);
  const updateSuccess = useAppSelector(state => state.elementKindEdition.updateSuccess);
  const handleClose = () => {
    props.history.push('/element-kind-edition');
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
    values.editionDateTime = convertDateTimeToServer(values.editionDateTime);

    const entity = {
      ...elementKindEditionEntity,
      ...values,
      editedElementKind: elementKinds.find(it => it.id.toString() === values.editedElementKind.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          editionDateTime: displayDefaultDateTime(),
        }
      : {
          ...elementKindEditionEntity,
          editionDateTime: convertDateTimeFromServer(elementKindEditionEntity.editionDateTime),
          editedElementKind: elementKindEditionEntity?.editedElementKind?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="lappLiApp.elementKindEdition.home.createOrEditLabel" data-cy="ElementKindEditionCreateUpdateHeading">
            <Translate contentKey="lappLiApp.elementKindEdition.home.createOrEditLabel">Create or edit a ElementKindEdition</Translate>
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
                  id="element-kind-edition-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('lappLiApp.elementKindEdition.newGramPerMeterLinearMass')}
                id="element-kind-edition-newGramPerMeterLinearMass"
                name="newGramPerMeterLinearMass"
                data-cy="newGramPerMeterLinearMass"
                type="text"
              />
              <ValidatedField
                label={translate('lappLiApp.elementKindEdition.newMilimeterDiameter')}
                id="element-kind-edition-newMilimeterDiameter"
                name="newMilimeterDiameter"
                data-cy="newMilimeterDiameter"
                type="text"
              />
              <ValidatedField
                label={translate('lappLiApp.elementKindEdition.newMilimeterInsulationThickness')}
                id="element-kind-edition-newMilimeterInsulationThickness"
                name="newMilimeterInsulationThickness"
                data-cy="newMilimeterInsulationThickness"
                type="text"
              />
              <ValidatedField
                id="element-kind-edition-editedElementKind"
                name="editedElementKind"
                data-cy="editedElementKind"
                label={translate('lappLiApp.elementKindEdition.editedElementKind')}
                type="select"
                required
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
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/element-kind-edition" replace color="info">
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

export default ElementKindEditionUpdate;
