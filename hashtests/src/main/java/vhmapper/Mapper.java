package vhmapper;

import lombok.SneakyThrows;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Stanislav Tretyakov
 * 27.04.2023
 */
public class Mapper {

    private static final char DELIMITER = '.';

    @SneakyThrows
    public static Map<String, VarHandle> init(Class<?> redEntity) {
        final var result = new HashMap<String, VarHandle>();
        for (Field declaredField : redEntity.getDeclaredFields()) {
            final var annotation = declaredField.getAnnotation(MappedValue.class);
            if (annotation != null) {
                final var value = annotation.value();
                VarHandle vh = MethodHandles
                        .privateLookupIn(redEntity, MethodHandles.lookup())
                        .findVarHandle(redEntity, declaredField.getName(), declaredField.getType());
                result.put(value, vh);
            }
        }
        return result;
    }

    @SneakyThrows
    public static <E extends MappedEntity> E fromMap(Map<String, String> vals, Class<E> rdClass) {
        final E result = rdClass.getConstructor().newInstance();
        vals.forEach((hashFieldName, hashFieldValue) -> assignSingleField(result, hashFieldName, hashFieldValue));
        return result;
    }

    private static <E extends MappedEntity> void assignSingleField(E result, String hashFieldName, String hashFieldValue) {
        Map<String, VarHandle> vhs = result.varHandles();
        final var vh = vhs.get(hashFieldName);
        if (vh != null) {
            fillTheField(result, hashFieldValue, vh);
        } else {
            VarHandle subRdEntityVarHandle = null;
            int lastDelimiter = 0;
            while (subRdEntityVarHandle == null) {
                final var endIndex = hashFieldName.indexOf(DELIMITER, lastDelimiter);
                subRdEntityVarHandle = vhs.get(hashFieldName.substring(0, endIndex));
                lastDelimiter = endIndex + 1;
            }
            if (subRdEntityVarHandle.get(result) == null) {
                initSubRdEntity(result, subRdEntityVarHandle);
            }
            assignSingleField((MappedEntity) subRdEntityVarHandle.get(result), hashFieldName, hashFieldValue);
        }
    }

    @SneakyThrows
    private static <E extends MappedEntity> void initSubRdEntity(E result, VarHandle subRdEntityVarHandle) {
        subRdEntityVarHandle.set(result, subRdEntityVarHandle.varType().getConstructor().newInstance());
    }

    private static void fillTheField(Object target, String rawValue, VarHandle vh) {
        final Class<?> fieldType = vh.varType();
        Object value = null;
        if (fieldType == String.class) {
            value = rawValue;
        } else if (fieldType == int.class || fieldType == Integer.class) {
            value = Integer.parseInt(rawValue);
        } else if (fieldType == boolean.class || fieldType == Boolean.class) {
            value = Boolean.valueOf(rawValue);
        } else if (fieldType.isEnum()) {
            for (final var enumConstant : fieldType.getEnumConstants()) {
                final var castEnum = (Enum) enumConstant;
                if (castEnum.name().equals(rawValue)) {
                    value = castEnum;
                    break;
                }
            }
        }
        vh.set(target, value);
    }

    public static void main(String[] args) {
        final var four = new Four();
        four.setHell(666);

        final var third = new Third();
        third.setHoard("BIG HOARD");
        third.setFour(four);

        final var newSub = new Subber();
        newSub.setSubBus(Subber.Sub.TWO);
        newSub.setTree(third);

        final var s = new TheSession();
        s.setUri("http://some-uri");
        s.setAcknowledged(true);
        s.setSomeNumber(123);
        s.setNSub(newSub);

        System.out.println(s);
        final var mapped = s.toMap();
        System.out.println(mapped);

        final var deserialized = TheSession.fromMap(mapped);
        System.out.println(deserialized);
    }
}
