import { ICopperFiber } from 'app/shared/model/copper-fiber.model';
import { ISteelFiber } from 'app/shared/model/steel-fiber.model';
import { IStrandSupply } from 'app/shared/model/strand-supply.model';
import { MetalFiberKind } from 'app/shared/model/enumerations/metal-fiber-kind.model';
import { IAbstractOperation } from './abstract-operation.model';
import { IAbstractMetalFiber } from './abstarct-metal-fiber.model';

export interface IPlait extends IAbstractOperation {
  targetCoveringRate?: number | null;
  targetDegreeAngle?: number | null;
  targetingCoveringRateNotAngle?: boolean;
  anonymousMetalFiberNumber?: number | null;
  anonymousMetalFiberDesignation?: string | null;
  anonymousMetalFiberMetalFiberKind?: MetalFiberKind | null;
  anonymousMetalFiberMilimeterDiameter?: number | null;
  finalMetalFiber?: IAbstractMetalFiber | null;
  metalFiber?: IAbstractMetalFiber | null;
  copperFiber?: ICopperFiber | null;
  steelFiber?: ISteelFiber | null;
  ownerStrandSupply?: IStrandSupply;
}

export const defaultValue: Readonly<IPlait> = {
  targetingCoveringRateNotAngle: false,
};
