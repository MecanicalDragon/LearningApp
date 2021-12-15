package net.medrag.microservices.aspectj.puppets;

import org.aspectj.lang.reflect.FieldSignature;

import java.lang.reflect.Field;

public privileged aspect PuppetAspect {

    void around(Puppet puppet): set(String Puppet.hash) && target(puppet) {
        FieldSignature fieldSignature = (FieldSignature) thisJoinPoint.getSignature();
        Field field = fieldSignature.getField();

        String oldValue = puppet.hash;
        String newValue = ((String) thisJoinPoint.getArgs()[0]);
        proceed(puppet);
        String actualNewValue = puppet.hash;

        System.out.printf("Puppet %s hash field has changed: old value=%s, new value=%s, actual new value=%s\n",
                puppet.name, oldValue, newValue, actualNewValue);
    }

}
