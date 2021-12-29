import React, { useEffect, useState } from 'react';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity, deleteEntity } from './custom-component-supply.reducer';
import {
  getOutFromStudySupplyStrandSupplyComponent,
  getStrandSupplyRedirectionUrl,
  SupplyKind,
} from '../index-management/index-management-lib';

export const CustomComponentSupplyDeleteDialog = (props: RouteComponentProps<{ strand_id: string; id: string }>) => {
  const [loadModal, setLoadModal] = useState(false);
  const dispatch = useAppDispatch();

  const redirectionUrl = getOutFromStudySupplyStrandSupplyComponent(props.match.url, null);

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
    setLoadModal(true);
  }, []);

  const customComponentSupplyEntity = useAppSelector(state => state.customComponentSupply.entity);
  const updateSuccess = useAppSelector(state => state.customComponentSupply.updateSuccess);

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
    dispatch(deleteEntity(customComponentSupplyEntity.id));
  };

  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose} data-cy="customComponentSupplyDeleteDialogHeading">
        <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
      </ModalHeader>
      <ModalBody id="lappLiApp.customComponentSupply.delete.question">
        <Translate contentKey="lappLiApp.customComponentSupply.delete.question" interpolate={{ id: customComponentSupplyEntity.id }}>
          Are you sure you want to delete this CustomComponentSupply?
        </Translate>
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp;
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
        <Button id="jhi-confirm-delete-customComponentSupply" data-cy="entityConfirmDeleteButton" color="danger" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp;
          <Translate contentKey="entity.action.delete">Delete</Translate>
        </Button>
      </ModalFooter>
    </Modal>
  );
};

export default CustomComponentSupplyDeleteDialog;
