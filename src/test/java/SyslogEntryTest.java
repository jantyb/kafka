import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import syslogEntryParser.*;
import syslogEntryParser.Process;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class SyslogEntryTest {

    String helaMeddelandet = "Apr  6 10:33:12 db01 sshd[21428]: pam_unix(sshd:session): session opened for user dba01 by (uid=0)";
    Agent agent;
    Host host;
    Event event;
    Process process;
    SyslogEntry entry;

    @BeforeEach
    void setUp() {
        try {
            String string = "Apr  6 10:33:12";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MMM  d HH:mm:ss");
            LocalDateTime timestampInSyslog = LocalDateTime.parse(string, formatter);
            System.out.println(timestampInSyslog); // Sat Jan 02 00:00:00 GMT 2010
            agent = new Agent("db01");
            host = new Host();
            event = new Event(timestampInSyslog, "started");
            process = new Process("21428", "sshd");
            LocalDateTime timeNow = LocalDateTime.now();
            entry = new SyslogEntry(timeNow, helaMeddelandet, agent, host, event, process);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    void syslogEntryToJson() {
        ObjectMapper Obj = new ObjectMapper();
        try {
            Obj.writeValueAsString(entry);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}