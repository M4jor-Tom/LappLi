import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './copper-fiber.reducer';
import { ICopperFiber } from 'app/shared/model/copper-fiber.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CopperFiberUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const copperFiberEntity = useAppSelector(state => state.copperFiber.entity);
  const loading = useAppSelector(state => state.copperFiber.loading);
  const updating = useAppSelector(state => state.copperFiber.updating);
  const updateSuccess = useAppSelector(state => state.copperFiber.updateSuccess);
  const handleClose = () => {
    props.history.push('/copper-fiber');
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
      ...copperFiberEntity,
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
          ...copperFiberEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="lappLiApp.copperFiber.home.createOrEditLabel" data-cy="CopperFiberCreateUpdateHeading">
            <Translate contentKey="lappLiApp.copperFiber.home.createOrEditLabel">Create or edit a CopperFiber</Translate>
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
                  id="copper-fiber-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('lappLiApp.copperFiber.number')}
                id="copper-fiber-number"
                name="number"
                data-cy="number"
                type="text"
                validate={{
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('lappLiApp.copperFiber.designation')}
                id="copper-fiber-designation"
                name="designation"
                data-cy="designation"
                type="text"
                validate={{}}
              />
              <ValidatedField
                label={translate('lappLiApp.copperFiber.copperIsRedNotTinned')}
                id="copper-fiber-copperIsRedNotTinned"
                name="copperIsRedNotTinned"
                data-cy="copperIsRedNotTinned"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('lappLiApp.copperFiber.milimeterDiameter')}
                id="copper-fiber-milimeterDiameter"
                name="milimeterDiameter"
                data-cy="milimeterDiameter"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/copper-fiber" replace color="info">
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

export default CopperFiberUpdate;
