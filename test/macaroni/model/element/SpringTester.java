package macaroni.model.element;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class SpringTester {
    private Spring testSpring;
    private Pipe mockPipe;
    private int waterTestNumber = 6;

    @BeforeEach
    public void init(){
        mockPipe = mock(Pipe.class);
        testSpring = new Spring(waterTestNumber, mockPipe);
    }

    @Test
    public void tickTester()
    {
        //Arrange
        when(mockPipe.addWater(waterTestNumber)).thenReturn(waterTestNumber);

        //Act
        testSpring.tick();

        //Assert
        verify(mockPipe, times(1)).addWater(waterTestNumber);

    }

    
}
