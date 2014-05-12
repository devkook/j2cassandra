package devkook.study.java2cassandra;

import me.prettyprint.cassandra.connection.LoadBalancingPolicy;
import me.prettyprint.cassandra.connection.RoundRobinBalancingPolicy;
import me.prettyprint.cassandra.serializers.ByteBufferSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.OrderedRows;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.MutationResult;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.ColumnQuery;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.RangeSlicesQuery;

import java.nio.ByteBuffer;
import java.util.Iterator;

import static devkook.study.java2cassandra.SingletonHector.getKeyspace;

public class App extends Thread {
    private final Keyspace keyspace;

    private final StringSerializer strSerializer;
    private static String columnfamilyName;
    private static String columnName;

    private static long loopcount;

    /**
     * @param hostIpPort
     * @param clusterName
     * @param keyspaceName
     * @param threadName
     */
    public App(String hostIpPort, String clusterName, String keyspaceName, String threadName) {

        CassandraHostConfigurator conf = new CassandraHostConfigurator(hostIpPort);
        conf.setMaxActive(30);
        conf.setCassandraThriftSocketTimeout(3000);
        conf.setMaxWaitTimeWhenExhausted(4000);

        //DynamicLoadBalancingPolicy, LeastActiveBalancingPolicy, RoundRobinBalancingPolicy
        LoadBalancingPolicy lb_policy = new RoundRobinBalancingPolicy();
        conf.setLoadBalancingPolicy(lb_policy);

        this.keyspace = getKeyspace(clusterName, conf, keyspaceName);
        this.strSerializer = StringSerializer.get();

        this.setName(threadName);
    }

    public MutationResult insert(String columnfamilyName, String columnName, String rowKey, String value) {
        Mutator<String> m = HFactory.createMutator(keyspace, this.strSerializer);
        return m.insert(rowKey, columnfamilyName, HFactory.createStringColumn(columnName, value));
    }

    public String select(String columnfamilyName, String columnName, String rowKey) {
        ColumnQuery<String, String, String> columnQuery
                = HFactory.createColumnQuery(keyspace, strSerializer, strSerializer, strSerializer);

        columnQuery.setColumnFamily(columnfamilyName);
        columnQuery.setName(columnName);
        columnQuery.setKey(rowKey);

        QueryResult<HColumn<String, String>> queryResult = columnQuery.execute();
        HColumn<String, String> hColumn = queryResult.get();
        String result = hColumn.getValue();
        return result;
    }

    public void delete(String columnfamilyName, String columnName, String rowKey) {
        Mutator<String> m = HFactory.createMutator(keyspace, this.strSerializer);
        m.delete(rowKey, columnfamilyName, columnName, this.strSerializer);
    }
    
    public void slicesDelete(String columnfamilyName, String columnName, int slicesRange, String slicesStartRowKey) {

        Mutator<String> m = HFactory.createMutator(keyspace, this.strSerializer);

        ByteBufferSerializer bbs = ByteBufferSerializer.get();
        RangeSlicesQuery<String, String, ByteBuffer> rsq = HFactory.createRangeSlicesQuery(keyspace, this.strSerializer, this.strSerializer, bbs);
        rsq.setColumnFamily(columnfamilyName);
        rsq.setRange(null, null, false, 10); //TODO
        rsq.setRowCount(slicesRange);
        rsq.setKeys(slicesStartRowKey, null);

        QueryResult<OrderedRows<String, String, ByteBuffer>> result = rsq.execute();
        OrderedRows<String, String, ByteBuffer> rows = result.get();
        Iterator<Row<String, String, ByteBuffer>> rowsIterator = rows.iterator();

        while (rowsIterator.hasNext()) {
            Row<String, String, ByteBuffer> row = rowsIterator.next();

            String rKey = row.getKey();
            System.out.println(rKey);

            m.delete(rKey, columnfamilyName, columnName, this.strSerializer);
        }
    }

    public static void main(String[] args) {

        if (args.length != 7) {
            System.err.println("ex) App localhost:9160,localhost:9160 clusterName keyspaceName columnfamilyName columnName loop_count thread_count");
            return;
        }

        String hostIpPort = args[0];
        String clusterName = args[1];
        String keyspaceName = args[2];

        columnfamilyName = args[3];
        columnName = args[4];
        loopcount = Long.parseLong(args[5]);

        int threadCount = Integer.parseInt(args[6]);
        for (int i = 1; i <= threadCount; i++) {
            new App(hostIpPort, clusterName, keyspaceName, String.valueOf(i)).start();
        }
    }

    public void run() {
        String threadName = Thread.currentThread().getName();

        String rowKey;
        String value;

        RandomGenerator r = new RandomGenerator();
        for (long i = 0; i < loopcount; i++) {
            rowKey = r.ranRowkey();
            value = r.ranString();
            insert(columnfamilyName, columnName, rowKey, value);
            System.out.print(threadName);
        }

        System.out.printf("%n [%s] END - %d %n", threadName, loopcount);
    }
}

