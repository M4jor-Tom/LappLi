import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { ICentralAssembly } from 'app/shared/model/central-assembly.model';
import { getEntities as getCentralAssemblies } from 'app/entities/central-assembly/central-assembly.reducer';
import { IStudy } from 'app/shared/model/study.model';
import { getEntity, updateEntity, createEntity, reset } from './strand.reducer';
import { IStrand } from 'app/shared/model/strand.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getOut } from '../index-management/index-management-lib';
import { getEntities as getStudies } from '../study/study.reducer';

export const StrandUpdate = (props: RouteComponentProps<{ study_id: string; id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const centralAssemblies = useAppSelector(state => state.centralAssembly.entities);
  const studies = useAppSelector(state => state.study.entities);
  const strandEntity = useAppSelector(state => state.strand.entity);
  const loading = useAppSelector(state => state.strand.loading);
  const updating = useAppSelector(state => state.strand.updating);
  const updateSuccess = useAppSelector(state => state.strand.updateSuccess);

  const getOutCount = props.match.params.study_id ? 2 : 1;

  const getOutUrl = getOut(props.match.url, getOutCount);

  const handleClose = () => {
    props.history.push(getOutUrl);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getCentralAssemblies({}));
    dispatch(getStudies({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...strandEntity,
      ...values,
      futureStudy: studies.find(it => it.id.toString() === values.futureStudy.toString()),
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
          ...strandEntity,
          futureStudy: strandEntity?.futureStudy?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="lappLiApp.strand.home.createOrEditLabel" data-cy="StrandCreateUpdateHeading">
            <Translate contentKey="lappLiApp.strand.home.createOrEditLabel">Create or edit a Strand</Translate>
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
                  id="strand-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                id="strand-futureStudy"
                name="futureStudy"
                data-cy="futureStudy"
                label={translate('lappLiApp.strand.futureStudy')}
                type="select"
                required
              >
                <option value="" key="0" />
                {studies
                  ? studies.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.number}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to={getOutUrl} replace color="info">
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

export default StrandUpdate;
