package devkook.study.java2cassandra;

import me.prettyprint.cassandra.connection.LoadBalancingPolicy;
import me.prettyprint.cassandra.connection.RoundRobinBalancingPolicy;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.MutationResult;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.ColumnQuery;
import me.prettyprint.hector.api.query.QueryResult;

public class App {
    private final Cluster cluster;
    private final Keyspace keyspace;

    private final StringSerializer strSerializer;

    /**
     *
     * @param hostIpPort
     * @param clusterName
     * @param keyspaceName
     */
    public App(String hostIpPort, String clusterName, String keyspaceName) {
        
        CassandraHostConfigurator conf = new CassandraHostConfigurator(hostIpPort);
        conf.setMaxActive(20);
        conf.setCassandraThriftSocketTimeout(3000);
        conf.setMaxWaitTimeWhenExhausted(4000);
        //DynamicLoadBalancingPolicy, LeastActiveBalancingPolicy, RoundRobinBalancingPolicy
        LoadBalancingPolicy lb_policy =  new RoundRobinBalancingPolicy();
        conf.setLoadBalancingPolicy(lb_policy);
        
        this.cluster = HFactory.getOrCreateCluster(clusterName, conf);
        this.keyspace = HFactory.createKeyspace(keyspaceName, this.cluster);

        this.strSerializer = StringSerializer.get();
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

    public static void main(String[] args) {

        if(args.length != 6) {
            System.err.println("ex) App localhost:9160 clusterName keyspaceName columnfamilyName columnName loop_count");
            return;
        }

        String hostIpPort = args[0];
        String clusterName = args[1];
        String keyspaceName = args[2];

        App app = new App(hostIpPort, clusterName, keyspaceName);

        String columnfamilyName = args[3];
        String columnName = args[4];
        long loopcount = Long.parseLong(args[5]);

        String rowKey;
        String value;
        RandomGenerator r = new RandomGenerator();
        for (long i = 0; i < loopcount; i++) {
            rowKey = r.ranRowkey();
            value = r.ranString();
            app.insert(columnfamilyName, columnName, rowKey, value);
            System.out.print(".");
        }
        System.out.println("");
        System.out.println("END:"+ loopcount);

    }
}

