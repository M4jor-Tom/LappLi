import { IStrand } from './strand.model';

export interface IAbstractOperation {
  afterThisMilimeterDiameter?: number;
  operationLayer?: number;
  productionStep?: number;
  designation?: string | null;
  ownerStrand?: IStrand;
}

export const defaultValue: Readonly<IAbstractOperation> = {};
