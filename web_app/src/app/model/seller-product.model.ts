import {Product} from "./product.model";

export class SellerProduct {
  id!: number;
  firstName!: string;
  lastName!: string;
  email!: string;
  image!: string;
  products!: Product[];
}

