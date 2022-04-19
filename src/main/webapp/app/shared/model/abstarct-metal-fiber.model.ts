import { MetalFiberKind } from 'app/shared/model/enumerations/metal-fiber-kind.model';

export interface IAbstractMetalFiber {
  number?: number | null;
  designation?: string | null;
  metalFiberKind?: MetalFiberKind;
  milimeterDiameter?: number;
}

export const defaultValue: Readonly<IAbstractMetalFiber> = {};
