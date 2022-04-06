import { IStrandSupply } from 'app/shared/model/strand-supply.model';

export interface IStripLaying {
  id?: number;
  operationLayer?: number;
  ownerStrandSupply?: IStrandSupply;
}

export const defaultValue: Readonly<IStripLaying> = {};
