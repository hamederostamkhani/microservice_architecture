import {Component, OnDestroy, OnInit} from '@angular/core';
import {ApiCallService} from "../service/api-call.service";
import {Seller} from "../model/seller.model";

export interface Columns {
  cols: number;
  rows: number;
  text: string;
  addOrRemove: string;
  sellers: Seller[];
}

@Component({
  selector: 'app-sellers',
  templateUrl: './sellers.component.html',
  styleUrls: ['./sellers.component.css']
})
export class SellersComponent implements OnInit, OnDestroy {

  dataList: Columns[] = [
    {
      text: 'Registered Sellers',
      cols: 2,
      rows: 1,
      addOrRemove: 'remove_circle',
      sellers: []
    },
    {
      text: 'Unregistered Sellers',
      cols: 2,
      rows: 1,
      addOrRemove: 'add_circle',
      sellers: []
    },
  ];

  constructor(private apiCallService: ApiCallService) {}

  ngOnInit(): void {
    this.apiCallService.getSellers().subscribe(sellersInfo => {
      this.dataList[0].sellers = sellersInfo.registeredSellers;
      this.dataList[1].sellers = sellersInfo.unregisteredSellers;
    });
  }

  ngOnDestroy(): void {
    console.log('ngOnDestroy');
  }

  addOrRemove(addOrRemove: string, seller: Seller) {
    if (addOrRemove === 'add_circle') {
      this.apiCallService.addSeller(seller).subscribe(id => {
        if (id != null) {
          const index: number = this.dataList[1].sellers.indexOf(seller);
          if (index !== -1)
            this.dataList[1].sellers.splice(index, 1);
          seller.id = id;
          this.dataList[0].sellers.push(seller);
        }
      });
    } else {
      this.apiCallService.removeSeller(seller).subscribe(res => {
        if (res != null) {
          const index: number = this.dataList[0].sellers.indexOf(seller);
          if (index !== -1)
            this.dataList[0].sellers.splice(index, 1);
          seller.id = 0;
          this.dataList[1].sellers.push(seller);
        }
      });
    }
  }
}
