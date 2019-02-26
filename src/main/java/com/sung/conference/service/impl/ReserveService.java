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
    public ResultDto setreserve(ReserveDto request) {

        ResultDto result = new ResultDto();
        StringBuilder builder = new StringBuilder();
        // normal
        if(request.getType() == TypeEnum.NORMAL.getType()){
            List<ReserveDto> reserveList = reserveRepository.findByReserveDate(request.getReserveDate()).stream().map(ReserveDto::new).collect(Collectors.toList());

            if(request.isEnabledReservation(reserveList)){
                reserveRepository.save(request.toEntity());
                result.setResult(true);
                result.setMsg("예약에 성공했습니다.");
            }else{
                result.setResult(false);
                result.setMsg("예약에 실패했습니다.");
            }
        }else{ // repeat
            int repeatCount = request.getRepeatCount();
            int failCount = 0;

            for(int i = 0; i <= repeatCount; i++){
                if(i == 0){
                    List<ReserveDto> reserveList = reserveRepository.findByReserveDate(request.getReserveDate()).stream().map(ReserveDto::new).collect(Collectors.toList());

                    if(request.isEnabledReservation(reserveList)){
                        reserveRepository.save(request.toEntity());
                    }else{
                        failCount++;
                    }
                }else{
                    LocalDate reserveDate = request.getReserveDate();
                    reserveDate = reserveDate.plusWeeks(i);
                    request.setReserveDate(reserveDate);
                    List<ReserveDto> reserveList = reserveRepository.findByReserveDate(request.getReserveDate()).stream().map(ReserveDto::new).collect(Collectors.toList());

                    if(request.isEnabledReservation(reserveList)){
                        reserveRepository.save(request.toEntity());
                    }else{
                        failCount++;
                    }
                }

                if(failCount == repeatCount){
                    builder.append("정기 회의 예약에 실패했습니다. 실패횟수 : ")
                            .append(repeatCount)
                            .append("회(완전 실패)");

                    result.setResult(false);
                    result.setMsg(builder.toString());
                }else if(failCount > 0){
                    builder.append("정기 회의 예약에 일부 성공했습니다. 실패횟수 : ")
                            .append(repeatCount)
                            .append("회");

                    result.setResult(true);
                    result.setMsg(builder.toString());
                }else{
                    result.setResult(true);
                    result.setMsg("정기 회의 예약에 성공했습니다.");
                }
            }
        }

        return result;
    }

    @Override
    public ResultDto getReserve(LocalDate requestDate) {

        ResultDto result = new ResultDto();

        List<ReserveDto> reserveList = reserveRepository.findByReserveDate(requestDate).stream().map(ReserveDto::new).collect(Collectors.toList());

        result.setResult(true);
        result.setMsg("조회에 성공했습니다.");
        result.setReserveList(reserveList);


        return result;
    }



}
