package macaroni.model.element;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import macaroni.model.misc.WaterCollector;
import macaroni.utils.ModelObjectFactory;

public class CisternTester {

    private Cistern testCistern;
    private Cistern testButaCistern;
    private Pipe mockPipe;
    private Pipe mockPipeNew;
    private WaterCollector mockWaterCollector;

    @BeforeEach
    public void init(){
        mockPipe = mock(Pipe.class);
        mockPipeNew = mock(Pipe.class);
        mockWaterCollector = mock(WaterCollector.class);
        testCistern = new Cistern(mockWaterCollector, mockPipe, mockPipeNew);
        testButaCistern = new Cistern(mockWaterCollector);
    }

   @Test
   public void testTick(){
        //Arrange
        when(mockPipe.removeAllWater()).thenReturn(6);
        
        //Act
        testCistern.tick();
        
        //Assert
        verify(mockPipe, times(1)).removeAllWater();

   } 

   @Test
   public void acquirePumpTest(){
        
          ModelObjectFactory.setCisternCreatePumpName("Test");
          assertNotEquals(testButaCistern.acquirePump(), null);

   }

   @Test
   public void spawnPipeTest(){
        
          ModelObjectFactory.setCisternCreatePipeName("Test");
          testCistern.spawnPipe();
          assertEquals(testCistern.getNewPipesSize(), 2);

   }

   @Test
   public void removePipeTest(){
        
          boolean result1 = testCistern.removePipe(mockPipe);
          boolean result2 = testCistern.removePipe(mockPipeNew);
          assertEquals(result1, false);
          assertEquals(result2, true);

   }

}

