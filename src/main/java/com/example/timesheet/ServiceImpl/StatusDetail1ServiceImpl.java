package com.example.timesheet.ServiceImpl;

import com.example.timesheet.Entity.StatusDetail1;
import com.example.timesheet.Respositories.StatusDetail1Respository;
import com.example.timesheet.Respositories.StatusDetailRespository;
import com.example.timesheet.Service.StatusDetail1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatusDetail1ServiceImpl implements StatusDetail1Service {

    @Autowired
    StatusDetail1Respository statusDetail1Respository;
    @Override
    public StatusDetail1 getStatusDetail1ById(Integer id) {
        return statusDetail1Respository.findById(id).orElse(null);
    }

    @Override
    public List<StatusDetail1> getAllStatusDetails() {
        return statusDetail1Respository.findAll();
    }

    @Override
    public StatusDetail1 saveStatusDetail(StatusDetail1 statusDetail) {
        return statusDetail1Respository.save(statusDetail);
    }

    @Override
    public StatusDetail1 updateStatusDetail(Integer id, StatusDetail1 statusDetail) {
        Optional<StatusDetail1> existingStatusDetailOptional = statusDetail1Respository.findById(id);

        if (existingStatusDetailOptional.isPresent()) {
            StatusDetail1 existingStatusDetail = existingStatusDetailOptional.get();
            // Update fields as needed
            existingStatusDetail.setStatus(statusDetail.getStatus());
            existingStatusDetail.setTimestamp(statusDetail.getTimestamp());
            existingStatusDetail.setDate(statusDetail.getDate());

            return statusDetail1Respository.save(existingStatusDetail);
        } else {
            return null; // Handle not found scenario
        }
    }

    @Override
    public void deleteStatusDetail(Integer id) {
        statusDetail1Respository.deleteById(id);
    }

}
