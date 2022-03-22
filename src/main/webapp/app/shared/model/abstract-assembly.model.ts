import { IAbstractOperation } from './abstract-operation.model';
import { IStrandSupply } from './strand-supply.model';

export interface IAbstractAssembly extends IAbstractOperation {
  completionComponentsCount?: number;
  utilityComponentsCount?: number;
}

export function isAssembly(object: IAbstractOperation): object is IAbstractAssembly {
  return (
    Object.prototype.hasOwnProperty.call(object, 'completionComponentsCount') &&
    Object.prototype.hasOwnProperty.call(object, 'utilityComponentsCount')
  );
}

export const defaultValue: Readonly<IAbstractAssembly> = {};
