package vhmapper;

import lombok.SneakyThrows;

import java.lang.invoke.VarHandle;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Stanislav Tretyakov
 * 27.04.2023
 */
public interface MappedEntity {

    default Map<String, String> toMap(Map<String, VarHandle> vh) {
        var res = new HashMap<String, String>();
        vh.forEach((k, v) -> {
            final var o = v.get(this);
            if (o != null) {
                if (o instanceof MappedEntity nr) {
                    res.putAll(nr.toMap());
                } else {
                    res.put(k, String.valueOf(o));
                }
            }
        });
        return res;
    }

    /**
     * Approach with overridden method.
     */
    Map<String, String> toMap();

    /**
     * Eases deserialization.
     */
    Map<String, VarHandle> varHandles();

    /**
     * Alternative approach with reflection.
     */
    @SneakyThrows
    default Map<String, String> toMapWithReflection() {
        final var vh = getClass().getDeclaredField("VH");
        vh.setAccessible(true);
        return toMap((Map<String, VarHandle>) vh.get(null));
    }

    /**
     * Eases deserialization. Alternative approach with reflection.
     */
    @SneakyThrows
    default Map<String, VarHandle> varHandlesWithReflection() {
        final var vh = getClass().getDeclaredField("VH");
        vh.setAccessible(true);
        return (Map<String, VarHandle>) vh.get(null);
    }
}
