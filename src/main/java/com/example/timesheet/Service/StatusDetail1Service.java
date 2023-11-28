package com.example.timesheet.Service;

import com.example.timesheet.Entity.StatusDetail1;

import java.util.List;

public interface StatusDetail1Service   {
    StatusDetail1 getStatusDetail1ById(Integer id);
    List<StatusDetail1> getAllStatusDetails();
    StatusDetail1 saveStatusDetail(StatusDetail1 statusDetail);
    StatusDetail1 updateStatusDetail(Integer id, StatusDetail1 statusDetail);
    void deleteStatusDetail(Integer id);


}
