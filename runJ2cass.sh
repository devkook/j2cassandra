
java -javaagent:/root/AppDynamicsLite/AppServerAgentLite/javaagent.jar \
-jar target/j2cassandra-1.0.0-jar-with-dependencies.jar \
127.0.0.1:9160 cluster_name hectortestkeyspace hectortestcolumfamily fake_column_1 1000000
