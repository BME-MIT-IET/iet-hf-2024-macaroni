package macaroni;

import macaroni.model.character.Plumber;
import macaroni.model.element.Cistern;
import macaroni.model.element.Pipe;
import macaroni.model.element.Pump;
import macaroni.model.element.Spring;
import macaroni.model.misc.WaterCollector;
import macaroni.utils.ModelObjectFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AttachTests {

    private WaterCollector ground;
    private WaterCollector cisternCollector;

    @BeforeEach
    public void setUp() {
        ModelObjectFactory.reset();

        ground = new WaterCollector();
        cisternCollector = new WaterCollector();
    }

    @Test
    public void attachBothEndsOfPipe() {
        // Arrange
        Pump pump1 = new Pump();
        Pump pump2 = new Pump();
        Pipe pipe = new Pipe(ground);
        Plumber plumber1 = new Plumber(pump1);
        Plumber plumber2 = new Plumber(pump2);

        pump1.addPipe(pipe);
        pump2.addPipe(pipe);

        // Act
        boolean success1 = plumber1.detachPipe(pump1, pipe);
        boolean success2 = plumber2.detachPipe(pump2, pipe);

        // Assert
        assertTrue(success1);
        assertTrue(success2);
        assertSame(plumber1.getHeldPipe(), pipe);
        assertSame(plumber2.getHeldPipe(), pipe);

        // Act
        boolean success3 = plumber1.attachPipe(pump1);
        boolean success4 = plumber2.attachPipe(pump2);

        // Assert
        assertTrue(success3);
        assertTrue(success4);
        assertNull(plumber1.getHeldPipe());
        assertNull(plumber2.getHeldPipe());
    }

    @Test
    public void attachBothEndsOfPipeToSamePump() {
        // Arrange
        Pump pump = new Pump();
        Pipe pipe = new Pipe(ground);
        Plumber plumber = new Plumber(pump, null, pipe);

        pump.addPipe(pipe);

        // Act
        boolean success = plumber.attachPipe(pump);

        // Assert
        assertFalse(success);
        assertSame(plumber.getHeldPipe(), pipe);
    }

    @Test
    public void attachBothEndsOfPipeToSameCistern() {
        // Arrange
        Pipe pipe = new Pipe(ground);
        Cistern cistern = new Cistern(cisternCollector);
        Plumber plumber = new Plumber(cistern, null, pipe);

        cistern.addPipe(pipe);

        // Act
        boolean success = plumber.attachPipe(cistern);

        // Assert
        assertFalse(success);
        assertSame(plumber.getHeldPipe(), pipe);
    }

    @Test
    public void attachBothEndsOfPipeToSameSpring() {
        // Arrange
        Pipe pipe = new Pipe(ground);
        Spring spring = new Spring();
        Plumber plumber = new Plumber(spring, null, pipe);

        spring.addPipe(pipe);

        // Act
        boolean success = plumber.attachPipe(spring);

        // Assert
        assertFalse(success);
        assertSame(plumber.getHeldPipe(), pipe);
    }

    @Test
    public void attachPipeToPumpWithNoFreePorts() {
        // Arrange
        Pump pump = new Pump(2);
        Pipe pipe1 = new Pipe(ground);
        Pipe pipe2 = new Pipe(ground);
        Pipe pipe3 = new Pipe(ground);

        Plumber plumber = new Plumber(pump, null, pipe3);

        pump.addPipe(pipe1);
        pump.addPipe(pipe2);

        // Act
        boolean success = plumber.attachPipe(pump);

        // Assert
        assertFalse(success);
        assertSame(plumber.getHeldPipe(), pipe3);
        assertSame(pipe3.getEndpoint(0), null);
    }
}
