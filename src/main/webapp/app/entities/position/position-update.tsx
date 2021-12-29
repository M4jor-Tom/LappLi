import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IElementSupply } from 'app/shared/model/element-supply.model';
import { getEntities as getElementSupplies } from 'app/entities/element-supply/element-supply.reducer';
import { IBangleSupply } from 'app/shared/model/bangle-supply.model';
import { getEntities as getBangleSupplies } from 'app/entities/bangle-supply/bangle-supply.reducer';
import { ICustomComponentSupply } from 'app/shared/model/custom-component-supply.model';
import { getEntities as getCustomComponentSupplies } from 'app/entities/custom-component-supply/custom-component-supply.reducer';
import { IOneStudySupply } from 'app/shared/model/one-study-supply.model';
import { getEntities as getOneStudySupplies } from 'app/entities/one-study-supply/one-study-supply.reducer';
import { ICentralAssembly } from 'app/shared/model/central-assembly.model';
import { getEntities as getCentralAssemblies } from 'app/entities/central-assembly/central-assembly.reducer';
import { ICoreAssembly } from 'app/shared/model/core-assembly.model';
import { getEntities as getCoreAssemblies } from 'app/entities/core-assembly/core-assembly.reducer';
import { IIntersticeAssembly } from 'app/shared/model/interstice-assembly.model';
import { getEntities as getIntersticeAssemblies } from 'app/entities/interstice-assembly/interstice-assembly.reducer';
import { getEntity, updateEntity, createEntity, reset } from './position.reducer';
import { IPosition } from 'app/shared/model/position.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PositionUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const elementSupplies = useAppSelector(state => state.elementSupply.entities);
  const bangleSupplies = useAppSelector(state => state.bangleSupply.entities);
  const customComponentSupplies = useAppSelector(state => state.customComponentSupply.entities);
  const oneStudySupplies = useAppSelector(state => state.oneStudySupply.entities);
  const centralAssemblies = useAppSelector(state => state.centralAssembly.entities);
  const coreAssemblies = useAppSelector(state => state.coreAssembly.entities);
  const intersticeAssemblies = useAppSelector(state => state.intersticeAssembly.entities);
  const positionEntity = useAppSelector(state => state.position.entity);
  const loading = useAppSelector(state => state.position.loading);
  const updating = useAppSelector(state => state.position.updating);
  const updateSuccess = useAppSelector(state => state.position.updateSuccess);
  const handleClose = () => {
    props.history.push('/position');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getElementSupplies({}));
    dispatch(getBangleSupplies({}));
    dispatch(getCustomComponentSupplies({}));
    dispatch(getOneStudySupplies({}));
    dispatch(getCentralAssemblies({}));
    dispatch(getCoreAssemblies({}));
    dispatch(getIntersticeAssemblies({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...positionEntity,
      ...values,
      elementSupply: elementSupplies.find(it => it.id.toString() === values.elementSupply.toString()),
      bangleSupply: bangleSupplies.find(it => it.id.toString() === values.bangleSupply.toString()),
      customComponentSupply: customComponentSupplies.find(it => it.id.toString() === values.customComponentSupply.toString()),
      oneStudySupply: oneStudySupplies.find(it => it.id.toString() === values.oneStudySupply.toString()),
      ownerCentralAssembly: centralAssemblies.find(it => it.id.toString() === values.ownerCentralAssembly.toString()),
      ownerCoreAssembly: coreAssemblies.find(it => it.id.toString() === values.ownerCoreAssembly.toString()),
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
          ...positionEntity,
          elementSupply: positionEntity?.elementSupply?.id,
          bangleSupply: positionEntity?.bangleSupply?.id,
          customComponentSupply: positionEntity?.customComponentSupply?.id,
          oneStudySupply: positionEntity?.oneStudySupply?.id,
          ownerCentralAssembly: positionEntity?.ownerCentralAssembly?.id,
          ownerCoreAssembly: positionEntity?.ownerCoreAssembly?.id,
          ownerIntersticeAssembly: positionEntity?.ownerIntersticeAssembly?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="lappLiApp.position.home.createOrEditLabel" data-cy="PositionCreateUpdateHeading">
            <Translate contentKey="lappLiApp.position.home.createOrEditLabel">Create or edit a Position</Translate>
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
                  id="position-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('lappLiApp.position.value')}
                id="position-value"
                name="value"
                data-cy="value"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                id="position-elementSupply"
                name="elementSupply"
                data-cy="elementSupply"
                label={translate('lappLiApp.position.elementSupply')}
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
                id="position-bangleSupply"
                name="bangleSupply"
                data-cy="bangleSupply"
                label={translate('lappLiApp.position.bangleSupply')}
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
                id="position-customComponentSupply"
                name="customComponentSupply"
                data-cy="customComponentSupply"
                label={translate('lappLiApp.position.customComponentSupply')}
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
                id="position-oneStudySupply"
                name="oneStudySupply"
                data-cy="oneStudySupply"
                label={translate('lappLiApp.position.oneStudySupply')}
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
                id="position-ownerCentralAssembly"
                name="ownerCentralAssembly"
                data-cy="ownerCentralAssembly"
                label={translate('lappLiApp.position.ownerCentralAssembly')}
                type="select"
              >
                <option value="" key="0" />
                {centralAssemblies
                  ? centralAssemblies.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.designation}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="position-ownerCoreAssembly"
                name="ownerCoreAssembly"
                data-cy="ownerCoreAssembly"
                label={translate('lappLiApp.position.ownerCoreAssembly')}
                type="select"
              >
                <option value="" key="0" />
                {coreAssemblies
                  ? coreAssemblies.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.designation}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="position-ownerIntersticeAssembly"
                name="ownerIntersticeAssembly"
                data-cy="ownerIntersticeAssembly"
                label={translate('lappLiApp.position.ownerIntersticeAssembly')}
                type="select"
              >
                <option value="" key="0" />
                {intersticeAssemblies
                  ? intersticeAssemblies.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.designation}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/position" replace color="info">
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

export default PositionUpdate;
