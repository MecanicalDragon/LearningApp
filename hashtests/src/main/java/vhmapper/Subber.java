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
public class Subber implements MappedEntity {
    private static final Map<String, VarHandle> VH = Mapper.init(Subber.class);

    @MappedValue("new_sub.sub_bus")
    private Sub subBus;

    @MappedValue("new_sub.third")
    private Third tree;

    @Override
    public Map<String, String> toMap() {
        return toMap(VH);
    }

    @Override
    public Map<String, VarHandle> varHandles() {
        return VH;
    }

    public static Subber fromMap(Map<String, String> vals) {
        return Mapper.fromMap(vals, Subber.class);
    }

    enum Sub {
        ONE, TWO
    }
}
