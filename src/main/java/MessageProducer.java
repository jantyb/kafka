
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class MessageProducer{
    private String topic;
    private KafkaProducer<String, String> kafkaProducer;

    public MessageProducer(){
        Properties properties = new Properties();
        properties.put("bootstrap.servers","localhost:9092");
        properties.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
        topic="syslog";
        kafkaProducer = new KafkaProducer<String, String>(properties);
    }

    public void Send(String msg){
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic,"name",msg);
        kafkaProducer.send(producerRecord);
    }

    public void close(){
        kafkaProducer.close();
    }


}