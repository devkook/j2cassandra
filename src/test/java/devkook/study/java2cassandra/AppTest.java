package devkook.study.java2cassandra;

import static org.junit.Assert.*;

public class AppTest {

    private App app;

    @org.junit.Before
    public void setUp() throws Exception {
        System.out.printf("CASS INSTALL & RUN & CREATE");
        System.out.printf("=> https://github.com/devkook/j2cassandra/blob/master/README.md");

        String host = "127.0.0.1:9160";
        String clusterNam = "FBWOTJQ";
        String keyspace = "hectortestkeyspace";
        app = new App(host, clusterNam, keyspace);

    }


    @org.junit.Test
    public void 테스트_INSERT(){
        //G
        String columnfamilyName = "hectortestcolumfamily";
        String columnName = "fake_column_1";
        String rowKey = "hectortestkey1";
        String value = "fake_value_1";

        //W
        app.insert(columnfamilyName, columnName, rowKey, value);

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
}