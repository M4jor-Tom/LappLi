import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IStrand } from 'app/shared/model/strand.model';
import { getEntities as getStrands } from 'app/entities/strand/strand.reducer';
import { getEntity, updateEntity, createEntity, reset } from './core-assembly.reducer';
import { ICoreAssembly } from 'app/shared/model/core-assembly.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import {
  AssemblyKind,
  getAssemblyStrandValidatedField,
  getOutFromStudySupplyStrandAssemblyComponent,
} from '../index-management/index-management-lib';

export const CoreAssemblyUpdate = (props: RouteComponentProps<{ strand_id: string; id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const redirectionUrl = getOutFromStudySupplyStrandAssemblyComponent(props.match.url, isNew);

  const strands = useAppSelector(state => state.strand.entities);
  const coreAssemblyEntity = useAppSelector(state => state.coreAssembly.entity);
  const loading = useAppSelector(state => state.coreAssembly.loading);
  const updating = useAppSelector(state => state.coreAssembly.updating);
  const updateSuccess = useAppSelector(state => state.coreAssembly.updateSuccess);
  const handleClose = () => {
    props.history.push(redirectionUrl);
  };

  const strandValidatedField = getAssemblyStrandValidatedField(props, strands, AssemblyKind.CORE);

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getStrands({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...coreAssemblyEntity,
      ...values,
      ownerStrand: strands.find(it => it.id.toString() === values.ownerStrand.toString()),
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
          ...coreAssemblyEntity,
          ownerStrand: coreAssemblyEntity?.ownerStrand?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="lappLiApp.coreAssembly.home.createOrEditLabel" data-cy="CoreAssemblyCreateUpdateHeading">
            <Translate contentKey="lappLiApp.coreAssembly.home.createOrEditLabel">Create or edit a CoreAssembly</Translate>
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
                  id="core-assembly-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('lappLiApp.coreAssembly.assemblyLayer')}
                id="core-assembly-assemblyLayer"
                name="assemblyLayer"
                data-cy="assemblyLayer"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('lappLiApp.coreAssembly.forcedMeanMilimeterComponentDiameter')}
                id="core-assembly-forcedMeanMilimeterComponentDiameter"
                name="forcedMeanMilimeterComponentDiameter"
                data-cy="forcedMeanMilimeterComponentDiameter"
                type="text"
              />
              <ValidatedField
                label={translate('lappLiApp.coreAssembly.componentsCount')}
                id="core-assembly-componentsCount"
                name="componentsCount"
                data-cy="componentsCount"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              {strandValidatedField}
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/core-assembly" replace color="info">
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

export default CoreAssemblyUpdate;
