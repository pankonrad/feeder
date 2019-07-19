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
  
    @Inject
    Feeder(Gpio gpio) {
      this.gpio = gpio
    }
	
    @Get("/feeder/{state}/{milliseconds}")
    @Produces(MediaType.TEXT_PLAIN)
    String run(int state, int milliseconds) {
		gpio.run(state, milliseconds)
		return "FEEDER OK"
    }
	
	@Get("/pwm/{state}/{rate}/{range}")
    @Produces(MediaType.TEXT_PLAIN)
    String run(int state, int rate, int range) {
		gpio.runPwm(state, rate, range)
		return "PWM PIN OK"
    }
}