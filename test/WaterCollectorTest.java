import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import macaroni.model.misc.WaterCollector;

public class WaterCollectorTest {

    private WaterCollector testWaterCollector;

    @BeforeEach
    public void init(){
        testWaterCollector = new WaterCollector();
    }

    @Test
    public void WaterTest()
    {
        testWaterCollector.storeAmount(6);
        assertEquals(testWaterCollector.getStoredAmount(), 6);
    }
    
}
