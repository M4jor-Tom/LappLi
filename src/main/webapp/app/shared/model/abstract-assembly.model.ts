import { IAbstractOperation } from './abstract-operation.model';

export interface IAbstractAssembly extends IAbstractOperation {}

export const defaultValue: Readonly<IAbstractAssembly> = {};
