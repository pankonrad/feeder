import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FeederAccessService } from './feeder-access.service';
import { Input } from '@angular/core';
import {SwitchBoxResponse } from './RelaysState';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
	
	states: SwitchBoxResponse;
  
	constructor(private service: FeederAccessService){};
  
	ngOnInit() {
		this.getStates();
	};
  
	getStates(){
		this.service.state().subscribe( relaysData => {
	        this.states = relaysData
	      }
	    );
	};
  
  
  public feeder(): void {
	  this.service.feeder().subscribe(x=> console.log(x));
  }
  
  public lightToggle(): void {

  }
  
  public filterToggle(): void {

  }
  
/*

  public feeder(): void {
	  this.feederEnabled = true;
	  this.service.feeder().subscribe(x=> console.log(x));
	  this.feederEnabled = false;
  }
  
  public lightToggle(): void {
	if(!this.lightEnabled) {
		  this.service.enableLight().subscribe(x=> console.log(x));
		  this.lightEnabled = true;
	} else {
		  this.service.disableLight().subscribe(x=> console.log(x));
		  this.lightEnabled = false;
	}
	
	this.service.state().subscribe(
      relaysData => {
        this.lightEnabled = relaysData.lightState
        this.filterEnabled = relaysData.filterState
      }
    );
  }
  
  public filterToggle(): void {
	if(!this.filterEnabled) {
		  this.service.enableFilter().subscribe(x=> console.log(x));
		  this.filterEnabled = true;
	} else {
		  this.service.disableFilter().subscribe(x=> console.log(x));
		  this.filterEnabled = false;
	}
	
	this.service.state().subscribe(
      relaysData => {
        this.lightEnabled = relaysData.lightState
        this.filterEnabled = relaysData.filterState
      }
    );
  }

*/
}
