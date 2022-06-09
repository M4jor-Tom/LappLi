import { IMaterial } from 'app/shared/model/material.model';
import { IStrandSupply } from 'app/shared/model/strand-supply.model';
import { IAbstractOperation } from './abstract-operation.model';
import { IAbstractSheathing, isAbstractSheathing } from './abstract-sheathing.model';
import { SheathingKind } from './enumerations/sheathing-kind.model';

export interface IFlatSheathing extends IAbstractSheathing {
  id?: number;
  milimeterWidth?: number;
  milimeterHeight?: number;
  kilogramPerKilometerLinearMass?: number;
}

export function isFlatSheathing(object: IAbstractOperation): object is IFlatSheathing {
  return (
    isAbstractSheathing(object) &&
    Object.prototype.hasOwnProperty.call(object, 'milimeterHeight') &&
    Object.prototype.hasOwnProperty.call(object, 'milimeterWidth')
  );
}

export const defaultValue: Readonly<IFlatSheathing> = {};
