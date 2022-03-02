import { OperationKind } from './enumerations/operation-kind.model';
import { IStrandSupply } from './strand-supply.model';

export interface IAbstractOperation {
  id?: number;
  operationKind?: OperationKind;
  productDesignation?: string;
  milimeterDiameterIncidency?: number;
  mullerStandardizedFormatMilimeterDiameterIncidency?: string;
  beforeThisMilimeterDiameter?: number;
  mullerStandardizedFormatBeforeThisMilimeterDiameter?: number;
  afterThisMilimeterDiameter?: number;
  mullerStandardizedFormatAfterThisMilimeterDiameter?: string;
  operationLayer?: number;
  productionStep?: number;
  designation?: string | null;
  ownerStrandSupply?: IStrandSupply;
}

export const defaultValue: Readonly<IAbstractOperation> = {};
