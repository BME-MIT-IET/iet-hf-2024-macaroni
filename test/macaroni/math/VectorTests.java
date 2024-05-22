package macaroni.math;

import macaroni.views.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VectorTests {

    private final double errorDelta = 0.00001d;

    @Test
    void testEquals() {
        Vector2D v1 = new Vector2D(2, 3);
        Vector2D v2 = new Vector2D(4, 5);
        Vector2D v3 = new Vector2D(4, 5);
        Vector2D v4 = new Vector2D(4, 6);

        assertEquals(v1, v1);
        assertNotEquals(v1, v2);
        assertEquals(v2, v3);
        assertNotEquals(v1, null);
        assertNotEquals(v1, "1, 4");
        assertNotEquals(v3, v4);
        assertEquals(v2.hashCode(), v3.hashCode());
        assertNotEquals(v1.hashCode(), v3.hashCode());
    }

    @Test
    void testAdd() {
        Vector2D v1 = new Vector2D(2, 3);
        Vector2D v2 = new Vector2D(-1, 6);

        assertEquals(v1.add(v2), new Vector2D(1, 9));
        assertEquals(v1.add(v2), v2.add(v1));
    }

    @Test
    void testNormalize() {
        Vector2D v1 = new Vector2D(3, 4);
        Vector2D v2 = new Vector2D(0, 9);
        Vector2D v3 = new Vector2D(-4, 0);

        Vector2D v1Normalized = v1.normalize();
        double dx = v1Normalized.getX() - 3 / 5f;
        double dy = v1Normalized.getY() - 4 / 5d;
        assertTrue(dx < errorDelta && dy < errorDelta);
        assertEquals(v2.normalize(), new Vector2D(0, 1));
        assertEquals(v3.normalize(), new Vector2D(-1, 0));
    }

    @Test
    void testDot() {
        assertEquals(new Vector2D(0, 3).dot(new Vector2D(-1, 0)), 0);
        assertEquals(new Vector2D(0, 2).dot(new Vector2D(0, -1)), -2);
    }

    @Test
    void testPosition() {
        Position p = new Position(3, 4);
        Vector2D v = new Vector2D(p);
        assertEquals(v.getX(), p.x());
        assertEquals(v.getY(), p.y());

        Vector2D v2 = new Vector2D(4, 9);
        Position p2 = v2.toPosition();
        assertEquals(v2.getX(), p2.x());
        assertEquals(v2.getY(), p2.y());
    }
}
