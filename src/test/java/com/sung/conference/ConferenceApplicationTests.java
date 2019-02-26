package com.sung.conference;

import com.sung.conference.dto.ReserveDto;
import com.sung.conference.dto.ResultDto;
import com.sung.conference.entity.ReserveInfo;
import com.sung.conference.repositoy.ReserveRepository;
import com.sung.conference.service.ReserveInterface;
import com.sung.conference.service.constant.ConferenceRoomEnum;
import com.sung.conference.service.constant.TypeEnum;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

// TODO
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "local")
public class ConferenceApplicationTests {

    @Autowired
    private ReserveRepository reserveRepository;

    @Autowired
    ReserveInterface reserveInterface;

    @After
    public void tearDown() {
        reserveRepository.deleteAll();
    }

    @Test
    public void setReserveRepository(){

        ReserveInfo reserveInfo = ReserveInfo.builder()
                .reserveDate(LocalDate.of(2019,02,25))
                .conferenceRoomName("회의실A")
                .user("성민규")
                .type(TypeEnum.NORMAL.getType())
                .repeatCount(0)
                .startTime(LocalTime.of(14,00,00))
                .endTime(LocalTime.of(15,00,00))
                .build();
        // 예약 데이터 저장
        reserveRepository.save(reserveInfo);
    }

    @Test
    public void setNormalReserveTest() {

        ReserveInfo existInfo = ReserveInfo.builder()
                .reserveDate(LocalDate.of(2019,02,25))
                .conferenceRoomName(ConferenceRoomEnum.회의실A.name())
                .user("성민규")
                .type(TypeEnum.NORMAL.getType())
                .repeatCount(0)
                .startTime(LocalTime.of(14,00,00))
                .endTime(LocalTime.of(15,00,00))
                .build();

        reserveRepository.save(existInfo);

        ReserveInfo reserveInfo = ReserveInfo.builder()
                .reserveDate(LocalDate.of(2019,02,25))
                .conferenceRoomName(ConferenceRoomEnum.회의실A.name())
                .user("성민규")
                .type(TypeEnum.NORMAL.getType())
                .repeatCount(0)
                .startTime(LocalTime.of(13,00,00))
                .endTime(LocalTime.of(14,30,00))
                .build();

        ReserveDto reserveDto = new ReserveDto(reserveInfo);

        ResultDto result = reserveInterface.setreserve(reserveDto);

        List<ReserveDto> reserveList = reserveRepository.findByReserveDate(LocalDate.of(2019,02,25)).stream().map(ReserveDto::new).collect(Collectors.toList());
        // 예약 성공?
        Assert.assertThat(reserveList.size(), CoreMatchers.is(1));
    }

    @Test
    public void setReapeatReserveTest() {

        ReserveInfo existInfo = ReserveInfo.builder()
                .reserveDate(LocalDate.of(2019,03,03))
                .conferenceRoomName(ConferenceRoomEnum.회의실A.name())
                .user("성민규")
                .type(TypeEnum.NORMAL.getType())
                .repeatCount(0)
                .startTime(LocalTime.of(14,00,00))
                .endTime(LocalTime.of(15,00,00))
                .build();

        reserveRepository.save(existInfo);

        ReserveInfo reserveInfo = ReserveInfo.builder()
                .reserveDate(LocalDate.of(2019,02,25))
                .conferenceRoomName(ConferenceRoomEnum.회의실A.name())
                .user("성민규")
                .type(TypeEnum.REPEAT.getType())
                .repeatCount(1)
                .startTime(LocalTime.of(13,00,00))
                .endTime(LocalTime.of(14,00,00))
                .build();

        ReserveDto reserveDto = new ReserveDto(reserveInfo);

        ResultDto result = reserveInterface.setreserve(reserveDto);

        List<ReserveDto> reserveList = reserveRepository.findByReserveDate(LocalDate.of(2019,03,04)).stream().map(ReserveDto::new).collect(Collectors.toList());
        // 예약 성공?
        Assert.assertThat(reserveList.size(), CoreMatchers.is(1));
    }

    @Test
    public void getReserve() {

        ReserveInfo existInfo = ReserveInfo.builder()
                .reserveDate(LocalDate.of(2019,02,25))
                .conferenceRoomName(ConferenceRoomEnum.회의실A.name())
                .user("성민규")
                .type(TypeEnum.NORMAL.getType())
                .repeatCount(0)
                .startTime(LocalTime.of(14,00,00))
                .endTime(LocalTime.of(15,00,00))
                .build();

        reserveRepository.save(existInfo);

        List<ReserveDto> reserveList = reserveRepository.findByReserveDate(LocalDate.of(2019,02,25)).stream().map(ReserveDto::new).collect(Collectors.toList());
        // 예약 조회
        Assert.assertThat(reserveList.size(), CoreMatchers.is(1));
    }

}
