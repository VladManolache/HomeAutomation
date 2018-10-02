import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-footer',
  template: `
    <footer class="component-page-footer">
      <span class="page-footer-text">Copyright &copy; 2018 Vlad Manolache </span>
    </footer>
  `,
  styleUrls: ['footer.component.scss']
})
export class FooterComponent implements OnInit {
  constructor() {}
  ngOnInit() {}
}
