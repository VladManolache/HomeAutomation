import { NgModule } from '@angular/core';
import { HomeComponent } from './home.component';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { MatSlideToggleModule, MatSliderModule, MatProgressBarModule } from '@angular/material';
import 'hammerjs/hammer';

@NgModule({
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MatProgressBarModule,
    MatSlideToggleModule,
    MatSliderModule
  ],
  exports: [HomeComponent],
  declarations: [
    HomeComponent
  ],
  providers: []
})
export class HomeModule {

}
