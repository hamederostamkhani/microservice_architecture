import {Component} from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html'
})
export class AppComponent {
  title = 'web_app';
  navLinks: any[];
  activeLinkIndex = -1;

  constructor(private router: Router) {
    this.navLinks = [
      {
        label: 'Sellers',
        link: './sellers',
        index: 0
      }, {
        label: 'Products',
        link: './products',
        index: 1
      }, {
        label: 'Product To Seller',
        link: './productToSeller',
        index: 2
      }, {
        label: 'Log Management',
        link: './logManagement',
        index: 3
      },
    ];
  }

  ngOnInit(): void {
    this.router.events.subscribe((res) => {
      this.activeLinkIndex = this.navLinks.indexOf(this.navLinks.find(tab => tab.link === '.' + this.router.url));
    });
  }
}
