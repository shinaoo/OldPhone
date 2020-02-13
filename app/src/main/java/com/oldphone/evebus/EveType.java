package com.oldphone.evebus;

public enum EveType {
    UNKNOWN(0),

    ACTION_CALL(100),

    CONTACT_ADD(1000),
    CONTACT_DELETE(1001),
    CONTACT_UPDATE(1002),
    CONTACT_SELECT(1003),
    CONTACT_FRESH(1004),
    ;
    private int val;

    EveType(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }

    public static EveType getTypeByVal(int val) {
        for (EveType type : values()) {
            if (type.getVal() == val) {
                return type;
            }
        }
        return null;
    }
}
