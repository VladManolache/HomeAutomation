import { DeviceType } from './device-type.model';

export class Device {
  id: string;
  name: string;
  type: DeviceType;
  state: any;
}
