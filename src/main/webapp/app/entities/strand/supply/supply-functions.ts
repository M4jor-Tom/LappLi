import { IAbstractSupply } from '../../../../app/shared/model/abstract-supply.model';

function getStrandId(strandId: string | null, abstractSupply: IAbstractSupply | null): number {
  if (strandId != null) {
    return +strandId;
  } else if (abstractSupply != null && abstractSupply.strand != null) {
    return abstractSupply.strand.id;
  }

  return -1;
}

export { getStrandId };
