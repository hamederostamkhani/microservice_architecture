import {Seller} from "./seller.model";

export interface SellersInfo {
  uid: string;
  registeredSellers: Seller[];
  unregisteredSellers: Seller[];
}
