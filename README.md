 * Hello CASSANDRA !
 * http://devkook.tumblr.com/post/75546064236/cassandra

 # INSTALL
 # curl -O http://mirror.apache-kr.org/cassandra/2.0.7/apache-cassandra-2.0.7-bin.tar.gz
 # gzip -d *.gz
 # tar -xvf *.tar

 # START
 # cd apache-cassandra-2.0.7-bin/bin
 # ./cassandra

 # TOOL
 # ./cassandra-cli

 # READY
 # connect localhost/9160
 # CREATE KEYSPACE hectortestkeyspace with placement_strategy = ‘SimpleStrategy’ and strategy_options = {replication_factor:1};
 # USE hectortestkeyspace;
 # CREATE COLUMN FAMILY hectortestcolumfamily;

 * 참조 : http://fbwotjq.tistory.com/467 , http://fbwotjq.tistory.com/439, http://fbwotjq.tistory.com/466