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
public class Other implements MappedEntity {
    private static final Map<String, VarHandle> VH = Mapper.init(Other.class);

    @MappedValue("other.empty")
    private String empty;

    @Override
    public Map<String, String> toMap() {
        return toMap(VH);
    }

    @Override
    public Map<String, VarHandle> varHandles() {
        return VH;
    }

    public static Other fromMap(Map<String, String> vals) {
        return Mapper.fromMap(vals, Other.class);
    }
}
