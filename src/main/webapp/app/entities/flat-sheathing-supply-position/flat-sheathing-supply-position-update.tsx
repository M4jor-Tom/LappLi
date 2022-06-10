import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { ISupplyPosition } from 'app/shared/model/supply-position.model';
import { getEntities as getSupplyPositions } from 'app/entities/supply-position/supply-position.reducer';
import { IFlatSheathing } from 'app/shared/model/flat-sheathing.model';
import { getEntities as getFlatSheathings } from 'app/entities/flat-sheathing/flat-sheathing.reducer';
import { getEntity, updateEntity, createEntity, reset } from './flat-sheathing-supply-position.reducer';
import { IFlatSheathingSupplyPosition } from 'app/shared/model/flat-sheathing-supply-position.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getOut } from '../index-management/index-management-lib';

export const FlatSheathingSupplyPositionUpdate = (
  props: RouteComponentProps<{ id: string; study_id: string; operation_id: string; strand_supply_id: string }>
) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);
  const pageComesFromStudyMenu: boolean =
    props.match.params.study_id != null && props.match.params.strand_supply_id != null && props.match.params.operation_id != null;

  const supplyPositions = useAppSelector(state => state.supplyPosition.entities);
  const flatSheathings = useAppSelector(state => state.flatSheathing.entities);
  const flatSheathingSupplyPositionEntity = useAppSelector(state => state.flatSheathingSupplyPosition.entity);
  const loading = useAppSelector(state => state.flatSheathingSupplyPosition.loading);
  const updating = useAppSelector(state => state.flatSheathingSupplyPosition.updating);

  const redirectionUrl = pageComesFromStudyMenu ? getOut(props.match.url, 3) : '/flat-sheathing-supply-position';

  const updateSuccess = useAppSelector(state => state.flatSheathingSupplyPosition.updateSuccess);
  const handleClose = () => {
    props.history.push(redirectionUrl);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getSupplyPositions({}));
    dispatch(getFlatSheathings({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...flatSheathingSupplyPositionEntity,
      ...values,
      supplyPosition: supplyPositions.find(it => it.id.toString() === values.supplyPosition.toString()),
      ownerFlatSheathing: flatSheathings.find(it => it.id.toString() === values.ownerFlatSheathing.toString()),
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
          ...flatSheathingSupplyPositionEntity,
          supplyPosition: flatSheathingSupplyPositionEntity?.supplyPosition?.id,
          ownerFlatSheathing: flatSheathingSupplyPositionEntity?.ownerFlatSheathing?.id,
        };

  return pageComesFromStudyMenu ? (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="lappLiApp.flatSheathingSupplyPosition.home.createOrEditLabel" data-cy="FlatSheathingSupplyPositionCreateUpdateHeading">
            <Translate contentKey="lappLiApp.flatSheathingSupplyPosition.home.createOrEditLabel">
              Create or edit a FlatSheathingSupplyPosition
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
                  id="flat-sheathing-supply-position-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('lappLiApp.flatSheathingSupplyPosition.locationInOwnerFlatSheathing')}
                id="flat-sheathing-supply-position-locationInOwnerFlatSheathing"
                name="locationInOwnerFlatSheathing"
                data-cy="locationInOwnerFlatSheathing"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  min: { value: 0, message: translate('entity.validation.min', { min: 0 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                id="flat-sheathing-supply-position-supplyPosition"
                name="supplyPosition"
                data-cy="supplyPosition"
                label={translate('lappLiApp.flatSheathingSupplyPosition.supplyPosition')}
                type="select"
                required
              >
                <option value="" key="0" />
                {supplyPositions
                  ? supplyPositions.map(otherEntity => (
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
                id="flat-sheathing-supply-position-ownerFlatSheathing"
                name="ownerFlatSheathing"
                data-cy="ownerFlatSheathing"
                label={translate('lappLiApp.flatSheathingSupplyPosition.ownerFlatSheathing')}
                type="select"
                required
              >
                <option value="" key="0" />
                {flatSheathings
                  ? flatSheathings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.designation}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to={redirectionUrl} replace color="info">
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
  ) : (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="lappLiApp.flatSheathingSupplyPosition.home.createOrEditLabel" data-cy="FlatSheathingSupplyPositionCreateUpdateHeading">
            <Translate contentKey="lappLiApp.flatSheathingSupplyPosition.home.createOrEditLabel">
              Create or edit a FlatSheathingSupplyPosition
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
                  id="flat-sheathing-supply-position-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('lappLiApp.flatSheathingSupplyPosition.locationInOwnerFlatSheathing')}
                id="flat-sheathing-supply-position-locationInOwnerFlatSheathing"
                name="locationInOwnerFlatSheathing"
                data-cy="locationInOwnerFlatSheathing"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  min: { value: 0, message: translate('entity.validation.min', { min: 0 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                id="flat-sheathing-supply-position-supplyPosition"
                name="supplyPosition"
                data-cy="supplyPosition"
                label={translate('lappLiApp.flatSheathingSupplyPosition.supplyPosition')}
                type="select"
                required
              >
                <option value="" key="0" />
                {supplyPositions
                  ? supplyPositions.map(otherEntity => (
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
                id="flat-sheathing-supply-position-ownerFlatSheathing"
                name="ownerFlatSheathing"
                data-cy="ownerFlatSheathing"
                label={translate('lappLiApp.flatSheathingSupplyPosition.ownerFlatSheathing')}
                type="select"
                required
              >
                <option value="" key="0" />
                {flatSheathings
                  ? flatSheathings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.designation}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to={redirectionUrl} replace color="info">
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

export default FlatSheathingSupplyPositionUpdate;
