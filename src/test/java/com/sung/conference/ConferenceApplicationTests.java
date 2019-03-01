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

    private final LocalDate testReserveDate = LocalDate.of(2019,03,01);
    private final LocalTime testStartTime = LocalTime.of(14,00,00);
    private final LocalTime testEndTime = LocalTime.of(15,30,00);

    @Test
    public void setDataByReservationRepository(){

        ReserveInfo reserveInfo = ReserveInfo.builder()
                .reserveDate(testReserveDate)
                .conferenceRoomName("회의실A")
                .user("성민규")
                .conferenceType(TypeEnum.NORMAL.getType())
                .repeatCount(0)
                .startTime(LocalTime.of(14,00,00))
                .endTime(LocalTime.of(15,00,00))
                .build();
        // 예약 데이터 저장
        reserveRepository.save(reserveInfo);
        List<ReserveDto> reserveList = reserveRepository.findByReserveDate(testReserveDate).stream().map(ReserveDto::new).collect(Collectors.toList());
        // 예약 성공?
        Assert.assertThat(reserveList.size(), CoreMatchers.is(1));
    }

    @Test
    public void getDataByReserveRepository() {

        ReserveInfo existInfo = ReserveInfo.builder()
                .reserveDate(LocalDate.of(2019,03,01))
                .conferenceRoomName(ConferenceRoomEnum.ROOM_A.getRoomName())
                .user("성민규")
                .conferenceType(TypeEnum.NORMAL.getType())
                .repeatCount(0)
                .startTime(LocalTime.of(14,00,00))
                .endTime(LocalTime.of(15,00,00))
                .build();

        reserveRepository.save(existInfo);

        List<ReserveDto> reserveList = reserveRepository.findByReserveDate(LocalDate.of(2019,03,1)).stream().map(ReserveDto::new).collect(Collectors.toList());
        // 예약 조회
        Assert.assertThat(reserveList.size(), CoreMatchers.is(1));
    }


    @Test
    public void getReservationTest() {

        ReserveInfo existInfo = ReserveInfo.builder()
                .reserveDate(LocalDate.of(2019,03,01))
                .conferenceRoomName(ConferenceRoomEnum.ROOM_A.getRoomName())
                .user("성민규")
                .conferenceType(TypeEnum.NORMAL.getType())
                .repeatCount(0)
                .startTime(LocalTime.of(14,00,00))
                .endTime(LocalTime.of(15,00,00))
                .build();

        reserveRepository.save(existInfo);


        LocalDate requestDate = LocalDate.of(2019,03,01);

        ResultDto resultDto  = reserveInterface.getReservation(requestDate);
        // 예약 조회
        Assert.assertThat(resultDto.getReservationList().size(), CoreMatchers.is(1));
    }

    @Test
    public void setNormalReserveTest() {

        ReserveInfo existInfo = ReserveInfo.builder()
                .reserveDate(LocalDate.of(2019,03,01))
                .conferenceRoomName(ConferenceRoomEnum.ROOM_A.getRoomName())
                .user("성민규")
                .conferenceType(TypeEnum.NORMAL.getType())
                .repeatCount(0)
                .startTime(LocalTime.of(14,00,00))
                .endTime(LocalTime.of(15,00,00))
                .build();

        reserveRepository.save(existInfo);

        ReserveInfo reserveInfo = ReserveInfo.builder()
                .reserveDate(LocalDate.of(2019,03,01))
                .conferenceRoomName(ConferenceRoomEnum.ROOM_A.getRoomName())
                .user("성민규")
                .conferenceType(TypeEnum.NORMAL.getType())
                .repeatCount(0)
                .startTime(LocalTime.of(13,00,00))
                .endTime(LocalTime.of(14,30,00))
                .build();

        ReserveDto reserveDto = new ReserveDto(reserveInfo);

        ResultDto result = reserveInterface.setReservation(reserveDto);

        List<ReserveDto> reserveList = reserveRepository.findByReserveDate(LocalDate.of(2019,03,1)).stream().map(ReserveDto::new).collect(Collectors.toList());
        // 예약 성공?
        Assert.assertThat(reserveList.size(), CoreMatchers.is(1));
    }

    @Test
    public void setReapeatReserveTest() {

        ReserveInfo existInfo = ReserveInfo.builder()
                .reserveDate(LocalDate.of(2019,03,8))
                .conferenceRoomName(ConferenceRoomEnum.ROOM_A.getRoomName())
                .user("성민규")
                .conferenceType(TypeEnum.NORMAL.getType())
                .repeatCount(0)
                .startTime(LocalTime.of(14,00,00))
                .endTime(LocalTime.of(15,00,00))
                .build();

        reserveRepository.save(existInfo);

        ReserveInfo reserveInfo = ReserveInfo.builder()
                .reserveDate(LocalDate.of(2019,03,01))
                .conferenceRoomName(ConferenceRoomEnum.ROOM_A.getRoomName())
                .user("성민규")
                .conferenceType(TypeEnum.REPEAT.getType())
                .repeatCount(1)
                .startTime(LocalTime.of(13,00,00))
                .endTime(LocalTime.of(14,00,00))
                .build();

        ReserveDto reserveDto = new ReserveDto(reserveInfo);

        ResultDto result = reserveInterface.setReservation(reserveDto);

        List<ReserveDto> reserveList = reserveRepository.findByReserveDate(LocalDate.of(2019,03,8)).stream().map(ReserveDto::new).collect(Collectors.toList());
        // 예약 성공?
        Assert.assertThat(reserveList.size(), CoreMatchers.is(1));
    }


}
