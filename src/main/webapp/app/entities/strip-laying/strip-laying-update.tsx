import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IStrandSupply } from 'app/shared/model/strand-supply.model';
import { getEntities as getStrandSupplies } from 'app/entities/strand-supply/strand-supply.reducer';
import { getEntity, updateEntity, createEntity, reset } from './strip-laying.reducer';
import { IStripLaying } from 'app/shared/model/strip-laying.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const StripLayingUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const strandSupplies = useAppSelector(state => state.strandSupply.entities);
  const stripLayingEntity = useAppSelector(state => state.stripLaying.entity);
  const loading = useAppSelector(state => state.stripLaying.loading);
  const updating = useAppSelector(state => state.stripLaying.updating);
  const updateSuccess = useAppSelector(state => state.stripLaying.updateSuccess);
  const handleClose = () => {
    props.history.push('/strip-laying');
  };

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
      ...stripLayingEntity,
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
          ...stripLayingEntity,
          ownerStrandSupply: stripLayingEntity?.ownerStrandSupply?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="lappLiApp.stripLaying.home.createOrEditLabel" data-cy="StripLayingCreateUpdateHeading">
            <Translate contentKey="lappLiApp.stripLaying.home.createOrEditLabel">Create or edit a StripLaying</Translate>
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
                  id="strip-laying-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('lappLiApp.stripLaying.operationLayer')}
                id="strip-laying-operationLayer"
                name="operationLayer"
                data-cy="operationLayer"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                id="strip-laying-ownerStrandSupply"
                name="ownerStrandSupply"
                data-cy="ownerStrandSupply"
                label={translate('lappLiApp.stripLaying.ownerStrandSupply')}
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
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/strip-laying" replace color="info">
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

export default StripLayingUpdate;
