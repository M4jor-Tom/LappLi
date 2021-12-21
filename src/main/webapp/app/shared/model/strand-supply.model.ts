import { IStrand } from 'app/shared/model/strand.model';
import { IStudy } from 'app/shared/model/study.model';
import { MarkingType } from 'app/shared/model/enumerations/marking-type.model';

export interface IStrandSupply {
  id?: number;
  apparitions?: number;
  markingType?: MarkingType;
  description?: string | null;
  strand?: IStrand;
  study?: IStudy;
}

export const defaultValue: Readonly<IStrandSupply> = {};
