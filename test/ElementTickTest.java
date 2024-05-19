import macaroni.model.character.Saboteur;
import macaroni.model.effects.BananaEffect;
import macaroni.model.effects.Effect;
import macaroni.model.effects.NoEffect;
import macaroni.model.effects.TechnokolEffect;
import macaroni.model.element.Cistern;
import macaroni.model.element.Pipe;
import macaroni.model.element.Pump;
import macaroni.model.element.Spring;
import macaroni.model.misc.WaterCollector;
import macaroni.utils.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ElementTickTest {
    private WaterCollector ground;
    private Pump pump1, pump2, pump3;
    private Spring spring1;
    private Pipe pipe1, pipe2;
    private WaterCollector cistern1Collector;
    private Cistern cistern1;

    private final int pipeCapacity = 5;

    @BeforeEach
    void InitMap() {
        ground = new WaterCollector();
        pump1 = new Pump();
        pump2 = new Pump();
        pump3 = new Pump();
        spring1 = new Spring(1);
        pipe1 = new Pipe(ground, pipeCapacity);
        pipe2 = new Pipe(ground, pipeCapacity);
        cistern1Collector = new WaterCollector();
        cistern1 = new Cistern(cistern1Collector);
    }

    @Test
    void waterflowFromSpring() {
        pump1.addPipe(pipe1);
        spring1.addPipe(pipe1);

        assertEquals(pipe1.getStoredWater(), 0);

        spring1.tick();

        assertEquals(pipe1.getStoredWater(), 1);
    }

    @Test
    void waterflowThroughPump() {
        pump1.addPipe(pipe1);
        pump2.addPipe(pipe1);
        pipe1.addWater(1);

        pump2.addPipe(pipe2);
        pump3.addPipe(pipe2);

        pump2.setInputPipe(pipe1);
        pump2.setOutputPipe(pipe2);

        assertEquals(pipe1.getStoredWater(), 1);
        assertEquals(pump2.getStoredWater(), 0);
        assertEquals(pipe2.getStoredWater(), 0);

        pump2.tick();

        assertEquals(pipe1.getStoredWater(), 0);
        assertEquals(pump2.getStoredWater(), 1);
        assertEquals(pipe2.getStoredWater(), 0);

        pump2.tick();

        assertEquals(pipe1.getStoredWater(), 0);
        assertEquals(pump2.getStoredWater(), 0);
        assertEquals(pipe2.getStoredWater(), 1);
    }

    @Test
    void waterflowIntoCistern() {
        pump1.addPipe(pipe1);
        cistern1.addPipe(pipe1);
        pipe1.addWater(1);

        assertEquals(pipe1.getStoredWater(), 1);
        assertEquals(cistern1Collector.getStoredAmount(), 0);

        cistern1.tick();

        assertEquals(pipe1.getStoredWater(), 0);
        assertEquals(cistern1Collector.getStoredAmount(), 1);
    }

    @Test
    void waterflowIntoFullPipe() {
        spring1.addPipe(pipe1);
        pump1.addPipe(pipe1);

        assertEquals(pipe1.getStoredWater(), 0);
        pipe1.addWater(pipeCapacity);
        assertEquals(pipe1.getStoredWater(), pipeCapacity);

        spring1.tick();

        assertEquals(pipe1.getStoredWater(), pipeCapacity);
    }

    @Test
    void waterflowIntoDetachedPipe() {
        spring1.addPipe(pipe1);

        assertEquals(pipe1.getStoredWater(), 0);
        assertEquals(ground.getStoredAmount(), 0);

        spring1.tick();

        assertEquals(pipe1.getStoredWater(), 0);
        assertEquals(ground.getStoredAmount(), 1);
    }

    @Test
    void waterflowIntoPiercedPipe() {
        pump1.addPipe(pipe1);
        spring1.addPipe(pipe1);

        pipe1.pierce();

        assertTrue(pipe1.isPierced());
        assertEquals(pipe1.getStoredWater(), 0);
        assertEquals(ground.getStoredAmount(), 0);

        spring1.tick();

        assertEquals(pipe1.getStoredWater(), 0);
        assertEquals(ground.getStoredAmount(), 1);
    }

    @Test
    void tickBananaPipe() {
        Saboteur saboteur = new Saboteur(pipe1);
        Random.setDeterministicValue(1);
        saboteur.dropBanana(pipe1);

        Effect effect = pipe1.getEffect();
        assertEquals(effect.getClass(), BananaEffect.class);
        BananaEffect bEffect = (BananaEffect) effect;
        assertEquals(bEffect.getCountdown(), 1);

        pipe1.tick();

        assertEquals(bEffect.getCountdown(), 0);
        assertEquals(pipe1.getEffect().getClass(), NoEffect.class);
    }

    @Test
    void tickTechnokoledPipe() {
        Saboteur saboteur = new Saboteur(pipe1);
        Random.setDeterministicValue(2);
        saboteur.applyTechnokol(pipe1);

        Effect effect = pipe1.getEffect();
        assertEquals(effect.getClass(), TechnokolEffect.class);
        TechnokolEffect tEffect = (TechnokolEffect) effect;
        assertEquals(tEffect.getCountdown(), 2);

        pipe1.tick();
        assertEquals(tEffect.getCountdown(), 1);
        assertEquals(pipe1.getEffect(), tEffect);

        pipe1.tick();

        assertEquals(tEffect.getCountdown(), 0);
        assertEquals(pipe1.getEffect().getClass(), NoEffect.class);
    }
}
