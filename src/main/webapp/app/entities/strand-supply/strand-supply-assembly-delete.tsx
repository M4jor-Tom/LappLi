import React, { useEffect, useState } from 'react';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity as getStrandSupplyEntity, updateEntity as updateStrandSupplyEntity } from '../strand-supply/strand-supply.reducer';
import { deleteEntity as deleteCentralAssemblyEntity } from '../central-assembly/central-assembly.reducer';
import { deleteEntity as deleteCoreAssemblyEntity } from '../core-assembly/core-assembly.reducer';
import { deleteEntity as deleteIntersticeAssemblyEntity } from '../interstice-assembly/interstice-assembly.reducer';
import { getOut } from '../index-management/index-management-lib';
import { IStrandSupply } from 'app/shared/model/strand-supply.model';

export const StrandSupplyAssemblyDeleteDialog = (props: RouteComponentProps<{ strand_supply_id: string }>) => {
  const [loadModal, setLoadModal] = useState(false);
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getStrandSupplyEntity(props.match.params.strand_supply_id));
    setLoadModal(true);
  }, []);

  const strandSupplyEntity: IStrandSupply = useAppSelector(state => state.strandSupply.entity);
  const updateSuccess = useAppSelector(state => state.coreAssembly.updateSuccess);

  const redirectionUrl = getOut(props.match.url, 1);

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
    /*  const strandSupplyEntityWithoutAssemblies: IStrandSupply = {
      ...strandSupplyEntity,
      centralAssembly: null,
      coreAssemblies: [],
      intersticeAssemblies: []
    }
    dispatch(
      updateStrandSupplyEntity(
        strandSupplyEntityWithoutAssemblies
      )
    );

    dispatch(deleteCentralAssemblyEntity(strandSupplyEntity.centralAssembly.id));
    strandSupplyEntity.coreAssemblies.forEach((coreAssembly) => {
        dispatch(deleteCoreAssemblyEntity(coreAssembly.id));
    });
    strandSupplyEntity.intersticeAssemblies.forEach((intersticeAssembly) => {
        dispatch(deleteIntersticeAssemblyEntity(intersticeAssembly.id));
    });*/
  };

  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose} data-cy="assemblyDeleteDialogHeading">
        <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
      </ModalHeader>
      <ModalBody id="lappLiApp.assembly.delete.question">
        <Translate contentKey="lappLiApp.assembly.delete.question">Are you sure you want to delete those assemblies ?</Translate>
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp;
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
        <Button id="jhi-confirm-delete-assembly" data-cy="entityConfirmDeleteButton" color="danger" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp;
          <Translate contentKey="entity.action.delete">Delete</Translate>
        </Button>
      </ModalFooter>
    </Modal>
  );
};

export default StrandSupplyAssemblyDeleteDialog;
