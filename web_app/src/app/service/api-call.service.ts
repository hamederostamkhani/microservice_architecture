import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {Seller} from "../model/seller.model";
import {Product} from "../model/product.model";
import {SellersInfo} from "../model/sellers-info.model";
import {ProductsInfo} from "../model/products-info.model";
import {FlowsInfo} from "../model/flows-info.model";
import {SellerProduct} from "../model/seller-product.model";
import {SellerProductIds} from "../model/seller-product-ids.model";
import {FlowFilter} from "../model/flow-filter.model";

@Injectable({
  providedIn: 'root'
})
export class ApiCallService {
  // baseUrl: string = 'http://localhost:9090';
  baseUrl: string = 'http://192.168.152.130:9090';

  constructor(private httpClient: HttpClient) {
  }

  addSeller(seller: Seller): Observable<number> {
    return this.httpClient.post<number>(this.baseUrl + '/seller', seller);
  }

  getSellers(): Observable<SellersInfo> {
    return this.httpClient.get<SellersInfo>(this.baseUrl + '/seller');
  }

  removeSeller(seller: Seller): Observable<any> {
    return this.httpClient.post(this.baseUrl + '/seller/remove', seller, {responseType: 'text'});
  }

  addProduct(product: Product): Observable<any> {
    return this.httpClient.post(this.baseUrl + '/product', product, {responseType: 'text'});
  }

  getProducts(): Observable<ProductsInfo> {
    return this.httpClient.get<ProductsInfo>(this.baseUrl + '/product');
  }

  removeProduct(product: Product): Observable<any> {
    return this.httpClient.post(this.baseUrl + '/product/remove', product, {responseType: 'text'});
  }

  getSellerProducts(): Observable<SellerProduct[]> {
    return this.httpClient.get<SellerProduct[]>(this.baseUrl + '/sellerProduct');
  }

  addProductToSeller(sellerProductIds: SellerProductIds): Observable<any> {
    return this.httpClient.post(this.baseUrl + '/sellerProduct', sellerProductIds, {responseType: 'text'});
  }

  removeProductSeller(sellerProductIds: SellerProductIds): Observable<any> {
    return this.httpClient.post(this.baseUrl + '/sellerProduct/removeSellerProduct', sellerProductIds, {responseType: 'text'});
  }

  getCorrelatedLogs(flowFilter: FlowFilter): Observable<FlowsInfo> {
    let params = new HttpParams().set('page', flowFilter.page).set('npp', flowFilter.npp);
    let body = {fromDate: flowFilter.fromDate, toDate: flowFilter.toDate};
    return this.httpClient.post<FlowsInfo>(this.baseUrl + '/logManagement', body, {params});
  }
}
