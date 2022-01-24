import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IMaterial } from 'app/shared/model/material.model';
import { getEntities as getMaterials } from 'app/entities/material/material.reducer';
import { IStrand } from 'app/shared/model/strand.model';
import { getEntities as getStrands } from 'app/entities/strand/strand.reducer';
import { getEntity, updateEntity, createEntity, reset } from './sheathing.reducer';
import { getEntity as getStrandSupply } from 'app/entities/strand-supply/strand-supply.reducer';
import { ISheathing } from 'app/shared/model/sheathing.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { SheathingKind } from 'app/shared/model/enumerations/sheathing-kind.model';
import { getOutFromStudySupplyStrandSheathing } from '../index-management/index-management-lib';

export const SheathingUpdate = (props: RouteComponentProps<{ strand_supply_id: string; id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const redirectionUrl = getOutFromStudySupplyStrandSheathing(props.match.url, isNew);

  const materials = useAppSelector(state => state.material.entities);
  const strands = useAppSelector(state => state.strand.entities);
  const sheathingEntity = useAppSelector(state => state.sheathing.entity);
  const loading = useAppSelector(state => state.sheathing.loading);
  const updating = useAppSelector(state => state.sheathing.updating);
  const updateSuccess = useAppSelector(state => state.sheathing.updateSuccess);
  const sheathingKindValues = Object.keys(SheathingKind);
  const handleClose = () => {
    props.history.push(redirectionUrl);
  };

  const strandSupplyEntity = useAppSelector(state => state.strandSupply.entity);

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getMaterials({}));
    dispatch(getStrands({}));

    if (props.match.params.strand_supply_id) {
      dispatch(getStrandSupply(props.match.params.strand_supply_id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...sheathingEntity,
      ...values,
      __typeName: 'Sheathing',
      material: materials.find(it => it.id.toString() === values.material.toString()),
      ownerStrand: props.match.params.strand_supply_id
        ? strands.find(it => it.id.toString() === strandSupplyEntity?.strand?.id.toString())
        : strands.find(it => it.id.toString() === values.ownerStrand.toString()),
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
          __typeName: 'Sheathing',
          sheathingKind: 'TUBE',
          ...sheathingEntity,
          material: sheathingEntity?.material?.id,
          ownerStrand: sheathingEntity?.ownerStrand?.id,
        };

  const titleTranslateContentKeyText = isNew ? 'lappLiApp.sheathing.home.createLabel' : 'lappLiApp.sheathing.home.createOrEditLabel';

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="lappLiApp.sheathing.home.createOrEditLabel" data-cy="SheathingCreateUpdateHeading">
            <Translate contentKey={titleTranslateContentKeyText} />
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
                  id="sheathing-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('lappLiApp.operation.operationLayer')}
                id="sheathing-operationLayer"
                name="operationLayer"
                data-cy="operationLayer"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('lappLiApp.sheathing.thickness')}
                id="sheathing-thickness"
                name="thickness"
                data-cy="thickness"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('lappLiApp.sheathing.sheathingKind')}
                id="sheathing-sheathingKind"
                name="sheathingKind"
                data-cy="sheathingKind"
                type="select"
              >
                {sheathingKindValues.map(sheathingKind => (
                  <option value={sheathingKind} key={sheathingKind}>
                    {translate('lappLiApp.SheathingKind.' + sheathingKind)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="sheathing-material"
                name="material"
                data-cy="material"
                label={translate('lappLiApp.sheathing.material')}
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
              {props.match.params ? (
                ''
              ) : (
                <ValidatedField
                  id="sheathing-ownerStrand"
                  name="ownerStrand"
                  data-cy="ownerStrand"
                  label={translate('lappLiApp.sheathing.ownerStrand')}
                  type="select"
                  required
                >
                  <option value="" key="0" />
                  {strands
                    ? strands.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.designation}
                        </option>
                      ))
                    : null}
                </ValidatedField>
              )}
              {props.match.params ? (
                ''
              ) : (
                <FormText>
                  <Translate contentKey="entity.validation.required">This field is required.</Translate>
                </FormText>
              )}
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

export default SheathingUpdate;
