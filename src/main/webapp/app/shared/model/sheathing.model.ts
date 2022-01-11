import { IMaterial } from 'app/shared/model/material.model';
import { IStrand } from 'app/shared/model/strand.model';
import { SheathingKind } from 'app/shared/model/enumerations/sheathing-kind.model';
import { IAbstractOperation } from './abstract-operation.model';

export interface ISheathing extends IAbstractOperation {
  id?: number;
  thickness?: number;
  sheathingKind?: SheathingKind;
  material?: IMaterial;
  ownerStrand?: IStrand;
}

export const defaultValue: Readonly<ISheathing> = {};
