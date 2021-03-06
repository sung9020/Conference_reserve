package com.sung.conference.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ResultDto {
    boolean result;
    String msg;
    List<ReserveDto> reservationList;
    int failCount;
    int errorCode;

}
