import {Product} from "./product.model";

export interface ProductsInfo {
  uid: string;
  registeredProducts: Product[];
  unregisteredProducts: Product[];
}
