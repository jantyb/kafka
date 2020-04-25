import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import syslogEntryParser.SyslogEntry;
import java.io.IOException;

public class CustomSyslogEntrySerializer extends StdSerializer<SyslogEntry> {

    public CustomSyslogEntrySerializer() {
        this(null);
    }

    protected CustomSyslogEntrySerializer(Class<SyslogEntry> t) {
        super(t);
    }

    @Override
    public void serialize(SyslogEntry syslogEntry, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        //ObjectMapper Obj = new ObjectMapper();

        ObjectMapper Obj = (ObjectMapper) jsonGenerator.getCodec();
        try {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("@timestamp", syslogEntry.getTimestamp().toString());
            jsonGenerator.writeStringField("message", syslogEntry.getMessage());

            String agentJsonStr = Obj.writeValueAsString(syslogEntry.getAgent());
            jsonGenerator.writeStringField("agent",agentJsonStr);

            String hostJsonStr = Obj.writeValueAsString(syslogEntry.getHost());
            jsonGenerator.writeStringField("host",hostJsonStr);

            String eventJsonStr = Obj.writeValueAsString(syslogEntry.getEvent());
            jsonGenerator.writeStringField("event",eventJsonStr);

            String processJsonStr = Obj.writeValueAsString(syslogEntry.getEvent());
            jsonGenerator.writeStringField("process",processJsonStr);

            jsonGenerator.writeEndObject();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
