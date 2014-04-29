package devkook.study.java2cassandra;

import me.prettyprint.cassandra.serializers.StringSerializer;
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

    public App(String host, String cluster, String keyspace) {
        this.cluster = HFactory.getOrCreateCluster(cluster, hostInfomation);
        this.keyspace = HFactory.createKeyspace(keyspace, this.cluster);

        this.strSerializer = StringSerializer.get();


    }

    public MutationResult insert(String columnfamilyName, String columnName, String rowKey, String value) {
        Mutator<String> mutator = this.mutator = HFactory.createMutator(keyspace, this.strSerializer);
        return mutator.insert(rowKey, columnfamilyName, HFactory.createStringColumn(columnName, value));
    }

    public String select(String columnfamilyName, String columnName, String rowKey){
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

    public void delete(String columnfamilyName, String columnName, String rowKey){
        Mutator<String> mutator = this.mutator = HFactory.createMutator(keyspace, this.strSerializer);
        mutator.delete(rowKey, columnfamilyName, columnName, stringSerializer);

    }
}
