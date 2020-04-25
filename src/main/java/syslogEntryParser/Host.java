package syslogEntryParser;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Host {
    String ip;

    public Host() {
        InetAddress inetAddress;
        try {
            inetAddress = InetAddress.getLocalHost();
            this.ip = inetAddress.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
