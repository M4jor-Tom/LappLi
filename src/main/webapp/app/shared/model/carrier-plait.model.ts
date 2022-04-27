import { ICarrierPlaitFiber } from 'app/shared/model/carrier-plait-fiber.model';
import { IStrandSupply } from 'app/shared/model/strand-supply.model';

export interface ICarrierPlait {
  id?: number;
  operationLayer?: number;
  minimumDecaNewtonLoad?: number;
  degreeAssemblyAngle?: number;
  forcedEndPerBobinsCount?: number | null;
  carrierPlaitFiber?: ICarrierPlaitFiber;
  ownerStrandSupply?: IStrandSupply;
}

export const defaultValue: Readonly<ICarrierPlait> = {};
