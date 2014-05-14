package devkook.study.java2cassandra;

import me.prettyprint.hector.api.mutation.MutationResult;
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

    @Test
    public void bath_INSERT(){
        //G
        String columnfamilyName = "hectortestcolumfamily";
        String columnName = "fake_column_1";
        String rowKey = "hectortestkey1";
        String value = "fake_value_1";

        //W
        MutationResult mr = app.bachInsert(columnfamilyName,columnName,10,0.01f);

        //T
        System.out.println(mr.getHostUsed());
        System.out.println(mr.toString());
        System.out.println(mr.hashCode());
        System.out.println(mr.getExecutionTimeMicro());
        System.out.println(mr.getExecutionTimeNano());
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
        app.insert(columnfamilyName, columnName, "ROW-2KB", r.getSingleTonekilobyteString(2));
        app.insert(columnfamilyName, columnName, "ROW-20KB", r.getSingleTonekilobyteString(20));
        app.insert(columnfamilyName, columnName, "ROW-200KB", r.getSingleTonekilobyteString(200));

        MutationResult mr = app.insert(columnfamilyName, columnName, r.ranRowkey(), r.getSingleTonekilobyteString(2));

        //T
        System.out.println(mr.getHostUsed());
        System.out.println(mr.toString());
        System.out.println(mr.hashCode());
        System.out.println(mr.getExecutionTimeMicro());
        System.out.println(mr.getExecutionTimeNano());
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
        System.out.println(app.select(columnfamilyName,columnName,"ROW-200KB").length());
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