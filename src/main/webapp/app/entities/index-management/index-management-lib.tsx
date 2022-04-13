import { SupplyKind } from 'app/shared/model/enumerations/supply-kind.model';
import { IStrandSupply } from 'app/shared/model/strand-supply.model';
import { IStrand } from 'app/shared/model/strand.model';
import { IStudy } from 'app/shared/model/study.model';
import React from 'react';
import { useState } from 'react';
import { translate, Translate, ValidatedField } from 'react-jhipster';
import { RouteComponentProps } from 'react-router-dom';
import { FormText } from 'reactstrap';

enum AssemblyKind {
  CENTRAL = 'central',
  CORE = 'core',
  INTERSTICE = 'interstice',
}

enum SupplyOwner {
  STRAND = 'strand',
  STUDY = 'study',
}

//  [OPERATION]

function getOutFromStudySupplyStrandTapeLaying(url: string, isNew: boolean): string {
  return getOutFromStudySupplyStrandComponent(url, isNew);
}

function getOutFromStudySupplyStrandScreen(url: string, isNew: boolean): string {
  return getOutFromStudySupplyStrandComponent(url, isNew);
}

function getOutFromStudySupplyStrandStripLaying(url: string, isNew: boolean): string {
  return getOutFromStudySupplyStrandComponent(url, isNew);
}

function getOutFromStudySupplyStrandSheathing(url: string, isNew: boolean): string {
  return getOutFromStudySupplyStrandComponent(url, isNew);
}

function getOutFromStudySupplyStrandContinuityWireLongitLaying(url: string, isNew: boolean): string {
  return getOutFromStudySupplyStrandComponent(url, isNew);
}

function getOutFromStudySupplyStrandAssemblyComponent(url: string, isNew: boolean): string {
  return getOutFromStudySupplyStrandComponent(url, isNew);
}

function getOutFromStudySupplyStrandSupplyComponent(url: string, isNew: boolean): string {
  return getOutFromStudySupplyStrandComponent(url, isNew);
}

function getOutFromStudySupplyStrandComponent(url: string, isNew: boolean): string {
  if (isNew != null && isNew) {
    return getOut(url, 1);
  }

  return getOut(url, 2);
}

function getOut(url: string, getOutCount: number): string {
  //  Remove 1 directory from url
  url = url.substring(0, url.lastIndexOf('/'));

  if (getOutCount > 0) {
    //  Remove getOutCount directories to url by recursion
    return getOut(url, getOutCount - 1);
  }

  //  Recursion leaf, get out normaly
  return url;
}

function isStudySupply(props: RouteComponentProps<{ study_id: string; id: string }>): string {
  const [ret] = useState(props.match.params && props.match.params.study_id);
  return ret;
}

function getStudySupplyRedirectionUrl(props: RouteComponentProps<{ study_id: string; id: string }>, supplyOwner: SupplyOwner): string {
  return isStudySupply(props)
    ? '/' + supplyOwner + '/' + props.match.params.study_id + '/study-supplies'
    : '/' + SupplyKind.STRAND + '-supply';
}

function isStrandSupply(props: RouteComponentProps<{ strand_supply_id: string; id: string }>): string {
  const [ret] = useState(props.match.params && props.match.params.strand_supply_id);
  return ret;
}

function getStrandSupplyRedirectionUrl(
  props: RouteComponentProps<{ strand_supply_id: string; id: string }>,
  supplyKind: SupplyKind
): string {
  return isStrandSupply(props)
    ? '/' + SupplyOwner.STRAND + '/' + props.match.params.strand_supply_id + '/supply'
    : '/' + supplyKind + '-supply';
}

function getSupplyOwner(props: RouteComponentProps<{ study_id: string | null; strand_id: string | null; id: string }>): SupplyOwner {
  return props.match.params.study_id == null ? SupplyOwner.STRAND : SupplyOwner.STUDY;
}

function getStrandSupplyUpdateComponentRedirectionUrl(
  props: RouteComponentProps<{ study_id: string | null; strand_id: string | null; id: string }>
): string {
  return getStudySupplyRedirectionUrl(props, getSupplyOwner(props));
}

function getStudyValidateField(props: RouteComponentProps<{ study_id: string | null; id: string }>, studies: readonly IStudy[]) {
  return isStudySupply(props) ? (
    useState(!props.match.params || !props.match.params.id)[0] ? (
      <ValidatedField id="strand-supply-study" name="study" data-cy="study" value={props.match.params.study_id} type="hidden" required />
    ) : (
      ''
    )
  ) : (
    <ValidatedField
      id="strand-supply-study"
      name="study"
      data-cy="study"
      label={translate('lappLiApp.strandSupply.study')}
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
  );
}

function getStrandSupplyValidatedField(
  props: RouteComponentProps<{ strand_supply_id: string | null; id: string }>,
  strandSupplies: readonly IStrandSupply[],
  fieldId: string
) {
  return isStrandSupply(props) ? (
    useState(!props.match.params || !props.match.params.id)[0] ? (
      <ValidatedField
        id={fieldId}
        name="ownerStrand"
        data-cy="ownerStrand"
        type="hidden"
        value={props.match.params.strand_supply_id}
        required
      />
    ) : (
      ''
    )
  ) : (
    <ValidatedField
      id="core-assembly-ownerStrandSupply"
      name="ownerStrandSupply"
      data-cy="ownerStrandSupply"
      label={translate('lappLiApp.operation.ownerStrandSupply')}
      type="select"
      required
    >
      <option value="" key="0" />
      {strandSupplies
        ? strandSupplies.map(otherEntity => (
            <option value={otherEntity.id} key={otherEntity.id}>
              {otherEntity.designation}
            </option>
          ))
        : null}
    </ValidatedField>
  );
}
/*
function getSupplyStrandValidatedField(
  props: RouteComponentProps<{ strand_id: string | null; id: string }>,
  strands: readonly IStrand[],
  supplyKind: SupplyKind
) {
  return getStrandSupplyValidatedField(props, strands, supplyKind + '-supply-strand');
}*/

function getAssemblyStrandSupplyValidatedField(
  props: RouteComponentProps<{ strand_supply_id: string | null; id: string }>,
  strandSupplies: readonly IStrandSupply[],
  assemblyKind: AssemblyKind
) {
  return getStrandSupplyValidatedField(props, strandSupplies, assemblyKind + '-assembly-strand');
}

export {
  AssemblyKind,
  getOutFromStudySupplyStrandTapeLaying,
  getOutFromStudySupplyStrandScreen,
  getOutFromStudySupplyStrandStripLaying,
  getOutFromStudySupplyStrandSheathing,
  getOutFromStudySupplyStrandContinuityWireLongitLaying,
  getOutFromStudySupplyStrandAssemblyComponent,
  getOutFromStudySupplyStrandSupplyComponent,
  getStrandSupplyValidatedField,
  getAssemblyStrandSupplyValidatedField,
  getOut,
  isStrandSupply,
  getStrandSupplyRedirectionUrl,
  getStrandSupplyUpdateComponentRedirectionUrl,
  getStudyValidateField,
};
