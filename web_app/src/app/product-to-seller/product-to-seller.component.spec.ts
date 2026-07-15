import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductToSellerComponent } from './product-to-seller.component';

describe('ProductToSellerComponent', () => {
  let component: ProductToSellerComponent;
  let fixture: ComponentFixture<ProductToSellerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProductToSellerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductToSellerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
