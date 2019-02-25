package com.sung.conference.dto;

import com.sung.conference.entity.ReserveInfo;
import lombok.Data;

import java.awt.dnd.DropTarget;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data // lombok
public class ReserveDto {
    String id;
    String conferenceRoomName;
    String user;
    int type;
    int repeatCount;
    LocalDate reserveDate;
    LocalTime startTime;
    LocalTime endTime;

    public ReserveDto(ReserveInfo entity){
        id = entity.getId();
        conferenceRoomName = entity.getConferenceRoomName();
        user = entity.getUser();
        type = entity.getType();
        repeatCount = entity.getRepeatCount();
        reserveDate = entity.getReserveDate();
        startTime = entity.getStartTime();
        endTime = entity.getEndTime();
    }

    public ReserveInfo toEntity(){
        return ReserveInfo.builder()
                .conferenceRoomName(conferenceRoomName)
                .user(user)
                .repeatCount(repeatCount)
                .reserveDate(reserveDate)
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }


    public boolean isEnabledReservation(List<ReserveDto> reserveList){

        boolean result = false;

        for(ReserveDto data : reserveList){
            if(!this.startTime.isBefore(data.startTime) || !this.startTime.isAfter(data.endTime) ){
                result = false;
            }else if(!this.endTime.isBefore(data.startTime) || ! this.endTime.isAfter(data.endTime)){
                result = false;
            }else{ // 같거나 다른 예약 시간 밖이다.
                result = true;
            }

            // 같은 회의실
            if(this.conferenceRoomName.equals(data.conferenceRoomName)){
                result = false;
            }else{
                result = true;
            }
        }

        return false;
    }
}
