import { IAbstractMetalFiber } from './abstarct-metal-fiber.model';

export interface ISteelFiber extends IAbstractMetalFiber {
  id?: number;
}

export const defaultValue: Readonly<ISteelFiber> = {};
