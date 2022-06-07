import { IMaterial } from 'app/shared/model/material.model';
import { IStrandSupply } from 'app/shared/model/strand-supply.model';
import { IAbstractOperation } from './abstract-operation.model';

export interface IFlatSheathing extends IAbstractOperation {
  id?: number;
  milimeterWidth?: number;
  milimeterHeight?: number;
  material?: IMaterial;
}

export function isFlatSheathing(object: IAbstractOperation): object is IFlatSheathing {
  return Object.prototype.hasOwnProperty.call(object, 'milimeterWidth') && Object.prototype.hasOwnProperty.call(object, 'milimeterHeight');
}

export const defaultValue: Readonly<IFlatSheathing> = {};
