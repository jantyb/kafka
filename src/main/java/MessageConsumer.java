import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import syslogEntryParser.SyslogEntry;
import syslogEntryParser.SyslogEntryParser;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class MessageConsumer {
    private String topic;
    private String groupId = "test1";
    private KafkaConsumer kafkaConsumer;

    public MessageConsumer() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("group.id", groupId);
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        topic = "syslog";

        kafkaConsumer = new KafkaConsumer(properties);
        kafkaConsumer.subscribe(Arrays.asList(topic));

    }

    public List<String> read() {
        Duration duration = Duration.ofMillis(1000);
        ConsumerRecords<Long, String> records = kafkaConsumer.poll(duration);

        List<String> entries = new ArrayList<>();
        ObjectMapper Obj = new ObjectMapper();
        for (ConsumerRecord<Long, String> record : records) {
            LocalDateTime timenow = LocalDateTime.now();
            SyslogEntryParser parser = new SyslogEntryParser();
            SyslogEntry syslogEntry = parser.parse(record.value(), timenow);

            String jsonStr = null;

            try {
                jsonStr = Obj.writeValueAsString(syslogEntry);
                entries.add(jsonStr);
                System.out.println("syslog entry as string: " + jsonStr);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return entries;
    }

    public void close() {
        kafkaConsumer.close();
    }

}
