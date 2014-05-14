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

    /**
     * 킬로바이트 단위의 문자열 생성
     * (주의) 성능을 한번생성된 스트링을 무조건 반환한다.
     * @param kilobyte
     * @return
     */
    public String getSingleTonekilobyteString(int kilobyte) {

        if(big_string == null) {
            int size = kilobyte * 1024;

            this.big_string = bingbingString('S', size);
        }

        return this.big_string;
    }

    /**
     * c 로된 킬로바이트 단위의 문자열 생성
     * @param c
     * @param kilobyte
     * @return
     */
    public String getKilobyteString(char c, float kilobyte) {

        String kb_string = "";

        int size = (int)(kilobyte * 1024);

        kb_string = bingbingString(c, size);

        return kb_string;
    }

    private String bingbingString(char c, int size) {
        String kb_string;
        StringBuilder sb = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            sb.append(c);
        }

        kb_string = sb.toString();
        return kb_string;
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
