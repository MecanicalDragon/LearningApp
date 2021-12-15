package net.medrag.microservices.aspectj.tracked;

import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.FieldSignature;

import java.lang.reflect.Field;

public aspect TrackedAspect {

    pointcut trackedFields(Tracked tracked, Object newVal)
            : @annotation(tracked) && set(* *) && args(newVal);

    void around(Tracked tracked, Object newVal): trackedFields(tracked, newVal) {
        try {
            Field fieldToUpdate = null;
            if (tracked.tracked()) {
                fieldToUpdate = initFieldToUpdate(thisJoinPoint.getSignature());
                final Object oldVal = fieldToUpdate.get(thisJoinPoint.getTarget());
                System.out.printf("Tracked field <%s> of class <%s> is going to be updated from <%s> to <%s>...\n",
                        fieldToUpdate.getName(), thisJoinPoint.getTarget().getClass(), oldVal, newVal);
            }
            if (!tracked.permitted()) {
                if (fieldToUpdate == null) {
                    fieldToUpdate = initFieldToUpdate(thisJoinPoint.getSignature());
                }
                if (fieldToUpdate.get(thisJoinPoint.getTarget()) != null) {
                    System.out.printf("Tracked field <%s> of class <%s> is restricted for update!",
                            fieldToUpdate.getName(), thisJoinPoint.getTarget().getClass());
                    return;
                }
            }

            proceed(tracked, newVal);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Field initFieldToUpdate(Signature signature) {
        FieldSignature fieldSignature = (FieldSignature) signature;
        Field fieldToUpdate = fieldSignature.getField();
        fieldToUpdate.setAccessible(true);
        return fieldToUpdate;
    }
}
