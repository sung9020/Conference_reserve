package com.sung.conference.service;

import com.sung.conference.dto.ReserveDto;
import com.sung.conference.dto.ResultDto;

import java.time.LocalDate;

public interface ReserveInterface {

    ResultDto setReservation(ReserveDto request);

    ResultDto getReservation(LocalDate requestDate);
}
