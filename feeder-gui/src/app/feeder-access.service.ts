import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FeederAccessService {

  constructor(private client: HttpClient) { }
  
  enableLight(): Observable<string> {
    return this.client.get<string>("http://192.168.0.254:8888/light/1");
  }
  
  disableLight(): Observable<string> {
    return this.client.get<string>("http://192.168.0.254:8888/light/0");
  }
  
  enableFilter(): Observable<string> {
    return this.client.get<string>("http://192.168.0.254:8888/filter/1");
  }
  
  disableFilter(): Observable<string> {
    return this.client.get<string>("http://192.168.0.254:8888/filter/0");
  }
  
  feeder(): Observable<string> {
    return this.client.get<string>("http://192.168.0.254:8888/feeder/1/250");
  }
  
  state(): Observable<string> {
    return this.client.get<string>("http://192.168.0.254:8888/switchBoxState");
  }
}