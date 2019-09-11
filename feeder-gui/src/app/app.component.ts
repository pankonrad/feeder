import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FeederAccessService } from './feeder-access.service';
import { Input } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
	
  
  constructor(private service: FeederAccessService){}
  
  lightEnabled = false;
  filterEnabled = true;
  feederEnabled = false;
  
  ngOnInit() {
	  this.service.state().subscribe(x=> console.log(x));
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
