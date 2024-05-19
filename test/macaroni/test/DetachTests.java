package macaroni.test;

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
import static org.mockito.Mockito.*;

public class DetachTests {

    private WaterCollector ground;
    private WaterCollector cisternCollector;

    @BeforeEach
    public void setUp() {
        ModelObjectFactory.reset();

        ground = spy(new WaterCollector());
        cisternCollector = spy(new WaterCollector());
    }

    @Test
    public void detachBothEndsOfUnoccupiedPipeFromPump() {
        // Arrange
        Pump pump1 = spy(new Pump());
        Pump pump2 = spy(new Pump());

        Plumber plumber1 = spy(new Plumber(pump1));
        Plumber plumber2 = spy(new Plumber(pump2));

        Pipe pipe = spy(new Pipe(ground));
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

        verify(pump1, times(1)).removePipe(pipe);
        verify(pump2, times(1)).removePipe(pipe);
        verify(pipe, times(1)).removeEndpoint(pump1);
        verify(pipe, times(1)).removeEndpoint(pump2);
        verify(ground, times(2)).storeAmount(0);
    }

    @Test
    public void detachPipeFromCistern() {
        // Arrange
        Pipe pipe = spy(new Pipe(ground));
        Pump pump = spy(new Pump());
        Cistern cistern = spy(new Cistern(cisternCollector));
        Plumber plumber = spy(new Plumber(cistern));

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

        verify(cistern, times(1)).removePipe(pipe);
        verify(pipe, times(1)).removeEndpoint(cistern);
        verify(pump, never()).removePipe(pipe);
        verify(pipe, never()).removeEndpoint(pump);
        verify(ground, times(1)).storeAmount(0);
    }

    @Test
    public void detachPipeFromSpring() {
        // Arrange
        Pipe pipe = spy(new Pipe(ground));
        Pump pump = spy(new Pump());
        Spring spring = spy(new Spring());
        Plumber plumber = spy(new Plumber(spring));

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

        verify(spring, times(1)).removePipe(pipe);
        verify(pipe, times(1)).removeEndpoint(spring);
        verify(pump, never()).removePipe(pipe);
        verify(pipe, never()).removeEndpoint(pump);
        verify(ground, times(1)).storeAmount(0);
    }

    @Test
    public void detachOccupiedPipe() {
        // Arrange
        Pipe origPipe = new Pipe(ground);
        Pipe pipe = spy(origPipe);
        Pump origPump1 = new Pump();
        Pump pump1 = spy(origPump1);
        Pump pump2 = spy(new Pump());

        pump1.addPipe(pipe);
        pump2.addPipe(pipe);

        Plumber plumber1 = spy(new Plumber(pump1));
        Plumber plumber2 = spy(new Plumber(pipe));

        assertSame(plumber2.getLocation(), pipe);

        // Act
        boolean success = plumber1.detachPipe(pump1, pipe);

        // Assert
        assertFalse(success);

        assertSame(plumber1.getHeldPipe(), null);
        assertSame(plumber1.getLocation(), pump1);
        assertSame(plumber2.getLocation(), pipe);
        assertSame(pipe.getEndpoint(0), pump1);
        assertSame(pipe.getEndpoint(1), pump2);

        verify(pump1, times(1)).removePipe(pipe);
        verify(pump2, never()).removePipe(pipe);
        verify(pipe, times(1)).removeEndpoint(pump1);
        verify(pipe, never()).removeEndpoint(pump2);
        verify(ground, never()).storeAmount(0);
    }

    @Test
    public void detachPipeEndWhileHoldingAnotherPipeEnd() {
        // Arrange
        Pipe pipe1 = spy(new Pipe(ground));
        Pipe pipe2 = spy(new Pipe(ground));
        Pump pump1 = spy(new Pump());
        Pump pump2 = spy(new Pump());
        Plumber plumber = spy(new Plumber(pump1));

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
        verify(pump1, times(1)).removePipe(pipe2);
        verify(pipe2, times(1)).removeEndpoint(pump1);

        // Act
        success = plumber.detachPipe(pump1, pipe1);

        // Assert
        assertFalse(success);
        assertSame(plumber.getHeldPipe(), pipe2);
        assertSame(plumber.getLocation(), pump1);
        verify(pump1, never()).removePipe(pipe1);
        verify(pipe1, never()).removeEndpoint(pump1);
    }

    @Test
    public void detachBothEndsOfNewPipe() {
        // Arrange
        Cistern cistern = spy(new Cistern(cisternCollector));
        Plumber plumber1 = spy(new Plumber(cistern));
        Plumber plumber2 = spy(new Plumber(cistern));

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
