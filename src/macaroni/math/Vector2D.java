package macaroni.math;

import macaroni.views.Position;

/**
 * Utility class that represents a vector in 2D
 */
public final class Vector2D {
    /**
     * the X coordinate of the vector
     */
    private final double x;
    /**
     * the Y coordinate of the vector
     */
    private final double y;

    /**
     * Constructs a Vector2D
     *
     * @param x the X coordinate of the vector
     * @param y the Y coordinate of the vector
     */
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vector2D vector2D = (Vector2D) o;

        return vector2D.x == x && vector2D.y == y;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    /**
     * Constructs a Vector2D from a Position
     *
     * @param other the position
     */
    public Vector2D(Position other) {
        x = other.x();
        y = other.y();
    }

    /**
     * Mathematically pure function
     *
     * @param other another vector
     * @return the sum of the two vectors
     */
    public Vector2D add(Vector2D other) {
        return new Vector2D(x + other.x, y + other.y);
    }

    /**
     * Mathematically pure function
     *
     * @param factor the scalar
     * @return the result of the scale
     */
    public Vector2D scale(double factor) {
        return new Vector2D(x * factor, y * factor);
    }

    /**
     * Mathematically pure function
     *
     * @return the length of the vector
     */
    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    /**
     * Mathematically pure function
     *
     * @return the normalized version of the vector
     */
    public Vector2D normalize() {
        return scale(1.0 / length());
    }

    /**
     * Mathematically pure function
     *
     * @param other another vector
     * @return the dot product of the two vectors
     */
    public double dot(Vector2D other) {
        return x * other.x + y * other.y;
    }

    /**
     * Gets the x coordinate of the vector
     *
     * @return the x coordinate
     */
    public double getX() {
        return x;
    }

    /**
     * Gets the y coordinate of the vector
     *
     * @return the y coordinate
     */
    public double getY() {
        return y;
    }

    /**
     * @return a converted version of the vector
     */
    public Position toPosition() {
        return new Position((int) x, (int) y);
    }
}
