package com.sung.conference;

import com.sung.conference.dto.ReserveDto;
import com.sung.conference.dto.ResultDto;
import com.sung.conference.entity.ReserveInfo;
import com.sung.conference.repositoy.ReserveRepository;
import com.sung.conference.service.ReserveInterface;
import com.sung.conference.service.constant.TypeEnum;
import com.sung.conference.service.impl.ReserveService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.security.auth.kerberos.KerberosKey;
import java.security.Key;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

// TODO
@RunWith(SpringRunner.class)
@SpringBootTest
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

    }

    @Test
    public void setReserve() {

        ReserveInfo reserveInfo = ReserveInfo.builder()
                .reserveDate(LocalDate.of(2019,02,25))


        reserveInterface.setReserve();

    }

    @Test
    public List<ReserveDto> getReserve(LocalDate requestDate) {
        reserveRepository.findByReserveDate( LocalDate.of(2019,02,02));


    }

}
