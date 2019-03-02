package com.sung.conference.repositoy;

import com.sung.conference.entity.ReserveInfo;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReserveRepository extends CrudRepository<ReserveInfo,String> {

    List<ReserveInfo> findByReserveDate(LocalDate reserveDate);

    List<ReserveInfo> findByReserveDateOrderByReserveDate(LocalDate reserveDate);
}
