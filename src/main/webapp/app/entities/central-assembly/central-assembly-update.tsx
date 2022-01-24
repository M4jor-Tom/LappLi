import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IStrand } from 'app/shared/model/strand.model';
import { getEntities as getStrands } from 'app/entities/strand/strand.reducer';
import { getEntity, updateEntity, createEntity, reset } from './central-assembly.reducer';
import { ICentralAssembly } from 'app/shared/model/central-assembly.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import {
  AssemblyKind,
  getAssemblyStrandValidatedField,
  getOutFromStudySupplyStrandAssemblyComponent,
} from '../index-management/index-management-lib';

export const CentralAssemblyUpdate = (props: RouteComponentProps<{ strand_id: string; id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const redirectionUrl = getOutFromStudySupplyStrandAssemblyComponent(props.match.url, isNew);

  const strands = useAppSelector(state => state.strand.entities);
  const centralAssemblyEntity = useAppSelector(state => state.centralAssembly.entity);
  const loading = useAppSelector(state => state.centralAssembly.loading);
  const updating = useAppSelector(state => state.centralAssembly.updating);
  const updateSuccess = useAppSelector(state => state.centralAssembly.updateSuccess);
  const handleClose = () => {
    props.history.push(redirectionUrl);
  };

  const strandValidatedField = getAssemblyStrandValidatedField(props, strands, AssemblyKind.CENTRAL);

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
      ...centralAssemblyEntity,
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
          ...centralAssemblyEntity,
          ownerStrand: centralAssemblyEntity?.ownerStrand?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="lappLiApp.centralAssembly.home.createOrEditLabel" data-cy="CentralAssemblyCreateUpdateHeading">
            <Translate contentKey="lappLiApp.centralAssembly.home.createOrEditLabel">Create or edit a CentralAssembly</Translate>
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
                  id="central-assembly-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              {strandValidatedField}
              <ValidatedField
                id="central-assembly-ownerStrand"
                name="ownerStrand"
                data-cy="ownerStrand"
                label={translate('lappLiApp.centralAssembly.ownerStrand')}
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

export default CentralAssemblyUpdate;
