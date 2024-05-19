package macaroni.test;

import macaroni.model.character.Plumber;
import macaroni.model.element.Pipe;
import macaroni.model.element.Pump;
import macaroni.model.misc.WaterCollector;
import macaroni.utils.ModelObjectFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CharacterMoveTest {

    private Pipe pipe;
    private Pump pump1;
    private Pump pump2;

    @BeforeEach
    void setup() {
        ModelObjectFactory.reset();
        WaterCollector ground = new WaterCollector();
        pipe = new Pipe(ground);
        pump1 = new Pump();
        pump2 = new Pump();
    }

    @Test
    void moveToUnoccupiedPipe() {
        assertTrue(pump1.addPipe(pipe));
        assertTrue(pump2.addPipe(pipe));
        var plumber = new Plumber(pump1);

        boolean success = plumber.moveTo(pipe);

        assertTrue(success);
        assertEquals(pipe, plumber.getLocation());
    }
}
