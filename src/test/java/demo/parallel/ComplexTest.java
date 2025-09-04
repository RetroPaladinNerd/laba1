package demo.parallel;

import demo.parallel.Complex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ComplexTest {

    private Complex a;
    private Complex b;

    @BeforeEach
    void setUp() {
        a = new Complex(3.0, 4.0); // 3 + 4i
        b = new Complex(1.0, 2.0); // 1 + 2i
    }

    @Test
    void testPlus() {
        Complex result = new Complex(a.re, a.im).plus(new Complex(b.re, b.im));
        assertEquals(4.0, result.re, 1e-10, "Real part should be 4.0");
        assertEquals(6.0, result.im, 1e-10, "Imaginary part should be 6.0");
    }

    @Test
    void testTimes() {
        Complex result = new Complex(a.re, a.im).times(new Complex(b.re, b.im));
        assertEquals(-5.0, result.re, 1e-10, "Real part should be -5.0");
        assertEquals(10.0, result.im, 1e-10, "Imaginary part should be 10.0");
    }

    @Test
    void testDivide() {
        Complex result = new Complex(a.re, a.im).divide(new Complex(b.re, b.im));
        assertEquals(2.2, result.re, 1e-10, "Real part should be 2.2");
        assertEquals(0.4, result.im, 1e-10, "Imaginary part should be 0.4");
    }

    @Test
    void testDivideByZero() {
        Complex zero = new Complex(0.0, 0.0);
        assertThrows(ArithmeticException.class, () -> new Complex(a.re, a.im).divide(zero), "Should throw ArithmeticException for division by zero");
    }

    @Test
    void testPowerZero() {
        Complex result = new Complex(a.re, a.im).power(0);
        assertEquals(1.0, result.re, 1e-10, "Real part should be 1.0 for power 0");
        assertEquals(0.0, result.im, 1e-10, "Imaginary part should be 0.0 for power 0");
    }

    @Test
    void testPowerOne() {
        Complex result = new Complex(a.re, a.im).power(1);
        assertEquals(3.0, result.re, 1e-10, "Real part should be 3.0");
        assertEquals(4.0, result.im, 1e-10, "Imaginary part should be 4.0");
    }

    @Test
    void testPowerTwo() {
        Complex result = new Complex(a.re, a.im).power(2);
        assertEquals(-7.0, result.re, 1e-10, "Real part should be -7.0");
        assertEquals(24.0, result.im, 1e-10, "Imaginary part should be 24.0");
    }

    @Test
    void testPowerNegative() {
        Complex result = new Complex(2.0, 0.0).power(-1);
        assertEquals(0.5, result.re, 1e-10, "Real part should be 0.5");
        assertEquals(0.0, result.im, 1e-10, "Imaginary part should be 0.0");
    }

    @Test
    void testLengthSQ() {
        double length = a.lengthSQ();
        assertEquals(25.0, length, 1e-10, "Length squared should be 25.0");
    }

    @Test
    void testCalc() {
        TestCalcClass calcClass = new TestCalcClass();
        Complex comp = new Complex(0.5, 0.5);
        int iterations = calcClass.calc(comp);
        assertTrue(iterations > 0 && iterations <= 1000, "Calc iterations should be within bounds");
    }

    static class TestCalcClass {
        private static final int CAL_MAX_COUNT = 1000;
        private static final double LENGTH_BOUNDARY = 4.0;

        private int calc(Complex comp) {
            int count = 0;
            Complex c = new Complex(0, 0);
            do {
                Complex temp = new Complex(c.re, c.im);
                c = temp.power(3).divide(comp).plus(comp);
                count++;
            } while (count < CAL_MAX_COUNT && c.lengthSQ() < LENGTH_BOUNDARY);
            return count;
        }
    }
}