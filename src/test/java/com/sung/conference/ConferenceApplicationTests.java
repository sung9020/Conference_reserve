package com.sung.conference;

import com.sung.conference.dto.ReserveInfoDto_new;
import com.sung.conference.repositoy.ReserveRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;

// TODO
@RunWith(SpringRunner.class)
@SpringBootTest
public class ConferenceApplicationTests {

    @Autowired
    private ReserveRepository reserveRepository;

    @After
    public void tearDown() {
        reserveRepository.deleteAll();
    }

    @Test
    public void setReservation(){
        //reserveRepository.save();


    }

    public void getReservation(){
       reserveRepository.findByReserveDate( LocalDate.of(2019,02,02));

    }


    @Test
    public void contextLoads() {
    }

}
