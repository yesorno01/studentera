package kafkatest;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

public class kafkaTest9 {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("zookeeper.connect", "10.191.129.82:12181,10.191.129.83:12181,10.191.129.84:12181");
        properties.put("group.id", "97_BroadBandOldSysProducerId");
        properties.put("zookeeper.session.timeout.ms", "3000000");
        properties.put("zookeeper.sync.time.ms", "2000");
        String topic = "97_BroadBandOldSysProducer";

        ConsumerConnector consumer = null;//KafkaProperties.Consumer.createJavaConsumerConnector(new ConsumerConfig(properties));
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(topic, 1);
        Map<String, List<KafkaStream<byte[], byte[]>>> messageStreams = consumer.createMessageStreams(topicCountMap);

        for (final KafkaStream<byte[], byte[]> stream : messageStreams.get(topic)) {

            ConsumerIterator<byte[], byte[]> iterator = stream.iterator();
            while (iterator.hasNext()) {
                String message = null;
                try {
                    message = new String(iterator.next().message(), "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                System.out.println("消费:--- " + topic + "|" + message);
            }
        }
    }
}
