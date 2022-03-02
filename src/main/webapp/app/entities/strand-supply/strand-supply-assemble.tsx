import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
  getEntityWithAutoAssemblyGeneration as getStrandSupplyWithAutoAssemblyGeneration,
  updateEntity as updateStrandSupplyEntity,
} from 'app/entities/strand-supply/strand-supply.reducer';
import { IStrandSupply } from 'app/shared/model/strand-supply.model';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getOut } from '../index-management/index-management-lib';
import { AssemblyMean } from 'app/shared/model/enumerations/assembly-mean.model';

export const StrandSupplyAssemble = (props: RouteComponentProps<{ strand_supply_id: string; study_id: string }>) => {
  const dispatch = useAppDispatch();

  const strandSupplyEntity = useAppSelector(state => state.strandSupply.entity);
  const updating = useAppSelector(state => state.strandSupply.updating);
  const updateSuccess = useAppSelector(state => state.strandSupply.updateSuccess);
  const assemblyMeanValues = Object.keys(AssemblyMean);
  const handleClose = () => {
    const path: string = getOut(props.match.url, 1);
    props.history.push(path);
  };

  useEffect(() => {
    dispatch(getStrandSupplyWithAutoAssemblyGeneration(props.match.params.strand_supply_id));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntities = values => {
    const updatedStrandSupply: IStrandSupply = {
      id: strandSupplyEntity.id,
      ...strandSupplyEntity,
      ...values,
    };

    dispatch(updateStrandSupplyEntity(updatedStrandSupply));
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
