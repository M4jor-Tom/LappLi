import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './metal-fiber.reducer';
import { IMetalFiber } from 'app/shared/model/metal-fiber.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { MetalFiberKind } from 'app/shared/model/enumerations/metal-fiber-kind.model';

export const MetalFiberUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const metalFiberEntity = useAppSelector(state => state.metalFiber.entity);
  const loading = useAppSelector(state => state.metalFiber.loading);
  const updating = useAppSelector(state => state.metalFiber.updating);
  const updateSuccess = useAppSelector(state => state.metalFiber.updateSuccess);
  const metalFiberKindValues = Object.keys(MetalFiberKind);
  const handleClose = () => {
    props.history.push('/metal-fiber');
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
      ...metalFiberEntity,
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
          metalFiberKind: 'RED_COPPER',
          ...metalFiberEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="lappLiApp.metalFiber.home.createOrEditLabel" data-cy="MetalFiberCreateUpdateHeading">
            <Translate contentKey="lappLiApp.metalFiber.home.createOrEditLabel">Create or edit a MetalFiber</Translate>
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
                  id="metal-fiber-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('lappLiApp.metalFiber.number')}
                id="metal-fiber-number"
                name="number"
                data-cy="number"
                type="text"
                validate={{
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('lappLiApp.metalFiber.designation')}
                id="metal-fiber-designation"
                name="designation"
                data-cy="designation"
                type="text"
                validate={{}}
              />
              <ValidatedField
                label={translate('lappLiApp.metalFiber.metalFiberKind')}
                id="metal-fiber-metalFiberKind"
                name="metalFiberKind"
                data-cy="metalFiberKind"
                type="select"
              >
                {metalFiberKindValues.map(metalFiberKind => (
                  <option value={metalFiberKind} key={metalFiberKind}>
                    {translate('lappLiApp.MetalFiberKind' + metalFiberKind)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('lappLiApp.metalFiber.milimeterDiameter')}
                id="metal-fiber-milimeterDiameter"
                name="milimeterDiameter"
                data-cy="milimeterDiameter"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/metal-fiber" replace color="info">
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

export default MetalFiberUpdate;
