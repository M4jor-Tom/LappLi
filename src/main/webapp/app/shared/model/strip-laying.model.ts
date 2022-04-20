import { IStrip } from 'app/shared/model/strip.model';
import { IStrandSupply } from 'app/shared/model/strand-supply.model';
import { IMeanedAssemblableOperation } from './meaned-assemblable-operation.model';

export interface IStripLaying extends IMeanedAssemblableOperation {
  id?: number;
  operationLayer?: number;
  strip?: IStrip;
  ownerStrandSupply?: IStrandSupply;
}

export const defaultValue: Readonly<IStripLaying> = {};
