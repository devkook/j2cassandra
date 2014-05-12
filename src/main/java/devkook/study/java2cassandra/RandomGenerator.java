package devkook.study.java2cassandra;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class RandomGenerator {
    private SecureRandom srandom = new SecureRandom();
    private Random random = new Random();
    private String big_string = null;

    public long ranLong() {
        Random r = new Random();
        return Math.abs(r.nextLong());
    }

    public String ranRowkey() {
        return "rk-" + ranLong();
    }

    public String ranString() {
        return new BigInteger(130, random).toString();
    }
    
    public String getSingleTonekilobyteString(int kilobyte) {

        if(big_string == null) {
            int size = kilobyte * 1024;
            StringBuilder sb = new StringBuilder(size);
            for (int i = 0; i < size; i++) {
                sb.append('a');
            }

            this.big_string = sb.toString();
        }

        return this.big_string;
    }

    public String getSampleJSON() {
        JSONObject json = new JSONObject();
        json.put("name", "my name is :D");
        json.put("age", 19);

        JSONArray list = new JSONArray();

        for(int i=1;i < 212; i++){
            list.add("msg="+i);
        }

        json.put("messages", list);

        return json.toJSONString();

    }
}
