import { IAbstractOperation } from './abstract-operation.model';

export interface IAbstractBobinsCountOwnerOperation extends IAbstractOperation {
  bobinsCount?: number;
}

export function isBobinsCountOwnerOperation(object: IAbstractOperation): object is IAbstractBobinsCountOwnerOperation {
  return Object.prototype.hasOwnProperty.call(object, 'bobinsCount');
}

export const defaultValue: Readonly<IAbstractBobinsCountOwnerOperation> = {};
