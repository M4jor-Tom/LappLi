import { ICarrierPlaitFiber } from 'app/shared/model/carrier-plait-fiber.model';
import { IStrandSupply } from 'app/shared/model/strand-supply.model';
import { IAbstractOperation } from './abstract-operation.model';

export interface ICarrierPlait extends IAbstractOperation {
  minimumDecaNewtonLoad?: number;
  degreeAssemblyAngle?: number;
  forcedEndPerBobinsCount?: number | null;
  carrierPlaitFiber?: ICarrierPlaitFiber;
}

export const defaultValue: Readonly<ICarrierPlait> = {};
