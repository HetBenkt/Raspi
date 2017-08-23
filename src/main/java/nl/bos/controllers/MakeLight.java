package nl.bos.controllers;

import com.pi4j.io.gpio.*;
import lombok.extern.java.Log;
import nl.bos.entities.Pin;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bosa on 11-8-2017.
 * Spring rest controller for handling the HTTP requests from the GUI to Pi4J library
 */
@Log
@RestController
@RequestMapping("/api")
public class MakeLight {
    private final Pin pin = new Pin();
    private GpioController gpio;

    @RequestMapping("/test")
    public ResponseEntity<Map<String,String>> apiTest() {
        Map<String,String> result = new HashMap<>();
        result.put("message", "Initialized...");
        return ResponseEntity.ok().body(result);
    }

    @RequestMapping("/gpio/init")
    public ResponseEntity<Pin> gpioInit() {
        gpio = GpioFactory.getInstance();
        return new ResponseEntity<>(pin, HttpStatus.OK);
    }

    @RequestMapping("/gpio/off")
    public ResponseEntity<Pin> gpioOff() {
        gpio.shutdown();
        return new ResponseEntity<>(pin, HttpStatus.OK);
    }

    @RequestMapping("/led/on")
    public ResponseEntity<Pin> ledOn() {
        final GpioPinDigitalOutput gpioPin = initGpioPin();
        if(gpioPin.isLow()) {
            gpioPin.high();
            log.info("LED IS ON");
            pin.setState(gpioPin.getState());
        }
        gpio.unprovisionPin(gpioPin);
        return new ResponseEntity<>(pin, HttpStatus.OK);
    }

    @RequestMapping("/led/off")
    public ResponseEntity<Pin> ledOff() {
        final GpioPinDigitalOutput gpioPin = initGpioPin();
        if(gpioPin.isHigh()) {
            gpioPin.low();
            log.info("LED IS OFF");
            pin.setState(gpioPin.getState());
        }

        gpio.unprovisionPin(gpioPin);
        return new ResponseEntity<>(pin, HttpStatus.OK);
    }

    @RequestMapping("/led/blink")
    public ResponseEntity<Pin> ledBlink() {
        final GpioPinDigitalOutput gpioPin = initGpioPin();
        if(gpioPin.isLow()) {
            gpioPin.blink(1000);
            log.info("LED IS BLINKING");
            pin.setState(gpioPin.getState());
        }

        gpio.unprovisionPin(gpioPin);
        return new ResponseEntity<>(pin, HttpStatus.OK);
    }

    @RequestMapping("/led/pulse")
    public ResponseEntity<Pin> ledPulse() {
        final GpioPinDigitalOutput gpioPin = initGpioPin();
        if(gpioPin.isLow()) {
            gpioPin.pulse(1000);
            log.info("LED IS PULSING");
            pin.setState(gpioPin.getState());
        }

        gpio.unprovisionPin(gpioPin);
        return new ResponseEntity<>(pin, HttpStatus.OK);
    }

    private GpioPinDigitalOutput initGpioPin() {
        final GpioPinDigitalOutput gpioPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01);
        gpioPin.setName("GPIO_01");
        pin.setName(gpioPin.getName());
        gpioPin.setShutdownOptions(true, PinState.LOW);
        return gpioPin;
    }
}