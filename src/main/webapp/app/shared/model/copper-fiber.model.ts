import { IAbstractMetalFiber } from './abstarct-metal-fiber.model';

export interface ICopperFiber extends IAbstractMetalFiber {
  id?: number;
}

export const defaultValue: Readonly<ICopperFiber> = {};
