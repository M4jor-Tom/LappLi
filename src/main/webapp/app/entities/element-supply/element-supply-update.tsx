import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IElement } from 'app/shared/model/element.model';
import { getEntities as getElements } from 'app/entities/element/element.reducer';
import { IPosition } from 'app/shared/model/position.model';
import { getEntities as getPositions } from 'app/entities/position/position.reducer';
import { IStrand } from 'app/shared/model/strand.model';
import { getEntities as getStrands } from 'app/entities/strand/strand.reducer';
import { getEntity, updateEntity, createEntity, reset } from './element-supply.reducer';
import { IElementSupply } from 'app/shared/model/element-supply.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { MarkingType } from 'app/shared/model/enumerations/marking-type.model';
import {
  getOut,
  getOutFromStudySupplyStrandSupplyComponent,
  getStrandSupplyRedirectionUrl,
  getSupplyStrandValidatedField,
  isStrandSupply,
} from '../index-management/index-management-lib';
import { SupplyKind } from 'app/shared/model/enumerations/supply-kind.model';

export const ElementSupplyUpdate = (props: RouteComponentProps<{ strand_id: string; id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const _isStrandSupply = isStrandSupply(props);

  const redirectionUrl = getOutFromStudySupplyStrandSupplyComponent(props.match.url, isNew);

  const elements = useAppSelector(state => state.element.entities);
  const positions = useAppSelector(state => state.position.entities);
  const strands = useAppSelector(state => state.strand.entities);
  const elementSupplyEntity = useAppSelector(state => state.elementSupply.entity);
  const loading = useAppSelector(state => state.elementSupply.loading);
  const updating = useAppSelector(state => state.elementSupply.updating);
  const updateSuccess = useAppSelector(state => state.elementSupply.updateSuccess);
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

    dispatch(getElements({}));
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
      ...elementSupplyEntity,
      ...values,
      element: elements.find(it => it.id.toString() === values.element.toString()),
      ownerStrand: strands.find(it => it.id.toString() === values.ownerStrand.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const strandValidateField = getSupplyStrandValidatedField(props, strands, SupplyKind.ELEMENT);

  const defaultValues = () =>
    isNew
      ? {}
      : {
          markingType: 'LIFTING',
          ...elementSupplyEntity,
          element: elementSupplyEntity?.element?.id,
          ownerStrand: elementSupplyEntity?.ownerStrand?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="lappLiApp.elementSupply.home.createOrEditLabel" data-cy="ElementSupplyCreateUpdateHeading">
            <Translate contentKey="lappLiApp.elementSupply.home.createOrEditLabel">Create or edit a ElementSupply</Translate>
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
                  id="element-supply-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('lappLiApp.supply.apparitions')}
                id="element-supply-apparitions"
                name="apparitions"
                data-cy="apparitions"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('lappLiApp.supply.markingType')}
                id="element-supply-markingType"
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
                label={translate('lappLiApp.supply.description')}
                id="element-supply-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                id="element-supply-element"
                name="element"
                data-cy="element"
                label={translate('lappLiApp.elementSupply.element')}
                type="select"
                required
              >
                <option value="" key="0" />
                {elements
                  ? elements.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.numberWithDesignationWithColor}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              {strandValidateField}
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

export default ElementSupplyUpdate;
