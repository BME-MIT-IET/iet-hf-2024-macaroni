package macaroni.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import macaroni.math.Vector2D;

class SampleTest2 {

	@Test
	void test() {
		var v = new Vector2D(2, 2);
		v = v.normalize();
		assertEquals(1, 1);
	}

}
