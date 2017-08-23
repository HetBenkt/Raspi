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
 */
@Log
@RestController
@RequestMapping("/api")
public class MakeLight {
    private Pin pin = new Pin();
    private GpioController gpio;

    @RequestMapping("/test")
    public ResponseEntity<Map<String,String>> apiTest() {
        Map<String,String> result = new HashMap<String,String>();
        result.put("message", "Initialized...");
        return ResponseEntity.ok().body(result);
    }

    @RequestMapping("/gpio/init")
    public ResponseEntity<Pin> gpioInit() {
        gpio = GpioFactory.getInstance();
        return new ResponseEntity<Pin>(pin, HttpStatus.OK);
    }

    @RequestMapping("/gpio/off")
    public ResponseEntity<Pin> gpioOff() {
        gpio.shutdown();
        return new ResponseEntity<Pin>(pin, HttpStatus.OK);
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
        return new ResponseEntity<Pin>(pin, HttpStatus.OK);
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
        return new ResponseEntity<Pin>(pin, HttpStatus.OK);
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
        return new ResponseEntity<Pin>(pin, HttpStatus.OK);
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
        return new ResponseEntity<Pin>(pin, HttpStatus.OK);
    }

    private GpioPinDigitalOutput initGpioPin() {
        final GpioPinDigitalOutput gpioPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01);
        gpioPin.setName("GPIO_01");
        pin.setName(gpioPin.getName());
        gpioPin.setShutdownOptions(true, PinState.LOW);
        return gpioPin;
    }

//    @RequestMapping("/pin/sample")
//    public void ledControl() throws InterruptedException {
//        log.info("START APP");
//
//        final GpioController gpio = GpioFactory.getInstance();
//        final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01);
//
//        pin.setShutdownOptions(true, PinState.LOW);
//
//        if(pin.isLow()) {
//            pin.high();
//            log.info(String.format("Pin %s has state %s. Light is ON", pin.getName(), pin.getState()));
//            Thread.sleep(3000);
//        }
//
//        if(pin.isHigh()) {
//            pin.low();
//            log.info(String.format("Pin %s has state %s. Light is OFF", pin.getName(), pin.getState()));
//            Thread.sleep(3000);
//        }
//
//        log.info(String.format("Light is BLINKING", pin.getName(), pin.getState()));
//        for (int i=0; i<10; i++) {
//            pin.high();
//            Thread.sleep(500);
//            pin.low();
//            Thread.sleep(500);
//        }
//
//        if(pin.isLow()) {
//            pin.high();
//            log.info(String.format("Pin %s has state %s. Light is ON", pin.getName(), pin.getState()));
//            Thread.sleep(2000);
//        }
//
//        pin.toggle();
//        log.info(String.format("Pin %s has state %s.", pin.getName(), pin.getState()));
//        Thread.sleep(2000);
//
//        if(pin.isHigh())
//            pin.low();
//        pin.pulse(1000);
//        log.info(String.format("Pin %s has state %s. Light is PULSE", pin.getName(), pin.getState()));
//        Thread.sleep(2000);
//
//        if(pin.isHigh())
//            pin.low();
//        pin.blink(100, 5000);
//        log.info(String.format("Pin %s has state %s. Light is BLINK", pin.getName(), pin.getState()));
//        Thread.sleep(2000);
//
//        gpio.shutdown();
//        log.info("GPIO shutdown");
//        gpio.unprovisionPin(pin);
//        log.info(String.format("Pin %s ìs unprovisioned and has state %s.", pin.getName(), pin.getState()));
//
//        log.info("END APP");
//    }
}