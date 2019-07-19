package org.pankonrad

import groovy.transform.CompileStatic
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import io.micronaut.http.MediaType

@CompileStatic
@Controller("/feeder") 
class Feeder {

	final String preInitCmd = """echo 16 > /sys/class/gpio/unexport && echo 20 > /sys/class/gpio/unexport && echo 21 > /sys/class/gpio/unexport"""
	final String initCmd = """echo 16 > /sys/class/gpio/export && echo 20 > /sys/class/gpio/export && echo 21 > /sys/class/gpio/export && echo out > /sys/class/gpio/gpio16/direction && echo out > /sys/class/gpio/gpio20/direction && echo out > /sys/class/gpio/gpio21/direction && echo 1 > /sys/class/gpio/gpio16/value && echo 0 > /sys/class/gpio/gpio20/value && echo 0 > /sys/class/gpio/gpio21/value"""
	
	final String feederCmd1 = "echo 1 > /sys/class/gpio/gpio21/value"
	final String feederCmd2 = "sleep 3"
	final String feederCmd3 = "echo 0 > /sys/class/gpio/gpio21/value"
	
	
	private Gpio gpio = new Gpio()

    @Get("/")
    @Produces(MediaType.TEXT_PLAIN)
    String run() {
		gpio.run()
		return "Done"
    }
}