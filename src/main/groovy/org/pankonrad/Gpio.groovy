package org.pankonrad

import com.pi4j.io.gpio.GpioPinPwmOutput
import com.pi4j.io.gpio.GpioController
import com.pi4j.io.gpio.GpioFactory
import com.pi4j.io.gpio.GpioPinDigitalOutput
import com.pi4j.io.gpio.PinState
import com.pi4j.io.gpio.RaspiPin
import com.pi4j.io.gpio.Pin
import com.pi4j.util.CommandArgumentParser
import javax.inject.Singleton
import javax.annotation.PostConstruct

@Singleton
public class Gpio {

// All Raspberry Pi models support a hardware PWM pin on GPIO_01.
// Raspberry Pi models A+, B+, 2B, 3B also support hardware PWM pins: GPIO_23, GPIO_24, GPIO_26
// by default we will use gpio pin #01; however, if an argument
// has been provided, then lookup the pin by address
// you can optionally use these wiringPi methods to further customize the PWM generator
// see: http://wiringpi.com/reference/raspberry-pi-specifics/

  private static GpioController gpio = null
  private static GpioPinDigitalOutput red = null, yellow = null, green = null
  private static GpioPinPwmOutput pwmPin = null

  @PostConstruct
  public void initialize() {
  
	try {
		Runtime.getRuntime().addShutdownHook(new Thread() {
		  @Override
		  public void run() {
			if (gpio != null) {
			  red.low();
			  yellow.low();
			  green.low();
			  gpio.shutdown();
			}
		  }
		});

		gpio = GpioFactory.getInstance()

		red = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_27, "red", PinState.LOW)
		yellow = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_28, "yellow", PinState.LOW)
		green = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_29, "green", PinState.LOW)
		
		Pin pin = CommandArgumentParser.getPin(RaspiPin.class, RaspiPin.GPIO_01, new String[0])
        pwmPin = gpio.provisionPwmOutputPin(pin)
		
	} catch(InterruptedException e) {
		println e;
	}
  }
  
  public void run(final int state, int milliseconds) {
	try {

	switch (state) {
		case 0:
			red.low()
			yellow.low()
			green.low()
			
			return
		case 1:
			red.high()
			yellow.low()
			green.high()
			
			Thread.sleep(milliseconds == 0 ? 1000 : milliseconds)

			red.low()
			yellow.low()
			green.low()
			
			return
	}
	  
	} catch(Exception e) {
		println e;
	}
  }
  
  public void runPwm(final int state, int rate, int range) {
	try {	

		switch (state) {
			case 0:
				pwmPin.setPwm(0)		
				return
			case 1:
				com.pi4j.wiringpi.Gpio.pwmSetMode(com.pi4j.wiringpi.Gpio.PWM_MODE_MS);
				com.pi4j.wiringpi.Gpio.pwmSetRange(range == 0 ? 1000 : range);
				com.pi4j.wiringpi.Gpio.pwmSetClock(rate == 0 ? 500 : rate);
				
				pwmPin.setPwm(rate)
				return
		}
	
	} catch(Exception e) {
		println e;
	}
  }
}