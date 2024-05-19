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

    private Pipe cisternPipe;
    private Pipe detachedPipe;
    private Pipe mainPipe;
    private Pipe springPipe;

    private Pump pump1;
    private Pump pump2;

    private Spring spring;

    @BeforeEach
    void setupMap() {
        ModelObjectFactory.reset();

        var ground = new WaterCollector();
        var cisternCollector = new WaterCollector();

        cistern = new Cistern(cisternCollector);
        cisternPipe = new Pipe(ground);
        detachedPipe = new Pipe(ground);
        mainPipe = new Pipe(ground);
        springPipe = new Pipe(ground);

        pump1 = new Pump();
        pump2 = new Pump();

        spring = new Spring();

        assertTrue(spring.addPipe(springPipe));
        assertTrue(pump1.addPipe(springPipe));
        assertTrue(pump1.addPipe(mainPipe));
        assertTrue(pump1.addPipe(detachedPipe));
        assertTrue(pump2.addPipe(mainPipe));
        assertTrue(pump2.addPipe(cisternPipe));
        assertTrue(cistern.addPipe(cisternPipe));
    }

    @Test
    void moveToUnoccupiedPipe() {
        var plumber = new Plumber(pump1);

        boolean success = plumber.moveTo(mainPipe);

        assertTrue(success);
        assertEquals(mainPipe, plumber.getLocation());
    }

    @Test
    void moveToOccupiedPipe() {
        var plumber1 = new Plumber(pump1);
        var plumber2 = new Plumber(pump1);
        assertTrue(plumber2.moveTo(mainPipe));

        boolean success = plumber1.moveTo(mainPipe);

        assertFalse(success);
        assertEquals(pump1, plumber1.getLocation());
    }

    @Test
    void moveToDetachedPipe() {
        var plumber1 = new Plumber(pump1);

        boolean success = plumber1.moveTo(detachedPipe);

        assertFalse(success);
        assertEquals(pump1, plumber1.getLocation());
    }

    @Test
    void moveToPump() {
        var plumber = new Plumber(mainPipe);

        boolean success = plumber.moveTo(pump2);

        assertTrue(success);
        assertEquals(pump2, plumber.getLocation());
    }

    @Test
    void moveToCistern() {
        var plumber = new Plumber(cisternPipe);

        boolean success = plumber.moveTo(cistern);

        assertTrue(success);
        assertEquals(cistern, plumber.getLocation());
    }

    @Test
    void moveToSpring() {
        var plumber = new Plumber(springPipe);

        boolean success = plumber.moveTo(spring);

        assertTrue(success);
        assertEquals(spring, plumber.getLocation());
    }

    @Test
    void moveToBananaPipe() {
        var saboteur = new Saboteur(mainPipe);

        Random.setDeterministicValue(1);
        assertTrue(saboteur.dropBanana(mainPipe));
        assertTrue(saboteur.moveTo(pump1));
        Random.setDeterministicSlideBack(mainPipe, false);
        assertFalse(saboteur.moveTo(mainPipe));
        assertEquals(pump2, saboteur.getLocation());

        Random.setDeterministicSlideBack(mainPipe, true);
        assertFalse(saboteur.moveTo(mainPipe));
        assertEquals(pump2, saboteur.getLocation());
    }

    @Test
    void moveThroughTechnokoledPipe() {
        var plumber = new Plumber(pump1);
        assertTrue(plumber.moveTo(mainPipe));

        Random.setDeterministicValue(1);
        assertTrue(plumber.applyTechnokol(mainPipe));
        assertTrue(plumber.moveTo(pump1));
        assertTrue(plumber.moveTo(mainPipe));

        plumber.moveTo(pump2);
        assertEquals(mainPipe, plumber.getLocation());

        mainPipe.tick();
        assertTrue(plumber.moveTo(pump2));
        assertEquals(pump2, plumber.getLocation());
    }
}
