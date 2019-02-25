package com.sung.conference.repositoy;

import com.sung.conference.entity.ReserveInfo;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.stream.Stream;

public interface ReserveRepository extends CrudRepository<ReserveInfo,String> {

    Stream<ReserveInfo> findByReserveDate(LocalDate reserveDate);
}
