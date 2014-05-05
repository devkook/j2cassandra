![JMX_HECTOR_POOL_APPDYNAMICS](https://github.com/devkook/j2cassandra/blob/master/img/JMX_HECTOR_POOL_APPDYNAMICS.png)
 
 * Hello CASSANDRA !
 * http://devkook.tumblr.com/post/75546064236/cassandra

 # INSTALL
 * curl -O http://mirror.apache-kr.org/cassandra/2.0.7/apache-cassandra-2.0.7-bin.tar.gz
 * gzip -d *.gz
 * tar -xvf *.tar

 # START
 * cd apache-cassandra-2.0.7-bin/bin
 * ./cassandra

 # TOOL
 * ./cassandra-cli

 # READY
 * connect localhost/9160
 * CREATE KEYSPACE hectortestkeyspace with placement_strategy = ‘SimpleStrategy’ and strategy_options = {replication_factor:1};
 * USE hectortestkeyspace;
 * CREATE COLUMN FAMILY hectortestcolumfamily;

 # TEST
 * git clone https://github.com/devkook/j2cassandra.git
 * cd j2cassandra
 * mvn clean assembly:assembly -Dmaven.test.skip=true
 * ./runJ2cass.sh

![appdynamics-call](https://31.media.tumblr.com/f5e6a54a9eadd50c2821bc26f72dc3e6/tumblr_inline_n501mbn7111sq4zng.png)
![appdynamics-jmx-hector](https://31.media.tumblr.com/3aa6539ff174f3f1ec1fead2e2642928/tumblr_inline_n501wqiHDi1sq4zng.png)

 # 참조
 * http://fbwotjq.tistory.com/467
 * http://fbwotjq.tistory.com/439
 * http://fbwotjq.tistory.com/466
