import { IStrand } from 'app/shared/model/strand.model';

export interface IISupply {
  id?: number;
  apparitions?: number;
  milimeterDiameter?: number;
  gramPerMeterLinearMass?: number;
  strand?: IStrand | null;
}

export const defaultValue: Readonly<IISupply> = {};
