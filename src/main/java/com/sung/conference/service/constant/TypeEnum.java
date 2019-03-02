package com.sung.conference.service.constant;

import lombok.Getter;

public enum  TypeEnum {

    NORMAL(0,"일반 회의"),
    REPEAT(1,"정기 회의");

    @Getter
    private final int type;

    @Getter
    private final String typeName;

    TypeEnum(int type, String typeName){
        this.type = type;
        this.typeName = typeName;
    }
}
