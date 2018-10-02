import { Component, OnInit } from '@angular/core';
import { Device } from '../../model/device.model';
import { HomeService } from './home.service';
import { DeviceType } from '../../model/device-type.model';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
  providers: [HomeService]
})
export class HomeComponent implements OnInit {

  devicesList: [Device];

  deviceType = DeviceType;

  isLoading = true;

  constructor(private homeService: HomeService) { }

  ngOnInit() {
    this.homeService.loadUpdates().then(result => {
      this.devicesList = result;
      this.isLoading = false;
    })
    .fail(err => {
      console.log(err);
      this.isLoading = false;
    });
  }

  didToggle(device: Device, event) {
    const previousValue = device.state.value;
    switch (device.type) {
      case DeviceType.LIGHT_SWITCH:
        device.state.value = device.state.value === 'on' ? 'off' : 'on';
        break;
      case DeviceType.CURTAIN:
        device.state.value = event.value;
        break;
      case DeviceType.TEMPERATURE_MONITOR:
        device.state.value = event.value;
        break;
    }

    this.isLoading = true;
    this.homeService.sendUpdates([device]).then(() => {
      this.isLoading = false;
    })
    .fail(err => {
      console.log(err);
      device.state.value = previousValue;
      this.isLoading = false;
    });
  }
}
