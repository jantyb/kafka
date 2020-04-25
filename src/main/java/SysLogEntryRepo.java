import java.util.ArrayList;
import java.util.List;

public class SysLogEntryRepo {

    private final List<String> sysLogEntries;

    public SysLogEntryRepo() {
        sysLogEntries = new ArrayList<>();
        sysLogEntries.add("Apr  6 10:33:08 db01 sshd[18989]: Received disconnect from 192.168.41.24 port 61088:11: disconnected by user");
        sysLogEntries.add("Apr  6 10:33:08 db01 sshd[18989]: Disconnected from 192.168.41.24 port 61088");
        sysLogEntries.add("Apr  6 10:33:08 db01 sshd[18989]: pam_unix(sshd:session): session closed for user dba01");
        sysLogEntries.add("Apr  6 10:33:12 db01 sshd[21428]: Accepted password for dba01 from 192.168.41.23 port 50760 ssh2");
        sysLogEntries.add("Apr  6 10:33:12 db01 sshd[21428]: pam_unix(sshd:session): session opened for user dba01 by (uid=0)");

    }

    public List<String> getSysLogEntries() {
        return sysLogEntries;
    }
}
