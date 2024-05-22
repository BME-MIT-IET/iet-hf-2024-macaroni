package macaroni.model.element;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class PumpTester {
    
    private Pump testPump;
    private Pump testEquipedPump;
    private Pipe mockPipe;
    private Pipe mockPipeIn;
    private Pipe mockPipeOut;
    private Pipe mockPipeAdd;
    private Pipe mockPipeAdd2;
    private int testNumber = 6;

    @BeforeEach
    public void init(){
        testPump = new Pump(6);
        mockPipe = mock(Pipe.class);
        mockPipeIn = mock(Pipe.class);
        mockPipeOut = mock(Pipe.class);
        mockPipeAdd = mock(Pipe.class);
        mockPipeAdd2 = mock(Pipe.class);
        testEquipedPump = new Pump(mockPipeIn, mockPipeOut, mockPipe, testNumber);
    }

    
    @Test
    public void testingBreak(){

        boolean result0 = testPump.isBroken();
        boolean represult0 = testPump.repair();
        boolean result1 = testPump.Break();
        boolean result2 = testPump.isBroken();
        boolean result3 = testPump.Break();
        boolean result4 = testPump.isBroken();
        boolean represult1 = testPump.repair();
        boolean result5 = testPump.isBroken();


        assertEquals(result0, false);
        assertEquals(result1, true);
        assertEquals(result2, true);
        assertEquals(result3, false);
        assertEquals(result4, true);
        assertEquals(result5, false);
        assertEquals(represult0, false);
        assertEquals(represult1, true);
     }

     @Test
     public void testTick(){
        //Arrange
        when(mockPipeIn.removeWater(5)).thenReturn(5);
        when(mockPipeOut.addWater(testNumber)).thenReturn(0);

        //Act

        testEquipedPump.tick();
        assertEquals(testEquipedPump.getStoredWater(), 5);
        testEquipedPump.Break();
        testEquipedPump.tick();
        assertEquals(testEquipedPump.getStoredWater(), 5);
        testPump.tick();

        //Assert
        verify(mockPipeIn, times(1)).removeWater(5);
        verify(mockPipeOut, times(1)).addWater(6);

     }

     @Test
     public void testAddPipe(){

        //Arrange
        when(mockPipe.addEndpoint(testEquipedPump)).thenReturn(true);
        when(mockPipeAdd.addEndpoint(testEquipedPump)).thenReturn(true);
        when(mockPipeAdd2.addEndpoint(testEquipedPump)).thenReturn(true);

        //Act
        boolean result0 = testEquipedPump.addPipe(mockPipe);
        boolean result1 = testEquipedPump.addPipe(mockPipeAdd);
        boolean result2 = testEquipedPump.addPipe(mockPipeAdd2);

        //Assert
        assertEquals(result0, false);
        assertEquals(result1, true);
        assertEquals(result2, false);

        verify(mockPipe, times(0)).addEndpoint(testEquipedPump);
        verify(mockPipeAdd, times(1)).addEndpoint(testEquipedPump);
        verify(mockPipeAdd2, times(0)).addEndpoint(testEquipedPump);

     }

     @Test
     public void testRemovePipe(){

        //Arrange
        when(mockPipe.removeEndpoint(testEquipedPump)).thenReturn(false);
        when(mockPipeIn.removeEndpoint(testEquipedPump)).thenReturn(true);
        when(mockPipeOut.removeEndpoint(testEquipedPump)).thenReturn(true);

        //Act
        Pipe outPipe = testEquipedPump.getOutputPipe();
        Pipe inPipe = testEquipedPump.getInputPipe();

        boolean result0 = testEquipedPump.removePipe(mockPipe);
        boolean result1 = testEquipedPump.removePipe(mockPipeIn);
        boolean result2 = testEquipedPump.removePipe(mockPipeOut);

        Pipe outPipeAfter = testEquipedPump.getOutputPipe();
        Pipe inPipeAfter = testEquipedPump.getInputPipe();

        //Assert
        assertEquals(result0, false);
        assertEquals(result1, true);
        assertEquals(result2, true);
        assertNotNull(inPipe);
        assertNotNull(outPipe);
        assertNull(inPipeAfter);
        assertNull(outPipeAfter);


        verify(mockPipe, times(1)).removeEndpoint(testEquipedPump);
        verify(mockPipeIn, times(1)).removeEndpoint(testEquipedPump);
        verify(mockPipeOut, times(1)).removeEndpoint(testEquipedPump);

     }

     @Test
     public void setInPipeTest(){

        boolean result1 = testEquipedPump.setInputPipe(mockPipeAdd);
        boolean result2 = testEquipedPump.setInputPipe(mockPipeIn);
        boolean result3 = testEquipedPump.setInputPipe(mockPipeOut);
        boolean result4 = testEquipedPump.setInputPipe(mockPipe);
        Pipe pipe = testEquipedPump.getInputPipe();

        assertEquals(pipe, mockPipe);
        assertFalse(result1);
        assertFalse(result2);
        assertFalse(result3);
        assertTrue(result4);

     }

     @Test
     public void setOutPipeTest(){

        boolean result1 = testEquipedPump.setOutputPipe(mockPipeAdd);
        boolean result2 = testEquipedPump.setOutputPipe(mockPipeIn);
        boolean result3 = testEquipedPump.setOutputPipe(mockPipeOut);
        boolean result4 = testEquipedPump.setOutputPipe(mockPipe);
        Pipe pipe = testEquipedPump.getOutputPipe();

        assertEquals(pipe, mockPipe);
        assertFalse(result1);
        assertFalse(result2);
        assertFalse(result3);
        assertTrue(result4);

     }
    
}
