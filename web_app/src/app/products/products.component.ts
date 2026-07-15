import {Component, OnDestroy, OnInit} from '@angular/core';
import {ApiCallService} from "../service/api-call.service";
import {Product} from "../model/product.model";

export interface Columns {
  cols: number;
  rows: number;
  text: string;
  addOrRemove: string;
  products: Product[];
}

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit, OnDestroy {

  dataList: Columns[] = [
    {
      text: 'Registered Products',
      cols: 2,
      rows: 1,
      addOrRemove: 'remove_circle',
      products: []
    },
    {
      text: 'Unregistered Products',
      cols: 2,
      rows: 1,
      addOrRemove: 'add_circle',
      products: []
    },
  ];

  constructor(private apiCallService: ApiCallService) {}

  ngOnInit(): void {
    this.apiCallService.getProducts().subscribe(productsInfo => {
      this.dataList[0].products = productsInfo.registeredProducts;
      this.dataList[1].products = productsInfo.unregisteredProducts;
    });
  }

  ngOnDestroy(): void {
    console.log('ngOnDestroy');
  }

  addOrRemove(addOrRemove: string, product: Product) {
    if (addOrRemove === 'add_circle') {
      this.apiCallService.addProduct(product).subscribe(productId => {
        if (productId != null) {
          const index: number = this.dataList[1].products.indexOf(product);
          if (index !== -1)
            this.dataList[1].products.splice(index, 1);
          product.id = productId;
          this.dataList[0].products.push(product);
        }
      });
    } else {
      this.apiCallService.removeProduct(product).subscribe(res => {
        if (res != null) {
          const index: number = this.dataList[0].products.indexOf(product);
          if (index !== -1)
            this.dataList[0].products.splice(index, 1);
          product.id = "";
          this.dataList[1].products.push(product);
        }
      });
    }
  }
}
