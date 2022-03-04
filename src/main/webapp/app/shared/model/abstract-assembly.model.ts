import { IAbstractOperation } from './abstract-operation.model';
import { IStrandSupply } from './strand-supply.model';

export interface IAbstractAssembly extends IAbstractOperation {
  ownerStrandSupply?: IStrandSupply;
  completionComponentsCount?: number;
  utilityComponentsCount?: number;
}

export const defaultValue: Readonly<IAbstractAssembly> = {};
