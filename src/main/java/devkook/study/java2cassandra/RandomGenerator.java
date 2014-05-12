package devkook.study.java2cassandra;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class RandomGenerator {
    private SecureRandom random = new SecureRandom();
    private String big_string = null;

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
    
    public String getBigString(int size) {

        if(big_string == null) {
            StringBuilder sb = new StringBuilder(size);
            for (int i = 0; i < size; i++) {
                sb.append('a');
            }

            this.big_string = sb.toString();
        }

        return this.big_string;
    }
}
