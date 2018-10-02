// src/app/components/header.component.ts
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-header',
  template: `
    <header class="component-page-header">
      <h1 class="page-header-text">Home</h1>
    </header>
  `,
  styleUrls: ['header.component.scss']
})
export class HeaderComponent implements OnInit {
  constructor() {}
  ngOnInit() {}
}
