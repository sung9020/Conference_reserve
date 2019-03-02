package com.sung.conference;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.sung.conference.controller.ReserveApiController;
import com.sung.conference.dto.ReserveDto;
import com.sung.conference.dto.ResultDto;
import com.sung.conference.entity.ReserveInfo;
import com.sung.conference.service.constant.ConferenceRoomEnum;
import com.sung.conference.service.constant.TypeEnum;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ReserveApiController.class)
@ActiveProfiles("local")
public class ReserveApiContrllerTest {

    @MockBean
    private ReserveApiController reserveApiController;

    private MockMvc mockMvc;

    private ObjectMapper mapper;

    @Before
    public void setup(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(reserveApiController).build();
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new ParameterNamesModule());
        this.mapper.registerModule(new Jdk8Module());
        this.mapper.registerModule(new JavaTimeModule());

    }

    // 기본 테스트 케이스(모든 테스트 케이스의 기본 예약 정보 - 일반 예약 타입)
    private final LocalDate testReserveDate = LocalDate.of(2019,03,8);
    private final LocalTime testStartTime = LocalTime.of(14,00,00);
    private final LocalTime testEndTime = LocalTime.of(15,30,00);
    private final String testConferenceRoomName = ConferenceRoomEnum.ROOM_D.getRoomName();

    @Test
    public void controllerTestForPost() throws Exception{

        ReserveInfo reserveInfo = ReserveInfo.builder()
                .reserveDate(testReserveDate).build();

        ReserveDto reserveDto = new ReserveDto(reserveInfo);
        String request = mapper.writeValueAsString(reserveDto);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = post("/reservation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk());
    }

    @Test
    public void controllerTestForPut() throws Exception{

        ReserveInfo reserveInfo = ReserveInfo.builder()
                .reserveDate(testReserveDate)
                .conferenceRoomName(testConferenceRoomName)
                .user("성민규")
                .conferenceType(TypeEnum.NORMAL.getType())
                .repeatCount(0)
                .startTime(testStartTime)
                .endTime(testEndTime)
                .build();

        ReserveDto reserveDto = new ReserveDto(reserveInfo);
        String request = mapper.writeValueAsString(reserveDto);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = put("/reservation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk());
    }
}
