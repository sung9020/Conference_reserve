package com.sung.conference.service.impl;

import com.sung.conference.dto.ReserveDto;
import com.sung.conference.dto.ResultDto;
import com.sung.conference.repositoy.ReserveRepository;
import com.sung.conference.service.ReserveInterface;
import com.sung.conference.service.constant.ErrorEnum;
import com.sung.conference.service.constant.TypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReserveService implements ReserveInterface {

    @Autowired
    ReserveRepository reserveRepository;

    @Override
    @Transactional
    public ResultDto setReservation(ReserveDto request) {

        ResultDto result = new ResultDto();
        StringBuilder builder = new StringBuilder();

        if(validateReserveData(request, result).isResult()){
            // normal
            if(request.getConferenceType() == TypeEnum.NORMAL.getType()){
                List<ReserveDto> reserveList = reserveRepository.findByReserveDateOrderByReserveDate(request.getReserveDate()).stream().map(ReserveDto::new).collect(Collectors.toList());

                if(request.isEnabledReservation(reserveList)){
                    reserveRepository.save(request.toEntity());
                    result.setResult(true);
                    result.setMsg(ErrorEnum.SET_DATA_COMPLETE.getMessage());
                    result.setErrorCode(ErrorEnum.SET_DATA_COMPLETE.getErrorCode());
                }else{
                    result.setResult(false);
                    result.setMsg(ErrorEnum.REDIS_ERROR.getMessage());
                    result.setErrorCode(ErrorEnum.REDIS_ERROR.getErrorCode());
                }
            }else{ // repeat
                int repeatCount = request.getRepeatCount();
                int failCount = 0;
                // 반복할 횟수가 reaptCount 이다. 2번이면 2번 반복. 0번째는 기본 예약이니.
                for(int i = 0; i <= repeatCount; i++){
                    if(i == 0){
                        List<ReserveDto> reserveList = reserveRepository.findByReserveDateOrderByReserveDate(request.getReserveDate()).stream().map(ReserveDto::new).collect(Collectors.toList());

                        if(request.isEnabledReservation(reserveList)){
                            reserveRepository.save(request.toEntity());
                        }else{
                            failCount++;
                        }
                    }else{
                        LocalDate reserveDate = request.getReserveDate();
                        reserveDate = reserveDate.plusWeeks(1);
                        request.setReserveDate(reserveDate);
                        List<ReserveDto> reserveList = reserveRepository.findByReserveDate(request.getReserveDate()).stream().map(ReserveDto::new).collect(Collectors.toList());

                        if(request.isEnabledReservation(reserveList)){
                            reserveRepository.save(request.toEntity());
                        }else{
                            failCount++;
                        }
                    }
                }

                if(failCount == (repeatCount + 1)){
                    builder.append("정기 회의 예약에 실패했습니다. \n 실패횟수 : ")
                            .append(failCount)
                            .append("회(기본 예약을 포함한 완전 실패)");

                    result.setResult(false);
                    result.setErrorCode(ErrorEnum.SET_DATA_ERROR.getErrorCode());
                    result.setMsg(builder.toString());
                }else if(failCount > 0){
                    builder.append("기존 예약된 회의가 있어 정기 회의 예약에 일부만 성공했습니다. \n 실패횟수 : ")
                            .append(failCount)
                            .append("회");

                    result.setResult(true);
                    result.setErrorCode(ErrorEnum.SET_PARTIAL_DATA_COMPLETE.getErrorCode());
                    result.setMsg(builder.toString());
                }else{
                    result.setResult(true);
                    result.setErrorCode(ErrorEnum.SET_DATA_COMPLETE.getErrorCode());
                    result.setMsg(ErrorEnum.SET_DATA_COMPLETE.getMessage());
                }
            }
        }

        return result;
    }

    @Override
    public ResultDto getReservation(LocalDate requestDate) {

        ResultDto result = new ResultDto();

        List<ReserveDto> reserveList = reserveRepository.findByReserveDateOrderByReserveDate(requestDate).stream().map(ReserveDto::new).collect(Collectors.toList());

        result.setResult(true);
        result.setMsg(ErrorEnum.GET_DATA_COMPLETE.getMessage());
        result.setErrorCode(ErrorEnum.GET_DATA_COMPLETE.getErrorCode());
        result.setReservationList(reserveList);


        return result;
    }

    private ResultDto validateReserveData(ReserveDto request, ResultDto result){
        result.setResult(true);

        if(request.getRepeatCount() < 0){
            result.setResult(false);
            result.setMsg(ErrorEnum.REPEAT_COUNT_ERROR.getMessage());
            result.setErrorCode(ErrorEnum.REPEAT_COUNT_ERROR.getErrorCode());
        }

        if(request.getStartTime().isAfter(request.getEndTime())){
            result.setResult(false);
            result.setMsg(ErrorEnum.TIME_BEFORE_AFTER_ERROR.getMessage());
            result.setErrorCode(ErrorEnum.TIME_BEFORE_AFTER_ERROR.getErrorCode());
        }

        if(request.getStartTime().equals(request.getEndTime())){
            result.setResult(false);
            result.setMsg(ErrorEnum.TIME_EQUALS_ERROR.getMessage());
            result.setErrorCode(ErrorEnum.TIME_EQUALS_ERROR.getErrorCode());
        }

        if(request.getReserveDate().isBefore(LocalDate.now())){
            result.setResult(false);
            result.setMsg(ErrorEnum.DATE_ERROR.getMessage());
            result.setErrorCode(ErrorEnum.DATE_ERROR.getErrorCode());
        }

        if(request.getStartTime().isBefore(LocalTime.of(6,00))
                || request.getEndTime().isBefore(LocalTime.of(6,00))){
            result.setResult(false);
            result.setMsg(ErrorEnum.TIME_LIMIT_ERROR.getMessage());
            result.setErrorCode(ErrorEnum.TIME_LIMIT_ERROR.getErrorCode());
        }

        if(request.getStartTime().isAfter(LocalTime.of(22,00))
                || request.getEndTime().isAfter(LocalTime.of(22,00))){
            result.setResult(false);
            result.setMsg(ErrorEnum.TIME_LIMIT_ERROR.getMessage());
            result.setErrorCode(ErrorEnum.TIME_LIMIT_ERROR.getErrorCode());
        }

        return result;
    }

}
