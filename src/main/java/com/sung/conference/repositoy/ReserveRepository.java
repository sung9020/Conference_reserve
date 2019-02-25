package com.sung.conference.repositoy;

import com.sung.conference.entity.ReserveInfo_NEW;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReserveRepository extends CrudRepository<ReserveInfo_NEW,String> {

    List<ReserveInfo_NEW> findByReserveDate(LocalDate reserveDate);
}
