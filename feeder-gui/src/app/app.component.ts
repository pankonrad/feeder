import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FeederAccessService } from './feeder-access.service';
import { Input } from '@angular/core';
import {SwitchBoxResponse } from './RelaysState';
import {FeederResponse } from './FeederState';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

	lightState: boolean;
	filterState: boolean;

	feeder1State: boolean;
	feeder2State: boolean;
	feeder3State: boolean;

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
		this.service.state2().subscribe( feederData => {
			    this.feeder1State = feederData.feeder1State;
			    this.feeder2State = feederData.feeder2State;
			    this.feeder3State = feederData.feeder3State;
	      }
	  );

	};


  public feeder1(): void {
	  this.service.feeder1().subscribe( state => {
      this.feeder1State = state;
    });
  }

  public feeder2(): void {
	  this.service.feeder2().subscribe( state => {
      this.feeder2State = state;
    });
  }

  public feeder3(): void {
	  this.service.feeder3().subscribe( state => {
      this.feeder3State = state;
    });
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
