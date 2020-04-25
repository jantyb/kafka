package syslogEntryParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;


class SyslogEntryParserTest {

    SyslogEntryParser parser;
    LocalDateTime timenow;
    String msg1 ="Apr  6 10:33:08 db01 sshd[18989]: Received disconnect from 192.168.41.24 port 61088:11: disconnected by user";
    String msg2 ="Apr  6 10:33:08 db01 sshd[18989]: Disconnected from 192.168.41.24 port 61088";
    String msg3 ="Apr  6 10:33:08 db01 sshd[18989]: pam_unix(sshd:session): session closed for user dba01";
    String msg4 ="Apr  6 10:33:12 db01 sshd[21428]: Accepted password for dba01 from 192.168.41.23 port 50760 ssh2";
    String msg5 ="Apr  6 10:33:12 db01 sshd[21428]: pam_unix(sshd:session): session opened for user dba01 by (uid=0)";

    @BeforeEach
    void setUp() {
        parser = new SyslogEntryParser();

    }

    @Test
    void parseAMessage() {
        timenow = LocalDateTime.now();
        parser.parse(msg5,timenow);
        parser.parse(msg2,timenow);
        parser.parse(msg3,timenow);
        parser.parse(msg4,timenow);
        parser.parse(msg5,timenow);

    }


}