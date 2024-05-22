package macaroni.model.character;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import macaroni.model.element.Pump;
import macaroni.model.element.Pipe;

import static org.mockito.Mockito.*;

public class SaboteurTester {
    private Pump mockPump;
    private Pipe mockPipe;
    private Saboteur testSaboteurOnPipe;
    private Saboteur testSaboteurOnPump;

    @BeforeEach
    public void init(){
        mockPump = mock(Pump.class);
        mockPipe = mock(Pipe.class);
        testSaboteurOnPump = new Saboteur(mockPump, true);
        testSaboteurOnPipe = new Saboteur(mockPipe, true);
        
    }

    @Test
    public void dropBananaSuccess(){

        //Act
        boolean result = testSaboteurOnPipe.dropBanana(mockPipe);
        assertEquals(result, true);

    }

    @Test
    public void dropBananaFail(){
        //Act
        boolean result = testSaboteurOnPump.dropBanana(mockPipe);
        assertEquals(result, false);

    }


}
