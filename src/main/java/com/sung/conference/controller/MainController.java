package com.sung.conference.controller;

import com.sung.conference.dto.ReserveDto;
import com.sung.conference.dto.ResultDto;
import com.sung.conference.service.ReserveInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    ReserveInterface reserveInterface;

    /* main */
    @RequestMapping(value = "/")
    public String index() throws Exception{

        return "redirect:/main";
    }

    /* main - 현재 서버 날짜 */
    @RequestMapping(value = "/main")
    public String main(Model model) throws Exception{

        LocalDate currentDate = LocalDate.now();
        ResultDto result = reserveInterface.getReserve(currentDate);
        model.addAttribute("reserveList", result);

        return "main";
    }

    /* POST */
    @RequestMapping(value = "/reservation", method = RequestMethod.POST)
    public ResultDto getReservation(@RequestParam("requestDate")
                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate requestDate) throws Exception{

        ResultDto result = reserveInterface.getReserve(requestDate);

        return result;
    }

    /* PUT */
    @RequestMapping(value = "/reservation", method = RequestMethod.PUT)
    public ResultDto setReservation(@RequestBody ReserveDto request) throws Exception{

        ResultDto result = reserveInterface.setreserve(request);

        return result;
    }

}
