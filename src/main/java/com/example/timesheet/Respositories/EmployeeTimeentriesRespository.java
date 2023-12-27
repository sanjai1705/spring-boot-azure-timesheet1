package com.example.timesheet.Respositories;


import com.example.timesheet.Entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeTimeentriesRespository extends JpaRepository<EmployeeTimeentries,Integer> {


    List<EmployeeTimeentries> findByDate(Date date);
    List<EmployeeTimeentries> findByUserUserId(Integer userId);

    List<EmployeeTimeentries> findByUserAndDateBetween(Users user, Date startDate, Date endDate);

    List<EmployeeTimeentries> findByUserAndProjectEmployeeAndDate(Users user, ProjectEmployee projectEmployee, Date date);

    List<EmployeeTimeentries> findByUserAndDate(Users user, Date date);



   // EmployeeTimeentries findByUserAnddate(Users user, Date date);

    List<EmployeeTimeentries> findByUserAndStatusIsNull(Users user);

    List<EmployeeTimeentries> findByUserAndTimesheetIdBetween(Users user, Integer startid, Integer endid);

    List<EmployeeTimeentries> findByTimesheetId(Integer timesheetId);

    //List<EmployeeTimeentries> findByProjectEmployee(ProjectEmployee projectEmployee, Users user, Date startdate, Date enddate);


    //List<EmployeeTimeentries>findByUserAndTimesheetIdBetween(Users user, Integer startid, Integer endid);




    /*@Query("SELECT CASE WHEN e.status IN ('Approved', 'Submitted') THEN 'Status: ' || e.status " +
            "                WHEN e.status = 'Rejected' AND e.status IS NOT NULL THEN 'Status: Rejected' " +
            "                ELSE '' END AS statusMessage " +
            "FROM EmployeeTimeentries e " +
            "WHERE e.projectEmployee = :projectEmployee " +
            "AND e.user = :user " +
            "AND e.date BETWEEN :startDate AND :endDate " +
            "AND (:status IS NULL OR e.status = :status)")
    List<String> findCustomDateByProjectEmployeeAndUserAndDateRange(
            @Param("projectEmployee") ProjectEmployee projectEmployee,
            @Param("user") Users user,
            @Param("startDate") Date startDate,

            @Param("endDate")  Date endDate,
            @Param("status") String status


    );*/


    @Query("SELECT e FROM EmployeeTimeentries  e WHERE e.user =:userId AND e.status = 'Submitted'")
    List<EmployeeTimeentries> findByUserAndSubmit(
            @Param("userId") Users userId
    );




    @Query("SELECT e.status AS statusMessage, e FROM EmployeeTimeentries e " +
            "WHERE e.projectEmployee = :projectEmployee " +
            "AND e.user = :user " +
            "AND e.date BETWEEN :startDate AND :endDate")
    List<Object[]> getStatusMessageAndTimeEntries(
            @Param("projectEmployee") ProjectEmployee projectEmployee,
            @Param("user") Users user,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);



}
