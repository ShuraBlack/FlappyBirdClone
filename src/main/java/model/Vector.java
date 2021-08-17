package model;

/**
 * @since 27.05.2021
 * @author ShuraBlack
 * Part of my basic draw template.
 * Reduced version.
 */
public class Vector {

    public double x;
    public double y;

    /**
     * Basic constructor which use the parameter to creates a vector
     * @param x position of vector
     * @param y position of vector
     */
    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Constructor which creates a (0,0) vector
     */
    public Vector() {
        this.x = 0;
        this.y = 0;
    }

    /**
     * @return X position
     */
    public double getX() {
        return x;
    }

    /**
     * @return Y position
     */
    public double getY() {
        return y;
    }

    /**
     * Adds the X and Y values of the parameter vector to the current vector
     * @param vector that will be added
     */
    public void add(Vector vector) {
        this.x = this.x + vector.getX();
        this.y = this.y + vector.getY();
    }

    /**
     * Multiplies the X and Y values of the vector by n
     * @param n is the multiply amount
     */
    public void mult(double n) {
        x *= n;
        y *= n;
    }

    /**
     * String representation of the vector
     * @return the Vector as a String
     */
    @Override
    public String toString() {
        return "Vector{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
