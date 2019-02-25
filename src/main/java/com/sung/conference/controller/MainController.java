package com.sung.conference.controller;

import com.sung.conference.dto.ReserveDto;
import com.sung.conference.service.ReserveInterface;
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


    /* main */
    @RequestMapping(value = "/main")
    public String main(Model model) throws Exception{

        LocalDate currentDate = LocalDate.now();
        List<ReserveDto> reserveList = reserveInterface.getReserve(currentDate);
        model.addAttribute("reserveList", reserveList);
//        List<KeywordRankDto> keywordRankDtoList = searchService.getTopKeywordRank();
//        model.addAttribute("keywordRankDtoList", keywordRankDtoList);

        return "main";
    }




}
