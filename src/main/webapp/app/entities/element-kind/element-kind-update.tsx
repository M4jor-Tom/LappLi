import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { ICopper } from 'app/shared/model/copper.model';
import { getEntities as getCoppers } from 'app/entities/copper/copper.reducer';
import { IMaterial } from 'app/shared/model/material.model';
import { getEntities as getMaterials } from 'app/entities/material/material.reducer';
import { getEntity, updateEntity, createEntity, reset } from './element-kind.reducer';
import { IElementKind } from 'app/shared/model/element-kind.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ElementKindUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const coppers = useAppSelector(state => state.copper.entities);
  const materials = useAppSelector(state => state.material.entities);
  const elementKindEntity = useAppSelector(state => state.elementKind.entity);
  const loading = useAppSelector(state => state.elementKind.loading);
  const updating = useAppSelector(state => state.elementKind.updating);
  const updateSuccess = useAppSelector(state => state.elementKind.updateSuccess);
  const handleClose = () => {
    props.history.push('/element-kind');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getCoppers({}));
    dispatch(getMaterials({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...elementKindEntity,
      ...values,
      copper: coppers.find(it => it.id.toString() === values.copper.toString()),
      insulationMaterial: materials.find(it => it.id.toString() === values.insulationMaterial.toString()),
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
          ...elementKindEntity,
          copper: elementKindEntity?.copper?.id,
          insulationMaterial: elementKindEntity?.insulationMaterial?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="lappLiApp.elementKind.home.createOrEditLabel" data-cy="ElementKindCreateUpdateHeading">
            <Translate contentKey="lappLiApp.elementKind.home.createOrEditLabel">Create or edit a ElementKind</Translate>
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
                  id="element-kind-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('lappLiApp.article.designation')}
                id="element-kind-designation"
                name="designation"
                data-cy="designation"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('lappLiApp.dimension.gramPerMeterLinearMass')}
                id="element-kind-gramPerMeterLinearMass"
                name="gramPerMeterLinearMass"
                data-cy="gramPerMeterLinearMass"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('lappLiApp.dimension.milimeterDiameter')}
                id="element-kind-milimeterDiameter"
                name="milimeterDiameter"
                data-cy="milimeterDiameter"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('lappLiApp.elementKind.milimeterInsulationThickness')}
                id="element-kind-milimeterInsulationThickness"
                name="milimeterInsulationThickness"
                data-cy="milimeterInsulationThickness"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                id="element-kind-copper"
                name="copper"
                data-cy="copper"
                label={translate('lappLiApp.elementKind.copper')}
                type="select"
                required
              >
                <option value="" key="0" />
                {coppers
                  ? coppers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.designation}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <ValidatedField
                id="element-kind-insulationMaterial"
                name="insulationMaterial"
                data-cy="insulationMaterial"
                label={translate('lappLiApp.elementKind.insulationMaterial')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/element-kind" replace color="info">
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

export default ElementKindUpdate;
