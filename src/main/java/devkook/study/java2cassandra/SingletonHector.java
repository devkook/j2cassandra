package devkook.study.java2cassandra;

import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.factory.HFactory;

/**
 * Created by diginori on 2014-05-07.
 */
public class SingletonHector {
    private static Keyspace keyspace = null;
    private static Cluster cluster = null;

    public static Keyspace getKeyspace(String clusterName, CassandraHostConfigurator conf, String keyspaceName) {
        if (keyspace == null) {
            cluster = HFactory.getOrCreateCluster(clusterName, conf);
            keyspace = HFactory.createKeyspace(keyspaceName, cluster);
            System.out.println("Create Cluster & Keyspace");
        }
        return keyspace;
    }
}
