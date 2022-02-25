import { OperationKind } from './enumerations/operation-kind.model';
import { IStrand } from './strand.model';

export interface IAbstractOperation {
  id?: number;
  operationKind?: OperationKind;
  productDesignation?: string;
  milimeterDiameterIncidency?: number;
  mullerStandardizedFormatMilimeterDiameterIncidency?: string;
  afterThisMilimeterDiameter?: number;
  mullerStandardizedFormatAfterThisMilimeterDiameter?: string;
  operationLayer?: number;
  productionStep?: number;
  designation?: string | null;
  ownerStrand?: IStrand;
}

export const defaultValue: Readonly<IAbstractOperation> = {};
