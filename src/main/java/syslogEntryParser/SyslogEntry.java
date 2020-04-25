package syslogEntryParser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@JsonPropertyOrder({ "@timestamp", "message", "agent","host","event","process"})
public class SyslogEntry {
    @JsonIgnore
    LocalDateTime localDateTime; //Tidsstämpel för när meddelandet konsumerades

    String timestamp;
    String message; //meddelandet i sin helhet.
    Agent agent; // hostnamnet från loggposten
    Host host;  // ipadress från konsumenten
    Event event; //någon av: access, allowed, denied, error, start, stop, unknown
    Process process; //info för loggande process. Från loggmeddelandet

    public SyslogEntry(LocalDateTime timestamp, String fullmessage, Agent agent, Host host, Event event, Process process) {
        this.localDateTime = timestamp;
        this.message = fullmessage;
        this.agent = agent;
        this.host = host;
        this.event = event;
        this.process = process;

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME ;
        this.timestamp =localDateTime.format(formatter);

    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    @JsonProperty("@timestamp")
    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }
}
