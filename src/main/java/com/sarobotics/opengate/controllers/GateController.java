package com.sarobotics.opengate.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

@RestController
public class GateController {
	
	private static final Logger log = LoggerFactory.getLogger(RestController.class);

    @GetMapping(value = "/apricancello")
    public ResponseEntity<String> apriCancello(){
    	String risposta = "Comando inviato!";
        new Thread(() -> {
            try {
                log.info("inizio apertura ");
                    // create gpio controller
                    final GpioController gpio = GpioFactory.getInstance();
                    // provision gpio pin #01 as an output pin and turn on
                    final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "MyLED", PinState.HIGH);
                    accendiPinPerMs(pin,600);
                    gpio.shutdown();
                    gpio.unprovisionPin(pin);
                log.info("fine apertura ");
            } catch (Exception e) {
                log.error("Codice 2" + e.getMessage());
            }
        }).start();
        return ResponseEntity.status(HttpStatus.OK).body(risposta);
    }


    @GetMapping(value = "/apricancelloeblocca")
    public ResponseEntity<String> apriCancelloEBlocca(){
    	 new Thread(() -> {
                try {
                    log.info("inizio apertura e bloccaggio cancello");
                    // create gpio controller
                    final GpioController gpio = GpioFactory.getInstance();
                    // provision gpio pin #01 as an output pin and turn on
                    final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "MyLED", PinState.HIGH);
                    pin.setShutdownOptions(true, PinState.LOW);
                    accendiPinPerMs(pin, 600);
                    Thread.sleep(30000);
                    accendiPinPerMs(pin, 600);
                    Thread.sleep(5000);
                    accendiPinPerMs(pin, 600);
                    gpio.shutdown();
                    gpio.unprovisionPin(pin);
                    log.info("fine apertura e bloccaggio cancello ");
                } catch (Exception e) {
                    log.error("Codice 1" + e.getMessage());
                }
         }).start();
        return ResponseEntity.status(HttpStatus.OK).body("Comando inviato");
    }

    public static void accendiPinPerMs(GpioPinDigitalOutput pin, int millisec) throws InterruptedException {
        // set shutdown state for this pin
        pin.high();
        log.info("--> GPIO state should be: ON");
        Thread.sleep(millisec);
        // turn off gpio pin #01
        pin.low();
        log.info("--> GPIO state should be: OFF");
    }

}
