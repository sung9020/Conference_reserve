package com.sung.conference.controller;

import com.sung.conference.service.ReserveInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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

        reserveInterface.getReserve();

//        List<KeywordRankDto> keywordRankDtoList = searchService.getTopKeywordRank();
//        model.addAttribute("keywordRankDtoList", keywordRankDtoList);

        return "main";
    }

}
