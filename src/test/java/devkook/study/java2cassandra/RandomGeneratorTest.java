package devkook.study.java2cassandra;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RandomGeneratorTest {

    private RandomGenerator r;

    @Before
    public void settup(){
        r = new RandomGenerator();
    }

    @Test
    public void testRanLong() throws Exception {
        System.out.println(r.ranLong());
    }

    @Test
    public void testRanRowkey() throws Exception {
        System.out.println(r.ranRowkey());

    }

    @Test
    public void testRanString() throws Exception {
        System.out.println(r.ranString());
        System.out.println(r.ranString().length());

    }


    @Test
    public void test_KbString(){

        //WHEN
        int kilobyte = 1;
        String value = r.getSingleTonekilobyteString(kilobyte);
        System.out.println(value);

        //THEN
        assertEquals("킬로바이트 만한것",kilobyte * 1024,value.length());
    }

    @Test
    public void test_getSampleJson(){

        //WHEN
        int kilobyte = 1;
        String value = r.getSampleJSON();
        System.out.println(value.length());
        System.out.println(value.length()/1024.0f + "KB");
        System.out.println(value);

        //THEN
//        assertEquals("킬로바이트 만한것",kilobyte * 1024,value.length());
    }
}