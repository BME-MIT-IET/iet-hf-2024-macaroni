//package macaroni.model.character;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import macaroni.model.element.Pump;
import macaroni.model.element.Pipe;
import macaroni.model.character.Plumber;
import macaroni.model.element.Cistern;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.*;

public class PlumberTester{

    private Pump mockPump;
    private Pipe mockPipe;
    private Pump mockPumpheld;
    private Pipe mockPipeheld;
    private Cistern mockCistern;
    private Plumber testPlumberOnPipe;
    private Plumber testPlumberOnPump;
    private Plumber testPlumberOnCistern;
    private Plumber testEquipedPlumberOnPipe;
    private Plumber testEquipedPlumberOnPump;
    

    @BeforeEach
    public void init(){
        mockPump = mock(Pump.class);
        mockPipe = mock(Pipe.class);
        mockPumpheld = mock(Pump.class);
        mockPipeheld = mock(Pipe.class);
        mockCistern = mock(Cistern.class);
        testPlumberOnPump = new Plumber(mockPump);
        testPlumberOnPipe = new Plumber(mockPipe);
        testPlumberOnCistern = new Plumber(mockCistern);
        testEquipedPlumberOnPipe = new Plumber(mockPipe, Collections.singletonList(mockPumpheld), mockPipeheld);
        testEquipedPlumberOnPump = new Plumber(mockPump, Collections.singletonList(mockPumpheld), mockPipeheld);


    }

    @Test
    public void RepairPipeItStandsOnSuccess(){
        
        //Arrange
        when(mockPipe.patch()).thenReturn(true);

        //Act
        boolean result = testPlumberOnPipe.repair(mockPipe);
        assertEquals(result, true);


        //Assert
        verify(mockPipe, times(1)).patch();
    }

    @Test
    public void RepairPipeItStandsOnFail(){
        
        //Arrange
        when(mockPipe.patch()).thenReturn(false);

        //Act
        boolean result = testPlumberOnPipe.repair(mockPipe);
        assertEquals(result, false);


        //Assert
        verify(mockPipe, times(1)).patch();
    }

    @Test
    public void RepairPumpItStandsOnSuccess(){
        
        //Arrange
        when(mockPump.repair()).thenReturn(true);

        //Act
        boolean result = testPlumberOnPump.repair(mockPump);
        assertEquals(result, true);


        //Assert
        verify(mockPump, times(1)).repair();
    }

    @Test
    public void RepairPumpItStandsOnFail(){
        
        //Arrange
        when(mockPump.repair()).thenReturn(false);

        //Act
        boolean result = testPlumberOnPump.repair(mockPump);
        assertEquals(result, false);


        //Assert
        verify(mockPump, times(1)).repair();
    }

    @Test
    public void RepairPumpItNotStandsOnFail(){
        
        //Arrange
        when(mockPump.repair()).thenReturn(true);

        //Act
        boolean result = testPlumberOnPipe.repair(mockPump);
        assertEquals(result, false);


        //Assert
        verify(mockPump, times(0)).repair();
        verify(mockPipe, times(0)).patch();
    }

    @Test
    public void RepairPipeItNotStandsOnFail(){
        
        //Arrange
        when(mockPipe.patch()).thenReturn(true);

        //Act
        boolean result = testPlumberOnPump.repair(mockPipe);
        assertEquals(result, false);


        //Assert
        verify(mockPipe, times(0)).patch();
        verify(mockPump, times(0)).repair();
    }

    @Test
    public void PickupPumpFromCisternSuccess(){
       
        //Arrange
        when(mockCistern.acquirePump()).thenReturn(mockPump);

        //Act
        boolean result = testPlumberOnCistern.pickUpPump(mockCistern);
        assertEquals(result, true);
        
        boolean result2 = false;
        if(testPlumberOnCistern.getHeldPump() != null){result2 = true;}
        assertEquals(result2, true);

        boolean result3 = false;
        if(testPlumberOnCistern.getHeldPumpCount() > 0){result3 = true;}
        assertEquals(result3, true);


        //Assert
        verify(mockCistern, times(1)).acquirePump();
        
    }

    @Test
    public void PickupPumpNotFromCisternFail(){
       
        //Arrange
        when(mockCistern.acquirePump()).thenReturn(mockPump);

        //Act
        boolean result = testPlumberOnPump.pickUpPump(mockCistern);
        assertEquals(result, false);

        boolean result2 = false;
        if(testPlumberOnPump.getHeldPump() != null){result2 = true;}
        assertEquals(result2, false);


        //Assert
        verify(mockCistern, times(0)).acquirePump();
        
    }

    @Test
    public void RemovePipeFromPumpEnptyHandsFirstAndSuccessThenNotEmptyHandsAndFail(){
        
        //Arrange
        when(mockPump.removePipe(mockPipe)).thenReturn(true); 

        //Act
        boolean result = testPlumberOnPump.detachPipe(mockPump, mockPipe);
        assertEquals(result, true);

        boolean result2 = testPlumberOnPump.detachPipe(mockPump, mockPipe);
        assertEquals(result2, false);

        boolean result3 = false;

        if(testPlumberOnPump.getHeldPipe() != null){result3 = true;}

        assertEquals(result3, true);


        //Assert
        verify(mockPump, times(1)).removePipe(mockPipe);


    }

    @Test
    public void RemovePipeNotFromPumpFail(){
        
        //Arrange
        when(mockPump.removePipe(mockPipe)).thenReturn(true); /// A kodbol nem derül ki, hogy a csorol nem lehet csovet lenyuzni...

        //Act
        boolean result = testPlumberOnPipe.detachPipe(mockPump, mockPipe);
        assertEquals(result, false);

        boolean result2 = testPlumberOnPipe.detachPipe(mockPump, mockPipe);
        assertEquals(result2, false);


        //Assert
        verify(mockPump, times(0)).removePipe(mockPipe);

    }

    @Test
    public void RemovePipeButPipeNotFoundFail(){
        
        //Arrange
        when(mockPump.removePipe(mockPipe)).thenReturn(false);

        //Act
        boolean result = testPlumberOnPump.detachPipe(mockPump, mockPipe);
        assertEquals(result, false);


        //Assert
        verify(mockPump, times(1)).removePipe(mockPipe);

    }

    @Test
    public void placePumpOnPipeHasPumpFirstSuccesThenNoPumpFail(){
        //Arrange
        when(mockPipe.leave()).thenReturn(true);

        //Act

        boolean result = testEquipedPlumberOnPipe.placePump(mockPipe);
        assertEquals(result, true);

        boolean result2 = testEquipedPlumberOnPipe.placePump(mockPipe);
        assertEquals(result2, false);


        //Assert
        verify(mockPipe, times(1)).leave();
        verify(mockPipe, times(1)).split();
        verify(mockPumpheld, times(1)).addPipe(mockPipe);

    }

    @Test
    public void placePumpNotOnPipeHasPumpFirstFailThenOnPipeNoPumpFail(){
        //Arrange
        when(mockPipe.leave()).thenReturn(true);

        //Act

        boolean result = testEquipedPlumberOnPump.placePump(mockPipe);
        assertEquals(result, false);

        boolean result2 = testPlumberOnPipe.placePump(mockPipe);
        assertEquals(result2, false);


        //Assert
        verify(mockPipe, times(0)).leave();
        verify(mockPipe, times(0)).split();
        verify(mockPumpheld, times(0)).addPipe(mockPipe);

    }

    @Test
    public void placePumpCantLeaveFail(){
        //Arrange
        when(mockPipe.leave()).thenReturn(false);

        //Act

        boolean result = testEquipedPlumberOnPipe.placePump(mockPipe);
        assertEquals(result, false);

        //Assert
        verify(mockPipe, times(1)).leave();
        verify(mockPipe, times(0)).split();
        verify(mockPumpheld, times(0)).addPipe(mockPipe);

    }

    @Test
    public void AttachPipeOnPumpHasPipeFirstSuccesThenNoPipeFail(){
        //Arrange
        when(mockPump.addPipe(mockPipeheld)).thenReturn(true);

        //Act

        boolean result = testEquipedPlumberOnPump.attachPipe(mockPump);
        assertEquals(result, true);

        boolean result2 = testEquipedPlumberOnPump.attachPipe(mockPump);
        assertEquals(result2, false);


        //Assert
        verify(mockPump, times(1)).addPipe(mockPipeheld);
        
    }

    @Test
    public void AttachPipeOnPumpUnattachableFail(){
        //Arrange
        when(mockPump.addPipe(mockPipeheld)).thenReturn(false);

        //Act

        boolean result = testEquipedPlumberOnPump.attachPipe(mockPump);
        assertEquals(result, false);



        //Assert
        verify(mockPump, times(1)).addPipe(mockPipeheld);
        
    }

    @Test
    public void AttachPipeNotOnPumpHasNoPipeFail(){
        //Arrange
        when(mockPump.addPipe(mockPipeheld)).thenReturn(true);

        //Act

        boolean result = testEquipedPlumberOnPipe.attachPipe(mockPump);
        assertEquals(result, false);

        //Assert
        verify(mockPump, times(0)).addPipe(mockPipeheld);
        
    }

}
