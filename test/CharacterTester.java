import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import macaroni.model.element.Pump;
import macaroni.model.element.Pipe;
import macaroni.model.character.Plumber;

import static org.mockito.Mockito.*;

public class CharacterTester {

    private Pump mockPump;
    private Pipe mockPipe;
    private Plumber testPlumberOnPipe;
    private Plumber testPlumberOnPump;


    @BeforeEach
    public void init(){
        mockPump = mock(Pump.class);
        mockPipe = mock(Pipe.class);
        testPlumberOnPump = new Plumber(mockPump);
        testPlumberOnPipe = new Plumber(mockPipe);
        
    }

    @Test
    public void TechocolOnPipeSuccess()
    {
        boolean result = testPlumberOnPipe.applyTechnokol(mockPipe);
        assertEquals(result, true);

    }

    @Test
    public void TechocolNotOnPipeFail()
    {
        boolean result = testPlumberOnPump.applyTechnokol(mockPipe);
        assertEquals(result, false);

    }

    @Test
    public void PierceOnPipeSuccess()
    {
        //Arrange
        when(mockPipe.pierce()).thenReturn(true);

        //Act
        boolean result = testPlumberOnPipe.pierce(mockPipe);
        assertEquals(result, true);

        //Assert
        verify(mockPipe, times(1)).pierce();

    }

    @Test
    public void PierceOnPipeFail()
    {
        //Arrange
        when(mockPipe.pierce()).thenReturn(false);

        //Act
        boolean result = testPlumberOnPipe.pierce(mockPipe);
        assertEquals(result, false);

        //Assert
        verify(mockPipe, times(1)).pierce();

    }
    
    @Test
    public void PierceNotOnPipeFail()
    {
        //Arrange
        when(mockPipe.pierce()).thenReturn(true);

        //Act
        boolean result = testPlumberOnPump.pierce(mockPipe);
        assertEquals(result, false);

        //Assert
        verify(mockPipe, times(0)).pierce();

    }

    @Test
    public void SetOutputPipeOnPumpSuccess()
    {
        //Arrange
        when(mockPump.setOutputPipe(mockPipe)).thenReturn(true);

        //Act
        boolean result = testPlumberOnPump.setOutputPipe(mockPump, mockPipe);
        assertEquals(result, true);

        //Assert
        verify(mockPump, times(1)).setOutputPipe(mockPipe);

    }

    @Test
    public void SetOutputPipeOnPumpFail()
    {
        //Arrange
        when(mockPump.setOutputPipe(mockPipe)).thenReturn(false);

        //Act
        boolean result = testPlumberOnPump.setOutputPipe(mockPump, mockPipe);
        assertEquals(result, false);

        //Assert
        verify(mockPump, times(1)).setOutputPipe(mockPipe);


    }
    
    @Test
    public void SetOutputPipeNotOnPumpFail()
    {
        //Arrange
        when(mockPump.setOutputPipe(mockPipe)).thenReturn(true);

        //Act
        boolean result = testPlumberOnPipe.setOutputPipe(mockPump, mockPipe);
        assertEquals(result, false);

        //Assert
        verify(mockPump, times(0)).setOutputPipe(mockPipe);
    }

    @Test
    public void SetInputPipeOnPumpSuccess()
    {
        //Arrange
        when(mockPump.setInputPipe(mockPipe)).thenReturn(true);

        //Act
        boolean result = testPlumberOnPump.setInputPipe(mockPump, mockPipe);
        assertEquals(result, true);

        //Assert
        verify(mockPump, times(1)).setInputPipe(mockPipe);

    }

    @Test
    public void SetInputPipeOnPumpFail()
    {
        //Arrange
        when(mockPump.setInputPipe(mockPipe)).thenReturn(false);

        //Act
        boolean result = testPlumberOnPump.setInputPipe(mockPump, mockPipe);
        assertEquals(result, false);

        //Assert
        verify(mockPump, times(1)).setInputPipe(mockPipe);


    }
    
    @Test
    public void SetInputPipeNotOnPumpFail()
    {
        //Arrange
        when(mockPump.setInputPipe(mockPipe)).thenReturn(true);

        //Act
        boolean result = testPlumberOnPipe.setInputPipe(mockPump, mockPipe);
        assertEquals(result, false);

        //Assert
        verify(mockPump, times(0)).setInputPipe(mockPipe);
    }

    @Test
    public void LeaveToSuccess()
    {
        //Arrange
        when(mockPump.leave()).thenReturn(true);

        //Act
        boolean result = testPlumberOnPump.leave(mockPipe);
        assertEquals(result, true);
        assertEquals(testPlumberOnPump.getLocation(), mockPipe);

        //Assert
        verify(mockPump, times(1)).leave();

    }

    @Test
    public void LeaveToFail()
    {
        //Arrange
        when(mockPump.leave()).thenReturn(false);

        //Act
        boolean result = testPlumberOnPump.leave(mockPipe);
        assertEquals(result, false);
        assertEquals(testPlumberOnPump.getLocation(), mockPump);

        //Assert
        verify(mockPump, times(1)).leave();

    }

    @Test
    public void EnterToSuccess()
    {
        //Arrange
        when(mockPump.enter(testPlumberOnPipe, mockPipe)).thenReturn(true);

        //Act
        boolean result = testPlumberOnPipe.moveTo(mockPump);
        assertEquals(result, true);

        //Assert
        verify(mockPump, times(1)).enter(testPlumberOnPipe, mockPipe);

    }

    @Test
    public void EnterToFail()
    {
        //Arrange
        when(mockPump.enter(testPlumberOnPipe, mockPipe)).thenReturn(false);

        //Act
        boolean result = testPlumberOnPipe.moveTo(mockPump);
        assertEquals(result, false);

        //Assert
        verify(mockPump, times(1)).enter(testPlumberOnPipe, mockPipe);


    }
    


    
}
