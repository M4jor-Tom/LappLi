import React, { useEffect, useState } from 'react';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity, deleteEntity } from './supply-position.reducer';
import { getOut } from '../index-management/index-management-lib';

export const SupplyPositionDeleteDialog = (props: RouteComponentProps<{ id: string; strand_id: string; study_id: string }>) => {
  const [loadModal, setLoadModal] = useState(false);
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
    setLoadModal(true);
  }, []);

  const supplyPositionEntity = useAppSelector(state => state.supplyPosition.entity);
  const updateSuccess = useAppSelector(state => state.supplyPosition.updateSuccess);

  const comesFromStudyInterface = props.match.params.strand_id != null && props.match.params.study_id != null;

  const redirectionUrl = comesFromStudyInterface ? getOut(props.match.url, 1) : '/supply-positions';

  const handleClose = () => {
    props.history.push(redirectionUrl);
  };

  useEffect(() => {
    if (updateSuccess && loadModal) {
      handleClose();
      setLoadModal(false);
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    dispatch(deleteEntity(supplyPositionEntity.id));
  };

  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose} data-cy="supplyPositionDeleteDialogHeading">
        <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
      </ModalHeader>
      <ModalBody id="lappLiApp.supplyPosition.delete.question">
        <Translate contentKey="lappLiApp.supplyPosition.delete.question" interpolate={{ id: supplyPositionEntity.id }}>
          Are you sure you want to delete this SupplyPosition?
        </Translate>
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp;
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
        <Button id="jhi-confirm-delete-supplyPosition" data-cy="entityConfirmDeleteButton" color="danger" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp;
          <Translate contentKey="entity.action.delete">Delete</Translate>
        </Button>
      </ModalFooter>
    </Modal>
  );
};

export default SupplyPositionDeleteDialog;
