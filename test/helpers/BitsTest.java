package helpers;

import org.junit.Test;

public class BitsTest {

    @Test
    public void hash() {
        assert Bits.hash("") == 0;
        assert Bits.hash(" ") == (long) 137438953504.0;
        assert Bits.hash("a") == (long) 416611827809.0;
        assert Bits.hash("b") == (long) 420906795106.0;
        assert Bits.hash("c") == (long) 425201762403.0;
        assert Bits.hash("d") == (long) 429496729700.0;
        assert Bits.hash("e") == (long) 433791696997.0;
        assert Bits.hash("Alex") == (long) 8776568102923838.0;
    }

    @Test
    public void hash2() {
        assert Bits.hash2("", 16) == 0;
        assert Bits.hash2(" ", 16) == 0;
        assert Bits.hash2("a", 16) == 0;
        assert Bits.hash2("b", 16) == 0;
        assert Bits.hash2("c", 16) == 0;
        assert Bits.hash2("d", 16) == 0;
        assert Bits.hash2("e", 16) == 0;
        assert Bits.hash2("ehljgfdskjfgh;jasdhg;jahdjkgha;dgha'sdghjlsdhgjksHFLKsdhfkjsdfh", 16) == 10;
    }

    @Test
    public void R() {
        assert Bits.R(0) == 1;
        assert Bits.R(1) == 2;
        assert Bits.R(2) == 1;
        assert Bits.R(3) == 4;
        assert Bits.R(5) == 2;
        assert Bits.R(10) == 1;
        assert Bits.R(16) == 1;
        assert Bits.R(20) == 1;
        assert Bits.R(31) == 32;
        assert Bits.R(10000000000000000L) == 1;
    }

    @Test
    public void r() {
        assert Bits.r(0) == 0;
        assert Bits.r(1) == 1;
        assert Bits.r(2) == 0;
        assert Bits.r(3) == 2;
        assert Bits.r(5) == 1;
        assert Bits.r(10) == 0;
        assert Bits.r(16) == 0;
        assert Bits.r(20) == 0;
        assert Bits.r(31) == 5;
        assert Bits.r(10000000000000000L) == 0;
    }

    @Test
    public void p() {
        assert Bits.p(0) == 0;
        assert Bits.p(1) == 1;
        assert Bits.p(2) == 1;
        assert Bits.p(3) == 2;
        assert Bits.p(5) == 2;
        assert Bits.p(10) == 2;
        assert Bits.p(16) == 1;
        assert Bits.p(20) == 2;
        assert Bits.p(31) == 5;
        assert Bits.p(10000000000000000L) == 20;
    }

    @Test
    public void testToString() {
        assert Bits.toString(65, 16).equals("0000000001000001");
        assert Bits.toString(100000000, 32).equals("00000101111101011110000100000000");
        assert Bits.toString(10000000000000000L, 64).equals("0000000000100011100001101111001001101111110000010000000000000000");

    }

    @Test
    public void main() {
        String[] args = new String[1];
        Bits.main(args);
    }
}
