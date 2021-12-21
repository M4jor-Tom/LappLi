import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IStrand } from 'app/shared/model/strand.model';
import { getEntities as getStrands } from 'app/entities/strand/strand.reducer';
import { IStudy } from 'app/shared/model/study.model';
import { getEntities as getStudies } from 'app/entities/study/study.reducer';
import { getEntity, updateEntity, createEntity, reset } from './strand-supply.reducer';
import { IStrandSupply } from 'app/shared/model/strand-supply.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { MarkingType } from 'app/shared/model/enumerations/marking-type.model';
import { getStrandSupplyUpdateComponentRedirectionUrl, getStudyValidateField, SupplyKind } from '../supply/index-management-lib';

export const StrandSupplyUpdate = (props: RouteComponentProps<{ strand_id: string | null; study_id: string | null; id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const redirectionUrl = getStrandSupplyUpdateComponentRedirectionUrl(props);

  const strands = useAppSelector(state => state.strand.entities);
  const studies = useAppSelector(state => state.study.entities);
  const strandSupplyEntity = useAppSelector(state => state.strandSupply.entity);
  const loading = useAppSelector(state => state.strandSupply.loading);
  const updating = useAppSelector(state => state.strandSupply.updating);
  const updateSuccess = useAppSelector(state => state.strandSupply.updateSuccess);
  const markingTypeValues = Object.keys(MarkingType);
  const handleClose = () => {
    props.history.push(redirectionUrl);
  };

  const studyValidateField = getStudyValidateField(props, studies);

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

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
      strand: strands.find(it => it.id.toString() === values.strand.toString()),
      study: studies.find(it => it.id.toString() === values.study.toString()),
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
          markingType: 'LIFTING',
          ...strandSupplyEntity,
          strand: strandSupplyEntity?.strand?.id,
          study: strandSupplyEntity?.study?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="lappLiApp.strandSupply.home.createOrEditLabel" data-cy="StrandSupplyCreateUpdateHeading">
            <Translate contentKey="lappLiApp.strandSupply.home.createOrEditLabel">Create or edit a StrandSupply</Translate>
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
                  id="strand-supply-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('lappLiApp.strandSupply.apparitions')}
                id="strand-supply-apparitions"
                name="apparitions"
                data-cy="apparitions"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('lappLiApp.strandSupply.markingType')}
                id="strand-supply-markingType"
                name="markingType"
                data-cy="markingType"
                type="select"
              >
                {markingTypeValues.map(markingType => (
                  <option value={markingType} key={markingType}>
                    {translate('lappLiApp.MarkingType' + markingType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('lappLiApp.strandSupply.description')}
                id="strand-supply-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                id="strand-supply-strand"
                name="strand"
                data-cy="strand"
                label={translate('lappLiApp.strandSupply.strand')}
                type="select"
                required
              >
                <option value="" key="0" />
                {strands
                  ? strands.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.designation}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              {studyValidateField}
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

export default StrandSupplyUpdate;
