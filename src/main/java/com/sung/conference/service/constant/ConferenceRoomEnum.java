package com.sung.conference.service.constant;

import lombok.Getter;

public enum ConferenceRoomEnum {
    ROOM_A("회의실A"),
    ROOM_B("회의실B"),
    ROOM_C("회의실C"),
    ROOM_D("회의실D"),
    ROOM_E("회의실E"),
    ROOM_F("회의실F"),
    ROOM_G("회의실G");

    @Getter
    private String roomName;

    ConferenceRoomEnum(String roomName){
        this.roomName = roomName;
    }

}
