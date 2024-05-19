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

public class DetachTests {

    private WaterCollector ground;
    private WaterCollector cisternCollector;

    @BeforeEach
    public void setUp() {
        ModelObjectFactory.reset();

        ground = new WaterCollector();
        cisternCollector = new WaterCollector();
    }

    @Test
    public void detachBothEndsOfUnoccupiedPipeFromPump() {
        // Arrange
        Pump pump1 = new Pump();
        Pump pump2 = new Pump();

        Plumber plumber1 = new Plumber(pump1);
        Plumber plumber2 = new Plumber(pump2);

        Pipe pipe = new Pipe(ground);
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
        assertSame(plumber1.getLocation(), pump1);
        assertSame(plumber2.getLocation(), pump2);
        assertNull(pipe.getEndpoint(0));
        assertNull(pipe.getEndpoint(1));
    }

    @Test
    public void detachPipeFromCistern() {
        // Arrange
        Pipe pipe = new Pipe(ground);
        Pump pump = new Pump();
        Cistern cistern = new Cistern(cisternCollector);
        Plumber plumber = new Plumber(cistern);

        pump.addPipe(pipe);
        cistern.addPipe(pipe);

        // Act
        boolean success = plumber.detachPipe(cistern, pipe);

        // Assert
        assertTrue(success);

        assertSame(plumber.getHeldPipe(), pipe);
        assertSame(plumber.getLocation(), cistern);
        assertNull(pipe.getEndpoint(1));
        assertSame(pipe.getEndpoint(0), pump);
    }

    @Test
    public void detachPipeFromSpring() {
        // Arrange
        Pipe pipe = new Pipe(ground);
        Pump pump = new Pump();
        Spring spring = new Spring();
        Plumber plumber = new Plumber(spring);

        pump.addPipe(pipe);
        spring.addPipe(pipe);

        // Act
        boolean success = plumber.detachPipe(spring, pipe);

        // Assert
        assertTrue(success);

        assertSame(plumber.getHeldPipe(), pipe);
        assertSame(plumber.getLocation(), spring);
        assertNull(pipe.getEndpoint(1));
        assertSame(pipe.getEndpoint(0), pump);
    }

    @Test
    public void detachOccupiedPipe() {
        // Arrange
        Pipe pipe = new Pipe(ground);
        Pump pump1 = new Pump();
        Pump pump2 = new Pump();

        pump1.addPipe(pipe);
        pump2.addPipe(pipe);

        Plumber plumber1 = new Plumber(pump1);
        Plumber plumber2 = new Plumber(pipe);

        // Act
        boolean success = plumber1.detachPipe(pump1, pipe);

        // Assert
        assertFalse(success);

        assertSame(plumber1.getHeldPipe(), null);
        assertSame(plumber1.getLocation(), pump1);
        assertSame(plumber2.getLocation(), pipe);
        assertSame(pipe.getEndpoint(0), pump1);
        assertSame(pipe.getEndpoint(1), pump2);
    }

    @Test
    public void detachPipeEndWhileHoldingAnotherPipeEnd() {
        // Arrange
        Pipe pipe1 = new Pipe(ground);
        Pipe pipe2 = new Pipe(ground);
        Pump pump1 = new Pump();
        Pump pump2 = new Pump();
        Plumber plumber = new Plumber(pump1);

        pump1.addPipe(pipe1);
        pump1.addPipe(pipe2);
        pump2.addPipe(pipe1);
        pump2.addPipe(pipe2);

        // Act
        boolean success = plumber.detachPipe(pump1, pipe2);

        // Assert
        assertTrue(success);
        assertSame(plumber.getHeldPipe(), pipe2);
        assertSame(plumber.getLocation(), pump1);

        // Act
        success = plumber.detachPipe(pump1, pipe1);

        // Assert
        assertFalse(success);
        assertSame(plumber.getHeldPipe(), pipe2);
        assertSame(plumber.getLocation(), pump1);
    }

    @Test
    public void detachBothEndsOfNewPipe() {
        // Arrange
        Cistern cistern = new Cistern(cisternCollector);
        Plumber plumber1 = new Plumber(cistern);
        Plumber plumber2 = new Plumber(cistern);

        ModelObjectFactory.setCisternCreatePipeGround(ground);
        ModelObjectFactory.setCisternCreatePipeName("pipe");
        cistern.spawnPipe();
        Pipe pipe = (Pipe) ModelObjectFactory.getObject("pipe");

        // Assert
        assertSame(pipe.getEndpoint(0), cistern);

        // Act
        boolean success = plumber1.detachPipe(cistern, pipe);

        // Assert
        assertTrue(success);
        assertSame(plumber1.getHeldPipe(), pipe);
        assertSame(pipe.getEndpoint(0), cistern);

        // Act
        success = plumber2.detachPipe(cistern, pipe);

        // Assert
        assertTrue(success);
        assertSame(plumber2.getHeldPipe(), pipe);
        assertNull(pipe.getEndpoint(0));
    }
}
