import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IStrip } from 'app/shared/model/strip.model';
import { getEntities as getStrips } from 'app/entities/strip/strip.reducer';
import { IStrandSupply } from 'app/shared/model/strand-supply.model';
import { getEntities as getStrandSupplies, getEntity as getStrandSupplyEntity } from 'app/entities/strand-supply/strand-supply.reducer';
import { getEntity, updateEntity, createEntity, reset } from './strip-laying.reducer';
import { IStripLaying } from 'app/shared/model/strip-laying.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getOutFromStudySupplyStrandStripLaying } from '../index-management/index-management-lib';

export const StripLayingUpdate = (props: RouteComponentProps<{ strand_supply_id: string; id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const strips = useAppSelector(state => state.strip.entities);
  const strandSupplies = useAppSelector(state => state.strandSupply.entities);
  const stripLayingEntity = useAppSelector(state => state.stripLaying.entity);
  const loading = useAppSelector(state => state.stripLaying.loading);
  const updating = useAppSelector(state => state.stripLaying.updating);
  const updateSuccess = useAppSelector(state => state.stripLaying.updateSuccess);

  //  Design for operation -- START

  const redirectionUrl = getOutFromStudySupplyStrandStripLaying(props.match.url, isNew);
  const futureOwnerStrandSupplyEntity = useAppSelector(state => state.strandSupply.entity);

  //  Design for operation -- END

  const handleClose = () => {
    props.history.push(redirectionUrl);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getStrips({}));
    dispatch(getStrandSupplies({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    //  Design for operation -- START

    let futureOwnerStrandSupply: IStrandSupply = {};

    if (values.ownerStrandSupply) {
      futureOwnerStrandSupply = strandSupplies.find(it => it.id.toString() === values.ownerStrandSupply.toString());
    } else {
      dispatch(getStrandSupplyEntity(props.match.params.strand_supply_id));
      futureOwnerStrandSupply = futureOwnerStrandSupplyEntity;
    }

    //  Design for operation -- END

    const entity = {
      ...stripLayingEntity,
      ...values,
      __typeName: 'StripLaying',
      strip: strips.find(it => it.id.toString() === values.strip.toString()),
      ownerStrandSupply: futureOwnerStrandSupply,
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
          strip: stripLayingEntity?.strip?.id,
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
                label={translate('lappLiApp.operation.operationLayer')}
                id="strip-laying-operationLayer"
                name="operationLayer"
                data-cy="operationLayer"
                type="text"
                defaultValue={-2}
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                id="strip-laying-strip"
                name="strip"
                data-cy="strip"
                label={translate('lappLiApp.stripLaying.strip')}
                type="select"
                required
              >
                <option value="" key="0" />
                {strips
                  ? strips.map(otherEntity => (
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

export default StripLayingUpdate;
