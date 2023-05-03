package vhmapper;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.invoke.VarHandle;
import java.util.Map;

/**
 * @author Stanislav Tretyakov
 * 27.04.2023
 */
@Data
@NoArgsConstructor
public class TheSession implements MappedEntity {
    private static final Map<String, VarHandle> VH = Mapper.init(TheSession.class);

    @MappedValue("session_uri")
    private String uri;
    @MappedValue("acknowledged")
    private boolean acknowledged;
    @MappedValue("session_name")
    private String name;
    @MappedValue("some_number")
    private int someNumber;
    @MappedValue("new_sub")
    private Subber nSub;
    @MappedValue("other")
    private Other other;

    @Override
    public Map<String, String> toMap() {
        return toMap(VH);
    }

    @Override
    public Map<String, VarHandle> varHandles() {
        return VH;
    }

    public static TheSession fromMap(Map<String, String> vals) {
        return Mapper.fromMap(vals, TheSession.class);
    }
}