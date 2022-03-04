import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IStrandSupply } from 'app/shared/model/strand-supply.model';
import { getEntities as getStrandSupplies } from 'app/entities/strand-supply/strand-supply.reducer';
import { getEntity, updateEntity, createEntity, reset } from './interstice-assembly.reducer';
import { IIntersticeAssembly } from 'app/shared/model/interstice-assembly.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import {
  AssemblyKind,
  getAssemblyStrandSupplyValidatedField,
  getOutFromStudySupplyStrandAssemblyComponent,
} from '../index-management/index-management-lib';

export const IntersticeAssemblyUpdate = (props: RouteComponentProps<{ strand_supply_id: string | null; id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const redirectionUrl = getOutFromStudySupplyStrandAssemblyComponent(props.match.url, isNew);

  const strandSupplies = useAppSelector(state => state.intersticeAssembly.entities);
  const intersticeAssemblyEntity = useAppSelector(state => state.intersticeAssembly.entity);
  const loading = useAppSelector(state => state.intersticeAssembly.loading);
  const updating = useAppSelector(state => state.intersticeAssembly.updating);
  const updateSuccess = useAppSelector(state => state.intersticeAssembly.updateSuccess);
  const handleClose = () => {
    props.history.push(redirectionUrl);
  };

  const strandValidatedField = getAssemblyStrandSupplyValidatedField(props, strandSupplies, AssemblyKind.INTERSTICE);

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getStrandSupplies({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...intersticeAssemblyEntity,
      ...values,
      ownerStrandSupply: strandSupplies.find(it => it.id.toString() === values.ownerStrandSupply.toString()),
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
          ...intersticeAssemblyEntity,
          ownerStrandSupply: intersticeAssemblyEntity?.ownerStrandSupply?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="lappLiApp.intersticeAssembly.home.createOrEditLabel" data-cy="IntersticeAssemblyCreateUpdateHeading">
            <Translate contentKey="lappLiApp.intersticeAssembly.home.createOrEditLabel">Create or edit a IntersticeAssembly</Translate>
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
                  id="interstice-assembly-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('lappLiApp.intersticeAssembly.operationLayer')}
                id="interstice-assembly-operationLayer"
                name="operationLayer"
                data-cy="operationLayer"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('lappLiApp.intersticeAssembly.intersticeLayer')}
                id="interstice-assembly-intersticeLayer"
                name="intersticeLayer"
                data-cy="intersticeLayer"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('lappLiApp.intersticeAssembly.forcedMeanMilimeterComponentDiameter')}
                id="interstice-assembly-forcedMeanMilimeterComponentDiameter"
                name="forcedMeanMilimeterComponentDiameter"
                data-cy="forcedMeanMilimeterComponentDiameter"
                type="text"
              />
              {strandValidatedField}
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

export default IntersticeAssemblyUpdate;
