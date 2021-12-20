import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IUserData } from 'app/shared/model/user-data.model';
import { getEntities as getUserData } from 'app/entities/user-data/user-data.reducer';
import { getEntity, updateEntity, createEntity, reset } from './study.reducer';
import { IStudy } from 'app/shared/model/study.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const StudyUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const userData = useAppSelector(state => state.userData.entities);
  const studyEntity = useAppSelector(state => state.study.entity);
  const loading = useAppSelector(state => state.study.loading);
  const updating = useAppSelector(state => state.study.updating);
  const updateSuccess = useAppSelector(state => state.study.updateSuccess);
  const handleClose = () => {
    props.history.push('/study');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getUserData({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.creationInstant = convertDateTimeToServer(values.creationInstant);

    const entity = {
      ...studyEntity,
      ...values,
      author: userData.find(it => it.id.toString() === values.author.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          creationInstant: displayDefaultDateTime(),
        }
      : {
          ...studyEntity,
          creationInstant: convertDateTimeFromServer(studyEntity.creationInstant),
          author: studyEntity?.author?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="lappLiApp.study.home.createOrEditLabel" data-cy="StudyCreateUpdateHeading">
            <Translate contentKey="lappLiApp.study.home.createOrEditLabel">Create or edit a Study</Translate>
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
                  id="study-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('lappLiApp.study.number')} id="study-number" name="number" data-cy="number" type="text" />
              <ValidatedField
                label={translate('lappLiApp.study.creationInstant')}
                id="study-creationInstant"
                name="creationInstant"
                data-cy="creationInstant"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="study-author"
                name="author"
                data-cy="author"
                label={translate('lappLiApp.study.author')}
                type="select"
                required
              >
                <option value="" key="0" />
                {userData
                  ? userData.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.user.login}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/study" replace color="info">
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

export default StudyUpdate;
