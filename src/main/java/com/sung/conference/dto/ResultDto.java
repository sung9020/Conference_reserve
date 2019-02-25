package com.sung.conference.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ResultDto {

    boolean result;
    String msg;
    Map<String, Object> value;

    public ResultDto(){
        result = false;
        msg ="";
        value = new HashMap<>();
    }

}
