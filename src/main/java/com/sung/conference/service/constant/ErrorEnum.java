package com.sung.conference.service.constant;

import lombok.Getter;

public enum ErrorEnum {
    SET_DATA_COMPLETE(0,"회의 예약에 성공했습니다."),
    GET_DATA_COMPLETE(1,"회의 내역 조회에 성공했습니다."),
    REDIS_ERROR(2,"DB 데이터 입력에 실패했습니다."),
    REPEAT_COUNT_ERROR(4,"반복 횟수를 0 이상 입력해주세요."),
    TIME_ERROR(5,"시작 시간과 종료 시간를 정상적으로 입력해주세요"),
    DATE_ERROR(6,"당일보다 이전 날짜는 예약 되지 않습니다.");

    @Getter
    private final int errorCode;

    @Getter
    private final String message;

    ErrorEnum(int errorCode, String message){
        this.errorCode = errorCode;
        this.message = message;
    }

}
