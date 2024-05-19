package macaroni.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import macaroni.model.character.Plumber;
import macaroni.model.character.Saboteur;
import macaroni.model.effects.BananaEffect;
import macaroni.model.effects.TechnokolEffect;
import macaroni.model.element.Cistern;
import macaroni.model.element.Pipe;
import macaroni.model.element.Pump;
import macaroni.model.misc.WaterCollector;
import macaroni.utils.ModelObjectFactory;

public class BasicActionsTest {
	private Pipe pipe;
	private Pump pump;
	private WaterCollector wc;

	@BeforeEach
	void setup() {
		ModelObjectFactory.reset();
		wc = new WaterCollector();
		pipe = new Pipe(wc);
		pump = new Pump();
		pipe.addEndpoint(new Pump());
		pipe.addEndpoint(new Pump());
	}

	@Test
	void repairPipe() {
		var plumber = new Plumber(pipe);

		pipe.pierce();
		boolean pierceSuccess = pipe.isPierced();
		boolean repairSuccess = plumber.repair(pipe);
		boolean isPierced = pipe.isPierced();

		assertTrue(pierceSuccess);
		assertTrue(repairSuccess);
		assertFalse(isPierced);
	}

	@Test
	void repairPipe_InvalidPos() {
		var plumber = new Plumber(pump);

		pipe.pierce();
		boolean pierceSuccess = pipe.isPierced();
		boolean repairSuccess = plumber.repair(pipe);
		boolean isPierced = pipe.isPierced();

		assertTrue(pierceSuccess);
		assertFalse(repairSuccess);
		assertTrue(isPierced);
	}

	@Test
	void piercePipe() {
		var plumber = new Plumber(pipe);

		boolean pierceSuccess = plumber.pierce(pipe);
		boolean isPierced = pipe.isPierced();

		assertTrue(pierceSuccess);
		assertTrue(isPierced);
	}

	@Test
	void piercePipe_InvalidPos() {
		var plumber = new Plumber(pump);

		boolean pierceSuccess = plumber.pierce(pipe);
		boolean isPierced = pipe.isPierced();

		assertFalse(pierceSuccess);
		assertFalse(isPierced);
	}

	@Test
	void repairPump() {
		var plumber = new Plumber(pump);

		boolean breakSuccess = pump.Break();
		boolean repairSuccess = plumber.repair(pump);
		boolean isBroken = pump.isBroken();

		assertTrue(breakSuccess);
		assertTrue(repairSuccess);
		assertFalse(isBroken);
	}

	@Test
	void repairPump_InvalidPos() {
		var plumber = new Plumber(pipe);

		boolean breakSuccess = pump.Break();
		boolean repairSuccess = plumber.repair(pump);
		boolean isBroken = pump.isBroken();

		assertTrue(breakSuccess);
		assertFalse(repairSuccess);
		assertTrue(isBroken);
	}

	@Test
	void technokolPipe() {
		var plumber = new Plumber(pipe);

		boolean applySuccess = plumber.applyTechnokol(pipe);
		var effect = pipe.getEffect();

		assertTrue(applySuccess);
		assertTrue(effect instanceof TechnokolEffect);
	}

	@Test
	void technokolPipe_InvalidPos() {
		var plumber = new Plumber(pump);

		boolean applySuccess = plumber.applyTechnokol(pipe);
		var effect = pipe.getEffect();

		assertFalse(applySuccess);
		assertFalse(effect instanceof TechnokolEffect);
	}

	@Test
	void bananaPipe() {
		var actor = new Saboteur(pipe);

		boolean applySuccess = actor.dropBanana(pipe);
		var effect = pipe.getEffect();

		assertTrue(applySuccess);
		assertTrue(effect instanceof BananaEffect);
	}

	@Test
	void bananaPipe_InvalidPos() {
		var actor = new Saboteur(pump);

		boolean applySuccess = actor.dropBanana(pipe);
		var effect = pipe.getEffect();

		assertFalse(applySuccess);
		assertFalse(effect instanceof BananaEffect);
	}

	@Test
	void setPumpInput() {
		var pipe = new Pipe(wc);
		var plumber = new Plumber(pump);
		boolean addSuccess = pump.addPipe(pipe);
		boolean setInputSuccess = plumber.setInputPipe(pump, pipe);

		assertTrue(addSuccess);
		assertTrue(setInputSuccess);
	}

	@Test
	void setPumpOutput() {
		var pipe = new Pipe(wc);
		var plumber = new Plumber(pump);
		boolean addSuccess = pump.addPipe(pipe);
		boolean setOutputSuccess = plumber.setOutputPipe(pump, pipe);

		assertTrue(addSuccess);
		assertTrue(setOutputSuccess);
	}

	@Test
	void setPumpInput_InvalidPos() {
		var pipe = new Pipe(wc);
		var plumber = new Plumber(this.pipe);
		boolean addSuccess = pump.addPipe(pipe);
		boolean setInputSuccess = plumber.setInputPipe(pump, pipe);

		assertTrue(addSuccess);
		assertFalse(setInputSuccess);
	}

	@Test
	void setPumpOutput_InvalidPos() {
		var pipe = new Pipe(wc);
		var plumber = new Plumber(this.pipe);
		boolean addSuccess = pump.addPipe(pipe);
		boolean setOutputSuccess = plumber.setOutputPipe(pump, pipe);

		assertTrue(addSuccess);
		assertFalse(setOutputSuccess);
	}

	@Test
	void setPumpInput_AlreadyOutput() {
		var pipe = new Pipe(wc);
		var plumber = new Plumber(pump);
		boolean addSuccess = pump.addPipe(pipe);
		boolean setOutputSuccess = pump.setOutputPipe(pipe);
		boolean setInputSuccess = plumber.setInputPipe(pump, pipe);

		assertTrue(addSuccess);
		assertTrue(setOutputSuccess);
		assertFalse(setInputSuccess);
	}

	@Test
	void setPumpOutput_AlreadyInput() {
		var pipe = new Pipe(wc);
		var plumber = new Plumber(pump);
		boolean addSuccess = pump.addPipe(pipe);
		boolean setInputSuccess = pump.setInputPipe(pipe);
		boolean setOutputSuccess = plumber.setOutputPipe(pump, pipe);

		assertTrue(addSuccess);
		assertTrue(setInputSuccess);
		assertFalse(setOutputSuccess);
	}

	@Test
	void setPumpInput_AlreadyInput() {
		var pipe = new Pipe(wc);
		var plumber = new Plumber(pump);
		boolean addSuccess = pump.addPipe(pipe);
		boolean setInputSuccess1 = pump.setInputPipe(pipe);
		boolean setInputSuccess2 = plumber.setInputPipe(pump, pipe);

		assertTrue(addSuccess);
		assertTrue(setInputSuccess1);
		assertFalse(setInputSuccess2);
	}

	@Test
	void setPumpOutput_AlreadyOutput() {
		var pipe = new Pipe(wc);
		var plumber = new Plumber(pump);
		boolean addSuccess = pump.addPipe(pipe);
		boolean setOutputSuccess1 = pump.setOutputPipe(pipe);
		boolean setOutputSuccess2 = plumber.setOutputPipe(pump, pipe);

		assertTrue(addSuccess);
		assertTrue(setOutputSuccess1);
		assertFalse(setOutputSuccess2);
	}

	@Test
	void pickupPump() {
		ModelObjectFactory.setCisternCreatePumpName("testPump");
		var cistern = new Cistern(wc);
		var plumber = new Plumber(cistern);

		boolean pickupSuccess = plumber.pickUpPump(cistern);

		assertTrue(pickupSuccess);
		assertEquals(plumber.getHeldPumpCount(), 1);
	}

	@Test
	void pickupPump_InvalidPos() {
		ModelObjectFactory.setCisternCreatePumpName("testPump");
		var cistern = new Cistern(wc);
		var plumber = new Plumber(pipe);

		boolean pickupSuccess = plumber.pickUpPump(cistern);

		assertFalse(pickupSuccess);
		assertEquals(plumber.getHeldPumpCount(), 0);
	}

	@Test
	void placePump() {
		var pipe = new Pipe(wc);
		var pump1 = new Pump();
		var pump2 = new Pump();
		var pump3 = new Pump();
		pump1.addPipe(pipe);
		pump2.addPipe(pipe);
		pipe.addEndpoint(pump1);
		pipe.addEndpoint(pump2);
		var plumber = new Plumber(pipe, new ArrayList<>(Arrays.asList(pump3)));

		ModelObjectFactory.setPipeCreatePipeName("newPipe");
		boolean success = plumber.placePump(pipe);

		Pipe newPipe = (Pipe) ModelObjectFactory.getObject("newPipe");
		assertTrue(success);
		assertEquals(plumber.getLocation(), pump3);
		assertEquals(pipe.getEndpoint(0), pump2);
		assertEquals(pipe.getEndpoint(1), pump3);
		assertEquals(newPipe.getEndpoint(0), pump1);
		assertEquals(newPipe.getEndpoint(1), pump3);
	}

	@Test
	void placePumpFailed() {
		var pipe = new Pipe(wc);
		var pump1 = new Pump();
		var pump2 = new Pump();
		pump1.addPipe(pipe);
		pump2.addPipe(pipe);
		pipe.addEndpoint(pump1);
		pipe.addEndpoint(pump2);
		var plumber = new Plumber(pipe);

		ModelObjectFactory.setPipeCreatePipeName("newPipe");
		boolean success = plumber.placePump(pipe);

		assertFalse(success);

		assertEquals(pipe.getEndpoint(0), pump1);
		assertEquals(pipe.getEndpoint(1), pump2);
		assertNull(ModelObjectFactory.getObject("newPipe"));
	}
}
