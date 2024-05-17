package macaroni.test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import macaroni.model.character.Plumber;
import macaroni.model.character.Saboteur;
import macaroni.model.element.Pipe;
import macaroni.model.misc.WaterCollector;

public class BasicActionsTest {
	private Plumber plumber;
	private Saboteur saboteur;
	private Pipe pipe;
	private WaterCollector wc;

	@BeforeEach
	void setup() {
		wc = new WaterCollector();
		pipe = new Pipe(wc);
		plumber = new Plumber(pipe);
		saboteur = new Saboteur(pipe);
	}

	@Test
	void repairPipe() {
		pipe.pierce();
		boolean pierceSuccess = pipe.isPierced();
		boolean repairSuccess = plumber.repair(pipe);
		boolean isPierced = pipe.isPierced();

		assertTrue(pierceSuccess);
		assertTrue(repairSuccess);
		assumeFalse(isPierced);
	}
}
