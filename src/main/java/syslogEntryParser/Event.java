package syslogEntryParser;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event {

    @JsonIgnore
    LocalDateTime localDateTime;

    String created; // Tidsstämpel från loggmeddelandet
    String type; //någon av: access, allowed, denied, error, start, stop, unknown


    public Event(LocalDateTime created, String type) {
        this.localDateTime = created;
        this.type = type;
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        this.created = localDateTime.format(formatter);

    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
