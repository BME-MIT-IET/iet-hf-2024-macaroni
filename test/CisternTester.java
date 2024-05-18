/*
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import macaroni.model.element.Cistern;
import macaroni.model.element.Pipe;
import macaroni.model.misc.WaterCollector;
import macaroni.utils.ModelObjectFactory;

public class CisternTester {

    private Cistern testCistern;
    private Pipe mockPipe;
    private WaterCollector mockWaterCollector;
    private ModelObjectFactory mockModelObjectFactory;

    @BeforeEach
    public void init(){
        mockPipe = mock(Pipe.class);
        mockWaterCollector = mock(WaterCollector.class);
        mockModelObjectFactory = mock(ModelObjectFactory.class);
        testCistern = new Cistern(mockWaterCollector);
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
   public void AcquirePumpTest(){
        
        assertNotEquals(testCistern.acquirePump(), null);

   }

}
*/
