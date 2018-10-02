import { Injectable } from '@angular/core';
import { Device } from '../../model/device.model';
import * as $ from 'jquery';
import { endpoints } from '../../../environments/endpoints';

@Injectable()
export class HomeService {

  public loadUpdates(): any {
    return $.getJSON(endpoints.devicesUrl);
  }

  public sendUpdates(devicesList: [Device]): any {
    return $.ajax({
      url: endpoints.devicesUrl,
      type: 'POST',
      data: JSON.stringify(devicesList)
    });
  }

}
