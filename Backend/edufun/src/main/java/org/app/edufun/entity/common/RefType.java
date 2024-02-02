package org.app.edufun.entity.common;

import java.util.Set;

public enum RefType {
    RECRUITMENT_CLIENT, OFFSHORE_CLIENT, OFFSHORE_RECRUITMENT_CLIENT;

    private static final Set<RefType> VALUES = Set.of(RefType.values());

    public static Set<RefType> getValues() {
        return VALUES;
    }

    /* String toString;

    private RefType(String toString) {
        this.toString = toString;
    }
    public String toString(){
        return toString;
    }*/
}
