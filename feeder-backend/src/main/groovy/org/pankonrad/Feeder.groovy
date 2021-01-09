package org.pankonrad

import groovy.json.JsonSlurper
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces

import javax.inject.Inject

// @CompileStatic
@Controller("/") 
class Feeder {

    Gpio gpio
	SwitchBoxClient switchBoxClient
  
    @Inject
    Feeder(Gpio gpio, SwitchBoxClient switchBoxClient) {
      this.gpio = gpio
	  this.switchBoxClient = switchBoxClient
    }

	@Get("/feeder/{pin}/{state}/{milliseconds}")
	@Produces(MediaType.TEXT_PLAIN)
	boolean run(int pin, int state, int milliseconds) {
		int resultPinState = gpio.run(pin, state, milliseconds)
		String retVal = "FEEDER OK. PIN: " + pin  + "; resultPinState: " + resultPinState;
		return (resultPinState > 0);
	}

	@Get("/feeder")
	@Produces(MediaType.APPLICATION_JSON)
	FeederResponse states() {
		return gpio.states();
	}
	
    @Get("/feeder/{state}/{milliseconds}")
    @Produces(MediaType.TEXT_PLAIN)
    String run(int state, int milliseconds) {
		milliseconds = (milliseconds > 1000) ? 1000 : milliseconds;
		
		gpio.run(25, state, milliseconds)
		String retVal = "FEEDER OK, state = " + state + ", milliseconds = " + milliseconds;
    }

    @Get("/pumpNormal")
    @Produces(MediaType.TEXT_PLAIN)
    String pumpNormal() {		
		gpio.pump(0, 0)
		String retVal = "pumpNormal OK, state28 = " + 0 + ", state29 = " + 0;
    }
	
    @Get("/pumpOut")
    @Produces(MediaType.TEXT_PLAIN)
    String pumpOut() {		
		gpio.pump(1, 1)
		String retVal = "pumpOut OK, state28 = " + 1 + ", state29 = " + 1;
    }
	
	/*
    @Get("/pumpIn")
    @Produces(MediaType.TEXT_PLAIN)
    String pumpIn() {		
		gpio.pump(1, 0)
		String retVal = "pumpIn not implemented";
    }
	*/
	
	@Get("/light/{state}")
    @Produces(MediaType.APPLICATION_JSON)
	SwitchBoxResponse changeLightState(int state) {
		String response = switchBoxClient.light(state)
		return prepareResponse(response)
	}
	
	@Get("/filter/{state}")
    @Produces(MediaType.APPLICATION_JSON)
	SwitchBoxResponse changeFilterState(int state) {
		String response = switchBoxClient.filter(state)
		return prepareResponse(response)
	}
	
	@Get("/switchBoxState")
    @Produces(MediaType.APPLICATION_JSON)
	SwitchBoxResponse switchBoxState() {
	  String response = switchBoxClient.state()
	  return prepareResponse(response)
	}
	
	private SwitchBoxResponse prepareResponse(String response) {
      def jsonSlurper = new JsonSlurper()
      def object = jsonSlurper.parseText(response)
	  SwitchBoxResponse switchBoxResponse = new SwitchBoxResponse()
	  switchBoxResponse.lightState = object.relays[0].state == 1 ? true : false
	  switchBoxResponse.filterState = object.relays[1].state == 1 ? true : false
	  println(switchBoxResponse.lightState)
      println(switchBoxResponse.filterState)
	  return switchBoxResponse
	}

	private FeederResponse feederResponse(String response) {
		def jsonSlurper = new JsonSlurper()
		def object = jsonSlurper.parseText(response)
		FeederResponse feederResponse = new FeederResponse()
		feederResponse.feeder1State = object.relays[0].state == 1 ? true : false
		feederResponse.feeder2State = object.relays[0].state == 1 ? true : false
		feederResponse.feeder3State = object.relays[0].state == 1 ? true : false

		println(feederResponse.feeder1State)
		println(feederResponse.feeder2State)
		println(feederResponse.feeder3State)
		return feederResponse
	}
}