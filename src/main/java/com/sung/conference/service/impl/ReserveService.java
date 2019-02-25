package com.sung.conference.service.impl;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sung.conference.dto.ReserveDto;
import com.sung.conference.dto.ResultDto;
import com.sung.conference.entity.ReserveInfo;
import com.sung.conference.repositoy.ReserveRepository;
import com.sung.conference.service.ReserveInterface;
import com.sung.conference.service.constant.TypeEnum;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReserveService implements ReserveInterface {

    @Autowired
    ReserveRepository reserveRepository;

    @Override
    @Transactional
    public ResultDto setReserve(ReserveDto request) {

        ResultDto result = new ResultDto();

        // normal
        if(request.getType() == TypeEnum.NORMAL.getType()){
            List<ReserveDto> reserveList = reserveRepository.findByReserveDate(request.getReserveDate()).map(ReserveDto::new).collect(Collectors.toList());

            if(request.isEnabledReservation(reserveList)){
                reserveRepository.save(request.toEntity());
            }

        }else{ // repeat
            int repeatCount = request.getRepeatCount();

            for(int i = 0; i < repeatCount; i++){
                if(i == 0){
                    List<ReserveDto> reserveList = reserveRepository.findByReserveDate(request.getReserveDate()).map(ReserveDto::new).collect(Collectors.toList());

                    if(request.isEnabledReservation(reserveList)){
                        reserveRepository.save(request.toEntity());
                    }
                }else{
                    LocalDate reserveDate = request.getReserveDate();
                    reserveDate.plusDays(i);
                    List<ReserveDto> reserveList = reserveRepository.findByReserveDate(request.getReserveDate()).map(ReserveDto::new).collect(Collectors.toList());

                    if(request.isEnabledReservation(reserveList)){
                        reserveRepository.save(request.toEntity());
                    }
                }
            }
        }

        return result;
    }

    @Override
    public List<ReserveDto> getReserve(LocalDate requestDate) {

        List<ReserveDto> reserveList = reserveRepository.findByReserveDate(requestDate).map(ReserveDto::new).collect(Collectors.toList());

        return reserveList;
    }


}
