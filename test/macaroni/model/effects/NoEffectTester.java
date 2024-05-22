package macaroni.model.effects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import macaroni.model.element.Pipe;
import macaroni.model.character.Plumber;


import static org.mockito.Mockito.*;

public class NoEffectTester {
    private NoEffect testNoEffect;
    private Pipe mockPipe;
    private Plumber mockPlumber;
   

     @BeforeEach
    public void init(){
        
        mockPipe = mock(Pipe.class);
        mockPlumber = mock(Plumber.class);
        testNoEffect = new NoEffect(mockPipe);
    }

    @Test
    public void TestTheGreatNothingSuccess()
    {
        //Arrange
        when(mockPlumber.leave(mockPipe)).thenReturn(true);

        //Act
        assertEquals(testNoEffect.leave(), true);
        testNoEffect.tick();
        assertEquals(testNoEffect.enter(mockPlumber), true);

         //Assert
         verify(mockPlumber, times(1)).leave(mockPipe);

    }

}
