import {Component, OnInit} from '@angular/core';
import {animate, state, style, transition, trigger} from "@angular/animations";
import {SellerProduct} from "../model/seller-product.model";
import {ApiCallService} from "../service/api-call.service";
import {Product} from "../model/product.model";

@Component({
  selector: 'app-product-to-seller',
  templateUrl: './product-to-seller.component.html',
  styleUrls: ['./product-to-seller.component.css'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
export class ProductToSellerComponent implements OnInit {

  dataSource: SellerProduct[] = [];
  columnsToDisplay = ['id', 'firstName', 'lastName', 'email'];
  expandedElement: SellerProduct | null = null;

  constructor(private apiCallService: ApiCallService) {
  }

  ngOnInit(): void {
    this.apiCallService.getSellerProducts().subscribe(response => {
      response = response.sort((a, b) => (a.id >= b.id) ? 1 : -1);
      this.dataSource = response;
    });
  }

  selectProductFor(product: Product, id: number) {
    if (product.selected) {
      this.apiCallService.removeProductSeller({productId: product.id, userId: id})
        .subscribe(res => {
          console.log(res);
          if (res != null) {
            product.selected = !product.selected;
          }
        });
    } else {
      this.apiCallService.addProductToSeller({productId: product.id, userId: id})
        .subscribe(res => {
          console.log(res);
          if (res != null) {
            product.selected = !product.selected;
          }
        });
    }
  }
}
