import { IStrip } from 'app/shared/model/strip.model';
import { IStrandSupply } from 'app/shared/model/strand-supply.model';

export interface IStripLaying {
  id?: number;
  operationLayer?: number;
  strip?: IStrip;
  ownerStrandSupply?: IStrandSupply;
}

export const defaultValue: Readonly<IStripLaying> = {};
