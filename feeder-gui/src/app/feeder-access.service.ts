import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import {SwitchBoxResponse } from './RelaysState';
@Injectable({
  providedIn: 'root'
})
export class FeederAccessService {

  constructor(private client: HttpClient) { }
  
  lightToggle(): Observable<SwitchBoxResponse> {
    return this.client.get<SwitchBoxResponse>("http://192.168.0.154/light/2").pipe(
        map(
        relaysData => {
          return relaysData;
        }
      )
    );
  }
  
  filterToggle(): Observable<SwitchBoxResponse> {
    return this.client.get<SwitchBoxResponse>("http://192.168.0.154/filter/2").pipe(
        map(
        relaysData => {
          return relaysData;
        }
      )
    );
  }
  
  state(): Observable<SwitchBoxResponse> {
    return this.client.get<SwitchBoxResponse>("http://192.168.0.154/switchBoxState").pipe(
        map(
        relaysData => {
          return relaysData;
        }
      )
    );
  }
  
  feeder(): Observable<string> {
    return this.client.get<string>("http://192.168.0.154/feeder/1/250");
  }
}