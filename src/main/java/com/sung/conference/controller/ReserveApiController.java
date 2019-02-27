package com.sung.conference.controller;

import com.sung.conference.dto.ReserveDto;
import com.sung.conference.dto.ResultDto;
import com.sung.conference.service.ReserveInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

public class ReserveApiController {

    @Autowired
    ReserveInterface reserveInterface;

    /* POST */
    @RequestMapping(value = "/reservation", method = RequestMethod.POST)
    public ResultDto getReservation(@RequestParam("requestDate")
                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate requestDate) throws Exception{

        ResultDto result = reserveInterface.getReservation(requestDate);

        return result;
    }

    /* PUT */
    @RequestMapping(value = "/reservation", method = RequestMethod.PUT)
    public ResultDto setReservation(@RequestBody ReserveDto request) throws Exception{

        ResultDto result = reserveInterface.setReservation(request);

        return result;
    }
}
