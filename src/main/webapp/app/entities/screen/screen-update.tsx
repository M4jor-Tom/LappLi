import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { ICopperFiber } from 'app/shared/model/copper-fiber.model';
import { getEntities as getCopperFibers } from 'app/entities/copper-fiber/copper-fiber.reducer';
import { IStrandSupply } from 'app/shared/model/strand-supply.model';
import { getEntities as getStrandSupplies, getEntity as getStrandSupplyEntity } from 'app/entities/strand-supply/strand-supply.reducer';
import { getEntity, updateEntity, createEntity, reset } from './screen.reducer';
import { IScreen } from 'app/shared/model/screen.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getOutFromStudySupplyStrandScreen } from '../index-management/index-management-lib';
import { MetalFiberKind } from 'app/shared/model/enumerations/metal-fiber-kind.model';

export const ScreenUpdate = (props: RouteComponentProps<{ strand_supply_id: string; id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const copperFibers = useAppSelector(state => state.copperFiber.entities);
  const strandSupplies = useAppSelector(state => state.strandSupply.entities);
  const screenEntity = useAppSelector(state => state.screen.entity);
  const loading = useAppSelector(state => state.screen.loading);
  const updating = useAppSelector(state => state.screen.updating);
  const updateSuccess = useAppSelector(state => state.screen.updateSuccess);
  const metalFiberKindValues = Object.keys(MetalFiberKind);

  //  Design for operation -- START

  const redirectionUrl = getOutFromStudySupplyStrandScreen(props.match.url, isNew);
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

    dispatch(getCopperFibers({}));
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
      ...screenEntity,
      ...values,
      __typeName: 'Screen',
      copperFiber: copperFibers.find(it => it.id.toString() === values.copperFiber.toString()),
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
          anonymousCopperFiberKind: 'RED_COPPER',
          ...screenEntity,
          copperFiber: screenEntity?.copperFiber?.id,
          ownerStrandSupply: screenEntity?.ownerStrandSupply?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="lappLiApp.screen.home.createOrEditLabel" data-cy="ScreenCreateUpdateHeading">
            <Translate contentKey="lappLiApp.screen.home.createOrEditLabel">Create or edit a Screen</Translate>
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
                  id="screen-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              {isNew ? (
                <ValidatedField
                  label={translate('lappLiApp.operation.operationLayer')}
                  id="screen-operationLayer"
                  name="operationLayer"
                  data-cy="operationLayer"
                  type="text"
                  defaultValue={-2}
                  validate={{
                    required: { value: true, message: translate('entity.validation.required') },
                    validate: v => isNumber(v) || translate('entity.validation.number'),
                  }}
                />
              ) : null}
              <ValidatedField
                label={translate('lappLiApp.screen.assemblyMeanIsSameThanAssemblys')}
                id="screen-assemblyMeanIsSameThanAssemblys"
                name="assemblyMeanIsSameThanAssemblys"
                data-cy="assemblyMeanIsSameThanAssemblys"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('lappLiApp.screen.forcedDiameterAssemblyStep')}
                id="screen-forcedDiameterAssemblyStep"
                name="forcedDiameterAssemblyStep"
                data-cy="forcedDiameterAssemblyStep"
                type="text"
              />
              <ValidatedField
                label={translate('lappLiApp.screen.anonymousCopperFiberNumber')}
                id="screen-anonymousCopperFiberNumber"
                name="anonymousCopperFiberNumber"
                data-cy="anonymousCopperFiberNumber"
                type="text"
              />
              <ValidatedField
                label={translate('lappLiApp.screen.anonymousCopperFiberDesignation')}
                id="screen-anonymousCopperFiberDesignation"
                name="anonymousCopperFiberDesignation"
                data-cy="anonymousCopperFiberDesignation"
                type="text"
              />
              <ValidatedField
                label={translate('lappLiApp.screen.anonymousCopperFiberKind')}
                id="screen-anonymousCopperFiberKind"
                name="anonymousCopperFiberKind"
                data-cy="anonymousCopperFiberKind"
                type="select"
              >
                {metalFiberKindValues.map(metalFiberKind => (
                  <option value={metalFiberKind} key={metalFiberKind}>
                    {translate('lappLiApp.MetalFiberKind.' + metalFiberKind)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('lappLiApp.screen.anonymousCopperFiberMilimeterDiameter') + ' *'}
                id="screen-anonymousCopperFiberMilimeterDiameter"
                name="anonymousCopperFiberMilimeterDiameter"
                data-cy="anonymousCopperFiberMilimeterDiameter"
                type="text"
              />
              <ValidatedField
                id="screen-copperFiber"
                name="copperFiber"
                data-cy="copperFiber"
                label={translate('lappLiApp.screen.copperFiber')}
                type="select"
              >
                <option value="" key="0" />
                {copperFibers
                  ? copperFibers.map(otherEntity => (
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
                  id="screen-ownerStrandSupply"
                  name="ownerStrandSupply"
                  data-cy="ownerStrandSupply"
                  label={translate('lappLiApp.screen.ownerStrandSupply')}
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

export default ScreenUpdate;
