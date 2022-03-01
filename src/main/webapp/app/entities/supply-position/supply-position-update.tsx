import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { ICentralAssembly } from 'app/shared/model/central-assembly.model';
import { getEntities as getCentralAssemblies } from 'app/entities/central-assembly/central-assembly.reducer';
import { IElementSupply } from 'app/shared/model/element-supply.model';
import { getEntities as getElementSupplies } from 'app/entities/element-supply/element-supply.reducer';
import { IBangleSupply } from 'app/shared/model/bangle-supply.model';
import { getEntities as getBangleSupplies } from 'app/entities/bangle-supply/bangle-supply.reducer';
import { ICustomComponentSupply } from 'app/shared/model/custom-component-supply.model';
import { getEntities as getCustomComponentSupplies } from 'app/entities/custom-component-supply/custom-component-supply.reducer';
import { IOneStudySupply } from 'app/shared/model/one-study-supply.model';
import { getEntities as getOneStudySupplies } from 'app/entities/one-study-supply/one-study-supply.reducer';
import { IStrand } from 'app/shared/model/strand.model';
import { getEntities as getStrands } from 'app/entities/strand/strand.reducer';
import { IIntersticeAssembly } from 'app/shared/model/interstice-assembly.model';
import { getEntities as getIntersticeAssemblies } from 'app/entities/interstice-assembly/interstice-assembly.reducer';
import { getEntity, updateEntity, createEntity, reset } from './supply-position.reducer';
import { ISupplyPosition } from 'app/shared/model/supply-position.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const SupplyPositionUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const centralAssemblies = useAppSelector(state => state.centralAssembly.entities);
  const elementSupplies = useAppSelector(state => state.elementSupply.entities);
  const bangleSupplies = useAppSelector(state => state.bangleSupply.entities);
  const customComponentSupplies = useAppSelector(state => state.customComponentSupply.entities);
  const oneStudySupplies = useAppSelector(state => state.oneStudySupply.entities);
  const strands = useAppSelector(state => state.strand.entities);
  const intersticeAssemblies = useAppSelector(state => state.intersticeAssembly.entities);
  const supplyPositionEntity = useAppSelector(state => state.supplyPosition.entity);
  const loading = useAppSelector(state => state.supplyPosition.loading);
  const updating = useAppSelector(state => state.supplyPosition.updating);
  const updateSuccess = useAppSelector(state => state.supplyPosition.updateSuccess);
  const handleClose = () => {
    props.history.push('/supply-position');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getCentralAssemblies({}));
    dispatch(getElementSupplies({}));
    dispatch(getBangleSupplies({}));
    dispatch(getCustomComponentSupplies({}));
    dispatch(getOneStudySupplies({}));
    dispatch(getStrands({}));
    dispatch(getIntersticeAssemblies({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...supplyPositionEntity,
      ...values,
      elementSupply: elementSupplies.find(it => it.id.toString() === values.elementSupply.toString()),
      bangleSupply: bangleSupplies.find(it => it.id.toString() === values.bangleSupply.toString()),
      customComponentSupply: customComponentSupplies.find(it => it.id.toString() === values.customComponentSupply.toString()),
      oneStudySupply: oneStudySupplies.find(it => it.id.toString() === values.oneStudySupply.toString()),
      ownerStrand: strands.find(it => it.id.toString() === values.ownerStrand.toString()),
      ownerIntersticeAssembly: intersticeAssemblies.find(it => it.id.toString() === values.ownerIntersticeAssembly.toString()),
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
          ...supplyPositionEntity,
          elementSupply: supplyPositionEntity?.elementSupply?.id,
          bangleSupply: supplyPositionEntity?.bangleSupply?.id,
          customComponentSupply: supplyPositionEntity?.customComponentSupply?.id,
          oneStudySupply: supplyPositionEntity?.oneStudySupply?.id,
          ownerStrand: supplyPositionEntity?.ownerStrand?.id,
          ownerIntersticeAssembly: supplyPositionEntity?.ownerIntersticeAssembly?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="lappLiApp.supplyPosition.home.createOrEditLabel" data-cy="SupplyPositionCreateUpdateHeading">
            <Translate contentKey="lappLiApp.supplyPosition.home.createOrEditLabel">Create or edit a SupplyPosition</Translate>
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
                  id="supply-position-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('lappLiApp.supplyPosition.supplyApparitionsUsage')}
                id="supply-position-supplyApparitionsUsage"
                name="supplyApparitionsUsage"
                data-cy="supplyApparitionsUsage"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                id="supply-position-elementSupply"
                name="elementSupply"
                data-cy="elementSupply"
                label={translate('lappLiApp.supplyPosition.elementSupply')}
                type="select"
              >
                <option value="" key="0" />
                {elementSupplies
                  ? elementSupplies.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.designation}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="supply-position-bangleSupply"
                name="bangleSupply"
                data-cy="bangleSupply"
                label={translate('lappLiApp.supplyPosition.bangleSupply')}
                type="select"
              >
                <option value="" key="0" />
                {bangleSupplies
                  ? bangleSupplies.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.designation}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="supply-position-customComponentSupply"
                name="customComponentSupply"
                data-cy="customComponentSupply"
                label={translate('lappLiApp.supplyPosition.customComponentSupply')}
                type="select"
              >
                <option value="" key="0" />
                {customComponentSupplies
                  ? customComponentSupplies.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.designation}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="supply-position-oneStudySupply"
                name="oneStudySupply"
                data-cy="oneStudySupply"
                label={translate('lappLiApp.supplyPosition.oneStudySupply')}
                type="select"
              >
                <option value="" key="0" />
                {oneStudySupplies
                  ? oneStudySupplies.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.designation}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="supply-position-ownerStrand"
                name="ownerStrand"
                data-cy="ownerStrand"
                label={translate('lappLiApp.supplyPosition.ownerStrand')}
                type="select"
              >
                <option value="" key="0" />
                {strands
                  ? strands.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="supply-position-ownerIntersticeAssembly"
                name="ownerIntersticeAssembly"
                data-cy="ownerIntersticeAssembly"
                label={translate('lappLiApp.supplyPosition.ownerIntersticeAssembly')}
                type="select"
              >
                <option value="" key="0" />
                {intersticeAssemblies
                  ? intersticeAssemblies.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.productDesignation}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/supply-position" replace color="info">
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

export default SupplyPositionUpdate;
