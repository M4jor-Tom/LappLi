import { MetalFiberKind } from 'app/shared/model/enumerations/metal-fiber-kind.model';
import { Flexibility } from 'app/shared/model/enumerations/flexibility.model';

export interface IContinuityWire {
  id?: number;
  designation?: string;
  gramPerMeterLinearMass?: number;
  metalFiberKind?: MetalFiberKind;
  milimeterDiameter?: number;
  flexibility?: Flexibility;
}

export const defaultValue: Readonly<IContinuityWire> = {};
