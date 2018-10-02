import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { HomeModule } from './features/home/home.module';
import { BrowserModule } from '@angular/platform-browser';
import { MatButtonModule, MatToolbarModule } from '@angular/material';
import { AppToolbarComponent } from './features/sections/toolbar/toolbar.component';
import { HeaderComponent } from './features/sections/header/header.component';
import { FooterComponent } from './features/sections/footer/footer.component';
import { AppRoutingModule } from './app-routing.module';

@NgModule({
  imports: [
    AppRoutingModule,
    HomeModule,
    BrowserModule,
    MatButtonModule,
    MatToolbarModule
  ],
  exports: [
    MatButtonModule
  ],
  declarations: [
    AppComponent,
    AppToolbarComponent,
    HeaderComponent,
    FooterComponent
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
