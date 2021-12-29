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

enum SupplyKind {
  BANGLE = 'bangle',
  CUSTOM_COMPONENT = 'custom-component',
  ELEMENT = 'element',
  ONE_STUDY = 'one-study',
  STRAND = 'strand',
}

enum SupplyOwner {
  STRAND = 'strand',
  STUDY = 'study',
}

function getOutFromStudySupplyStrandAssemblyComponent(url: string, isNew: boolean): string {
  if (isNew != null && isNew) {
    return getOut(url, 1);
  }

  return getOut(url, 2);
}

function getOutFromStudySupplyStrandSupplyComponent(url: string, isNew: boolean): string {
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

function isStrandSupply(props: RouteComponentProps<{ strand_id: string; id: string }>): string {
  const [ret] = useState(props.match.params && props.match.params.strand_id);
  return ret;
}

function getStrandSupplyRedirectionUrl(props: RouteComponentProps<{ strand_id: string; id: string }>, supplyKind: SupplyKind): string {
  return isStrandSupply(props) ? '/' + SupplyOwner.STRAND + '/' + props.match.params.strand_id + '/supply' : '/' + supplyKind + '-supply';
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
    <>
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
      <FormText>
        <Translate contentKey="entity.validation.required">This field is required.</Translate>
      </FormText>
    </>
  );
}

function getStrandValidatedField(
  props: RouteComponentProps<{ strand_id: string | null; id: string }>,
  strands: readonly IStrand[],
  fieldId: string
) {
  return isStrandSupply(props) ? (
    useState(!props.match.params || !props.match.params.id)[0] ? (
      <ValidatedField id={fieldId} name="strand" data-cy="strand" type="hidden" value={props.match.params.strand_id} required />
    ) : (
      ''
    )
  ) : (
    <>
      <ValidatedField id={fieldId} name="strand" data-cy="strand" label={translate('lappLiApp.supply.strand')} type="select" required>
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
    </>
  );
}

function getSupplyStrandValidatedField(
  props: RouteComponentProps<{ strand_id: string | null; id: string }>,
  strands: readonly IStrand[],
  supplyKind: SupplyKind
) {
  return getStrandValidatedField(props, strands, supplyKind + '-supply-strand');
}

function getAssemblyStrandValidatedField(
  props: RouteComponentProps<{ strand_id: string | null; id: string }>,
  strands: readonly IStrand[],
  assemblyKind: AssemblyKind
) {
  return getStrandValidatedField(props, strands, assemblyKind + '-assmelby-strand');
}

export {
  SupplyKind,
  getOutFromStudySupplyStrandAssemblyComponent,
  getOutFromStudySupplyStrandSupplyComponent,
  getSupplyStrandValidatedField,
  getAssemblyStrandValidatedField,
  getOut,
  isStrandSupply,
  getStrandSupplyRedirectionUrl,
  getStrandSupplyUpdateComponentRedirectionUrl,
  getStudyValidateField,
};
