import { IAbstractOperation } from './abstract-operation.model';
import { IAbstractSheathing, isAbstractSheathing } from './abstract-sheathing.model';

export interface ISheathing extends IAbstractSheathing {
  milimeterThickness?: number;
}

export function isSheathing(object: IAbstractOperation): object is ISheathing {
  return isAbstractSheathing(object) && Object.prototype.hasOwnProperty.call(object, 'milimeterThickness');
}

export const defaultValue: Readonly<ISheathing> = {};
