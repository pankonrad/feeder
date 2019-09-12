package org.pankonrad

import groovy.transform.CompileStatic
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import io.micronaut.http.MediaType
import javax.inject.Inject
import groovy.json.JsonSlurper

@CompileStatic
@Controller("/") 
class Feeder {

    Gpio gpio
	SwitchBoxClient switchBoxClient
  
    @Inject
    Feeder(Gpio gpio, SwitchBoxClient switchBoxClient) {
      this.gpio = gpio
	  this.switchBoxClient = switchBoxClient
    }
	
    @Get("/feeder/{state}/{milliseconds}")
    @Produces(MediaType.TEXT_PLAIN)
    String run(int state, int milliseconds) {
		milliseconds = (milliseconds > 1000) ? 1000 : milliseconds;
		
		gpio.run(state, milliseconds)
		String retVal = "FEEDER OK, state = " + state + ", milliseconds = " + milliseconds;
    }
	
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
}