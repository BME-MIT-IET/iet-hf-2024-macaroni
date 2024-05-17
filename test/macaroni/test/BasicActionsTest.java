package macaroni.test;

import org.junit.jupiter.api.BeforeEach;

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


}
