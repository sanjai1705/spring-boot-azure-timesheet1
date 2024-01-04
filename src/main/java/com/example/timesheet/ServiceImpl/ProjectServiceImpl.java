package com.example.timesheet.ServiceImpl;

import com.example.timesheet.Entity.ClientTable;
import com.example.timesheet.Entity.EmployeeTimeentries;
import com.example.timesheet.Entity.Project;
import com.example.timesheet.Entity.ProjectEmployee;
import com.example.timesheet.Respositories.EmployeeTimeentriesRespository;
import com.example.timesheet.Respositories.ProjectEmployeeRespository;
import com.example.timesheet.Respositories.ProjectRespository;
import com.example.timesheet.Service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    ProjectRespository projectRespository;



    @Autowired
    private ProjectEmployeeRespository projectEmployeeRepository;

    @Autowired
    private EmployeeTimeentriesRespository employeeTimeentriesRepository;

    @Override
    public List<Project> getAllProjects() {
        return projectRespository.findAll();
    }

    @Override
    public Project getProjectById(Integer projectId) {
        return projectRespository.findById(projectId).orElse(null);
    }

    @Override
    public Project createProject(Project project) {
        return projectRespository.save(project);
    }

    @Override
    public Project updateProject(Integer projectId, Project updatedProject) {
        if (projectRespository.existsById(projectId)) {
            updatedProject.setProjectId(projectId);
            return projectRespository.save(updatedProject);
        } else {
            return null;
        }
    }

    @Override
    public List<Project> applicableDays(boolean applicable){
        return projectRespository.findByapplicable(applicable);


        //throw new IllegalArgumentException("it is not applicable");
    }

    @Override
    public void deleteProject(Integer projectId) {
        projectRespository.deleteById(projectId);
    }

    @Override
    public List<Project> getProjectsByClient(ClientTable client) {
        return projectRespository.findByClient(client);
    }


    @Override
    public List<Project> getProjectsByClientId(Integer clientId) {
        return projectRespository.findByClientClientId(clientId);
    }

    @Override
    public List<ProjectEmployee> getProjectEmployeesByProjectId(Integer projectId) {
        return projectEmployeeRepository.findByProjectProjectId(projectId);
    }

    @Override
    public List<EmployeeTimeentries> getEmployeeTimeentriesByEmpIdAndDateRange(Integer empId, Date startDate, Date endDate) {
        return employeeTimeentriesRepository.findByProjectEmployeeEmpIDAndDateBetween(empId, startDate, endDate);
    }


}

