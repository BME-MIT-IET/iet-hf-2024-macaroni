import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import macaroni.model.effects.BananaEffect;
import macaroni.model.element.Pipe;
import macaroni.model.element.Pump;
import macaroni.model.misc.WaterCollector;
import macaroni.utils.ModelObjectFactory;
import macaroni.utils.Random;


public class PipeTester {

    private Pipe testPipe;
    private Pump mockPumpA;
    private Pump mockPumpB;
    private Pump mockPumpC;
    private WaterCollector mockWaterCollector;
    private BananaEffect bEffect;

    @BeforeEach
    public void init(){

        mockPumpA = mock(Pump.class);
        mockPumpB = mock(Pump.class);
        mockPumpC = mock(Pump.class);
        bEffect = mock(BananaEffect.class);
        mockWaterCollector = mock(WaterCollector.class);
        testPipe = new Pipe(mockWaterCollector, mockPumpA, mockPumpB);
    }

    @Test
    public void TestSplit()
    {
        ModelObjectFactory.setPipeCreatePipeName("test");
       Pipe pipe = testPipe.split();
       assertNotNull(pipe);
    }

    @Test
    public void removeEndpointTest(){
        boolean resultA = testPipe.removeEndpoint(mockPumpA);
        boolean resultB = testPipe.removeEndpoint(mockPumpA);
        testPipe.setOccupied(true);
        boolean resultC = testPipe.removeEndpoint(mockPumpB);

        assertTrue(resultA);
        assertFalse(resultB);
        assertFalse(resultC);

    }

    @Test
    public void addEndpointTest(){
        boolean resultA = testPipe.addEndpoint(mockPumpA);
        boolean resultC = testPipe.addEndpoint(mockPumpC);
        boolean resultB = testPipe.removeEndpoint(mockPumpA);
        boolean resultD = testPipe.addEndpoint(mockPumpA);


        assertFalse(resultA);
        assertTrue(resultB);
        assertFalse(resultC);
        assertTrue(resultD);

    }

    @Test
    public void testremoveAllWater(){
        int w1 = testPipe.getStoredWater();
        int w2 = testPipe.removeAllWater();
        int w3 = testPipe.getStoredWater();

        assertNotEquals(w1, 0);
        assertEquals(w1, w2);
        assertEquals(w3, 0);
        
    }

    @Test
    public void testRemoveWaterGreaterAmount(){

        int w1 = testPipe.getStoredWater();
        int w2 = testPipe.removeWater(60);
        int w3 = testPipe.getStoredWater();

        assertNotEquals(w1, 0);
        assertEquals(w2, 10);
        assertEquals(w3, 0);

    }

    @Test
    public void testRemoveWaterLesserAmount (){

        int w1 = testPipe.getStoredWater();
        int w2 = testPipe.removeWater(6);
        int w3 = testPipe.getStoredWater();

        assertNotEquals(w1, 0);
        assertEquals(w2, 6);
        assertEquals(w3, 4);

    }

    @Test
    public void testAddWater(){
        testPipe.pierce();
        int w0 = testPipe.addWater(60);
        testPipe.patch();
        int w1 = testPipe.addWater(10);
        int w2 = testPipe.addWater(60);
        testPipe.removeEndpoint(mockPumpA);
        int w3 = testPipe.addWater(60);
        assertEquals(w0, 0);
        assertEquals(w1, 0);
        assertEquals(w2, 60);
        assertEquals(w3, 0);

    }

    @Test
    public void pierceAndPatch(){
        assertFalse(testPipe.patch());
        assertTrue(testPipe.pierce());
        assertTrue(testPipe.isPierced());
        testPipe.setPierceCooldown(2);
        assertFalse(testPipe.pierce());
        assertTrue(testPipe.patch());
        assertFalse(testPipe.isPierced());
    }

    @Test
    public void testTick(){
        testPipe.setPierceCooldown(2);
        testPipe.tick();
        assertEquals(1, testPipe.getPierceCooldown());
        testPipe.tick();
        testPipe.tick();
        testPipe.tick();
        assertEquals(0, testPipe.getPierceCooldown());
    }

    @Test
    public void testLeave(){

        //Assert
        when(bEffect.leave()).thenReturn(false);

        assertTrue(testPipe.leave());
        testPipe.setOccupied(true);
        testPipe.setEffect(bEffect);
    }

    
}
