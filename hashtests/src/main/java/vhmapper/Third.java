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
public class Third implements MappedEntity {
    private static final Map<String, VarHandle> VH = Mapper.init(Third.class);

    @MappedValue("new_sub.third.hrd")
    private String hoard;

    @MappedValue("new_sub.third.f4")
    private Four four;

    @Override
    public Map<String, String> toMap() {
        return toMap(VH);
    }

    @Override
    public Map<String, VarHandle> varHandles() {
        return VH;
    }

    public static Third fromMap(Map<String, String> vals) {
        return Mapper.fromMap(vals, Third.class);
    }
}
