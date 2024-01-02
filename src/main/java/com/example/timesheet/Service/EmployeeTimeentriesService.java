package com.example.timesheet.Service;

//import com.example.timesheet.Entity.EmployeeManager;
import com.example.timesheet.Entity.*;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface EmployeeTimeentriesService {
    List<EmployeeTimeentries> getAllEmployeeTimeentries();

    EmployeeTimeentries getEmployeeTimeentriesById(Integer timesheetId);



    EmployeeTimeentries saveEmployeeTimeentries(EmployeeTimeentries employeeTimeentries);


    EmployeeTimeentries getEmployeeByUserProjectCreate(EmployeeTimeentries employeeTimeentries);




    List<EmployeeTimeentries> getEntriesByUserAndEmployeeAndDate(Users user, ProjectEmployee projectEmployee, Date date);

    List<EmployeeTimeentries> getByUserId(Integer userId);


    List<EmployeeTimeentries> customDate(Users user, Date startdate, Date enddate);

    //List<EmployeeTimeentries> customDatee(ProjectEmployee projectEmployee, Users user, Date startdate, Date enddate);

    void updateRejectedstatus(Users user, Date date);

    EmployeeTimeentries updateEmployeeTimeentries(Integer id, EmployeeTimeentries employeeTimeentries);





    void deleteEmployeeTimeentries(Integer timesheetId);

    boolean isLoginLogoutOverlap(EmployeeTimeentries newEntry);

    List<EmployeeTimeentries> findByUserIdAndStatusIsNull(Integer userId);


    void submitTimesheetInRange(Users user, Integer startid, Integer endid);

    void approveTimesheetInRange(Integer timesheetId);


    void submitTimesheet(Users user, Date startdate, Date enddate);


    /*List<String> findCustomDateByProjectEmployeeAndUserAndDateRange(
            ProjectEmployee projectEmployee,
            Users user,
            String startDate,
            String endDate,
            String status
    ) ;*/



    void rejectTimesheetInRange(Integer timesheetId,String rejectionDescription);

    List<EmployeeTimeentries> findByUserIdAndStatusIsSubmitted(Users userId);




    List<Object[]> getStatusMessageAndTimeEntries(
            ProjectEmployee projectEmployee,
            Users user,
            Date startDate,
            Date endDate);



}



