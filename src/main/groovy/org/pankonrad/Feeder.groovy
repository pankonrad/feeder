package org.pankonrad

import groovy.transform.CompileStatic
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import io.micronaut.http.MediaType
import javax.inject.Inject

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
		
//		changeFilterState(0)
		
		gpio.run(state, milliseconds)
		String retVal = "FEEDER OK, state = " + state + ", milliseconds = " + milliseconds;
    }
	
	@Get("/light/{state}")
    @Produces(MediaType.TEXT_PLAIN)
	String changeLightState(int state) {
		return switchBoxClient.light(state)
	}
	
	@Get("/filter/{state}")
    @Produces(MediaType.TEXT_PLAIN)
	String changeFilterState(int state) {
		return switchBoxClient.filter(state)
	}
	
/*
	@Get("/pwm/{state}/{rate}/{range}")
    @Produces(MediaType.TEXT_PLAIN)
    String run(int state, int rate, int range) {
		gpio.runPwm(state, rate, range)
		return "PWM PIN OK"
    }
*/
}