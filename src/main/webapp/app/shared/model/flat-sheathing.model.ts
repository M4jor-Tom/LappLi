import { IMaterial } from 'app/shared/model/material.model';
import { IStrandSupply } from 'app/shared/model/strand-supply.model';

export interface IFlatSheathing {
  id?: number;
  operationLayer?: number;
  milimeterWidth?: number;
  milimeterHeight?: number;
  material?: IMaterial;
  ownerStrandSupply?: IStrandSupply;
}

export const defaultValue: Readonly<IFlatSheathing> = {};
