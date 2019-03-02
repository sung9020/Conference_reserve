package com.sung.conference;

import com.sung.conference.controller.ReserveApiController;
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

import static org.junit.Assert.*;


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

    // 기본 테스트 케이스(모든 테스트 케이스의 기본 예약 정보 - 일반 예약 타입)
    private final LocalDate testReserveDate = LocalDate.of(2019,03,8);
    private final LocalTime testStartTime = LocalTime.of(14,00,00);
    private final LocalTime testEndTime = LocalTime.of(15,30,00);
    private final String testConferenceRoomName = ConferenceRoomEnum.ROOM_D.getRoomName();

    // 신규 예약 삽입 테스트 할때 쓰는 테스트 케이스 (일반 예약)
    private final LocalDate normalReserveDate = LocalDate.of(2019,03,9);
    private final LocalTime normalStartTime = LocalTime.of(14,00,00);
    private final LocalTime normalEndTime = LocalTime.of(15,00,00);
    private final String normalConferenceRoomName = ConferenceRoomEnum.ROOM_D.getRoomName();

    // 신규 예약 삽입 테스트 할때 쓰는 테스트 케이스 (정기 예약)
    private final LocalDate repeatReserveDate = LocalDate.of(2019,03,9);
    private final LocalTime repeatStartTime = LocalTime.of(14,00,00);
    private final LocalTime repeatEndTime = LocalTime.of(15,00,00);
    private final String repeatConferenceRoomName = ConferenceRoomEnum.ROOM_D.getRoomName();
    private final int repaertCount = 2;

    @Test
    public void setDataByReservationRepositoryForTest(){

        ReserveInfo reserveInfo = ReserveInfo.builder()
                .reserveDate(testReserveDate)
                .conferenceRoomName(testConferenceRoomName)
                .user("성민규")
                .conferenceType(TypeEnum.NORMAL.getType())
                .repeatCount(0)
                .startTime(testStartTime)
                .endTime(testEndTime)
                .build();
        // 예약 데이터 저장
        reserveRepository.save(reserveInfo);
        List<ReserveDto> reserveList = reserveRepository.findByReserveDateOrderByReserveDate(testReserveDate).stream().map(ReserveDto::new).collect(Collectors.toList());
        // 예약 성공?
        if(reserveList.size() > 0) {
            assertThat(reserveList.get(0).getConferenceRoomName(), CoreMatchers.is(testConferenceRoomName));
            assertThat(reserveList.get(0).getReserveDate(), CoreMatchers.is(testReserveDate));
            assertThat(reserveList.get(0).getStartTime(), CoreMatchers.is(testStartTime));
            assertThat(reserveList.get(0).getEndTime(), CoreMatchers.is(testEndTime));
        }else{
            fail("레디스 적재 테스트 실패");
        }
    }

    @Test
    public void getReservationForTest() {

        ReserveInfo existInfo = ReserveInfo.builder()
                .reserveDate(testReserveDate)
                .conferenceRoomName(testConferenceRoomName)
                .user("성민규")
                .conferenceType(TypeEnum.NORMAL.getType())
                .repeatCount(0)
                .startTime(testStartTime)
                .endTime(testEndTime)
                .build();

        reserveRepository.save(existInfo);

        ResultDto resultDto  = reserveInterface.getReservation(testReserveDate);
        // 예약 조회
        if(resultDto.getReservationList().size() > 0){
            assertThat(resultDto.getReservationList().get(0).getConferenceRoomName(), CoreMatchers.is(testConferenceRoomName));
            assertThat(resultDto.getReservationList().get(0).getReserveDate(), CoreMatchers.is(testReserveDate));
            assertThat(resultDto.getReservationList().get(0).getStartTime(), CoreMatchers.is(testStartTime));
            assertThat(resultDto.getReservationList().get(0).getEndTime(), CoreMatchers.is(testEndTime));
        }else{
            fail("예약 조회 함수 테스트 실패");
        }

    }

    @Test
    public void setNormalReserveForTest() {

        ReserveInfo existInfo = ReserveInfo.builder()
                .reserveDate(testReserveDate)
                .conferenceRoomName(testConferenceRoomName)
                .user("성민규")
                .conferenceType(TypeEnum.NORMAL.getType())
                .repeatCount(0)
                .startTime(testStartTime)
                .endTime(testEndTime)
                .build();

        reserveRepository.save(existInfo);

        ReserveInfo reserveInfo = ReserveInfo.builder()
                .reserveDate(normalReserveDate)
                .conferenceRoomName(normalConferenceRoomName)
                .user("성민규")
                .conferenceType(TypeEnum.NORMAL.getType())
                .repeatCount(0)
                .startTime(normalStartTime)
                .endTime(normalEndTime)
                .build();

        ReserveDto reserveDto = new ReserveDto(reserveInfo);

        ResultDto result = reserveInterface.setReservation(reserveDto);

        List<ReserveDto> reserveList = reserveRepository.findByReserveDateOrderByReserveDate(normalReserveDate).stream().map(ReserveDto::new).collect(Collectors.toList());

        // 예약 성공?
        if(reserveList.size() > 0){
            assertThat(reserveList.get(0).getConferenceRoomName(), CoreMatchers.is(normalConferenceRoomName));
            assertThat(reserveList.get(0).getReserveDate(), CoreMatchers.is(normalReserveDate));
            assertThat(reserveList.get(0).getStartTime(), CoreMatchers.is(normalStartTime));
            assertThat(reserveList.get(0).getEndTime(), CoreMatchers.is(normalEndTime));
        }else{
            fail("일반 예약 함수 테스트 실패");
        }


    }

    @Test
    public void setReapeatReserveForTest() {

        // 예약 해놓기
        ReserveInfo existInfo = ReserveInfo.builder()
                .reserveDate(testReserveDate)
                .conferenceRoomName(testConferenceRoomName)
                .user("성민규")
                .conferenceType(TypeEnum.NORMAL.getType())
                .repeatCount(0)
                .startTime(testStartTime)
                .endTime(testEndTime)
                .build();
        ReserveDto existReserveDto = new ReserveDto(existInfo);
        reserveInterface.setReservation(existReserveDto);

        ReserveInfo reserveInfo = ReserveInfo.builder()
                .reserveDate(repeatReserveDate)
                .conferenceRoomName(repeatConferenceRoomName)
                .user("성민규")
                .conferenceType(TypeEnum.REPEAT.getType())
                .repeatCount(repaertCount)
                .startTime(repeatStartTime)
                .endTime(repeatEndTime)
                .build();

        ReserveDto reserveDto = new ReserveDto(reserveInfo);
        reserveInterface.setReservation(reserveDto);

        LocalDate tempLocalDate = repeatReserveDate;
        for(int i = 0; i <= repaertCount ; i++){

            if(i > 0) {
                tempLocalDate = tempLocalDate.plusWeeks(1);
            }

            List<ReserveDto> reserveList = reserveRepository.findByReserveDateOrderByReserveDate(repeatReserveDate.plusDays(i*7)).stream().map(ReserveDto::new).collect(Collectors.toList());
            if(reserveList.size() > 0){
                assertThat(reserveList.get(0).getConferenceRoomName(), CoreMatchers.is(repeatConferenceRoomName));
                assertThat(reserveList.get(0).getReserveDate(), CoreMatchers.is(tempLocalDate));
                assertThat(reserveList.get(0).getStartTime(), CoreMatchers.is(repeatStartTime));
                assertThat(reserveList.get(0).getEndTime(), CoreMatchers.is(repeatEndTime));
            }else{
                fail("정기 예약 함수 테스트 실패");
            }
        }
    }
}
