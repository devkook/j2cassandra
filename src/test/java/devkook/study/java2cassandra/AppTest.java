package devkook.study.java2cassandra;

import org.junit.Test;

import static org.junit.Assert.*;

public class AppTest {

    private App app;

    @org.junit.Before
    public void setUp() throws Exception {
        System.out.printf("CASS INSTALL & RUN & CREATE");
        System.out.printf("=> https://github.com/devkook/j2cassandra/blob/master/README.md");

        String host = "127.0.0.1:9160,localhost:9160";
        String clusterNam = "FBWOTJQ";
        String keyspace = "hectortestkeyspace";
        app = new App(host, clusterNam, keyspace, "1");

    }


    @org.junit.Test
    public void 테스트_INSERT(){
        //G
        String columnfamilyName = "hectortestcolumfamily";
        String columnName = "fake_column_1";
        String rowKey = "hectortestkey1";
        String value = "fake_value_1";

        RandomGenerator r = new RandomGenerator();

        //W
        app.insert(columnfamilyName, columnName, rowKey, value);
        app.insert(columnfamilyName, columnName, r.ranRowkey(), r.ranString());
        app.insert(columnfamilyName, columnName, r.ranRowkey(), r.getSingleTonekilobyteString(2));
        app.insert(columnfamilyName, columnName, r.ranRowkey(), r.getSingleTonekilobyteString(2));
        app.insert(columnfamilyName, columnName, r.ranRowkey(), r.getSingleTonekilobyteString(2));
        app.insert(columnfamilyName, columnName, r.ranRowkey(), r.getSingleTonekilobyteString(2));
        app.insert(columnfamilyName, columnName, r.ranRowkey(), r.getSingleTonekilobyteString(2));
        app.insert(columnfamilyName, columnName, r.ranRowkey(), r.getSingleTonekilobyteString(2));
        app.insert(columnfamilyName, columnName, r.ranRowkey(), r.getSingleTonekilobyteString(2));
        app.insert(columnfamilyName, columnName, r.ranRowkey(), r.getSingleTonekilobyteString(2));



        //T
    }

    @org.junit.Test
    public void 테스트_SELECT(){
        //G
        String columnfamilyName = "hectortestcolumfamily";
        String columnName = "fake_column_1";
        String rowKey = "hectortestkey1";

        String value = "fake_value_1";

        //W
        String selectedValue = app.select(columnfamilyName,columnName,rowKey);

        //T
        assertEquals(value, selectedValue);
    }

    @Test
    public void sliceSelectPrint(){
        //G
        String columnfamilyName = "hectortestcolumfamily";
        String columnName = "fake_column_1";

        String rowKey = new RandomGenerator().ranRowkey();


        //W
        app.slicesSelectPrint(columnfamilyName,columnName,10,rowKey);

        //T
    }

    @Test
    public void test_slicedelete(){
        //G
        String columnfamilyName = "hectortestcolumfamily";
        String columnName = "fake_column_1";
        String rowKey = "hectortestkey1";


        //W
        app.slicesDelete(columnfamilyName, columnName, 10, rowKey);

        //T
    }
}