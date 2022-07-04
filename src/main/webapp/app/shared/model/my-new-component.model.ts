import { ICylindricComponent } from './cylindric-component.model';

export interface IMyNewComponent extends ICylindricComponent {
  id?: number;
  number?: number;
  designation?: string;
  data?: number;
}

export const defaultValue: Readonly<IMyNewComponent> = {};
