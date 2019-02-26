package com.sung.conference.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@RedisHash("conference")
@Data
public class ReserveInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    String conferenceRoomName;
    String user;
    int type;
    int repeatCount;

    @Indexed
    LocalDate reserveDate;

    LocalTime startTime;
    LocalTime endTime;

    @Builder
    public ReserveInfo(String conferenceRoomName, String user, int type, int repeatCount, LocalDate reserveDate, LocalTime startTime, LocalTime endTime){
        this.conferenceRoomName = conferenceRoomName;
        this.user = user;
        this.type = type;
        this.repeatCount = repeatCount;
        this.reserveDate = reserveDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}