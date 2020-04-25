package syslogEntryParser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SyslogEntryParser {

    public SyslogEntry parse(String message,LocalDateTime consumedDateTime) {
        SyslogEntry entry = null;
        try {

            LocalDateTime dateTime = parseTimestamp(message);
            String hostname = parseHostName(message);
            String processName = parseProcessName(message, hostname);
            String processId = parseProcessId(message, hostname, processName);
            String typeOfEvent = analyseEvent(parseEvent(message));

            Agent agent = new Agent(hostname);
            Host host = new Host();
            Process proc = new Process(processId,processName);
            Event event = new Event(dateTime,typeOfEvent);

            entry= new SyslogEntry(
                    consumedDateTime,
                    message,
                    agent,
                    host,
                    event,
                    proc);

        } catch (Exception e) {
            System.out.println("Syslog Parser failed");
            e.printStackTrace();

        }
        return entry;
    }


    private LocalDateTime parseTimestamp(String message) {
        String messageTimestamp = null;
        Pattern timestampPattern = Pattern.compile("\\w{3}\\s+\\d{1,2}\\s+\\d{2}:\\d{2}:\\d{2}");
        Matcher matcher = timestampPattern.matcher(message);
        if (matcher.find()) {
            messageTimestamp = matcher.group();
        }
        messageTimestamp = (LocalDateTime.now().getYear() + " " + messageTimestamp).toLowerCase();
        DateTimeFormatter formatter;
        if(isOneDigitDate(messageTimestamp)){
            formatter = DateTimeFormatter.ofPattern("yyyy MMM  d HH:mm:ss");
        }else{
            formatter = DateTimeFormatter.ofPattern("yyyy MMM dd HH:mm:ss");
        }

        return  LocalDateTime.parse(messageTimestamp, formatter).plusNanos(0);
    }

    private boolean isOneDigitDate(String timeStamp){
        boolean oneDigitDate = false;
        Pattern datePattern = Pattern.compile("\\s+\\d\\s+");
        Matcher dateMatcher = datePattern.matcher(timeStamp);
        if (dateMatcher.find()) {
            oneDigitDate = true;
        }
        return oneDigitDate;
    }

    private String parseHostName(String message) {
        String hostName = null;
        Pattern pattern = Pattern.compile(":\\d{2}\\s+\\w*\\s+");
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            hostName = matcher.group();
        }
        hostName = hostName.substring(4, hostName.length()-1);
        return hostName;
    }

    private String parseProcessName(String message, String hostname) {
        String processName = null;

        Pattern pattern = Pattern.compile(":\\d{2}\\s+\\w*\\s+\\w*\\[");
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            processName = matcher.group();
        }
        processName = processName.substring(4 + hostname.length(), processName.length() - 1);

        return processName;
    }

    private String parseProcessId(String message, String hostname, String processName) {
        String processid = null;

        Pattern pattern = Pattern.compile(":\\d{2}\\s+\\w*\\s+\\w*\\[\\d*\\]");
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            processid = matcher.group();
        }
        processid = processid.substring(5 + hostname.length() + processName.length(), processid.length() - 1);
        return processid;
    }

    private String parseEvent(String message) {
        String event;
        String header = null;
        Pattern headerPattern = Pattern.compile("\\w{3}\\s+\\d{1,2}\\s+\\d{2}:\\d{2}:\\d{2}\\s+\\w*\\s+\\w*\\[\\d*\\]:\\s+");
        Matcher matcher = headerPattern.matcher(message);
        if (matcher.find()) {
            header = matcher.group();
        }
        event = message.substring(header.length());
        return event;
    }

    private String analyseEvent(String event) {
        String eventType = "unknown";

        Pattern disconnectedPattern = Pattern.compile("Disconnected\\s+");
        Pattern disconnectPattern = Pattern.compile("\\s+disconnect\\s+");
        Pattern ipPattern = Pattern.compile("\\s+[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}\\s+");
        Pattern portPattern = Pattern.compile("\\s+port\\s+[0-9]{1,5}");
        Pattern sessionClosedPattern = Pattern.compile("\\s+session\\s+closed\\s+");
        Pattern passwordAcceptedPattern = Pattern.compile("Accepted\\s+password\\s+for\\s+\\w+\\s+");
        Pattern sessionOpenedPattern = Pattern.compile("\\s+session\\s+opened\\s+for\\s+\\w+\\s+\\w+\\s+");
        Pattern userPattern = Pattern.compile("\\s+user\\s+\\w+\\s");


        String disconnectedString;
        Matcher disconnectedMatcher = disconnectedPattern.matcher(event);
        if (disconnectedMatcher.find()) {
            disconnectedString = disconnectedMatcher.group();
            eventType = "Stop";
        }

        String disconnectString;
        Matcher disconnectMatcher = disconnectPattern.matcher(event);
        if (disconnectMatcher.find()) {
            disconnectString = disconnectMatcher.group();
            eventType = "Stop";
        }

        String openedString;
        Matcher sessionoOpenedMatcher = sessionOpenedPattern.matcher(event);
        if (sessionoOpenedMatcher.find()) {
            openedString = sessionoOpenedMatcher.group();
            eventType = "Allowed";
        }

        String ipString;
        Matcher ipMatcher = ipPattern.matcher(event);
        if (ipMatcher.find()) {
            ipString = ipMatcher.group();
        }

        String portString;
        Matcher portMatcher = portPattern.matcher(event);
        if (portMatcher.find()) {
            portString = portMatcher.group();
        }

        String userString;
        Matcher userMatcher = userPattern.matcher(event);
        if (userMatcher.find()) {
            userString = userMatcher.group();
        }

        String passwordAcceptedString;
        Matcher passwordAcceptedMatcher = passwordAcceptedPattern.matcher(event);
        if (passwordAcceptedMatcher.find()) {
            passwordAcceptedString = passwordAcceptedMatcher.group();
            eventType = "Access";
        }

        String sessionClosedString;
        Matcher sessionClosedMatcher = sessionClosedPattern.matcher(event);
        if (sessionClosedMatcher.find()) {
            sessionClosedString = sessionClosedMatcher.group();
            eventType = "Stop";
        }

        return eventType;
    }

}
