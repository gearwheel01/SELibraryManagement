import {Genre} from './genre';
import {Author} from './author';

export interface Product {

  isbn: string;
  genres?: Genre[];
  authors?: Author[];
  title: string;
  publication: Date;
  publisher: string;
  copies: number;
  availableCopies?: number;

}
