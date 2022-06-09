import { IAbstractOperation } from './abstract-operation.model';
import { SheathingKind } from './enumerations/sheathing-kind.model';
import { IMaterial } from './material.model';

export interface IAbstractSheathing extends IAbstractOperation {
  sheathingKind?: SheathingKind;
  material?: IMaterial;
}

export function isAbstractSheathing(object: IAbstractOperation): object is IAbstractSheathing {
  return Object.prototype.hasOwnProperty.call(object, 'sheathingKind') && Object.prototype.hasOwnProperty.call(object, 'material');
}

export const defaultValue: Readonly<IAbstractSheathing> = {};
