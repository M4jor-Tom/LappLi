import { MetalFiberKind } from 'app/shared/model/enumerations/metal-fiber-kind.model';

export interface ISteelFiber {
  id?: number;
  number?: number | null;
  designation?: string | null;
  metalFiberKind?: MetalFiberKind;
  milimeterDiameter?: number;
}

export const defaultValue: Readonly<ISteelFiber> = {};
