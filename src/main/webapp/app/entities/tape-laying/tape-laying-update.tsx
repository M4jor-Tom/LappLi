import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { ITape } from 'app/shared/model/tape.model';
import { getEntities as getTapes } from 'app/entities/tape/tape.reducer';
import { IStrandSupply } from 'app/shared/model/strand-supply.model';
import { getEntities as getStrandSupplies } from 'app/entities/strand-supply/strand-supply.reducer';
import { getEntity, updateEntity, createEntity, reset } from './tape-laying.reducer';
import { ITapeLaying } from 'app/shared/model/tape-laying.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { AssemblyMean } from 'app/shared/model/enumerations/assembly-mean.model';
import { getOutFromStudySupplyStrandTapeLaying } from '../index-management/index-management-lib';

export const TapeLayingUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const redirectionUrl = getOutFromStudySupplyStrandTapeLaying(props.match.url, isNew);

  const tapes = useAppSelector(state => state.tape.entities);
  const strandSupplies = useAppSelector(state => state.strandSupply.entities);
  const tapeLayingEntity = useAppSelector(state => state.tapeLaying.entity);
  const loading = useAppSelector(state => state.tapeLaying.loading);
  const updating = useAppSelector(state => state.tapeLaying.updating);
  const updateSuccess = useAppSelector(state => state.tapeLaying.updateSuccess);
  const assemblyMeanValues = Object.keys(AssemblyMean);
  const handleClose = () => {
    props.history.push(redirectionUrl);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getTapes({}));
    dispatch(getStrandSupplies({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...tapeLayingEntity,
      ...values,
      __typeName: 'TapeLaying',
      tape: tapes.find(it => it.id.toString() === values.tape.toString()),
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
          assemblyMean: 'RIGHT',
          ...tapeLayingEntity,
          tape: tapeLayingEntity?.tape?.id,
          ownerStrandSupply: tapeLayingEntity?.ownerStrandSupply?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="lappLiApp.tapeLaying.home.createOrEditLabel" data-cy="TapeLayingCreateUpdateHeading">
            <Translate contentKey="lappLiApp.tapeLaying.home.createOrEditLabel">Create or edit a TapeLaying</Translate>
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
                  id="tape-laying-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('lappLiApp.tapeLaying.operationLayer')}
                id="tape-laying-operationLayer"
                name="operationLayer"
                data-cy="operationLayer"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('lappLiApp.tapeLaying.assemblyMean')}
                id="tape-laying-assemblyMean"
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
                id="tape-laying-tape"
                name="tape"
                data-cy="tape"
                label={translate('lappLiApp.tapeLaying.tape')}
                type="select"
                required
              >
                <option value="" key="0" />
                {tapes
                  ? tapes.map(otherEntity => (
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
                  id="tape-laying-ownerStrandSupply"
                  name="ownerStrandSupply"
                  data-cy="ownerStrandSupply"
                  label={translate('lappLiApp.tapeLaying.ownerStrandSupply')}
                  type="select"
                  required
                >
                  <option value="" key="0" />
                  {strandSupplies
                    ? strandSupplies.map(otherEntity => (
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

export default TapeLayingUpdate;
