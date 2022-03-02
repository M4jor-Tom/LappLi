import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IStrand } from 'app/shared/model/strand.model';
import {
  getEntityWithAutoAssemblyGeneration as getStrandSupplyWithAutoAssemblyGeneration,
  updateEntity as updateStrandSupplyEntity,
} from 'app/entities/strand-supply/strand-supply.reducer';
import { IStudy } from 'app/shared/model/study.model';
import { getEntity as getStudy } from 'app/entities/study/study.reducer';
import { createEntity as createCentralAssemblyEntity, reset as resetCentralAssemblyEntity } from '../strand-supply/strand-supply.reducer';
import { createEntity as createCoreAssemblyEntity, reset as resetCoreAssembly } from '../strand-supply/strand-supply.reducer';
import { createEntity as createIntersticeAssemblyEntity, reset as resetIntersticeAssembly } from '../strand-supply/strand-supply.reducer';
import { IStrandSupply } from 'app/shared/model/strand-supply.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { MarkingType } from 'app/shared/model/enumerations/marking-type.model';
import { getOut, getStudyValidateField } from '../index-management/index-management-lib';
import { toNumber } from 'lodash';
import { ICoreAssembly } from 'app/shared/model/core-assembly.model';
import { ICentralAssembly } from 'app/shared/model/central-assembly.model';
import { IIntersticeAssembly } from 'app/shared/model/interstice-assembly.model';
import { AssemblyMean } from 'app/shared/model/enumerations/assembly-mean.model';

export const StrandSupplyAssemble = (props: RouteComponentProps<{ strand_supply_id: string; study_id: string }>) => {
  const dispatch = useAppDispatch();

  const strandSupplyEntity = useAppSelector(state => state.strandSupply.entity);
  const studyEntity = useAppSelector(state => state.study.entity);
  const updating = useAppSelector(state => state.strandSupply.updating);
  const updateSuccess = useAppSelector(state => state.strandSupply.updateSuccess);
  const assemblyMeanValues = Object.keys(AssemblyMean);
  const handleClose = () => {
    const path: string = getOut(props.match.url, 1);
    props.history.push(path);
  };

  useEffect(() => {
    dispatch(resetCentralAssemblyEntity());
    dispatch(resetCoreAssembly());
    dispatch(resetIntersticeAssembly());

    dispatch(getStrandSupplyWithAutoAssemblyGeneration(props.match.params.strand_supply_id));
    dispatch(getStudy(props.match.params.study_id));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const setCentralAssembly = false;
  const coreLayersCount = 0;
  const intersticeLayersCount = 0;

  const saveEntities = values => {
    const updatedStrandSupply: IStrandSupply = {
      id: strandSupplyEntity.id,
      diameterAssemblyStep: values.diameterAssemblyStep,
      assemblyMean: values,
    };

    dispatch(updateStrandSupplyEntity(updatedStrandSupply));

    if (setCentralAssembly) {
      const centralAssemblyEntity: ICentralAssembly = {
        ownerStrandSupply: strandSupplyEntity,
        supplyPosition: {
          supplyApparitionsUsage: 1,
          supply: {},
        },
      };

      dispatch(createCentralAssemblyEntity(centralAssemblyEntity));
    }

    for (let i: number; i < coreLayersCount; i++) {
      const coreAssemblyEntity: ICoreAssembly = {
        ownerStrandSupply: strandSupplyEntity,
        assemblyLayer: NaN,
        componentsCount: NaN,
      };

      dispatch(createCoreAssemblyEntity(coreAssemblyEntity));
    }

    for (let i: number; i < intersticeLayersCount; i++) {
      const intersticeAssemblyEntity: IIntersticeAssembly = {
        ownerStrandSupply: strandSupplyEntity,
        assemblyLayer: NaN,
        intersticeLayer: NaN,
        supplyPosition: {
          supplyApparitionsUsage: NaN,
          supply: {},
        },
      };

      dispatch(createIntersticeAssemblyEntity(intersticeAssemblyEntity));
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="lappLiApp.strandSupply.home.createLabel" data-cy="StrandSupplyCreateHeading">
            <Translate contentKey="lappLiApp.assembly.home.createLabel">Create a Assembly</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          <ValidatedForm onSubmit={saveEntities}>
            <ValidatedField
              name="diameterAssemblyStep"
              required
              id="diameter-assembly-step"
              label={translate('lappLiApp.strandSupply.diameterAssemblyStep')}
              validate={{ required: true }}
            />
            <ValidatedField
              label={translate('lappLiApp.strandSupply.assemblyMean')}
              id="strand-assembly-mean"
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
              label={translate('lappLiApp.strandSupply.forceCentralUtilityComponent')}
              id="strand-supply-forceCentralUtilityComponent"
              name="forceCentralUtilityComponent"
              data-cy="forceCentralUtilityComponent"
              check
              type="checkbox"
            />
            <br />
            <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" onClick={handleClose} replace color="info">
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
        </Col>
      </Row>
    </div>
  );
};

export default StrandSupplyAssemble;
