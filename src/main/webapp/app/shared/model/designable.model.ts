import { IArticle } from './article.model';

export interface IDesignable extends IArticle {
  designation?: string;
}

export const defaultValue: Readonly<IDesignable> = {};
