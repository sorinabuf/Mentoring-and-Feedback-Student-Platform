import { Component } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  currentRoute: string;
  isAuth: boolean;

  constructor(router: Router) {
    this.currentRoute = router.url;
    this.isAuth = false;

    router.events
      .subscribe(event => {

        if (event instanceof NavigationEnd) {
          this.currentRoute = event.urlAfterRedirects;
          window.scrollTo(0, 0);
        }

        this.isAuthPage();
      });
  }

  isAuthPage(): void {
    console.log(this.currentRoute);
    if (this.currentRoute === "/login" || this.currentRoute === "/register" || this.currentRoute === "/" || this.currentRoute === "/not-found") {
      this.isAuth = true;
      return;
    }

    this.isAuth = false;
  }
}
