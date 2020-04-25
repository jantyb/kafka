import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MessageProducerTest {
    MessageProducer syslogMessageProducer;
    SysLogEntryRepo repo;
    @BeforeEach
    void setUp() {
        syslogMessageProducer = new MessageProducer();
        repo = new SysLogEntryRepo();
    }

    @Test
    void SendALogEntry() {
        syslogMessageProducer.Send(repo.getSysLogEntries().get(0));
    }

    @AfterEach
    void tearDown() {
        syslogMessageProducer.close();
    }
}