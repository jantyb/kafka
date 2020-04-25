import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MessageConsumerTest {
    MessageConsumer syslogMessageConsumer;
    @BeforeEach
    void setUp() {
        syslogMessageConsumer = new MessageConsumer();
    }

    @Test
    void ReadLogEntry() {
        while(true) {
            syslogMessageConsumer.read();
        }
    }

    @AfterEach
    void tearDown() {
        syslogMessageConsumer.close();
    }

}