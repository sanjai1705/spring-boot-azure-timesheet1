package com.example.timesheet.Controller;

import com.example.timesheet.Entity.StatusDetail1;
import com.example.timesheet.Service.StatusDetail1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Timesheet")
public class StatusDetail1Controller {

    @Autowired
    private StatusDetail1Service statusDetail1Service;

    @GetMapping("/StatusDetail1/{id}")
    public StatusDetail1 getStatusDetailById(@PathVariable Integer id) {
        return statusDetail1Service.getStatusDetail1ById(id);
    }

    @GetMapping("/StatusDetail1")
    public List<StatusDetail1> getAllStatusDetails() {
        return statusDetail1Service.getAllStatusDetails();
    }

    @PostMapping("/StatusDetail1/Create")
    public StatusDetail1 saveStatusDetail(@RequestBody StatusDetail1 statusDetail) {
        return statusDetail1Service.saveStatusDetail(statusDetail);
    }

    @PutMapping("/StatusDetail1/Update/{id}")
    public StatusDetail1 updateStatusDetail(@PathVariable Integer id, @RequestBody StatusDetail1 statusDetail) {
        return statusDetail1Service.updateStatusDetail(id, statusDetail);
    }

    @DeleteMapping("/StatusDetail1/Delete/{id}")
    public void deleteStatusDetail(@PathVariable Integer id) {
        statusDetail1Service.deleteStatusDetail(id);
    }


}
