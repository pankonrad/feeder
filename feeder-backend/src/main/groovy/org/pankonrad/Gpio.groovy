package org.pankonrad

import javax.annotation.PostConstruct
import javax.inject.Singleton

import com.pi4j.io.gpio.GpioController
import com.pi4j.io.gpio.GpioFactory
import com.pi4j.io.gpio.GpioPinDigitalOutput
import com.pi4j.io.gpio.PinState
import com.pi4j.io.gpio.RaspiPin

@Singleton
public class Gpio {

  private static GpioController gpio = null
  private static GpioPinDigitalOutput pin_25 = null, pin_28 = null, pin_29 = null

  @PostConstruct
  public void initialize() {
  
	try {
		Runtime.getRuntime().addShutdownHook(new Thread() {
		  @Override
		  public void run() {
			if (gpio != null) {
			  pin_25.low();
//			  pin_28.low();
//			  pin_29.low();
			  gpio.shutdown();
			}
		  }
		});

		gpio = GpioFactory.getInstance()

		pin_25 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_25, "pin_25", PinState.LOW)
//		pin_28 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_28, "pin_28", PinState.LOW)
//		pin_29 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_29, "pin_29", PinState.LOW)
		
	} catch(InterruptedException e) {
		println e;
	}
  }
  
  public void run(final int state, int milliseconds) {
	try {

	switch (state) {
		case 0:
			pin_25.low()			
			return
		case 1:
			pin_25.high()
			Thread.sleep(milliseconds == 0 ? 1000 : milliseconds)
			pin_25.low()
			return
	}
	  
	} catch(Exception e) {
		println e;
	}
  }
}