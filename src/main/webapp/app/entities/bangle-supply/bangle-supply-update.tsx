import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IBangle } from 'app/shared/model/bangle.model';
import { getEntities as getBangles } from 'app/entities/bangle/bangle.reducer';
import { IPosition } from 'app/shared/model/position.model';
import { getEntities as getPositions } from 'app/entities/position/position.reducer';
import { IStrand } from 'app/shared/model/strand.model';
import { getEntities as getStrands } from 'app/entities/strand/strand.reducer';
import { getEntity, updateEntity, createEntity, reset } from './bangle-supply.reducer';
import { IBangleSupply } from 'app/shared/model/bangle-supply.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { SupplyState } from 'app/shared/model/enumerations/supply-state.model';
import { getSupplyStrandValidatedField, SupplyKind } from '../index-management/index-management-lib';

export const BangleSupplyUpdate = (props: RouteComponentProps<{ id: string; strand_id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const bangles = useAppSelector(state => state.bangle.entities);
  const positions = useAppSelector(state => state.position.entities);
  const strands = useAppSelector(state => state.strand.entities);
  const bangleSupplyEntity = useAppSelector(state => state.bangleSupply.entity);
  const loading = useAppSelector(state => state.bangleSupply.loading);
  const updating = useAppSelector(state => state.bangleSupply.updating);
  const updateSuccess = useAppSelector(state => state.bangleSupply.updateSuccess);
  const supplyStateValues = Object.keys(SupplyState);
  const handleClose = () => {
    props.history.push('/bangle-supply');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getBangles({}));
    dispatch(getPositions({}));
    dispatch(getStrands({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...bangleSupplyEntity,
      ...values,
      bangle: bangles.find(it => it.id.toString() === values.bangle.toString()),
      strand: strands.find(it => it.id.toString() === values.strand.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const strandValidateField = getSupplyStrandValidatedField(props, strands, SupplyKind.BANGLE);

  const defaultValues = () =>
    isNew
      ? {}
      : {
          supplyState: 'UNDIVIDED',
          ...bangleSupplyEntity,
          bangle: bangleSupplyEntity?.bangle?.id,
          strand: bangleSupplyEntity?.strand?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="lappLiApp.bangleSupply.home.createOrEditLabel" data-cy="BangleSupplyCreateUpdateHeading">
            <Translate contentKey="lappLiApp.bangleSupply.home.createOrEditLabel">Create or edit a BangleSupply</Translate>
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
                  id="bangle-supply-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('lappLiApp.bangleSupply.supplyState')}
                id="bangle-supply-supplyState"
                name="supplyState"
                data-cy="supplyState"
                type="select"
              >
                {supplyStateValues.map(supplyState => (
                  <option value={supplyState} key={supplyState}>
                    {translate('lappLiApp.SupplyState' + supplyState)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('lappLiApp.bangleSupply.apparitions')}
                id="bangle-supply-apparitions"
                name="apparitions"
                data-cy="apparitions"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('lappLiApp.bangleSupply.description')}
                id="bangle-supply-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                id="bangle-supply-bangle"
                name="bangle"
                data-cy="bangle"
                label={translate('lappLiApp.bangleSupply.bangle')}
                type="select"
                required
              >
                <option value="" key="0" />
                {bangles
                  ? bangles.map(otherEntity => (
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
                id="bangle-supply-strand"
                name="strand"
                data-cy="strand"
                label={translate('lappLiApp.supply.strand')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/bangle-supply" replace color="info">
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

export default BangleSupplyUpdate;
