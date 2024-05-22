package macaroni.model.effects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import macaroni.model.element.Pipe;
import macaroni.model.character.Plumber;

import static org.mockito.Mockito.*;

public class TechnocolEffectTester {
    private Pipe mockPipe;
    private Plumber mockPlumber;
    private TechnokolEffect testTechnokolEffect;
   

     @BeforeEach
    public void init(){
        mockPipe = mock(Pipe.class);
        //mockRandom = mock(Random.class);
        mockPlumber = mock(Plumber.class);
        testTechnokolEffect = new TechnokolEffect(mockPipe);
    }

    @Test
    public void EnterTest()
    {
        //Arrange
        when(mockPlumber.leave(mockPipe)).thenReturn(true);

        //Act
        assertEquals(testTechnokolEffect.enter(mockPlumber), true);

         //Assert
         verify(mockPlumber, times(1)).leave(mockPipe);

    }

    @Test
    public void TestLeave(){

        //Act
        boolean result1 = testTechnokolEffect.isActivated();
        assertEquals(result1, false);

        boolean result2 = testTechnokolEffect.leave();
        assertEquals(result2, true);

        boolean result3 = testTechnokolEffect.isActivated();
        assertEquals(result3, true);

        boolean result4 = testTechnokolEffect.leave();
        assertEquals(result4, false);


    }

    @Test
    public void TestTick(){

        //Act
        mockPipe.setEffect(testTechnokolEffect);
        assertNotEquals(testTechnokolEffect.getCountdown(), 0);
        while(testTechnokolEffect.getCountdown() != 0){testTechnokolEffect.tick();}
        assertNotEquals(testTechnokolEffect, mockPipe.getEffect());

    }

   

    
}
