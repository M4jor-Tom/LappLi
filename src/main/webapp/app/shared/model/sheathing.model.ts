import { IMaterial } from 'app/shared/model/material.model';
import { SheathingKind } from 'app/shared/model/enumerations/sheathing-kind.model';
import { IAbstractOperation } from './abstract-operation.model';

export interface ISheathing extends IAbstractOperation {
  milimeterThickness?: number;
  sheathingKind?: SheathingKind;
  material?: IMaterial;
}

export function isSheathing(object: IAbstractOperation): object is ISheathing {
  return (
    Object.prototype.hasOwnProperty.call(object, 'milimeterThickness') &&
    Object.prototype.hasOwnProperty.call(object, 'sheathingKind') &&
    Object.prototype.hasOwnProperty.call(object, 'material')
  );
}

export const defaultValue: Readonly<ISheathing> = {};
