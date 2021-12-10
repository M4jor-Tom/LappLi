import { ICustomComponent } from 'app/shared/model/custom-component.model';
import { IAbstractMarkedLiftedSupply } from './abstract-marked-lifted-supply.model';

export interface ICustomComponentSupply extends IAbstractMarkedLiftedSupply {
  id?: number;
  customComponent?: ICustomComponent;
}

export const defaultValue: Readonly<ICustomComponentSupply> = {};
