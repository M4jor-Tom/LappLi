import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { ICentralAssembly } from 'app/shared/model/central-assembly.model';
import { getEntities as getCentralAssemblies } from 'app/entities/central-assembly/central-assembly.reducer';
import { getEntity, updateEntity, createEntity, reset } from './strand.reducer';
import { IStrand } from 'app/shared/model/strand.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getOut } from '../index-management/index-management-lib';

export const StrandUpdate = (props: RouteComponentProps<{ study_id: string; id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const centralAssemblies = useAppSelector(state => state.centralAssembly.entities);
  const strandEntity = useAppSelector(state => state.strand.entity);
  const loading = useAppSelector(state => state.strand.loading);
  const updating = useAppSelector(state => state.strand.updating);
  const updateSuccess = useAppSelector(state => state.strand.updateSuccess);

  const handleClose =
    props.match.params.study_id == null
      ? () => {
          props.history.push('/strand');
        }
      : () => {
          props.history.push('/study/' + props.match.params.study_id + '/study-supplies/new');
        };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getCentralAssemblies({}));
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to={getOut(props.match.url, 0)} replace color="info">
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
