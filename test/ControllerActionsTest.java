import macaroni.model.element.Cistern;
import macaroni.model.element.Pipe;
import macaroni.model.element.Pump;
import macaroni.model.misc.WaterCollector;
import macaroni.utils.ModelObjectFactory;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ControllerActionsTest {

    @Test
    void breakPump() {
        Pump pump = new Pump();

        assertFalse(pump.isBroken());
        pump.Break();
        assertTrue(pump.isBroken());
    }

    @Test
    void spawnPipe() {
        WaterCollector collector1 = new WaterCollector();
        WaterCollector collector2 = new WaterCollector();
        Cistern cistern = new Cistern(collector1);

        Pipe createdPipe = new Pipe(collector2);

        try (MockedStatic<ModelObjectFactory> mock = mockStatic(ModelObjectFactory.class)) {
            mock.when(ModelObjectFactory::cisternCreatePipe).thenReturn(createdPipe);
            cistern.spawnPipe();
        }

        assertEquals(createdPipe.getEndpoint(0), cistern);
        assertTrue(cistern.removePipe(createdPipe));
    }
}
