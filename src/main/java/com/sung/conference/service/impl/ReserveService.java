package com.sung.conference.service.impl;

import com.sung.conference.entity.ReserveInfo_NEW;
import com.sung.conference.repositoy.ReserveRepository;
import com.sung.conference.service.ReserveInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReserveService implements ReserveInterface {

    @Autowired
    ReserveRepository reserveRepository;

    @Override
    @Transactional
    public void setReserve(ReserveInfo_NEW reserveInfo) {

    }

    @Override
    public void getReserve() {

    }
}
