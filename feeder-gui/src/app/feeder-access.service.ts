import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import {SwitchBoxResponse } from './RelaysState';
import {FeederResponse } from './FeederState';
@Injectable({
  providedIn: 'root'
})
export class FeederAccessService {

  constructor(private client: HttpClient) { }

  lightToggle(): Observable<SwitchBoxResponse> {
    return this.client.get<SwitchBoxResponse>("http://10.0.0.254/light/2").pipe(
        map(
        relaysData => {
          return relaysData;
        }
      )
    );
  }

  filterToggle(): Observable<SwitchBoxResponse> {
    return this.client.get<SwitchBoxResponse>("http://10.0.0.254/filter/2").pipe(
        map(
        relaysData => {
          return relaysData;
        }
      )
    );
  }

  state(): Observable<SwitchBoxResponse> {
    return this.client.get<SwitchBoxResponse>("http://10.0.0.254/switchBoxState").pipe(
        map(
        relaysData => {
          return relaysData;
        }
      )
    );
  }

  state2(): Observable<FeederResponse> {
    return this.client.get<FeederResponse>("http://10.0.0.254/feeder").pipe(
          map(feederData => {
            return feederData;
          }
      )
    );
  }

  feeder1(): Observable<boolean> {
    return this.client.get<boolean>("http://10.0.0.254/feeder/25/2/250");
  }

  feeder2(): Observable<boolean> {
    return this.client.get<boolean>("http://10.0.0.254/feeder/28/2/250");
  }

  feeder3(): Observable<boolean> {
    return this.client.get<boolean>("http://10.0.0.254/feeder/29/2/250");
  }
}
