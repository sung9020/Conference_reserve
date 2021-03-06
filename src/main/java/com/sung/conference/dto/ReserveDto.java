package com.sung.conference.dto;

import com.sung.conference.entity.ReserveInfo;
import lombok.Data;

import java.awt.dnd.DropTarget;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data // lombok
public class ReserveDto {
    String id;
    String conferenceRoomName;
    String user;
    int conferenceType;
    int repeatCount;
    LocalDate reserveDate;
    LocalTime startTime;
    LocalTime endTime;

    // default constructor
    public ReserveDto(){
    }


    public ReserveDto(ReserveInfo entity){
        this.id = entity.getId();
        this.conferenceRoomName = entity.getConferenceRoomName();
        this.user = entity.getUser();
        this.conferenceType = entity.getConferenceType();
        this.repeatCount = entity.getRepeatCount();
        this.reserveDate = entity.getReserveDate();
        this.startTime = entity.getStartTime();
        this.endTime = entity.getEndTime();
    }

    public ReserveInfo toEntity(){
        return ReserveInfo.builder()
                .conferenceRoomName(conferenceRoomName)
                .user(user)
                .conferenceType(conferenceType)
                .repeatCount(repeatCount)
                .reserveDate(reserveDate)
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }


    public boolean isEnabledReservation(List<ReserveDto> reserveList){

        boolean result = false;

        if(reserveList.size() > 0){
            reserveList = reserveList.stream().filter(data -> data.getConferenceRoomName().equals(this.conferenceRoomName)).collect(Collectors.toList());
            if(reserveList.size() > 0){
                for(ReserveDto data : reserveList){
                    if (this.startTime.isBefore(data.startTime)) {
                        if (this.endTime.isBefore(data.startTime) || this.endTime.equals(data.startTime)) {
                            result = true;
                        } else {
                            result = false;
                        }
                    } else if (this.startTime.isAfter(data.endTime) || this.startTime.equals(data.endTime)) {
                        result = true;
                    } else {
                        result = false;
                    }
                }
            }else{
                //empty reservation
                result = true;
            }
        }else{
            //empty reservation
            result = true;
        }

        return result;
    }
}
