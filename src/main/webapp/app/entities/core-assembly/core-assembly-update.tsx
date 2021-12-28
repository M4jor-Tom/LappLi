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
import { AssemblyMean } from 'app/shared/model/enumerations/assembly-mean.model';

export const CoreAssemblyUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const strands = useAppSelector(state => state.strand.entities);
  const coreAssemblyEntity = useAppSelector(state => state.coreAssembly.entity);
  const loading = useAppSelector(state => state.coreAssembly.loading);
  const updating = useAppSelector(state => state.coreAssembly.updating);
  const updateSuccess = useAppSelector(state => state.coreAssembly.updateSuccess);
  const assemblyMeanValues = Object.keys(AssemblyMean);
  const handleClose = () => {
    props.history.push('/core-assembly');
  };

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
      strand: strands.find(it => it.id.toString() === values.strand.toString()),
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
          assemblyMean: 'RIGHT',
          ...coreAssemblyEntity,
          strand: coreAssemblyEntity?.strand?.id,
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
                label={translate('lappLiApp.coreAssembly.operationLayer')}
                id="core-assembly-operationLayer"
                name="operationLayer"
                data-cy="operationLayer"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('lappLiApp.coreAssembly.productionStep')}
                id="core-assembly-productionStep"
                name="productionStep"
                data-cy="productionStep"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('lappLiApp.coreAssembly.diameterAssemblyStep')}
                id="core-assembly-diameterAssemblyStep"
                name="diameterAssemblyStep"
                data-cy="diameterAssemblyStep"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('lappLiApp.coreAssembly.assemblyMean')}
                id="core-assembly-assemblyMean"
                name="assemblyMean"
                data-cy="assemblyMean"
                type="select"
              >
                {assemblyMeanValues.map(assemblyMean => (
                  <option value={assemblyMean} key={assemblyMean}>
                    {translate('lappLiApp.AssemblyMean.' + assemblyMean)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="core-assembly-strand"
                name="strand"
                data-cy="strand"
                label={translate('lappLiApp.coreAssembly.strand')}
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
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
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
