import { Input, Component } from '@angular/core';

import { MatMenuTrigger } from '@angular/material/menu';
import { Router } from '@angular/router';

/**
 * Fixes menu bug. Hovering over the nagivation link button opens the links menu. Leaving the button/menu forces menu to close.
 * 
 * https://stackblitz.com/edit/mat-nested-menu-p1a5b8?file=app%2Fnested-menu-example.ts
 */
@Component({
  selector: 'app-button-menu',
  templateUrl: './button-menu.component.html',
  styleUrls: ['./button-menu.component.scss']
})
export class ButtonMenuComponent {
  @Input() buttonName: string = "";
  @Input() menuItems: string[] = [];

  isButtonHovered = false;
  isMenuOpen = false;

  constructor(private router: Router) { }

  menuEnter(): void {
    this.isMenuOpen = true;
  }

  menuLeave(menuTrigger: MatMenuTrigger): void {
    setTimeout(() => {
      if (!this.isButtonHovered) {
        this.isMenuOpen = false;
        menuTrigger.closeMenu();
      }
    }, 100
    )
  }

  triggerEnter(menuTrigger: MatMenuTrigger): void {
    this.isButtonHovered = true;
    this.isMenuOpen = true;
    menuTrigger.openMenu();
  }

  triggerLeave(menuTrigger: MatMenuTrigger): void {
    this.isMenuOpen = false;

    setTimeout(() => {
      if (!this.isMenuOpen) {
        menuTrigger.closeMenu();
      }

      this.isButtonHovered = false;
    }, 100)
  }
}
