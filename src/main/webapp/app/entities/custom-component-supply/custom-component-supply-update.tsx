import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { ICustomComponent } from 'app/shared/model/custom-component.model';
import { getEntities as getCustomComponents } from 'app/entities/custom-component/custom-component.reducer';
import { getEntity, updateEntity, createEntity, reset } from './custom-component-supply.reducer';
import { ICustomComponentSupply } from 'app/shared/model/custom-component-supply.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { MarkingType } from 'app/shared/model/enumerations/marking-type.model';
import {
  getOutFromStudySupplyStrandSupplyComponent,
  getStrandSupplyRedirectionUrl,
  getStrandSupplyValidatedField,
} from '../index-management/index-management-lib';

import { createEntity as createSupplyPositionEntity } from '../supply-position/supply-position.reducer';
import { getEntity as getStrand } from '../strand/strand.reducer';
import { ISupplyPosition } from 'app/shared/model/supply-position.model';
import { IStrand } from 'app/shared/model/strand.model';

export const CustomComponentSupplyUpdate = (props: RouteComponentProps<{ id: string; strand_id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const redirectionUrl = getOutFromStudySupplyStrandSupplyComponent(props.match.url, isNew);

  const customComponents = useAppSelector(state => state.customComponent.entities);
  const strand: IStrand = useAppSelector(state => state.strand.entity);
  const customComponentSupplyEntity = useAppSelector(state => state.customComponentSupply.entity);
  const loading = useAppSelector(state => state.customComponentSupply.loading);
  const updating = useAppSelector(state => state.customComponentSupply.updating);
  const updateSuccess = useAppSelector(state => state.customComponentSupply.updateSuccess);
  const markingTypeValues = Object.keys(MarkingType);
  const handleClose = () => {
    props.history.push(redirectionUrl);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getCustomComponents({}));
    dispatch(getStrand(props.match.params.strand_id));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      __typeName: 'CustomComponentSupply',
      ...customComponentSupplyEntity,
      ...values,
      customComponent: customComponents.find(it => it.id.toString() === values.customComponent.toString()),
    };

    const createdSupplyPosition: ISupplyPosition = {
      supplyApparitionsUsage: 0,
      ownerStrand: strand,
      customComponentSupply: entity,
    };

    if (isNew) {
      dispatch(createSupplyPositionEntity(createdSupplyPosition));
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          markingType: 'LIFTING',
          ...customComponentSupplyEntity,
          customComponent: customComponentSupplyEntity?.customComponent?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="lappLiApp.customComponentSupply.home.createOrEditLabel" data-cy="CustomComponentSupplyCreateUpdateHeading">
            <Translate contentKey="lappLiApp.customComponentSupply.home.createOrEditLabel">
              Create or edit a CustomComponentSupply
            </Translate>
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
                  id="custom-component-supply-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('lappLiApp.supply.apparitions')}
                id="custom-component-supply-apparitions"
                name="apparitions"
                data-cy="apparitions"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('lappLiApp.supply.description')}
                id="custom-component-supply-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('lappLiApp.supply.markingType')}
                id="custom-component-supply-markingType"
                name="markingType"
                data-cy="markingType"
                type="select"
              >
                {markingTypeValues.map(markingType => (
                  <option value={markingType} key={markingType}>
                    {translate('lappLiApp.MarkingType.' + markingType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="custom-component-supply-customComponent"
                name="customComponent"
                data-cy="customComponent"
                label={translate('lappLiApp.customComponentSupply.customComponent')}
                type="select"
                required
              >
                <option value="" key="0" />
                {customComponents
                  ? customComponents.map(otherEntity => (
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

export default CustomComponentSupplyUpdate;
