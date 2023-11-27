package com.example.timesheet.ServiceImpl;

import com.example.timesheet.Entity.StatusDetail1;
import com.example.timesheet.Respositories.StatusDetail1Respository;
import com.example.timesheet.Respositories.StatusDetailRespository;
import com.example.timesheet.Service.StatusDetail1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusDetail1ServiceImpl implements StatusDetail1Service {

    @Autowired
    StatusDetail1Respository statusDetail1Respository;

}
