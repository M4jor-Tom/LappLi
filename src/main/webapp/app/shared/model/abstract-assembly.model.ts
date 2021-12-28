import { IPosition } from './position.model';

export interface IAbstractAssembly {
  productionStep?: number;
  position?: IPosition;
  designation?: string | null;
}

export const defaultValue: Readonly<IAbstractAssembly> = {};
