import { IMaterial } from 'app/shared/model/material.model';
import { SheathingKind } from 'app/shared/model/enumerations/sheathing-kind.model';
import { IAbstractOperation } from './abstract-operation.model';

export interface ISheathing extends IAbstractOperation {
  thickness?: number;
  milimeterThickness?: number;
  sheathingKind?: SheathingKind;
  material?: IMaterial;
}

export const defaultValue: Readonly<ISheathing> = {};
