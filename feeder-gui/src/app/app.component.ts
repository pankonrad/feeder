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
	
	lightState: boolean;
	filterState: boolean;
  
	constructor(private service: FeederAccessService){};
  
	ngOnInit() {
		this.getStates();
	};
  
	getStates(){
		this.service.state().subscribe( relaysData => {
	        this.lightState = relaysData.lightState;
			this.filterState = relaysData.filterState;
	      }
	    );
	};
  
  
  public feeder(): void {
	  this.service.feeder().subscribe(x=> console.log(x));
  }
  
  public lightToggle(): void {
		this.service.lightToggle().subscribe( relaysData => {
	        this.lightState = relaysData.lightState;
			this.filterState = relaysData.filterState;
	      }
	    );
  }
  
  public filterToggle(): void {
		this.service.filterToggle().subscribe( relaysData => {
	        this.lightState = relaysData.lightState;
			this.filterState = relaysData.filterState;
	      }
	    );
  }
}
