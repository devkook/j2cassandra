package devkook.study.java2cassandra;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class RandomGenerator {
    private SecureRandom random = new SecureRandom();

    public long ranLong() {
        Random r = new Random();
        return Math.abs(r.nextLong());
    }

    public String ranRowkey() {
        return "rk-" + ranLong();
    }

    public String ranString() {
        return new BigInteger(130, random).toString(32);
    }
}
