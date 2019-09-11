import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FeederAccessService } from './feeder-access.service';
import { Input } from '@angular/core';
import {RelaysState } from './RelaysState';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
	
  
  constructor(private service: FeederAccessService){}
  
  lightEnabled = true;
  filterEnabled = true;
  feederEnabled = false;
  
  ngOnInit() {
	  this.service.state().subscribe(
      relaysData => {
        this.lightEnabled = relaysData[0].state
        this.filterEnabled = relaysData[1].state
      }
    );
  }
  
  public feeder(): void {
	  this.feederEnabled = true;
	  this.service.feeder().subscribe(x=> console.log(x));
	  this.feederEnabled = false;
  }
  
  public lightState(): void {
	if(!this.lightEnabled) {
		  this.service.enableLight().subscribe(x=> console.log(x));
		  this.lightEnabled = true;
	} else {
		  this.service.disableLight().subscribe(x=> console.log(x));
		  this.lightEnabled = false;
	}
  }
  
  public filterState(): void {
	if(!this.filterEnabled) {
		  this.service.enableFilter().subscribe(x=> console.log(x));
		  this.filterEnabled = true;
	} else {
		  this.service.disableFilter().subscribe(x=> console.log(x));
		  this.filterEnabled = false;
	}
  }
}
