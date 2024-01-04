package com.example.timesheet.Service;

import com.example.timesheet.Entity.ClientTable;
import com.example.timesheet.Entity.EmployeeTimeentries;
import com.example.timesheet.Entity.Project;
import com.example.timesheet.Entity.ProjectEmployee;

import java.util.Date;
import java.util.List;

public interface ProjectService {

    List<Project> getAllProjects();
    Project getProjectById(Integer projectId);
    Project createProject(Project project);
    Project updateProject(Integer projectId, Project project);


    List<Project> applicableDays(boolean applicable);

    void deleteProject(Integer projectId);

    List<Project> getProjectsByClient(ClientTable client);


    List<Project> getProjectsByClientId(Integer clientId);
    List<ProjectEmployee> getProjectEmployeesByProjectId(Integer projectId);
    List<EmployeeTimeentries> getEmployeeTimeentriesByEmpIdAndDateRange(Integer empId, Date startDate, Date endDate);




}
