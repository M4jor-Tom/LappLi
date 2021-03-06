import { OperationKind } from './enumerations/operation-kind.model';
import { IStrandSupply } from './strand-supply.model';
import { IAbstractMachine } from './abstract-machine.model';

export interface IAbstractOperation {
  id?: number;
  operationKind?: OperationKind;
  productDesignation?: string;
  milimeterDiameterIncidency?: number;
  mullerStandardizedFormatMilimeterDiameterIncidency?: string;
  mullerStandardizedFormatHourPreparationTime?: string;
  mullerStandardizedFormatHourExecutionTimeForAllStrandSupplies?: string;
  beforeThisMilimeterDiameter?: number;
  mullerStandardizedFormatBeforeThisMilimeterDiameter?: string;
  afterThisMilimeterDiameter?: number;
  mullerStandardizedFormatAfterThisMilimeterDiameter?: string;
  operationLayer?: number;
  productionStep?: number;
  designation?: string | null;
  ownerStrandSupply?: IStrandSupply;
  operatingMachine?: IAbstractMachine;
  hourExecutionTime?: number;
}

export const defaultValue: Readonly<IAbstractOperation> = {};
