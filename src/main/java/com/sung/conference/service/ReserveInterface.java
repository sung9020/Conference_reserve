package com.sung.conference.service;

import com.sung.conference.dto.ReserveDto;
import com.sung.conference.dto.ResultDto;
import com.sung.conference.entity.ReserveInfo;

import java.time.LocalDate;
import java.util.List;

public interface ReserveInterface {

    ResultDto setReserve(ReserveDto request);

    List<ReserveDto> getReserve(LocalDate requestDate);
}
