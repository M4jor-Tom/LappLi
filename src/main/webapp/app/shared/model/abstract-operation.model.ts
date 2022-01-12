import { OperationKind } from './enumerations/operation-kind.model';
import { IStrand } from './strand.model';

export interface IAbstractOperation {
  operationKind?: OperationKind;
  afterThisMilimeterDiameter?: number;
  operationLayer?: number;
  productionStep?: number;
  designation?: string | null;
  ownerStrand?: IStrand;
}

export const defaultValue: Readonly<IAbstractOperation> = {};
