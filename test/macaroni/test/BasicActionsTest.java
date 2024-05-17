package macaroni.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import macaroni.model.character.Plumber;
import macaroni.model.element.Pipe;
import macaroni.model.element.Pump;
import macaroni.model.misc.WaterCollector;

public class BasicActionsTest {
	private Pipe pipe;
	private Pump pump;
	private WaterCollector wc;

	@BeforeEach
	void setup() {
		wc = new WaterCollector();
		pipe = new Pipe(wc);
		pump = new Pump();
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


}
