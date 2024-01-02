package com.example.timesheet.Respositories;

import com.example.timesheet.Entity.ClientTable;
import com.example.timesheet.Entity.EmployeeTimeentries;
import com.example.timesheet.Entity.Project;
import com.example.timesheet.Entity.ProjectEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ProjectEmployeeRespository extends JpaRepository<ProjectEmployee,Integer> {

     List<ProjectEmployee> findByUserUserId(Integer userId);
    List<ProjectEmployee> findByProjectProjectId(Integer projectId);

    List<ProjectEmployee> findByProject_ProjectIdAndProjectStartDateBetween(Integer projectId, Date startDate, Date endDate);



}
