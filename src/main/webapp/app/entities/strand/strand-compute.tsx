import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IStrand } from 'app/shared/model/strand.model';
import { getEntities as getStrands } from 'app/entities/strand/strand.reducer';
import { IStudy } from 'app/shared/model/study.model';
import { getEntities as getStudies } from 'app/entities/study/study.reducer';
import { getEntity, updateEntity, createEntity, reset } from '../strand-supply/strand-supply.reducer';
import { IStrandSupply } from 'app/shared/model/strand-supply.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { MarkingType } from 'app/shared/model/enumerations/marking-type.model';
import { getOut, getStudyValidateField } from '../index-management/index-management-lib';

export const StrandCompute = (props: RouteComponentProps<{ strand_id: string | null; study_id: string | null; id: string }>) => {
  const dispatch = useAppDispatch();

  const strands = useAppSelector(state => state.strand.entities);
  const studies = useAppSelector(state => state.study.entities);
  const strandSupplyEntity = useAppSelector(state => state.strandSupply.entity);
  const loading = useAppSelector(state => state.strandSupply.loading);
  const updating = useAppSelector(state => state.strandSupply.updating);
  const updateSuccess = useAppSelector(state => state.strandSupply.updateSuccess);
  const markingTypeValues = Object.keys(MarkingType);
  const handleClose = () => {
    props.history.push(getOut(props.match.url, 0) + '/supply');
  };

  useEffect(() => {
    dispatch(reset());

    dispatch(getStrands({}));
    dispatch(getStudies({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...strandSupplyEntity,
      ...values,
      strand: strands.find(it => it.id.toString() === props.match.params.strand_id), //  values.strand.toString()),
      study: studies.find(it => it.id.toString() === props.match.params.study_id), // values.study.toString()),
    };

    dispatch(createEntity(entity));
  };

  const defaultValues = {
    markingType: 'LIFTING',
    ...strandSupplyEntity,
    strand: strandSupplyEntity?.strand?.id,
    study: strandSupplyEntity?.study?.id,
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="lappLiApp.strandSupply.home.createLabel" data-cy="StrandSupplyCreateHeading">
            <Translate contentKey="lappLiApp.strandSupply.home.createLabel">Create a StrandSupply</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues} onSubmit={saveEntity}>
              <ValidatedField
                label={translate('lappLiApp.strandSupply.subSuppliesToAssemble')}
                id="sub-supplies-to-assemble"
                name="subSuppliesToAssembleCheck"
                type="number"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('lappLiApp.strandSupply.strandsCount')}
                id="strand-supply-apparitions"
                name="apparitions"
                data-cy="apparitions"
                type="number"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
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
          )}
        </Col>
      </Row>
    </div>
  );
};

export default StrandCompute;
