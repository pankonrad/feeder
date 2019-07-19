package org.pankonrad

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class Gpio {
  private static GpioController gpio = null; 
  private static GpioPinDigitalOutput red = null;
  private static GpioPinDigitalOutput yellow = null;
  private static GpioPinDigitalOutput green = null;

  public Gpio() {
  
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

		gpio = GpioFactory.getInstance();

		red = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_27, "Red", PinState.LOW);
		yellow = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_28, "Red", PinState.LOW);
		green = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_29, "Red", PinState.LOW);
		
	} catch(InterruptedException e) {
		println e;
	}
  }
  
  public void run() {
	try {
      red.high();
      yellow.low();
      green.high();
      Thread.sleep(3000);
	  
	  red.low();
	  yellow.low();
	  green.low();
	} catch(Exception e) {
		println e;
	}
  }
}