package com.sung.conference.dto;

import com.sung.conference.entity.ReserveInfo_NEW;
import lombok.Data;

import javax.naming.Name;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Data // lombok
public class ReserveInfoDto_new {
    String id;
    String conferenceRoomName;
    String user;
    int type;
    int repeatCount;
    LocalDate reserveDate;
    LocalTime startTime;
    LocalTime endTime;

    public ReserveInfo_NEW toEntity(){
        return ReserveInfo_NEW.builder()
                .conferenceRoomName(conferenceRoomName)
                .user(user)
                .repeatCount(repeatCount)
                .reserveDate(reserveDate)
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }
}
