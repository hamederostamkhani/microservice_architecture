import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {SellersComponent} from "./sellers/sellers.component";
import {ProductsComponent} from "./products/products.component";
import {ProductToSellerComponent} from "./product-to-seller/product-to-seller.component";
import {LogManagementComponent} from "./log-management/log-management.component";
import {NotFoundComponent} from "./not-found/not-found.component";

const routes: Routes = [
  { path: '', redirectTo: '/sellers', pathMatch: 'full' },
  { path: 'sellers', component: SellersComponent },
  { path: 'products', component: ProductsComponent },
  { path: 'productToSeller', component: ProductToSellerComponent },
  { path: 'logManagement', component: LogManagementComponent },
  { path: '**', component: NotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
