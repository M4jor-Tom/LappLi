import { IStrand } from 'app/shared/model/strand.model';
import React from 'react';
import { useState } from 'react';
import { translate, Translate, ValidatedField } from 'react-jhipster';
import { RouteComponentProps } from 'react-router-dom';
import { FormText } from 'reactstrap';

enum SupplyKind {
  BANGLE = 'bangle',
  CUSTOM_COMPONENT = 'custom-component',
  ELEMENT = 'element',
  ONE_STUDY_SUPPLY = 'one-study-supply',
}

function isStrandSupply(props: RouteComponentProps<{ strand_id: string; id: string }>): string {
  const [ret] = useState(props.match.params && props.match.params.strand_id);
  return ret;
}

function getRedirectionUrl(props: RouteComponentProps<{ strand_id: string; id: string }>, supplyKind: SupplyKind): string {
  return isStrandSupply(props) ? '/strand/' + props.match.params.strand_id + '/supply' : '/' + supplyKind + '-supply';
}

function getStrandValidateField(
  props: RouteComponentProps<{ strand_id: string; id: string }>,
  strands: readonly IStrand[],
  supplyKind: SupplyKind
) {
  return isStrandSupply(props) ? (
    useState(!props.match.params || !props.match.params.id)[0] ? (
      <ValidatedField
        id={supplyKind + '-supply-strand'}
        name="strand"
        data-cy="strand"
        type="hidden"
        value={props.match.params.strand_id}
        required
      />
    ) : (
      ''
    )
  ) : (
    <>
      <ValidatedField
        id={supplyKind + '-supply-strand'}
        name="strand"
        data-cy="strand"
        label={translate('lappLiApp.supply.strand')}
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
    </>
  );
}

export { SupplyKind, isStrandSupply, getRedirectionUrl, getStrandValidateField };
