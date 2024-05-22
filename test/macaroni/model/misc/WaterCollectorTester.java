package macaroni.model.misc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WaterCollectorTester {

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
