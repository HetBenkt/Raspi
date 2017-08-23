package nl.bos.entities;

import com.pi4j.io.gpio.PinState;
import lombok.Data;

/**
 * Created by bosa on 22-8-2017.
 */
@Data
public class Pin {
    PinState state;
    String name;
}
