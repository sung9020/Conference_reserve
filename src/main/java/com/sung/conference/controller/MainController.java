package com.sung.conference.controller;

import com.sung.conference.dto.ResultDto;
import com.sung.conference.service.ReserveInterface;
import com.sung.conference.service.constant.ConferenceRoomEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
        ResultDto result = reserveInterface.getReservation(currentDate);
        model.addAttribute("localDate", currentDate);
        model.addAttribute("reserveList", result);
        model.addAttribute("conferenceRoomList", ConferenceRoomEnum.values());

        return "main_new";
    }



}
