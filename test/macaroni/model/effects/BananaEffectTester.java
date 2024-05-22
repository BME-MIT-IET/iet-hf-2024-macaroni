package macaroni.model.effects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import macaroni.model.element.Pipe;
import macaroni.model.element.Pump;
import macaroni.model.character.Plumber;

import static org.mockito.Mockito.*;

public class BananaEffectTester {
    
    private Pump mockPump;
    private Pipe mockPipe;
    private Plumber mockPlumber;
    private BananaEffect testBananaEffect;
   

     @BeforeEach
    public void init(){
        mockPump = mock(Pump.class);
        mockPipe = mock(Pipe.class);
        //mockRandom = mock(Random.class);
        mockPlumber = mock(Plumber.class);
        testBananaEffect = new BananaEffect(mockPipe);
    }

    @Test
    public void EnterCharacterSuccess(){
        
        //Arrange
        when(mockPipe.getRandomEndpoint()).thenReturn(mockPump);
        when(mockPlumber.leave(mockPipe)).thenReturn(true);

        //Act
        boolean result = testBananaEffect.enter(mockPlumber);
        assertEquals(result, false);


        //Assert
        verify(mockPlumber, times(1)).moveTo(mockPump);
    }

    @Test
    public void EnterCharacterFail(){
        
        //Arrange
        when(mockPipe.getRandomEndpoint()).thenReturn(mockPump);
        when(mockPlumber.leave(mockPipe)).thenReturn(false);

        //Act
        boolean result = testBananaEffect.enter(mockPlumber);
        assertEquals(result, false);


        //Assert
        verify(mockPlumber, times(0)).moveTo(mockPump);
    }

    @Test
    public void LeaveSuccess(){
        //Act
        boolean result = testBananaEffect.leave();
        assertEquals(result, true);

    }

    @Test
    public void TetTick(){

        //Act
        mockPipe.setEffect(testBananaEffect);
        assertNotEquals(testBananaEffect.getCountdown(), 0);
        while(testBananaEffect.getCountdown() != 0){testBananaEffect.tick();}
        assertNotEquals(testBananaEffect, mockPipe.getEffect());

    }


}
