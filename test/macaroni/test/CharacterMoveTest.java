package macaroni.test;

import macaroni.model.character.Plumber;
import macaroni.model.character.Saboteur;
import macaroni.model.element.Cistern;
import macaroni.model.element.Pipe;
import macaroni.model.element.Pump;
import macaroni.model.element.Spring;
import macaroni.model.misc.WaterCollector;
import macaroni.utils.ModelObjectFactory;
import macaroni.utils.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CharacterMoveTest {

    private Cistern cistern;
    private Pipe pipe;
    private Pump pump1;
    private Pump pump2;
    private Spring spring;

    @BeforeEach
    void setup() {
        ModelObjectFactory.reset();
        WaterCollector ground = new WaterCollector();
        WaterCollector cisternCollector = new WaterCollector();
        cistern = new Cistern(cisternCollector);
        pipe = new Pipe(ground);
        pump1 = new Pump();
        pump2 = new Pump();
        spring = new Spring();
    }

    @Test
    void moveToUnoccupiedPipe() {
        assertTrue(pump1.addPipe(pipe));
        assertTrue(pump2.addPipe(pipe));
        var plumber = new Plumber(pump1);

        boolean success = plumber.moveTo(pipe);

        assertTrue(success);
        assertEquals(pipe, plumber.getLocation());
    }

    // TODO place plumber2 on pipe when constructed
    @Test
    void moveToOccupiedPipe() {
        assertTrue(pump1.addPipe(pipe));
        assertTrue(pump2.addPipe(pipe));
        var plumber1 = new Plumber(pump1);
        var plumber2 = new Plumber(pump1);
        assertTrue(plumber2.moveTo(pipe));

        boolean success = plumber1.moveTo(pipe);

        assertFalse(success);
        assertEquals(pump1, plumber1.getLocation());
    }

    @Test
    void moveToDetachedPipe() {
        assertTrue(pump1.addPipe(pipe));
        var plumber1 = new Plumber(pump1);

        boolean success = plumber1.moveTo(pipe);

        assertFalse(success);
        assertEquals(pump1, plumber1.getLocation());
    }

    @Test
    void moveToPump() {
        assertTrue(pump1.addPipe(pipe));
        assertTrue(pump2.addPipe(pipe));
        var plumber = new Plumber(pipe);

        boolean success = plumber.moveTo(pump2);

        assertTrue(success);
        assertEquals(pump2, plumber.getLocation());
    }

    @Test
    void moveToCistern() {
        assertTrue(pump1.addPipe(pipe));
        assertTrue(cistern.addPipe(pipe));
        var plumber = new Plumber(pipe);

        boolean success = plumber.moveTo(cistern);

        assertTrue(success);
        assertEquals(cistern, plumber.getLocation());
    }

    @Test
    void moveToSpring() {
        assertTrue(pump1.addPipe(pipe));
        assertTrue(spring.addPipe(pipe));
        var plumber = new Plumber(pipe);

        boolean success = plumber.moveTo(spring);

        assertTrue(success);
        assertEquals(spring, plumber.getLocation());
    }

    @Test
    void moveToBananaPipe() {
        assertTrue(pump1.addPipe(pipe));
        assertTrue(pump2.addPipe(pipe));
        var saboteur = new Saboteur(pipe);

        Random.setDeterministicValue(1);
        assertTrue(saboteur.dropBanana(pipe));
        assertTrue(saboteur.moveTo(pump1));
        Random.setDeterministicSlideBack(pipe, false);
        assertFalse(saboteur.moveTo(pipe));
        assertEquals(pump2, saboteur.getLocation());

        Random.setDeterministicSlideBack(pipe, true);
        assertFalse(saboteur.moveTo(pipe));
        assertEquals(pump2, saboteur.getLocation());
    }

    // TODO place plumber on pipe when constructed
    @Test
    void moveThroughTechnokoledPipe() {
        assertTrue(pump1.addPipe(pipe));
        assertTrue(pump2.addPipe(pipe));
        var plumber = new Plumber(pump1);
        assertTrue(plumber.moveTo(pipe));

        Random.setDeterministicValue(1);
        assertTrue(plumber.applyTechnokol(pipe));
        assertTrue(plumber.moveTo(pump1));
        assertTrue(plumber.moveTo(pipe));

        plumber.moveTo(pump2);
        assertEquals(pipe, plumber.getLocation());

        pipe.tick();
        assertTrue(plumber.moveTo(pump2));
        assertEquals(pump2, plumber.getLocation());
    }
}
