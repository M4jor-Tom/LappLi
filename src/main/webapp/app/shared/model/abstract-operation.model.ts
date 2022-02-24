import { OperationKind } from './enumerations/operation-kind.model';
import { IStrandSupply } from './strand-supply.model';

export interface IAbstractOperation {
  id?: number;
  operationKind?: OperationKind;
  productDesignation?: string;
  milimeterDiameterIncidency?: number;
  afterThisMilimeterDiameter?: number;
  operationLayer?: number;
  productionStep?: number;
  designation?: string | null;
  ownerStrandSupply?: IStrandSupply;
}

export const defaultValue: Readonly<IAbstractOperation> = {};
