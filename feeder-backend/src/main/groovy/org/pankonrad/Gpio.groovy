package org.pankonrad

import com.pi4j.io.gpio.GpioPinDigitalInput

import javax.annotation.PostConstruct
import javax.inject.Singleton

import com.pi4j.io.gpio.GpioController
import com.pi4j.io.gpio.GpioFactory
import com.pi4j.io.gpio.GpioPinDigitalOutput
import com.pi4j.io.gpio.PinState
import com.pi4j.io.gpio.RaspiPin
import java.util.HashMap

@Singleton
public class Gpio {

  private static GpioController gpio = null
  private static GpioPinDigitalOutput pin_25 = null, pin_28 = null, pin_29 = null

	public FeederResponse states() {
		FeederResponse resp = new FeederResponse()
		resp.feeder1State = pin_25.getState().high;
		resp.feeder2State = pin_28.getState().high
		resp.feeder3State = pin_29.getState().high
		return resp;

	}
  @PostConstruct
  public void initialize() {
  
	try {
		Runtime.getRuntime().addShutdownHook(new Thread() {
		  @Override
		  public void run() {
			if (gpio != null) {
			
			  pin_25.high();
			  pin_28.high();
			  pin_29.high();
		
			  gpio.shutdown();
			}
		  }
		});

		gpio = GpioFactory.getInstance()
		
		pin_25 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_25, "pin_25", PinState.HIGH)
		pin_28 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_28, "pin_28", PinState.HIGH)
		pin_29 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_29, "pin_29", PinState.HIGH)
		
		pin_25.high();
		pin_28.high();
		pin_29.high();
		
	} catch(InterruptedException e) {
		println e;
	}
  }

	private GpioPinDigitalOutput evaluatePin(int pin) {
		if(pin == 25) {
			return pin_25
		} else if(pin == 28) {
			return pin_28
		} else if(pin == 29) {
			return pin_29
		}
	}
  public int run(int pin, final int state, int milliseconds) {
	  try {
		  switch (state) {
			  case 0:
				  evaluatePin(pin).low()
				  break;
			  case 1:
				  evaluatePin(pin).high()
				  break;
			  case 2:
				  evaluatePin(pin).toggle()
				  break;
		  }

	  } catch (Exception e) {
		  println e;
	  }

	  return evaluatePin(pin).getState().value;
  }
  
  public void pump(final int pin28State, final int pin29State) {
	try {
		
		if(pin28State == 0) {
			pin_28.high()
		} else {
			pin_28.low()
		}

		if(pin29State == 0) {
			pin_29.high()
		} else {
			pin_29.low()
		}
		
		return
	
	} catch(Exception e) {
		println e;
	}
  }
}