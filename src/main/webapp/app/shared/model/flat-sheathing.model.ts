import { IMaterial } from 'app/shared/model/material.model';
import { IStrandSupply } from 'app/shared/model/strand-supply.model';
import { IAbstractOperation } from './abstract-operation.model';
import { SheathingKind } from './enumerations/sheathing-kind.model';

export interface IFlatSheathing extends IAbstractOperation {
  id?: number;
  sheathingKind?: SheathingKind;
  milimeterWidth?: number;
  milimeterHeight?: number;
  material?: IMaterial;
}

export function isFlatSheathing(object: IAbstractOperation): object is IFlatSheathing {
  return (
    Object.prototype.hasOwnProperty.call(object, 'sheathingKind') &&
    Object.prototype.hasOwnProperty.call(object, 'milimeterHeight') &&
    Object.prototype.hasOwnProperty.call(object, 'milimeterHeight') &&
    Object.prototype.hasOwnProperty.call(object, 'material')
  );
}

export const defaultValue: Readonly<IFlatSheathing> = {};
