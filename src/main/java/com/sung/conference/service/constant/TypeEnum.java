package com.sung.conference.service.constant;

import lombok.Data;
import lombok.Getter;
import org.springframework.core.type.filter.RegexPatternTypeFilter;

import java.lang.annotation.Repeatable;
import java.security.PrivateKey;

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
