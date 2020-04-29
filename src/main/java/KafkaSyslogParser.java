
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class KafkaSyslogParser {

    private static final Logger LOGG = LogManager.getLogger(KafkaSyslogParser.class.getName());

    public static void main(String[] args) {
        System.out.println("Starting.");
        KafkaSyslogParser parser = new KafkaSyslogParser();
        parser.publishToKafkaServer();
        parser.readFromKafkaServer();
    }

    private void publishToKafkaServer() {
        MessageProducer syslogMessageProducer = new MessageProducer();
        SysLogEntryRepo repo = new SysLogEntryRepo();

        repo.getSysLogEntries().forEach(logEntry -> {
            syslogMessageProducer.Send(logEntry);
            System.out.println("Sending:" + logEntry);
        });

        syslogMessageProducer.close();
    }

    private void readFromKafkaServer() {
        MessageConsumer syslogMessageConsumer = new MessageConsumer();
        LocalDateTime timenow = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now().plusSeconds(5);

        List<String> entries = new ArrayList<>();
        while (endTime.isAfter(timenow)) {
            List<String> temp = syslogMessageConsumer.read();
            if (temp.size() > 0) {
                entries = temp;
            }
            timenow = LocalDateTime.now();
        }
        syslogMessageConsumer.close();

        entries.forEach((entry)->{
            LOGG.info("entries:" + entry.toString());
            //System.out.println("entries:" + entry.toString());
        });

    }

}


