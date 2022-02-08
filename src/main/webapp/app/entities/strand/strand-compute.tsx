import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IStrand } from 'app/shared/model/strand.model';
import { getEntity as getStrand } from 'app/entities/strand/strand.reducer';
import { IStudy } from 'app/shared/model/study.model';
import { getEntity as getStudy } from 'app/entities/study/study.reducer';
import { getEntity, updateEntity, createEntity, reset } from '../strand-supply/strand-supply.reducer';
import { IStrandSupply } from 'app/shared/model/strand-supply.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { MarkingType } from 'app/shared/model/enumerations/marking-type.model';
import { getOut, getStudyValidateField } from '../index-management/index-management-lib';
import { toNumber } from 'lodash';

export const StrandCompute = (props: RouteComponentProps<{ strand_id: string; study_id: string; id: string }>) => {
  const dispatch = useAppDispatch();

  const strandEntity = useAppSelector(state => state.strand.entity);
  const studyEntity = useAppSelector(state => state.study.entity);
  //  const strandSupplyEntity = useAppSelector(state => state.strandSupply.entity);
  const loading = useAppSelector(state => state.strandSupply.loading);
  const updating = useAppSelector(state => state.strandSupply.updating);
  const updateSuccess = useAppSelector(state => state.strandSupply.updateSuccess);
  const markingTypeValues = Object.keys(MarkingType);
  const handleClose = (saved: boolean = null) => {
    let path = '';

    if (saved) {
      path = getOut(props.match.url, 2);
    } else {
      path = getOut(props.match.url, 0) + '/supply';
    }

    props.history.push(path);
  };

  const handleCancel = () => handleClose(false);

  useEffect(() => {
    dispatch(reset());

    dispatch(getStrand(props.match.params.strand_id));
    dispatch(getStudy(props.match.params.study_id));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose(true);
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity: IStrandSupply = {
      study: studyEntity,
      strand: strandEntity,
      markingType: 'LIFTING',
      ...values,
    };

    //  const realStrandSubSupplyCount = strandEntity.suppliesCount / values.apparitions;
    const apparitions: number = toNumber(values.apparitions);
    const subSuppliesToAssembleCheck: number = toNumber(values.subSuppliesToAssembleCheck);
    const apparitionsIsCommonDivider: boolean = strandEntity.suppliesCountsCommonDividers.includes(apparitions);
    //  const givenStrandSubSupplyCount = toNumber(values.subSuppliesToAssembleCheck);

    if (!apparitionsIsCommonDivider) {
      alert(translate('lappLiApp.strandSupply.errors.apparitionsIsNotSubSupplyCountsCommonDivider'));
      //  alert(apparitions + " must be included in " + strandEntity.suppliesCountsCommonDividers.concat());
    } else if (apparitions * subSuppliesToAssembleCheck !== strandEntity.suppliesCount) {
      alert(translate('lappLiApp.strandSupply.errors.computedSuppliesCountInequalToReadSuppliesCount'));
      //  alert(apparitions + " * " + subSuppliesToAssembleCheck + " must be equal to " + strandEntity.suppliesCount);
    } else {
      dispatch(createEntity(entity));
    }
  };

  /*  const defaultValues = {
    markingType: 'LIFTING',
    ...strandSupplyEntity,
    strand: strandSupplyEntity?.strand?.id,
    study: strandSupplyEntity?.study?.id,
  };*/

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
            <ValidatedForm /* defaultValues={defaultValues}*/ onSubmit={saveEntity}>
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" onClick={handleCancel} replace color="info">
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
